package edu.uw.w34ma.processing;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.parser.common.ParserQuery;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreebankLanguagePack;
import edu.uw.w34ma.models.*;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.*;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 26/03/17.
 */
public class Processor {
    private final LexicalizedParser parser;
    private final TreebankLanguagePack tlp;
    private final String encoding;
    private final Lemmatizer lemmatizer = new Lemmatizer();
    private final WordNet wordNet = new WordNet();
    private final BehaviourFinder behaviourFinder = new BehaviourFinder(wordNet, lemmatizer);
    private final IdentityFinder identityFinder = new IdentityFinder();
    private final EmotionEvaluator evaluator = new InteractEmotionEvaluator(identityFinder, behaviourFinder);
    private final EmotionFinder emotionFinder = new EmotionFinder(wordNet, lemmatizer);
    private final EmojiFinder emojiFinder = new EmojiFinder(emotionFinder);

    private BookCharacter lastActioner = null;
    private BookCharacter lastActionee = null;

    private static final Set<String> PEOPLE = new HashSet<>(Arrays.asList(
            "NN", "NNS", "NNP", "NNPS"
    ));

    private static final Set<String> BEHAVIOUR = new HashSet<>(Arrays.asList(
        "VB", "VBD", "VBN", "VBP", "VBZ"
    ));

    private static final Set<String> STOPPER = new HashSet<>(Arrays.asList(
            "NN", "NNS", "NNP", "NNPS", "DT"
    ));

    private static final Set<String> BES = new HashSet<>(Arrays.asList(
            "be", "is", "am", "are", "get", "become", "feel"
    ));

    private static final Set<String> PRP = new HashSet<>(Arrays.asList(
            "he", "she"
    ));

    public Processor() {
        // load stanford parser
        this.parser = LexicalizedParser.loadModelFromZip(
                "lib/stanford-parser-3.7.0-models.jar",
                "edu/stanford/nlp/models/lexparser/englishRNN.ser.gz");
        this.tlp = parser.getOp().langpack();
        this.encoding = tlp.getEncoding();
    }

    private String stringify(Map<BookCharacter, EmotionResult> emotions) {
        StringBuilder sb = new StringBuilder();
        for (EmotionResult emotion : emotions.values()) {
            sb.append(emotion.toString()).append(" ");
        }
        return sb.toString();
    }

    public ProcessResult process(File book, List<BookCharacter> characters) {
        String content;
        try {
            content = new String(Files.readAllBytes(book.toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Unable to read book content", e);
        }

        List<String> sentences = PreProcessor.findSentences(content);
        List<BaseEvent> events = analyze(sentences, characters);
        List<Map<BookCharacter, EmotionResult>> emotions;
        try {
            emotions = evaluator.evaluate(characters, events);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to process book", e);
        }
        List<Map<String, String>> emojis = new ArrayList<>();
        for (Map<BookCharacter, EmotionResult> emotion : emotions) {
            Map<String, String> emoji = new HashMap<>();
            for (EmotionResult result : emotion.values()) {
                emoji.put(result.character.name, emojiFinder.findEmoji(result).label);
            }

            emojis.add(emoji);
        }

        List<TableEntry> entries = new ArrayList<>();
        for (int i = 0; i < sentences.size(); i++) {
            String sentence = sentences.get(i);
            String event = events.get(i) == null ? "" : events.get(i).toString();
            String emotion = stringify(emotions.get(i));
            entries.add(new TableEntry(sentence, event, emotion));
        }



        return new ProcessResult(entries, emojis);
    }

    private List<BaseEvent> analyze(List<String> sentences, List<BookCharacter> characters) {
        List<BaseEvent> events = new ArrayList<>();
        boolean isSpeaking = false;
        for (int i = 0; i < sentences.size(); i++) {
            System.out.println("Process sentence " + i);
            String sentence = sentences.get(i);
            Tree tree = parse(sentence);
            int quotes = 0;
            boolean hasPRP = false;
            for (Character c : sentence.toCharArray()) {
                if (c == '"') {
                    quotes++;
                }
            }
            if (quotes == 0) {
                if (isSpeaking) {
                    BookCharacter speaker = lastActioner;
                    BookCharacter listener = lastActionee;
                    if (listener == null || listener == speaker) {
                        listener = getOther(characters, speaker);
                    }
                    ACTAction action = behaviourFinder.findCommunication(tree);
                    events.add(createEvent(lastActioner, lastActionee, action));
                    continue;
                }
                // not saying anything
                BookCharacter actioner = null;
                BookCharacter actionee = null;
                boolean needSwitch = false;
                boolean isAssignment = false;
                String adjective = null;
                List<String> verbs = new ArrayList<>();
                for (int j = 0; j < tree.getLeaves().size(); j++) {
                    Tree leaf = tree.getLeaves().get(j);
                    String text = leaf.value();
                    String label = leaf.ancestor(1, tree).label().value();
                    if (PEOPLE.contains(label)) {
                        BookCharacter match = null;
                        for (BookCharacter c : characters) {
                            if (c.name.toLowerCase().equals(text.toLowerCase())) {
                                if (actioner == null) {
                                    actioner = c;
                                } else {
                                    actionee = c;
                                }
                            }
                        }
                    } else if (BEHAVIOUR.contains(label)) {
                        if (BES.contains(lemmatizer.lemmatize(text))) {
                            isAssignment = true;
                        }
                        if ((label.equals("VBN") || label.equals("VBD")) && j > 0) {
                            Tree previousWord = tree.getLeaves().get(j - 1);
                            String previousLabel = previousWord.ancestor(1, tree).label().value();
                            if (BEHAVIOUR.contains(previousLabel)
                                    && BES.contains(lemmatizer.lemmatize(previousWord.value()).toLowerCase())) {
                                needSwitch = true;
                            }
                        }

                        String phrase = lemmatizer.lemmatize(text).toLowerCase();
                        verbs.add(phrase);
                        if (j + 1 < tree.getLeaves().size()) {
                            Tree next = tree.getLeaves().get(j + 1);
                            String nextText = next.value();
                            verbs.add(phrase + " " + nextText);
                        }
                    } else if (PRP.contains(lemmatizer.lemmatize(text).toLowerCase())) {
                        hasPRP = true;
                    } else if (label.equals("JJ")) {
                        adjective = lemmatizer.lemmatize(text);
                    }
                }

                if (actioner == null && hasPRP) {
                    actioner = lastActioner;
                }

                if (actioner != null && isAssignment && adjective != null) {
                    // this is an emotion assignment event
                    ACTEmotion found = emotionFinder.find(adjective);
                    if (found != null) {
                        double E = actioner.gender == BookCharacter.Gender.MALE ? found.EM : found.EF;
                        double P = actioner.gender == BookCharacter.Gender.MALE ? found.PM : found.PF;
                        double A = actioner.gender == BookCharacter.Gender.MALE ? found.AM : found.AF;
                        events.add(new EmotionEvent(actioner, found.label, E, P, A));
                    } else {
                        events.add(null);
                    }
                    continue;
                }

                if (actioner != null && actionee == null) {
                    actionee = lastActionee;
                }

                if (needSwitch && actionee != null) {
                    BookCharacter temp = actionee;
                    actionee = actioner;
                    actioner = temp;
                }
                events.add(createEvent(actioner, actionee, verbs));
            } else {
                String speech = getSpeech(sentence, isSpeaking);
                String nonSpeech = getNonSpeech(sentence, isSpeaking);
                if (quotes % 2 == 1) {
                    isSpeaking = !isSpeaking;
                }

                if (nonSpeech.length() == 0) {
                    BookCharacter speaker = lastActioner;
                    BookCharacter listener = lastActionee;
                    if (listener == null || listener == speaker) {
                        listener = getOther(characters, speaker);
                    }
                    ACTAction action = behaviourFinder.findCommunication(parse(speech));
                    events.add(createEvent(lastActioner, lastActionee, action));
                    continue;
                }

                tree = parse(nonSpeech);

                BookCharacter speaker = null;
                BookCharacter listener = null;
                boolean needSwap = false;
                int nameIndex = -1;
                List<String> verbs = new ArrayList<>();
                for (int j = 0; j < tree.getLeaves().size(); j++) {
                    Tree leaf = tree.getLeaves().get(j);
                    String text = leaf.value();
                    String label = leaf.ancestor(1, tree).label().value();
                    if (PEOPLE.contains(label)) {
                        for (BookCharacter c : characters) {
                            if (c.name.toLowerCase().equals(text.toLowerCase())) {
                                if (speaker == null) {
                                    speaker = c;
                                    nameIndex = j;
                                }
                            }
                        }
                    } else if (BEHAVIOUR.contains(label) && label.equals("VBD")) {
                        needSwap = true;
                    }
                }
                if (nameIndex > 0) {
                    Tree prev = tree.getLeaves().get(nameIndex - 1);
                    String prevText = prev.value();
                    String prevLabel = prev.ancestor(1, tree).label().value();
                    if (BEHAVIOUR.contains(prevLabel)) {
                        verbs.add(lemmatizer.lemmatize(prevText));
                    }
                }
                if (nameIndex > 0 && nameIndex < tree.getLeaves().size() - 1) {
                    Tree next = tree.getLeaves().get(nameIndex + 1);
                    String nextText = next.value();
                    String nextLabel = next.ancestor(1, tree).label().value();
                    if (BEHAVIOUR.contains(nextLabel)) {
                        verbs.add(lemmatizer.lemmatize(nextLabel));
                    }
                }

                if (speaker == null && nameIndex < 0 && needSwap) {
                    speaker = lastActionee;
                }

                ACTAction action = null;
                if (verbs.size() > 0) {
                    action = behaviourFinder.findCommunication(verbs);
                }
                if (action == null) {
                    tree = parse(speech);
                    action = behaviourFinder.findCommunication(tree);
                }

                if (lastActionee != speaker) {
                    listener = lastActionee;
                } else {
                    listener = lastActioner;
                }

                if (listener == null || listener == speaker) {
                    listener = getOther(characters, speaker);
                }

                events.add(createEvent(speaker, listener, action));

            }
        }
        return events;
    }

    private ActionEvent createEvent(BookCharacter actioner, BookCharacter actionee, ACTAction action) {
        if (actioner == null && actionee == null) {
            return null;
        } else if (action != null) {
            if (actioner == null) {
                actioner = actionee;
                actionee = null;
            }
            if (actioner == actionee) {
                actionee = null;
            }
            lastActioner = actioner;
            lastActionee = actionee;
            return new ActionEvent(actioner, actionee, action);

        } else {
            return null;
        }
    }

    private ActionEvent createEvent(BookCharacter actioner, BookCharacter actionee, List<String> verbs) {
        ACTAction action = behaviourFinder.find(verbs);
        return createEvent(actioner, actionee, action);
    }

    private BookCharacter getOther(List<BookCharacter> characters, BookCharacter one) {
        for (BookCharacter character : characters) {
            if (!character.name.equals(one.name)) {
                return character;
            }
        }
        return null;
    }

    private String getSpeech(String sentence, boolean isSpeaking) {
        boolean recording = isSpeaking;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sentence.length(); i++) {
            if (sentence.charAt(i) == '"') {
                recording = !recording;
            } else if (recording) {
                sb.append(sentence.charAt(i));
            }
        }

        return sb.toString().replace("\"", "").trim();
    }

    private String getNonSpeech(String sentence, boolean isSpeaking) {
        boolean recording = isSpeaking;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sentence.length(); i++) {
            if (sentence.charAt(i) == '"') {
                recording = !recording;
            } else if (!recording) {
                sb.append(sentence.charAt(i));
            }
        }

        return sb.toString().replace("\"", "").trim();
    }

    private Tree parse(String sentence) {
        Tokenizer<? extends HasWord> toke = tlp.getTokenizerFactory().getTokenizer(new StringReader(sentence));
        List<? extends HasWord> wordList = toke.tokenize();
        ParserQuery query = parser.parserQuery();
        query.parse(wordList);
        return query.getBestParse();
    }
}

package edu.uw.w34ma.processing;

import edu.mit.jwi.item.POS;
import culture.Behaviors;
import edu.stanford.nlp.trees.Tree;
import edu.uw.w34ma.models.ACTAction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 30/03/17.
 */
public class BehaviourFinder {
    private static final String SWN = "swn.txt";
    public static final double DECAY = 0.8;
    public static final double START_SCORE = 1;
    private final Map<String, ACTAction> actions;
    private final Map<String, Double> positivities;
    private double minNegativity = 0;
    private double maxPositivity = 0;
    private double minBadness = 0;
    private double maxGoodness = 0;
    private final WordNet wordNet;
    private final Lemmatizer lemmatizer;
    private final Map<ACTAction, List<String>> synsets = new HashMap<>();
    private final Map<Double, ACTAction> communications = new LinkedHashMap<>(); // ranked by score from high to low

    private static final Set<String> VERBS = new HashSet<>(Arrays.asList(
            "VB", "VBD", "VBN", "VBP", "VBZ"
    ));

    private static final Set<String> NOUNS = new HashSet<>(Arrays.asList(
            "NN", "NNS", "NNP", "NNPS", "PRP"
    ));

    private static final Set<String> ADJECTIVES = new HashSet<>(Arrays.asList(
            "JJ"
    ));

    private static final Set<String> ADVERBS = new HashSet<>(Arrays.asList(
            "RB"
    ));


    public BehaviourFinder(WordNet wordNet, Lemmatizer lemmatizer) {
        this.actions = loadActions();
        this.wordNet = wordNet;
        this.lemmatizer = lemmatizer;
        this.positivities = loadPositivities();
        generateSynsets();
        prepareCommunications();
    }

    private Map<String, Double> loadPositivities() {
        Map<String, Double> result = new HashMap<>();

        try {
            Path p = new File(getClass().getClassLoader().getResource(SWN).toURI()).toPath();
            List<String> lines = Files.readAllLines(p);
            for (String line : lines) {
                String[] tokens = line.replace("\t", " ").split(" ");
                if (tokens[0].equals("#")) {
                    continue;
                }
                double score = Double.parseDouble(tokens[2]) - Double.parseDouble(tokens[3]);

                if (score < minNegativity) {
                    minNegativity = score;
                }

                if (score > maxPositivity) {
                    maxPositivity = score;
                }

                for (int i = 4; i < tokens.length; i++) {
                    String token = tokens[i];
                    if (token.contains("#")) {
                        result.put(tokens[0] + "#" + token, score);
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to load positivities", e);
        }
        return result;
    }

    private void generateSynsets() {
        for (ACTAction action : actions.values()) {
            List<String> synset = new ArrayList<>();
            synset.addAll(wordNet.getSynonyms(action.label, POS.VERB));
            synsets.put(action, synset);
        }
    }

    private void prepareCommunications() {
        communications.put(getPositivity(actions.get("accuse")), actions.get("accuse"));
        communications.put(getPositivity(actions.get("address")), actions.get("address"));
        //communications.put(getPositivity(actions.get("advise")), actions.get("advise"));
        communications.put(getPositivity(actions.get("answer")), actions.get("answer"));
        communications.put(getPositivity(actions.get("argue with")), actions.get("argue with"));
        communications.put(getPositivity(actions.get("ask about something")), actions.get("ask about something"));
        communications.put(getPositivity(actions.get("address")), actions.get("address"));
        communications.put(getPositivity(actions.get("babble to")), actions.get("babble to"));
        //communications.put(getPositivity(actions.get("banter with")), actions.get("banter with"));
        communications.put(getPositivity(actions.get("bash")), actions.get("bash"));
        communications.put(getPositivity(actions.get("beg")), actions.get("beg"));
        //communications.put(getPositivity(actions.get("bicker with")), actions.get("bicker with"));
        communications.put(getPositivity(actions.get("blame")), actions.get("blame"));
        communications.put(getPositivity(actions.get("brief")), actions.get("brief"));
        communications.put(getPositivity(actions.get("chat up")), actions.get("chat up"));
        communications.put(getPositivity(actions.get("chat with")), actions.get("chat with"));
        communications.put(getPositivity(actions.get("chatter to")), actions.get("chatter to"));
        communications.put(getPositivity(actions.get("chitchat with")), actions.get("chitchat with"));
        communications.put(getPositivity(actions.get("converse with")), actions.get("converse with"));
        communications.put(getPositivity(actions.get("criticize")), actions.get("criticize"));
        //communications.put(getPositivity(actions.get("dicker with")), actions.get("dicker with"));
        // communications.put(getPositivity(actions.get("greet")), actions.get("greet"));
        communications.put(getPositivity(actions.get("inform")), actions.get("inform"));
        communications.put(getPositivity(actions.get("lie to")), actions.get("lie to"));
        communications.put(getPositivity(actions.get("question")), actions.get("question"));
        communications.put(getPositivity(actions.get("quarrel with")), actions.get("quarrel with"));
        communications.put(getPositivity(actions.get("reply to")), actions.get("reply to"));
        communications.put(getPositivity(actions.get("shout at")), actions.get("shout at"));
        communications.put(getPositivity(actions.get("sing to")), actions.get("sing to"));
        communications.put(getPositivity(actions.get("speak to")), actions.get("speak to"));
        communications.put(getPositivity(actions.get("talk down to")), actions.get("talk down to"));
        communications.put(getPositivity(actions.get("talk to")), actions.get("talk to"));
        communications.put(getPositivity(actions.get("warn")), actions.get("warn"));
        communications.put(getPositivity(actions.get("whine to")), actions.get("whine to"));
        communications.put(getPositivity(actions.get("whisper to")), actions.get("whisper to"));
        communications.put(getPositivity(actions.get("yell at")), actions.get("yell at"));
    }

    private double getPositivity(ACTAction action) {
        double E = (action.EM + action.EF) / 2;
        if (E > maxGoodness) {
            maxGoodness = E;
        }
        if (E < minBadness) {
            minBadness = E;
        }
        return E;
    }

    private Map<String, ACTAction> loadActions() {
        Map<String, ACTAction> actions = new HashMap<>();

        String[] actionData = (String[]) new Behaviors().getContents()[1][1];
        String label = null;
        double EM = 0f, PM = 0f, AM = 0f, EF = 0f, PF = 0f, AF = 0f;
        for (int i = 0; i < actionData.length; i++) {
            if (i % 8 == 0) {
                label = actionData[i];
            } else if (i % 8 == 1) {
                EM = Double.parseDouble(actionData[i]);
            } else if (i % 8 == 2) {
                PM = Double.parseDouble(actionData[i]);
            } else if (i % 8 == 3) {
                AM = Double.parseDouble(actionData[i]);
            } else if (i % 8 == 4) {
                EF = Double.parseDouble(actionData[i]);
            } else if (i % 8 == 5) {
                PF = Double.parseDouble(actionData[i]);
            } else if (i % 8 == 6) {
                AF = Double.parseDouble(actionData[i]);
            } else {
                actions.put(label, new ACTAction(label, EM, PM, AM, EF, PF, AF));
            }
        }

        return actions;
    }

    public ACTAction findByLabel(String label) {
        return actions.getOrDefault(label, null);
    }

    public ACTAction findCommunication(Tree sentence) {
        double score = 0;
        for (int i = 0; i < sentence.getLeaves().size(); i++) {
            Tree leaf = sentence.getLeaves().get(i);
            String text = lemmatizer.lemmatize(leaf.value());
            String label = leaf.ancestor(1, sentence).label().value();
            List<String> synsets;
            if (NOUNS.contains(label)) {
                synsets = wordNet.getSynsets(text, POS.NOUN);
            } else if (VERBS.contains(label)) {
                synsets = wordNet.getSynsets(text, POS.VERB);
            } else if (ADJECTIVES.contains(label)) {
                synsets = wordNet.getSynsets(text, POS.ADJECTIVE);
            } else if (ADVERBS.contains(label)) {
                synsets = wordNet.getSynsets(text, POS.ADVERB);
            } else {
                continue;
            }
            for (String key : synsets) {
                Double localScore = positivities.getOrDefault(key, null);
                if (localScore != null) {
                    score += localScore;
                    break;
                }
            }
        }

        double normalizedScore = (score - minNegativity) * (maxGoodness - minBadness)
                / (maxPositivity - minNegativity) + minBadness;
        return findCommunication(normalizedScore);
    }

    public ACTAction findCommunication(double positivity) {
        double distance = Double.MAX_VALUE;
        ACTAction match = null;

        for (double score : communications.keySet()) {
            double localDistance = Math.abs(positivity - score);
            if (localDistance < distance) {
                distance = localDistance;
                match = communications.get(score);
            }
        }

        return match;
    }

    public ACTAction findCommunication(List<String> verbs) {
        // verbs = verbs.stream().flatMap(v -> Arrays.stream(v.split(" "))).collect(Collectors.toList());
        double maxScore = 0;
        ACTAction match = null;
        for (ACTAction action : communications.values()) {
            List<String> synonyms = synsets.get(action);
            double localScore = 0;
            for (int i = 0; i < verbs.size(); i++) {
                double nextScore = START_SCORE * Math.pow(DECAY, i);
                List<String> extendedVerbs = wordNet.getSynonyms(verbs.get(i), POS.VERB);
                for (int j = 0; j < extendedVerbs.size(); j++) {
                    nextScore = nextScore * Math.pow(DECAY, j);
                    for (int k = 0; k < synonyms.size(); k++) {
                        if (synonyms.get(k).equals(extendedVerbs.get(j))) {
                            localScore += nextScore * Math.pow(DECAY, k);
                        }
                    }
                }
            }

            if (localScore > maxScore) {
                maxScore = localScore;
                match = action;
            }
        }

        return match;
    }

    public ACTAction find(List<String> verbs) {
        double maxScore = 0;
        ACTAction match = null;
        for (ACTAction action : synsets.keySet()) {
            List<String> synonyms = synsets.get(action);
            double localScore = 0;
            for (int i = 0; i < verbs.size(); i++) {
                double nextScore = START_SCORE * Math.pow(DECAY, i);
                List<String> extendedVerbs = wordNet.getSynonyms(verbs.get(i), POS.VERB);
                for (int j = 0; j < extendedVerbs.size(); j++) {
                    nextScore = nextScore * Math.pow(DECAY, j);
                    for (int k = 0; k < synonyms.size(); k++) {
                        if (synonyms.get(k).equals(extendedVerbs.get(j))) {
                            localScore += nextScore * Math.pow(DECAY, k);
                        }
                    }
                }
            }

            if (localScore > maxScore) {
                maxScore = localScore;
                match = action;
            }
        }

        return match;
    }
}

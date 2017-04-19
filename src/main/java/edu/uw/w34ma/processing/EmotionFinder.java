package edu.uw.w34ma.processing;

import culture.Modifiers;
import edu.mit.jwi.item.POS;
import edu.uw.w34ma.models.ACTEmotion;
import edu.uw.w34ma.models.Emoji;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.uw.w34ma.processing.BehaviourFinder.DECAY;
import static edu.uw.w34ma.processing.BehaviourFinder.START_SCORE;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 05/04/17.
 */
public class EmotionFinder {
    private final Map<String, ACTEmotion> emotions;
    private final WordNet wordNet;
    private final Lemmatizer lemmatizer;
    private final Map<ACTEmotion, List<String>> synsets = new HashMap<>();

    public EmotionFinder(WordNet wordNet, Lemmatizer lemmatizer) {
        this.emotions = loadEmotions();
        this.wordNet = wordNet;
        this.lemmatizer = lemmatizer;
        generateSynsets();
    }

    private Map<String, ACTEmotion> loadEmotions() {
        Map<String, ACTEmotion> emotions = new HashMap<>();

        String[] identityData = (String[]) new Modifiers().getContents()[1][1];
        String label = null;
        double EM = 0f, PM = 0f, AM = 0f, EF = 0f, PF = 0f, AF = 0f;
        for (int i = 0; i < identityData.length; i++) {
            if (i % 8 == 0) {
                label = identityData[i];
            } else if (i % 8 == 1) {
                EM = Double.parseDouble(identityData[i]);
            } else if (i % 8 == 2) {
                PM = Double.parseDouble(identityData[i]);
            } else if (i % 8 == 3) {
                AM = Double.parseDouble(identityData[i]);
            } else if (i % 8 == 4) {
                EF = Double.parseDouble(identityData[i]);
            } else if (i % 8 == 5) {
                PF = Double.parseDouble(identityData[i]);
            } else if (i % 8 == 6) {
                AF = Double.parseDouble(identityData[i]);
            } else {
                emotions.put(label, new ACTEmotion(label, EM, PM, AM, EF, PF, AF));
            }
        }
        return emotions;
    }

    private void generateSynsets() {
        for (ACTEmotion emotion : emotions.values()) {
            List<String> synset = new ArrayList<>();
            synset.addAll(wordNet.getSynonyms(emotion.label, POS.ADJECTIVE));
            synsets.put(emotion, synset);
        }
    }

    public ACTEmotion findByLabel(String label) {
        return emotions.getOrDefault(label, null);
    }

    public Emoji createEmojiFromLabel(String label) {
        ACTEmotion emotion = emotions.getOrDefault(label, null);
        if (emotion == null) {
            return null;
        } else {
            return new Emoji(label, emotion.EM, emotion.PM, emotion.AM, emotion.EF, emotion.PF, emotion.AF);
        }
    }

    public ACTEmotion find(String adj) {
        double maxScore = 0;
        ACTEmotion match = null;

        for (ACTEmotion emotion : synsets.keySet()) {
            List<String> synonyms = synsets.get(emotion);
            double localScore = 0;
            List<String> extendedAdjectives = wordNet.getSynonyms(adj, POS.ADJECTIVE);
            for (int i = 0; i < extendedAdjectives.size(); i++) {
                double nextScore = START_SCORE * Math.pow(DECAY, i);
                for (int j = 0; j < synonyms.size(); j++) {
                    if (synonyms.get(j).equals(extendedAdjectives.get(i))) {
                        localScore += nextScore * Math.pow(DECAY, j);
                    }
                }
            }

            if (localScore > maxScore) {
                maxScore = localScore;
                match = emotion;
            }
        }

        return match;
    }
}

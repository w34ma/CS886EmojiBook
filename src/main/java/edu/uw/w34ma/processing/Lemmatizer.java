package edu.uw.w34ma.processing;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.util.CoreMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 29/03/17.
 */
public class Lemmatizer {
    private final StanfordCoreNLP pipeline;
    public Lemmatizer() {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");
        this.pipeline = new StanfordCoreNLP(props);
    }

    public String lemmatize(String word) {
        Annotation document = new Annotation(word);
        pipeline.annotate(document);
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        List<String> lemmas = new ArrayList<>();
        for (CoreMap sentence : sentences) {
            for (CoreLabel label : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                lemmas.add(label.get(CoreAnnotations.LemmaAnnotation.class));
            }
        }
        if (lemmas.size() < 1) {
            return null;
        } else {
            return lemmas.get(0);
        }
    }
}

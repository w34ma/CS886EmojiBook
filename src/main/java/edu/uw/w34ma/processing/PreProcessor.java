package edu.uw.w34ma.processing;

import edu.stanford.nlp.ling.HasOffset;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.PennTreebankLanguagePack;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 26/03/17.
 */
public class PreProcessor {
    // constants for finding nearest sentence boundary
    private static final int SEEK_FORWARD = 1;
    private static final int SEEK_BACK = -1;

    public static List<String> findSentences(String content) {
        // first do some basic clean up
        content = content.replace("\r", " ").replace("\n", " ").replace("``", "\"").replace("''", "\"")
                .replace("’", "'").replace("“", "\"").replace("”", "\"").replace("‘", "'").trim();
        // use stanford parser to find sentences
        int startIndex = 0;
        int endIndex = 0;
        List<String> sentences = new ArrayList<>();
        while (endIndex < content.length() - 1) {
            startIndex = nearestDelimiter(content, endIndex + 1, SEEK_BACK) + 1;
            endIndex = nearestDelimiter(content, startIndex, SEEK_FORWARD);
            if (endIndex == -1 || endIndex == content.length() - 1) {
                endIndex = content.length();
            }
            sentences.add(content.substring(startIndex, endIndex));
        }

        return sentences;
        //return merge(sentences);
    }

    private static List<String> merge(List<String> sentences) {
        List<String> merged = new ArrayList<>();
        int index = 0;
        while (index < sentences.size()) {
            String sentence = sentences.get(index);
            boolean extraQuote = false;
            for (int i = 0; i < sentence.length(); i++) {
                if (sentence.charAt(i) == '"') {
                    extraQuote = !extraQuote;
                }
            }
            if (extraQuote) {

            } else {
                merged.add(sentence);
                index++;
            }
        }

        return merged;
    }

    /**
     * Finds the nearest delimiter starting from index start. If <tt>seekDir</tt>
     * is SEEK_FORWARD, finds the nearest delimiter after start.  Else, if it is
     * SEEK_BACK, finds the nearest delimiter before start.
     */
    private static int nearestDelimiter(String text, int start, int seekDir) {
        PennTreebankLanguagePack tlp = new PennTreebankLanguagePack();

        if (seekDir != SEEK_BACK && seekDir != SEEK_FORWARD) {
            throw new IllegalArgumentException("Unknown seek direction " +
                    seekDir);
        }
        StringReader reader = new StringReader(text);
        DocumentPreprocessor processor = new DocumentPreprocessor(reader);
        TokenizerFactory<? extends HasWord> tf = tlp.getTokenizerFactory();
        processor.setTokenizerFactory(tf);
        List<Integer> boundaries = new ArrayList<>();
        for (List<HasWord> sentence : processor) {
            if (sentence.size() == 0)
                continue;
            if (!(sentence.get(0) instanceof HasOffset)) {
                throw new ClassCastException("Expected HasOffsets from the " +
                        "DocumentPreprocessor");
            }
            if (boundaries.size() == 0) {
                boundaries.add(0);
            } else {
                HasOffset first = (HasOffset) sentence.get(0);
                boundaries.add(first.beginPosition());
            }
        }
        boundaries.add(text.length());
        for (int i = 0; i < boundaries.size() - 1; ++i) {
            if (boundaries.get(i) <= start && start < boundaries.get(i + 1)) {
                if (seekDir == SEEK_BACK) {
                    return boundaries.get(i) - 1;
                } else {
                    return boundaries.get(i + 1) - 1;
                }
            }
        }
        // The cursor position at the end is actually one past the text length.
        // We might as well highlight the last interval in that case.
        if (boundaries.size() >= 2 && start >= text.length()) {
            if (seekDir == SEEK_BACK) {
                return boundaries.get(boundaries.size() - 2) - 1;
            } else {
                return boundaries.get(boundaries.size() - 1) - 1;
            }
        }
        return -1;
    }
}

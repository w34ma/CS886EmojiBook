package edu.uw.w34ma.processing;

import edu.mit.jwi.RAMDictionary;
import edu.mit.jwi.data.ILoadPolicy;
import edu.mit.jwi.item.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 29/03/17.
 */
public class WordNet {
    private final RAMDictionary dict;
    public WordNet() {
        this.dict = new RAMDictionary(new File("lib/dict"), ILoadPolicy.IMMEDIATE_LOAD);
        try {
            dict.open();
        } catch (IOException e) {
            System.out.println("Unable to open word net dictionary");
            System.exit(1);
        }
    }

    public List<String> getSynsets(String word, POS pos) {
        word = word.replace("-", " ").replace("_", " ");
        IIndexWord idxWord = dict.getIndexWord(word.toLowerCase(), pos);

        if (idxWord == null && word.contains(" ")) {
            String[] tokens = word.split(" ");
            for (String token : tokens) {
                idxWord = dict.getIndexWord(token, pos);
                if (idxWord != null) break;
            }

        }

        if (idxWord == null) {
            return new ArrayList<>();
        }

        List<String> result = new ArrayList<>();
        for (IWordID wid : idxWord.getWordIDs()) {
            result.add(parse(wid));
        }

        return result;
    }

    private String parse(IWordID wid) {
        String header = wid.toString();
        String[] tokens = header.split("-");
        return tokens[2].toLowerCase() + "#" + tokens[4].toLowerCase() + "#"
                + (tokens[3].startsWith("0") ? tokens[3].substring(1) : tokens[3]);
    }

    public List<String> getSynonyms(String word, POS pos) {
        word = word.replace("-", " ").replace("_", " ");
        IIndexWord idxWord = dict.getIndexWord(word.toLowerCase(), pos);

        if (idxWord == null && word.contains(" ")) {
            String[] tokens = word.split(" ");
            for (String token : tokens) {
                idxWord = dict.getIndexWord(token, pos);
                if (idxWord != null) break;
            }

        }

        if (idxWord == null) {
            return Arrays.asList(word);
        }

        Set<String> result = new LinkedHashSet<>();
        result.add(word);
        for (IWordID wid : idxWord.getWordIDs()) {
            ISynset synset = dict.getWord(wid).getSynset();
            for (IWord w : synset.getWords()) {
                result.add(w.getLemma().replace("_", " "));
            }
        }

        return result.stream().collect(Collectors.toList());
    }
}

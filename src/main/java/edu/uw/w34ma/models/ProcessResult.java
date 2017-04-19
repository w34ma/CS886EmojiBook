package edu.uw.w34ma.models;

import java.util.List;
import java.util.Map;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 11/04/17.
 */
public class ProcessResult {
    public final List<TableEntry> entries;
    public final List<Map<String, String>> emojis;

    public ProcessResult(List<TableEntry> entries, List<Map<String, String>> emojis) {
        this.entries = entries;
        this.emojis = emojis;
    }
}

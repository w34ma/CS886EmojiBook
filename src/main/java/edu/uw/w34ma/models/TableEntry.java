package edu.uw.w34ma.models;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 28/03/17.
 */
public class TableEntry {
    private final SimpleStringProperty sentence;
    private final SimpleStringProperty event;
    private final SimpleStringProperty emotion;

    public TableEntry(String sentence, String event, String emotion) {
        this.sentence = new SimpleStringProperty(sentence);
        this.event = new SimpleStringProperty(event);
        this.emotion = new SimpleStringProperty(emotion);
    }

    public String getSentence() {
        return sentence.get();
    }

    public void setSentence(String sentence) {
        this.sentence.set(sentence);
    }

    public String getEvent() {
        return event.get();
    }

    public void setEvent(String event) {
        this.event.set(event);
    }

    public String getEmotion() {
        return emotion.get();
    }

    public void setEmotion(String emotion) {
        this.emotion.set(emotion);
    }
}

package edu.uw.w34ma.models;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 28/03/17.
 */
public class ActionEvent implements BaseEvent {
    public final BookCharacter actioner;
    public final BookCharacter actionee;
    public final ACTAction action;

    public ActionEvent(BookCharacter actioner, BookCharacter actionee, ACTAction action) {
        this.actioner = actioner;
        this.actionee = actionee;
        this.action = action;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (actioner != null) {
            sb.append(actioner.name);
        } else {
            sb.append("NULL");
        }
        sb.append(" <").append(action.label).append("> ");
        if (actionee != null) {
            sb.append(actionee.name);
        } else {
            sb.append("NULL");
        }

        return sb.toString();
    }
}

package edu.uw.w34ma.processing;

import edu.uw.w34ma.models.BookCharacter;
import edu.uw.w34ma.models.Emoji;
import edu.uw.w34ma.models.EmotionResult;

import java.util.Arrays;
import java.util.List;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 05/04/17.
 */
public class EmojiFinder {
    private final List<Emoji> emojis;
    private final EmotionFinder finder;

    public EmojiFinder(EmotionFinder finder) {
        this.finder = finder;
        emojis = Arrays.asList(
                finder.createEmojiFromLabel("accommodating"),
                finder.createEmojiFromLabel("adventurous"),
                finder.createEmojiFromLabel("afraid"),
                finder.createEmojiFromLabel("amused"),
                finder.createEmojiFromLabel("angry"),
                finder.createEmojiFromLabel("annoyed"),
                finder.createEmojiFromLabel("at-ease"),
                finder.createEmojiFromLabel("awe-struck"),
                finder.createEmojiFromLabel("broken-hearted"),
                finder.createEmojiFromLabel("cheerful"),
                finder.createEmojiFromLabel("childish"),
                finder.createEmojiFromLabel("cocky"),
                finder.createEmojiFromLabel("cold"),
                //finder.createEmojiFromLabel("cowardly"),
                finder.createEmojiFromLabel("delighted"),
                finder.createEmojiFromLabel("depressed"),
                finder.createEmojiFromLabel("discontented"),
                finder.createEmojiFromLabel("disgusted"),
                finder.createEmojiFromLabel("dismayed"),
                finder.createEmojiFromLabel("displeased"),
                finder.createEmojiFromLabel("dissatisfied"),
                finder.createEmojiFromLabel("embarrassed"),
                finder.createEmojiFromLabel("frustrated"),
                finder.createEmojiFromLabel("furious"),
                finder.createEmojiFromLabel("greedy"),
                finder.createEmojiFromLabel("happy"),
                finder.createEmojiFromLabel("horny"),
                finder.createEmojiFromLabel("joyful"),
                finder.createEmojiFromLabel("lazy"),
                finder.createEmojiFromLabel("lovesick"),
                finder.createEmojiFromLabel("lustful"),
                finder.createEmojiFromLabel("mad"),
                finder.createEmojiFromLabel("merry"),
                finder.createEmojiFromLabel("no emotion"),
                finder.createEmojiFromLabel("overjoyed"),
                finder.createEmojiFromLabel("overwhelmed"),
                finder.createEmojiFromLabel("playful"),
                finder.createEmojiFromLabel("pleased"),
                finder.createEmojiFromLabel("relieved"),
                finder.createEmojiFromLabel("sad"),
                finder.createEmojiFromLabel("scared"),
                finder.createEmojiFromLabel("scornful"),
                finder.createEmojiFromLabel("shocked"),
                finder.createEmojiFromLabel("shy"),
                //finder.createEmojiFromLabel("sickened"),
                finder.createEmojiFromLabel("sly"),
                finder.createEmojiFromLabel("sorrowful"),
                finder.createEmojiFromLabel("suspicious"),
                finder.createEmojiFromLabel("thankful"),
                finder.createEmojiFromLabel("thrilled"),
                finder.createEmojiFromLabel("upset"),
                finder.createEmojiFromLabel("wise"),
                finder.createEmojiFromLabel("worried")
        );
    }

    public Emoji findEmoji(EmotionResult emotion) {
        BookCharacter character = emotion.character;
        double globalDistance = Double.MAX_VALUE;
        Emoji result = null;
        for (Emoji emoji : emojis) {
            double localDistance;
            if (character.gender == BookCharacter.Gender.MALE) {
                localDistance = Math.pow(emotion.E - emoji.EM, 2)
                        + Math.pow(emotion.P - emoji.PM, 2)
                        + Math.pow(emotion.A - emoji.AM, 2);
            } else {
                // female
                localDistance = Math.pow(emotion.E - emoji.EF, 2)
                        + Math.pow(emotion.P - emoji.PF, 2)
                        + Math.pow(emotion.A - emoji.AF, 2);
            }
            if (localDistance < globalDistance) {
                globalDistance = localDistance;
                result = emoji;
            }
        }
        return result;
    }
}

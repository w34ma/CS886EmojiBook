package edu.uw.w34ma.processing;

import edu.uw.w34ma.models.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;


/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 06/04/17.
 */
public class InteractEmotionEvaluatorTest {
    @Test
    public void testInteractEmotionEvaluator() {
        WordNet wordNet = new WordNet();
        Lemmatizer lemmatizer = new Lemmatizer();
        BehaviourFinder behaviourFinder = new BehaviourFinder(wordNet, lemmatizer);
        IdentityFinder identityFinder = new IdentityFinder();
        EmotionEvaluator evaluator = new InteractEmotionEvaluator(identityFinder, behaviourFinder);
        BookCharacter frog = new BookCharacter("frog", "friend", BookCharacter.Gender.MALE);
        BookCharacter toad = new BookCharacter("toad", "friend", BookCharacter.Gender.MALE);

        List<BookCharacter> characters = new ArrayList<>();
        characters.add(frog);
        characters.add(toad);

        List<BaseEvent> events = new ArrayList<>();
        events.add(new ActionEvent(frog, toad, behaviourFinder.findByLabel("amuse")));
        events.add(new ActionEvent(toad, frog, behaviourFinder.findByLabel("abuse")));

        List<Map<BookCharacter, EmotionResult>> emotions = evaluator.evaluate(characters, events);

        emotions.forEach(m -> {
            m.values().forEach(r -> {
                System.out.println("------");
                System.out.println(r.toString());
            });
        });

        assertThat(emotions.size()).isEqualTo(events.size());
    }
}

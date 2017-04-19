package edu.uw.w34ma.processing;

import edu.uw.w34ma.models.BaseEvent;
import edu.uw.w34ma.models.BookCharacter;
import edu.uw.w34ma.models.EmotionResult;

import java.util.List;
import java.util.Map;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 03/04/17.
 */
public interface EmotionEvaluator {
    List<Map<BookCharacter, EmotionResult>> evaluate(List<BookCharacter> characters, List<BaseEvent> events);
}

package edu.uw.w34ma.processing;

import culture.*;
import edu.uw.w34ma.models.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Weicong Ma
 *         Created by Weicong Ma on 03/04/17.
 */
public class InteractEmotionEvaluator implements EmotionEvaluator {
    private Interact interact;
    private IdentityFinder identityFinder;
    private BehaviourFinder behaviourFinder;
    public InteractEmotionEvaluator(IdentityFinder identityFinder, BehaviourFinder behaviourFinder) {
        interact = new Interact();
        this.identityFinder = identityFinder;
        this.behaviourFinder = behaviourFinder;
    }

    private void initializeInteract(List<BookCharacter> characters) {
        // initialize people
        for (int i = 0; i < characters.size(); i++) {
            BookCharacter character = characters.get(i);
            Interact.person[i].name = character.name;
            if (character.gender.equals(BookCharacter.Gender.MALE)) {
                Interact.person[i].visage = 0;
                Interact.person[i].sex = Person.MALE;
            } else {
                Interact.person[i].visage = 3;
                Interact.person[i].sex = Person.FEMALE;
            }
            for (int abos = 0; abos < 4; abos++) {
                Interact.person[i].controllingCase[abos] = true;
            }
            for (int epa = 0; epa < 3; epa++) {
                Interact.person[i].controllingEPA[epa] = true;
            }
        }
    }

    @Override
    public List<Map<BookCharacter, EmotionResult>> evaluate(List<BookCharacter> characters, List<BaseEvent> events) {
        if (characters.size() > 4) {
            throw new IllegalArgumentException("Only support up to 4 characters, but got " + characters.size());
        }
        initializeInteract(characters);
        List<Map<BookCharacter, EmotionResult>> results = new ArrayList<>();
        double previousFundamentals[][] = new double[4][3];
        double previousTransients[][] = new double[4][3];
        for (int i = 0; i < 4; i++) {
            if (i < characters.size()) {
                BookCharacter character = characters.get(i);
                ACTIdentity id = identityFinder.findByLabel(character.identity);
                previousFundamentals[i][0] = character.gender == BookCharacter.Gender.MALE ? id.EM : id.EF;
                previousFundamentals[i][1] = character.gender == BookCharacter.Gender.MALE ? id.PM : id.PF;
                previousFundamentals[i][2] = character.gender == BookCharacter.Gender.MALE ? id.AM : id.AF;
                previousTransients[i] = previousFundamentals[i];
            } else {
                for (int j = 0; j < 3; j++) {
                    previousFundamentals[i][j] = Interact.MISSING_VALUE;
                    previousTransients[i][j] = Interact.MISSING_VALUE;
                }
            }
        }

        for (BaseEvent event : events) {
            Map<BookCharacter, EmotionResult> result = new HashMap<>();
            if (event instanceof ActionEvent) {
                ActionEvent ae = (ActionEvent) event;
                int actor = characters.indexOf(ae.actioner);
                int object = ae.actionee != null ? characters.indexOf(ae.actionee) : characters.indexOf(ae.actioner);
                int action = Interact.behaviors.getIndex(ae.action.label);

                EventRecord record = new EventRecord(0, actor, action, object);
                record.abosIndexes[0][0] = -1;
                record.abosIndexes[0][1] = Interact.identities.getIndex(ae.actioner.identity);
                record.abosIndexes[1][1] = action;
                record.abosIndexes[2][0] = -1;

                if (object != actor) {
                    record.abosIndexes[2][1] = Interact.identities.getIndex(ae.actionee.identity);
                } else {
                    record.abosIndexes[2][1] = Interact.identities.getIndex(ae.actioner.identity);
                }

                record.abosIndexes[3][1] = -1;
                record.restartAtZero = false;
                record.beginNewAnalysis = false;
                record.simultaneityCode = "";

                // set fundamentals and transients
                double[] actionEPA = new double[3];
                actionEPA[0] = ae.actioner.gender == BookCharacter.Gender.MALE ? ae.action.EM : ae.action.EF;
                actionEPA[1] = ae.actioner.gender == BookCharacter.Gender.MALE ? ae.action.PM : ae.action.PF;
                actionEPA[2] = ae.actioner.gender == BookCharacter.Gender.MALE ? ae.action.AM : ae.action.AF;

                record.abosFundamentals[0] = previousFundamentals[actor];
                record.abosFundamentals[1] = actionEPA;
                record.abosFundamentals[2] = previousFundamentals[object];
                record.abosTransientsIn[0] = previousTransients[actor];
                record.abosTransientsIn[1] = actionEPA;
                record.abosTransientsIn[2] = previousTransients[object];

                boolean male, interpersonalActionRatherThanSelfDirected;
                MathModel equationSet, emotionEqs;
                double[] profile;
                DataList matchesToProfile;
                Interact.viewer = 0;

                // Select right equations for kind of event and viewer sex.
                male = Interact.person[Interact.viewer].sex;
                interpersonalActionRatherThanSelfDirected = true;
                if (male) {
                    emotionEqs = Interact.maleEmotion;
                    if (record.vao[1] == record.vao[2]) {
                        // Male self-directed event.
                        interpersonalActionRatherThanSelfDirected = false;
                        equationSet = Interact.maleSelf;
                    } else {
                        if (record.abosIndexes[3][1] > -1) {
                            equationSet = Interact.maleaboS;
                        } else {
                            equationSet = Interact.maleabo;
                        }
                    }
                } else {
                    emotionEqs = Interact.femaleEmotion;
                    if (record.vao[1] == record.vao[2]) {
                        // Female self-directed event.
                        interpersonalActionRatherThanSelfDirected = false;
                        equationSet = Interact.femaleSelf;
                    } else {
                        if (record.abosIndexes[3][1] > -1) {
                            equationSet = Interact.femaleaboS;
                        } else {
                            equationSet = Interact.femaleabo;
                        }
                    }
                }

                record.setConceptGates();
                boolean[] modifierPairConceptGate = { true, true };

                // Compute impressions and deflection.
                equationSet.impressions(record);

                // Actor emotion.
                profile = emotionEqs.computeModifier(record.abosFundamentals[0],
                        record.abosTransientsOut[0]);

                // Show matching words.
                matchesToProfile =
                        Interact.modifiers.getMatches(profile, male,
                                modifierPairConceptGate,
                                EventRecord.EMOTION_DIVISION_CONCEPT_GATE,
                                EventRecord.ALL_FALSE_COMPLEX_CONCEPT_GATE);

                result.put(ae.actioner, new EmotionResult(
                        ae.actioner, profile[0], profile[1], profile[2], ((Retrieval)matchesToProfile.get(0)).word));

                previousTransients[actor] = record.abosTransientsOut[0];

                // Object emotion.
                if (!interpersonalActionRatherThanSelfDirected) {
                    profile = Face.BLANKING_PROFILE;
                } else {
                    profile = emotionEqs.computeModifier(record.abosFundamentals[2],
                            record.abosTransientsOut[2]);
                    previousTransients[object] = record.abosTransientsOut[2];
                }
                // Show matching words.
                matchesToProfile =
                        Interact.modifiers.getMatches(profile, male,
                                modifierPairConceptGate,
                                EventRecord.EMOTION_DIVISION_CONCEPT_GATE,
                                EventRecord.ALL_FALSE_COMPLEX_CONCEPT_GATE);

                if (ae.actionee != null) {
                    result.put(ae.actionee, new EmotionResult(
                            ae.actionee, profile[0], profile[1], profile[2], ((Retrieval) matchesToProfile.get(0)).word));
                }
            } else if (event instanceof EmotionEvent) {
                // over
                EmotionEvent ee = (EmotionEvent) event;
                int actor = characters.indexOf(ee.character);
                boolean male = ee.character.gender == BookCharacter.Gender.MALE;
                double[] profile = {ee.E, ee.P, ee.A};
                boolean[] modifierPairConceptGate = { true, true };
                DataList matchesToProfile =
                        Interact.modifiers.getMatches(profile, male,
                                modifierPairConceptGate,
                                EventRecord.EMOTION_DIVISION_CONCEPT_GATE,
                                EventRecord.ALL_FALSE_COMPLEX_CONCEPT_GATE);

                previousTransients[actor] = profile;

                result.put(ee.character, new EmotionResult(
                        ee.character, profile[0], profile[1], profile[2], ((Retrieval)matchesToProfile.get(0)).word));
            }
            results.add(result);
        }
        return results;
    }
}

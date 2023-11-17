package it.unibo.deathnote;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

public class TestDeathNote {

    private DeathNote deathNote;
    private static final int NEGATIVE_RULE_NUMBER = -1;
    private static final String MARTIN_TOMASSI = "Martin Tomassi";
    private static final String KARTING_DEATH_CAUSE = "karting accident";
    private static final String CAR_CRASH_DEATH_CAUSE = "killed at the wheel";
    private static final String RUN_DEATH_DETAILS = "ran for too long";
    private static final String CAR_DEATH_DETAILS = "lost control of the car";
    private static final String EMPTY_STRING = "";

    @BeforeEach
    public void initialize(){
        deathNote = new DeathNoteImpl();
    }

    @Test
    public void testInvalidRules(){
        Exception exception;
        for(final int ruleCounter : List.of(NEGATIVE_RULE_NUMBER, 0, DeathNoteImpl.RULES.size() + 1)){
            exception = assertThrowsExactly(IllegalArgumentException.class, () -> this.deathNote.getRule(ruleCounter));
            assertNotNull(exception.getMessage());
            assertFalse(exception.getMessage().isBlank());
        }
    }

    @Test
    public void testRules(){
        for(int i = 1; i <= DeathNote.RULES.size(); i++) {
            final String rule = deathNote.getRule(i);
            assertNotNull(rule);
            assertFalse(rule.isBlank());
        }
    }

    @Test
    public void testDeathWriting(){
        assertFalse(deathNote.isNameWritten(MARTIN_TOMASSI));
        assertFalse(deathNote.isNameWritten(EMPTY_STRING));
        assertThrowsExactly(NullPointerException.class, () -> this.deathNote.writeName(null));
    }

    @Test
    public void testDeathCause() throws InterruptedException {
        assertThrowsExactly(IllegalStateException.class, () -> this.deathNote.writeDeathCause(CAR_CRASH_DEATH_CAUSE));
        deathNote.writeName(MARTIN_TOMASSI);
        assertTrue(deathNote.writeDeathCause(KARTING_DEATH_CAUSE));
        assertEquals(KARTING_DEATH_CAUSE, deathNote.getDeathCause(MARTIN_TOMASSI));
        Thread.sleep(100);
        assertFalse(deathNote.writeDeathCause(KARTING_DEATH_CAUSE));
        assertNotEquals(CAR_CRASH_DEATH_CAUSE, deathNote.getDeathCause(MARTIN_TOMASSI));
    }

    @Test
    public void testDeathDetails() throws InterruptedException  {
        assertThrowsExactly(IllegalStateException.class, () -> this.deathNote.writeDetails("fatally dead"));
        deathNote.writeName(MARTIN_TOMASSI);
        assertEquals(EMPTY_STRING, deathNote.getDeathDetails(MARTIN_TOMASSI));
        assertTrue(deathNote.writeDetails(RUN_DEATH_DETAILS));
        assertEquals(RUN_DEATH_DETAILS, deathNote.getDeathDetails(MARTIN_TOMASSI));
        assertTrue(deathNote.writeDetails(CAR_DEATH_DETAILS));
        assertEquals(CAR_DEATH_DETAILS, deathNote.getDeathDetails(MARTIN_TOMASSI));
        Thread.sleep(6100);
    }

}
package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DeathNoteImpl implements DeathNote {

    private static final long MAX_CAUSE_TIME_MS = 40;
    private static final long MAX_DETAILS_TIME_MS = 6040;
    private static final String EMPTY_STRING = "";
    
    private final Map<String, DeathInfo> deathNoteHumans;
    private long startWriteTime;
    private String lastWrittenName;

    public DeathNoteImpl() {
        this.deathNoteHumans = new HashMap<String, DeathInfo>();
        this.lastWrittenName = EMPTY_STRING;
    }

    @Override
    public String getRule(final int ruleNumber) {
        if(ruleNumber < 1 || ruleNumber > DeathNote.RULES.size()) {
            throw new IllegalArgumentException("Rule number is smaller than 1 or larger than " + DeathNote.RULES.size());
        }
        return DeathNote.RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(final String name) {
        if(Objects.isNull(name)){
            throw new NullPointerException("The given name is null");
        }
        this.deathNoteHumans.put(name, new DeathInfo());
        this.startWriteTime = System.currentTimeMillis();
        this.lastWrittenName = name;
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if(Objects.isNull(cause) || this.deathNoteHumans.isEmpty()) {
            throw new IllegalStateException("There is no name written in this DeathNote or the cause is null");
        }
        if((System.currentTimeMillis() - this.startWriteTime) <= MAX_CAUSE_TIME_MS){
            this.deathNoteHumans.get(this.lastWrittenName).setCause(cause);
            return true;
        } else{
            return false;
        }
    }

    @Override
    public boolean writeDetails(final String details) {
        if(Objects.isNull(details) || this.deathNoteHumans.isEmpty()) {
            throw new IllegalStateException("There is no name written in this DeathNote or the details are null");
        }
        if((System.currentTimeMillis() - this.startWriteTime) <= MAX_DETAILS_TIME_MS){
            this.deathNoteHumans.get(this.lastWrittenName).setDetails(details);
            return true;
        } else{
            return false;
        }
    }

    @Override
    public String getDeathCause(final String name) {
        if(!this.deathNoteHumans.containsKey(name)){
            throw new IllegalArgumentException(name + " is not written in this DeathNote");
        }
        return this.deathNoteHumans.get(name).getDeathCause();
    }

    @Override
    public String getDeathDetails(final String name) {
        if(!(this.deathNoteHumans.containsKey(name))){
            throw new IllegalArgumentException(name + " is not written in this DeathNote");
        }
        return this.deathNoteHumans.get(name).getDeathDetails();
    }

    @Override
    public boolean isNameWritten(final String name) {
        return this.deathNoteHumans.containsKey(name);
    }

}
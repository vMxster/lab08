package it.unibo.deathnote.impl;

public class DeathInfo {

    private static final String DEFAULT_DEATH_CAUSE = "heart attack";
    private static final String EMPTY_STRING = "";

    private String deathCause;
    private String deathDetails;

    public DeathInfo() {
        this.deathDetails = EMPTY_STRING;
        this.deathCause = DEFAULT_DEATH_CAUSE;
    }

    public void setCause(final String deathCause){
        this.deathCause = deathCause;
    }

    public void setDetails(final String details){
        this.deathDetails = details;
    }

    public String getDeathCause(){
        return this.deathCause;
    }

    public String getDeathDetails(){
        return this.deathDetails;
    }
}
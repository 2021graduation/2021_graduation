package org.techtown.graduation_project;

public class InfectionData {
    private String gubnu;
    private String defCnt;
    private String incDec;
    private String localOccCnt;
    private String deathCnt;
    private String isolClearCnt;

    public InfectionData (String gubnu, String defCnt, String incDec, String localOccCnt, String deathCnt, String isolClearCnt){
        this.gubnu = gubnu;
        this.defCnt = defCnt;
        this.incDec = incDec;
        this.localOccCnt = localOccCnt;
        this.deathCnt = deathCnt;
        this.isolClearCnt = isolClearCnt;
    }

    public String getGubnu() {
        return gubnu;
    }
    public void setGubnu(String gubnu) {
        this.gubnu = gubnu;
    }

    public String getDefCnt() {
        return defCnt;
    }
    public void setDefCnt(String defCnt) {
        this.defCnt = defCnt;
    }

    public String getIncDec() {
        return incDec;
    }
    public void setIncDec(String incDec) {
        this.incDec = incDec;
    }

    public String getLocalOccCnt() {
        return localOccCnt;
    }
    public void setLocalOccCnt(String localOccCnt) {
        this.localOccCnt = localOccCnt;
    }

    public String getDeathCnt() {
        return deathCnt;
    }
    public void setDeathCnt(String deathCnt) {
        this.deathCnt = deathCnt;
    }

    public String getIsolClearCnt() {
        return isolClearCnt;
    }
    public void setIsolClearCnt(String isolClearCnt) {
        this.isolClearCnt = isolClearCnt;
    }
}

package org.techtown.graduation_project;

public class MainData {

    private int iv_profile;
    private String sgguNm;
    private String sidoNm;
    private String yadmNm;
    private String telno;

    public MainData(int iv_profile, String sgguNm, String sidoNm, String yadmNm, String telno) {
        this.iv_profile = iv_profile;
        this.sgguNm = sgguNm;
        this.sidoNm = sidoNm;
        this.yadmNm = yadmNm;
        this.telno = telno;
    }

    public int getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(int iv_profile) {
        this.iv_profile = iv_profile;
    }

    public String getSgguNm() {
        return sgguNm;
    }

    public void setSgguNm(String sgguNm) {
        this.sgguNm = sgguNm;
    }

    public String getSidoNm() {
        return sidoNm;
    }

    public void setSidoNm(String sidoNm) {
        this.sidoNm = sidoNm;
    }

    public String getYadmNm() {
        return yadmNm;
    }

    public void setYadmNm(String yadmNm) {
        this.yadmNm = yadmNm;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }
}

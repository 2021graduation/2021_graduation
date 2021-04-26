package org.techtown.graduation_project;

public class MaskData {private int iv_profile;
    private String ITEM_NAME;
    private String ENTP_NAME;
    private String ITEM_PERMIT_DATE;

    public MaskData(String ITEM_NAME,  String ENTP_NAME, String ITEM_PERMIT_DATE) {

        this.ITEM_NAME = ITEM_NAME;
        this.ENTP_NAME = ENTP_NAME;
        this.ITEM_PERMIT_DATE = ITEM_PERMIT_DATE;
    }


    public int getIv_profile() {
        return iv_profile;
    }

    public void setIv_profile(int iv_profile) {
        this.iv_profile = iv_profile;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public String getENTP_NAME() {
        return ENTP_NAME;
    }

    public void setENTP_NAME(String ENTP_NAME) {
        this.ENTP_NAME = ENTP_NAME;
    }

    public String getITEM_PERMIT_DATE() {
        return ITEM_PERMIT_DATE;
    }

    public void setITEM_PERMIT_DATE(String ITEM_PERMIT_DATE) {
        this.ITEM_PERMIT_DATE = ITEM_PERMIT_DATE;
    }
}

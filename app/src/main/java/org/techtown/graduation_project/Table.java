package org.techtown.graduation_project;

public class Table {

    private String date;
    private String latlng_count;
    private String disaster_count;

    public Table(String date, String latlng_count, String disaster_count) {
        this.date = date;
        this.latlng_count = latlng_count;
        this.disaster_count = disaster_count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLatlng_count() {
        return latlng_count;
    }

    public void setLatlng_count(String latlng_count) {
        this.latlng_count = latlng_count;
    }

    public String getDisaster_count() {
        return disaster_count;
    }

    public void setDisaster_count(String disaster_count) {
        this.disaster_count = disaster_count;
    }
}

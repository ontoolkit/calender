package com.project.srmcalender.Pojo;

public class Event {

    private String date;
    private String eventdesc;
    private String id;

    public Event() {
    }

    public Event(String date, String eventdesc, String id) {
        this.date = date;
        this.eventdesc = eventdesc;
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public String getEventdesc() {
        return eventdesc;
    }

    public String getId() {
        return id;
    }
}

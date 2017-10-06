package com.blacksmithyouthclub.model;

/**
 * Created by pc on 9/29/2017.
 */

public class Event_Pojo {

    private String EventId,EventName,EventDate,EventDesc,EventImg;
    public Event_Pojo(String EventId,String EventName,String EventDate,String EventDesc,String EventImg) {
        this.EventId=EventId;
        this.EventName=EventName;
        this.EventDate=EventDate;
        this.EventDesc=EventDesc;
        this.EventImg=EventImg;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getEventDesc() {
        return EventDesc;
    }

    public void setEventDesc(String eventDesc) {
        EventDesc = eventDesc;
    }

    public String getEventImg() {
        return EventImg;
    }

    public void setEventImg(String eventImg) {
        EventImg = eventImg;
    }
}

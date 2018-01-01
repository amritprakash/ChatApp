package com.amrit.chatapp;

import java.util.Date;
import java.util.List;

/**
 *
 * Created by hp on 31-12-2017.
 */

public class Message {

    private Date date;

    private String text;

    private String id;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

package com.ical4jtest.domain.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class IcsEvent {

    @ApiModelProperty(name = "summary", value = "Calendar event summary", example = "")
    private String summary;

    @ApiModelProperty(name = "description", value = "Calendar event description", example = "")
    private String description;

    @ApiModelProperty(name = "timeBoxInHours", value = "How much hours the event will have", example = "4")
    private int timeBoxInHours;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(name = "date", value = "The following format is used: yyyy-MM-dd", example = "2020-01-20")
    private Date date;

    @ApiModelProperty(value = "Person who will receive the email", example = "")
    private String emailTo;

    public IcsEvent() {
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTimeBoxInHours() {
        return timeBoxInHours;
    }

    public void setTimeBoxInHours(int timeBoxInHours) {
        this.timeBoxInHours = timeBoxInHours;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }
}

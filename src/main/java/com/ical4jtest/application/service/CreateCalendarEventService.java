package com.ical4jtest.application.service;
import com.ical4jtest.domain.model.IcsEvent;

public interface CreateCalendarEventService {
    String createEventByDate(IcsEvent icsEvent);
}

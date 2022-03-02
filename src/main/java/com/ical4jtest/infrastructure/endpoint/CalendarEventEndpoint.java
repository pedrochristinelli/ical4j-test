package com.ical4jtest.infrastructure.endpoint;

import com.ical4jtest.application.service.CreateCalendarEventService;
import com.ical4jtest.application.service.implementation.CreateCalendarEventServiceImpl;
import com.ical4jtest.domain.model.IcsEvent;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.parameters.RequestBody;


@RestController
@RequestMapping(EndPointMaps.CALENDAR_EVENT)
public class CalendarEventEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalendarEventEndpoint.class);
    private final CreateCalendarEventService createCalendarEventService;

    @Autowired
    public CalendarEventEndpoint(CreateCalendarEventServiceImpl createCalendarEventService) {
        this.createCalendarEventService = createCalendarEventService;
    }

    @Operation(summary = "Create an Calendar Event given an simple date")
    @PostMapping
    public String createCalendarEventByDate(@RequestBody IcsEvent icsEvent){
        LOGGER.info("Starting to process the endpoint '{}' with the method '{}' with the following variable: {}", "/calendarEvent", "POST", icsEvent.getDate());
        return createCalendarEventService.createEventByDate(icsEvent);
    }
}

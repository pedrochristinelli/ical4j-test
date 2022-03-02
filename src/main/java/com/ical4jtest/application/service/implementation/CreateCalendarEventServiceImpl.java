package com.ical4jtest.application.service.implementation;

import com.ical4jtest.application.service.CreateCalendarEventService;
import com.ical4jtest.domain.model.IcsEvent;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.*;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class CreateCalendarEventServiceImpl implements CreateCalendarEventService {
    public CreateCalendarEventServiceImpl() {
    }

    public String createEventByDate(IcsEvent icsEvent) {
        Date date = icsEvent.getDate();
        //Create an iCal4J Calendar
        Calendar c = new Calendar();
        //"EN" is the language.
        c.add(new ProdId("-//Ben Fortuna//iCal4j 1.0//EN"));
        c.add(Version.VERSION_2_0);
        c.add(CalScale.GREGORIAN);

        //Add an event to the calendar
        c.add(createSingleEvent(date, icsEvent));
        String icsExport = c.toString();
        String filePath = "mymeeting.ics";
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(filePath);
            CalendarOutputter outputter = new CalendarOutputter();
            outputter.output(c, fout);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fout != null) {
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "there u go mate:/n" + icsExport ;
    }

    private VEvent createSingleEvent(Date date, IcsEvent icsEvent) {
        //Create a new calendar event that starts on 5th March 2021 at midday Australian Eastern Daylight Savings Time and goes for 1 hour.
        VEvent vEvent = new VEvent();
        java.time.ZonedDateTime now = java.time.ZonedDateTime.now();

        //Create a unique ID for the event
        String uidTimestamp = java.time.format.DateTimeFormatter
                .ofPattern("yyyyMMdd'T'hhmmssXX")
                .format(now);
        //In the real world this could be a number from a generated sequence:
        String uidSequence = "/" + (int) Math.ceil(Math.random() * 1000);
        vEvent.add(new Uid(uidTimestamp + uidSequence));

        //Add a start datetime and a duration
        TimeZoneRegistry tzReg = TimeZoneRegistryFactory.getInstance().createRegistry();
        TimeZone timezone = tzReg.getTimeZone(TimeZone.getDefault().getID());
        try {
            String formattedEndpointDate = new SimpleDateFormat("yyyyMMdd'T'hhmmssXX").format(date);
            vEvent.add(new DtStart(String.valueOf(new DateTime(formattedEndpointDate, timezone))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        vEvent.add(new Duration(java.time.Duration.ofHours(icsEvent.getTimeBoxInHours())));

        //Add title and description
        vEvent.add(new Summary(icsEvent.getSummary()));
        vEvent.add(new Description(icsEvent.getDescription()));
        return vEvent;
    }

}

package com.alexstyl.specialdates.search;

import com.alexstyl.specialdates.Optional;
import com.alexstyl.specialdates.TestDateLabelCreator;
import com.alexstyl.specialdates.contact.Contact;
import com.alexstyl.specialdates.date.ContactEvent;
import com.alexstyl.specialdates.date.Date;
import com.alexstyl.specialdates.events.peopleevents.CustomEventType;
import com.alexstyl.specialdates.events.peopleevents.EventType;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.alexstyl.specialdates.date.DateConstants.DECEMBER;
import static com.alexstyl.specialdates.events.peopleevents.StandardEventType.*;
import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class EventLabelCreatorTest {

    private ContactEventLabelCreator creator;
    @Mock
    private Contact mockContact;

    @Before
    public void setUp() {
        creator = new ContactEventLabelCreator(Date.today(), new DumbTestResources(), TestDateLabelCreator.forUS());
    }

    @Test
    public void birthdayWithoutYearIsCalculatedCorrectly() {
        Date date = Date.on(12, DECEMBER);
        ContactEvent event = contactEventOn(date, BIRTHDAY);
        String label = creator.createFor(event);

        assertThat(label).isEqualTo("Birthday on December 12");
    }

    @Test
    public void birthdayWithYearIsCalculatedCorrectly() {
        int age = Date.CURRENT_YEAR - 1990;
        Date date = Date.on(12, DECEMBER, 1990);

        ContactEvent event = contactEventOn(date, BIRTHDAY);
        String label = creator.createFor(event);

        assertThat(label).isEqualTo("Turns " + age + " on December 12");
    }

    @Test
    public void namedayIsCalculatedCorrectly() {
        Date date = Date.on(12, DECEMBER);
        ContactEvent event = contactEventOn(date, NAMEDAY);
        String label = creator.createFor(event);

        assertThat(label).isEqualTo("Nameday on December 12");
    }

    @Test
    public void anniversaryIsCalculatedCorrectly() {
        Date date = Date.on(12, DECEMBER);
        ContactEvent event = contactEventOn(date, ANNIVERSARY);
        String label = creator.createFor(event);

        assertThat(label).isEqualTo("Anniversary on December 12");
    }

    @Test
    public void otherIsCalculatedCorrectly() {
        Date date = Date.on(12, DECEMBER);
        ContactEvent event = contactEventOn(date, OTHER);
        String label = creator.createFor(event);

        assertThat(label).isEqualTo("Other on December 12");
    }

    @Test
    public void customIsCalculatedCorrectly() {
        Date date = Date.on(12, DECEMBER);
        ContactEvent event = contactEventOn(date, new CustomEventType("H4x"));
        String label = creator.createFor(event);

        assertThat(label).isEqualTo("H4x on December 12");
    }

    private ContactEvent contactEventOn(Date date, EventType eventType) {
        return new ContactEvent(Optional.<Long>absent(), eventType, date, mockContact);
    }
}

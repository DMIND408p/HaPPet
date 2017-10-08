package compactcalendarview.comparators;

import java.util.Comparator;

import compactcalendarview.domain.Event;

public class EventComparator implements Comparator<Event> {

    @Override
    public int compare(Event lhs, Event rhs) {
        return lhs.getTime_in_millis() < rhs.getTime_in_millis() ? -1 : lhs.getTime_in_millis() == rhs.getTime_in_millis() ? 0 : 1;
    }
}

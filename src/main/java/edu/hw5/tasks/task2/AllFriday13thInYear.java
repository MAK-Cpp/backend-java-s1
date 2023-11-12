package edu.hw5.tasks.task2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

public final class AllFriday13thInYear {
    private AllFriday13thInYear() {
    }

    public static Collection<Date> find(final int year) {
        final Date lastDay = new GregorianCalendar(year, Calendar.DECEMBER, 31).getTime();
        Calendar calendar = Calendar.getInstance();
        ArrayList<Date> answer = new ArrayList<>();
        calendar.setTime(new GregorianCalendar(year, Calendar.JANUARY, 13).getTime());
        for (; !calendar.getTime().after(lastDay); calendar.add(Calendar.MONTH, 1)) {
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                answer.add(calendar.getTime());
            }
        }
        return answer;
    }
}

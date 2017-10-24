package model;


/*** Stores an amount of hours and minutes representing a time of day ***/
public class TimeOfDay implements Comparable<TimeOfDay> {
    private int minutes;
    private int hours;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int HOURS_IN_DAY = 24;

    public TimeOfDay(int hoursMinutes) {
        hoursMinutes = Math.floorMod(hoursMinutes, MINUTES_IN_HOUR * HOURS_IN_DAY);
        this.minutes = hoursMinutes % MINUTES_IN_HOUR;
        this.hours = hoursMinutes / MINUTES_IN_HOUR;
    }
    public TimeOfDay(int hours, int minutes) {
        this(minutes + (hours * MINUTES_IN_HOUR));
    }

    public int getHours() {
        return this.hours;
    }
    public int getMinutes() {
        return this.minutes;
    }

    public int asInt() {
        return (hours * MINUTES_IN_HOUR) + minutes;
    }


    public TimeOfDay plusHours(int offsetHours) {
        return plusMinutes(offsetHours * MINUTES_IN_HOUR);
    }

    public TimeOfDay plusMinutes(int offsetMinutes) {
        int currentTime = asInt();
        return new TimeOfDay(
                Math.floorMod(currentTime + offsetMinutes, HOURS_IN_DAY * MINUTES_IN_HOUR));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!obj.getClass().isAssignableFrom(this.getClass())) {
            return false;
        }
        if (this.asInt() == ((TimeOfDay) obj).asInt()) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(TimeOfDay service) {
        return Integer.compare(asInt(), service.asInt());
    }
}

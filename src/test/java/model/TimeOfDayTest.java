package model;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class TimeOfDayTest {

    private TimeOfDay hourTimeOfDay = null;
    private TimeOfDay minuteTimeOfDay = null;
    private TimeOfDay excess = null;
    private TimeOfDay negative = null;
    
    private static final int START_MINUTES = 30;
    private static final int START_HOURS = 6;
    private static final int START_HOURS_MINUTES = 390;
    private static final int NEG_START_HOURS_MINUTES = 1050;
    private static final int NEG_START_HOURS = 17;
    
    @Before
    public void setUpTime() {
        // Create two time of day objects with the time of 6:30, through 
        // both constructors
        minuteTimeOfDay = new TimeOfDay(START_HOURS_MINUTES);
        hourTimeOfDay = new TimeOfDay(START_HOURS, START_MINUTES);
        // Create also an object with a 90 minutes passed for the minutes field
        // The excess should carry on to the hours
        excess = new TimeOfDay(START_HOURS, START_MINUTES*3);
        // Create a time of day object that should have 17 hours and 30 minutes,
        // from a negative 1 hour and negative 30 minutes
        negative = new TimeOfDay(-START_HOURS, -START_MINUTES);
        
    }
    
    @Test
    public void testGetHours() {
        assertTrue(minuteTimeOfDay.getHours() == hourTimeOfDay.getHours());
        assertTrue(minuteTimeOfDay.getHours() == START_HOURS);
    }
    
    @Test
    public void testGetMinutes() {
        assertTrue(minuteTimeOfDay.getMinutes() == hourTimeOfDay.getMinutes());
        assertTrue(minuteTimeOfDay.getMinutes() == START_MINUTES);
    }
    
    @Test
    public void testGetHoursMinutes() {
        assertTrue(minuteTimeOfDay.asInt() == hourTimeOfDay.asInt());
        assertTrue(minuteTimeOfDay.asInt() == START_HOURS_MINUTES);
    }
    
    @Test
    public void testExcessStartMinutes() {
        assertTrue(excess.getHours() == START_HOURS + 1);
        assertTrue(excess.getMinutes() == START_MINUTES);
    }
    
    @Test
    public void testNegative() {
        assertTrue(negative.asInt() == NEG_START_HOURS_MINUTES);
    }
    
    @Test
    public void testNegativeHoursMinutes() {
        assertTrue(negative.getHours() == NEG_START_HOURS);
        assertTrue(negative.getMinutes() == START_MINUTES);
    }
    
    @Test
    public void testPlusHours() {
        assertTrue(hourTimeOfDay.plusHours(1).getHours() == START_HOURS + 1);
        assertTrue(minuteTimeOfDay.plusHours(1).getHours() == START_HOURS + 1);
    }
    
    @Test
    public void testPlusHoursRollover() {
        hourTimeOfDay = hourTimeOfDay.plusHours(TimeOfDay.HOURS_IN_DAY - START_HOURS - 1);
        assertTrue(hourTimeOfDay.getHours() == 23);
        hourTimeOfDay = hourTimeOfDay.plusHours(1);
        assertTrue(hourTimeOfDay.getHours() == 0);
    }
    
    @Test 
    public void testMinutesRollover() {
        hourTimeOfDay = hourTimeOfDay.plusHours(TimeOfDay.HOURS_IN_DAY - START_HOURS - 1);
        hourTimeOfDay = hourTimeOfDay.plusMinutes(30);
        assertTrue(hourTimeOfDay.getHours() == 0);
    }
    
    @Test
    public void testImmutable() {
        hourTimeOfDay.plusHours(5);
        hourTimeOfDay.plusMinutes(50);
        assertTrue(hourTimeOfDay.equals(minuteTimeOfDay));
    }
    
    @Test 
    public void testNotEquals() {
        assertFalse(hourTimeOfDay.equals(negative));
    }
    
    @Test
    public void testNotEqualsNull() {
        assertFalse(hourTimeOfDay.equals(null));
    }
}

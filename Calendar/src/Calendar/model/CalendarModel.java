package Calendar.model;

import java.util.Calendar;

public class CalendarModel {
    private Calendar cal;
    private int year;
    private int month;
    
    public CalendarModel() {
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
    }
    
    public int getYear() {
        return year;
    }
    
    public int getMonth() {
        return month;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public void setMonth(int month) {
        this.month = month;
    }
    
    public int getFirstDay() {
        cal.set(year, month - 1, 1);
        return cal.get(Calendar.DAY_OF_WEEK) - 1;
    }
    
    public int getLastDay() {
        cal.set(year, month, 0);
        return cal.get(Calendar.DATE);
    }
    
    public void incrementMonth() {
        if (month == 12) {
            month = 1;
            year += 1;
        } else {
            month += 1;
        }
    }
    
    public void decrementMonth() {
        if (month == 1) {
            month = 12;
            year -= 1;
        } else {
            month -= 1;
        }
    }
    
    public String formatMonth(int n) {
        return (n < 10) ? "0" + n : Integer.toString(n);
    }
}

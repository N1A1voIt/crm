package site.easy.to.build.crm.google.model.calendar;

public class EventDateTime {
//    private String date;
    private String dateTime;

    private String timeZone;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    // Getters and setters

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }


    public void addStartTime() {
        if (this.getDateTime() == null) this.setDateTime(this.getDate()+"T"+"00:00:00+03:00");
    }
}
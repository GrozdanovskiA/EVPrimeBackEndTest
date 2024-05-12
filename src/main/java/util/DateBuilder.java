package util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateBuilder {

    public String currentDateAndTime(){
        ZonedDateTime date = ZonedDateTime.now().minusHours(1);
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return date.format(formater);
    }

    public String currentDate(){
        ZonedDateTime date = ZonedDateTime.now();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formater);
    }
}

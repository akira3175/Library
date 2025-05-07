package org.example.GUI.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static Date parseDate(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }
}

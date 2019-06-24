package com.example.graphviewlibrary2.GraphView.DateHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {

    public static String longToDate(long date, DateLabel dateLabel) {
        Date d = new Date(date * 1000);
        SimpleDateFormat sdf;
        switch (dateLabel) {
            case WEEK:
                sdf = new SimpleDateFormat("E, dd");
                break;
            case MONTH:
                sdf = new SimpleDateFormat("MMM, dd");
                break;
            case YEAR:
                sdf = new SimpleDateFormat("MMM, yy");
                break;
            case YEARS:
                sdf = new SimpleDateFormat("yyyy");
                break;
            default:
                sdf = new SimpleDateFormat("MMM, yy");
                break;
        }
        return sdf.format(d);
    }

}

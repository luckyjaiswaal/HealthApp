package com.example.healthapp.chatmodel;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class GetTimeCovertAgo {
    public static Date parseDate(String dateString, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            Date parsedDate = sdf.parse(dateString);
            return parsedDate;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    public static String getNewsFeeTimeAgo(long milisecond) {


        String inputString = "";
        long totalday = 0;
        int millisec = 0, sec = 0, min = 0, hour = 0, day = 0, week = 0, month = 0, year = 0;
        int miniute = 60;// 60 second
        int houre = 60 * 60; //
        int dayTime = 60 * 60 * 24;
        int WeakIme = 60 * 60 * 24 * 7;
        int monthTime = 60 * 60 * 24 * 30;
        int yearTime = 60 * 60 * 24 * 30 * 12;
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date mDate = new Date();
        TimeZone tz = TimeZone.getTimeZone("GMT");
        sdf.setTimeZone(tz);
        String date = sdf.format(mDate);
        Date currentDate = parseDate(date, dateFormat);
        long currentDuration = currentDate.getTime();
        long mainday = currentDuration - milisecond;
        if (mainday > 1000) {

            sec = (int) (mainday / 1000);

            if (sec < 60) {
                if (sec > 1)
                    inputString = Integer.toString(sec) + " Seconds ago";
                else
                    inputString = Integer.toString(sec) + " Second ago";
            } else if (sec >= 60 && sec < houre) {
                min = sec / 60;
                if (min > 1)
                    inputString = Integer.toString(min) + " minutes ago";
                else
                    inputString = Integer.toString(min) + " minute ago";
            } else if (sec >= houre && sec < dayTime) {
                hour = sec / houre;
                if (hour > 1)
                    inputString = Integer.toString(hour) + " hours ago";
                else
                    inputString = Integer.toString(hour) + " hour ago";

            } else if (sec >= dayTime && sec < WeakIme) {
                day = sec / dayTime;
                if (day > 1)
                    inputString = Integer.toString(day) + " days ago";
                else
                    inputString = Integer.toString(day) + " day ago";

            } else if (sec >= WeakIme && sec < monthTime) {
                week = sec / WeakIme;
                if (week > 1)
                    inputString = Integer.toString(week) + " weeks ago";
                else
                    inputString = Integer.toString(week) + " week ago";

            } else if (sec >= monthTime && sec < yearTime) {
                month = sec / monthTime;
                if (month > 1)
                    inputString = Integer.toString(month) + " months ago";
                else
                    inputString = Integer.toString(month) + " month ago";

            } else if (sec >= yearTime) {
                year = sec / yearTime;
                if (year > 1)
                    inputString = Integer.toString(month) + " years ago";
                else
                    inputString = Integer.toString(month) + " year ago";
            }

        } else {
            inputString = " 1 Second";
        }

        return inputString;

    }
    
    

    public static String getNewsFeeTimeAgoFromNotification(long milisecond) {

        String inputString = "";
        Date date = null;
        long totalday = 0;
        int millisec = 0, sec = 0, min = 0, hour = 0, day = 0, week = 0, month = 0, year = 0;
        int miniute = 60;// 60 second
        int houre = 60 * 60; //
        int dayTime = 60 * 60 * 24;
        int WeakIme = 60 * 60 * 24 * 7;
        int monthTime = 60 * 60 * 24 * 30;
        int yearTime = 60 * 60 * 24 * 30 * 12;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateformateCurrent = new Date();
        try {
            dateformateCurrent = sdf.parse(getTime("UTC"));
            Log.w("dateformateCurrent", "d" + dateformateCurrent);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        long lDateTime = dateformateCurrent.getTime();

        long mainday = lDateTime - milisecond;

        if (mainday > 1000) {

            sec = (int) (mainday / 1000);

            if (sec < 60) {

                if (sec > 1)
                    inputString = Integer.toString(sec) + " Seconds ago";
                else
                    inputString = Integer.toString(sec) + " Second ago";

            } else if (sec >= 60 && sec < houre) {
                min = sec / 60;
                if (min > 1)
                    inputString = Integer.toString(min) + " minutes ago";
                else
                    inputString = Integer.toString(min) + " minute ago";
            } else if (sec >= houre && sec < dayTime) {
                hour = sec / houre;
                if (hour > 1)
                    inputString = Integer.toString(hour) + " hours ago";
                else
                    inputString = Integer.toString(hour) + " hour ago";

            } else if (sec >= dayTime && sec < WeakIme) {
                day = sec / dayTime;
                if (day > 1)
                    inputString = Integer.toString(day) + " days ago";
                else
                    inputString = Integer.toString(day) + " day ago";

            } else if (sec >= WeakIme && sec < monthTime) {
                week = sec / WeakIme;
                if (week > 1)
                    inputString = Integer.toString(week) + " weeks ago";
                else
                    inputString = Integer.toString(week) + " week ago";

            } else if (sec >= monthTime && sec < yearTime) {
                month = sec / monthTime;
                if (month > 1)
                    inputString = Integer.toString(month) + " months ago";
                else
                    inputString = Integer.toString(month) + " month ago";

            } else if (sec >= yearTime) {
                year = sec / yearTime;
                if (year > 1)
                    inputString = Integer.toString(month) + " years ago";
                else
                    inputString = Integer.toString(month) + " year ago";
            }

        } else {
            inputString = "5 Seconds ago";
        }

        return inputString;

    }

    public static String getTime(String timezone) {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone(timezone));
        Date date = c.getTime(); // current date and time in UTC
        String year = "" + c.get(Calendar.YEAR);
        int Moth = c.get(Calendar.MONTH);
        String Day = "" + c.get(Calendar.DATE);
        Moth = Moth + 1;
        String mothString = "";
        if (9 >= Moth)
            mothString = "0" + Moth;
        else
            mothString = "" + Moth;

        String timeFormate = year + "-" + mothString + "-" + Day + " "
                + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)
                + ":" + c.get(Calendar.SECOND);

        return timeFormate;
    }

    public static String datetimeconvert(String givenDateString) {
        long timeInMilliseconds = 0, timeInMillisecondsCurrent = 0;
        long currentMil = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();

            Date dateformateCurrent = new Date();
            String currentData = sdf.format(dateformateCurrent);
            Date mDateCurrent = sdf.parse(currentData);
            timeInMillisecondsCurrent = mDateCurrent.getTime();
            currentMil = timeInMilliseconds - timeInMillisecondsCurrent;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return getDurationBreakdown(currentMil);
    }

    public static String getDurationBreakdown(long millis) {
        String text = "";
        if (millis < 0) {
            text = "";
        } else {

            long days = TimeUnit.MILLISECONDS.toDays(millis);
            millis -= TimeUnit.DAYS.toMillis(days);
            long hours = TimeUnit.MILLISECONDS.toHours(millis);
            millis -= TimeUnit.HOURS.toMillis(hours);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
            millis -= TimeUnit.MINUTES.toMillis(minutes);
            text = days + "d" + " " + hours + "hrs" + " " + minutes + "m";

        }

        return text;
    }

    public static long datetimeconvertMilsisecon(String givenDateString) {
        long timeInMilliseconds = 0, timeInMillisecondsCurrent = 0;
        long currentMil = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();

            Date dateformateCurrent = new Date();
            String currentData = sdf.format(dateformateCurrent);
            Date mDateCurrent = sdf.parse(currentData);
            timeInMillisecondsCurrent = mDateCurrent.getTime();
            currentMil = timeInMilliseconds - timeInMillisecondsCurrent;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return currentMil;
    }

    public static String timeFormateforFavourite(String givenDateString) {
        String text = "";
        long timeInMilliseconds = 0, timeInMillisecondsCurrent = 0;
        long currentMil = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();

            Date dateformateCurrent = new Date();
            String currentData = sdf.format(dateformateCurrent);
            Date mDateCurrent = sdf.parse(currentData);
            timeInMillisecondsCurrent = mDateCurrent.getTime();
            currentMil = timeInMilliseconds - timeInMillisecondsCurrent;
            if (currentMil < 0) {
                currentMil = timeInMillisecondsCurrent - timeInMilliseconds;

            }
            long days = TimeUnit.MILLISECONDS.toDays(currentMil);
            currentMil -= TimeUnit.DAYS.toMillis(days);
            long hours = TimeUnit.MILLISECONDS.toHours(currentMil);
            currentMil -= TimeUnit.HOURS.toMillis(hours);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(currentMil);
            currentMil -= TimeUnit.MINUTES.toMillis(minutes);
            text = days + "d" + " " + hours + "hrs" + " " + minutes + "m";


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return text;

    }

    public static boolean checkTimePassed(String givenDateString) {
        boolean flag = false;
        long timeInMilliseconds = 0, timeInMillisecondsCurrent = 0;
        long currentMil = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();

            Date dateformateCurrent = new Date();
            String currentData = sdf.format(dateformateCurrent);
            Date mDateCurrent = sdf.parse(currentData);
            timeInMillisecondsCurrent = mDateCurrent.getTime();
            currentMil = timeInMilliseconds - timeInMillisecondsCurrent;
            if (currentMil < 0) {
                flag = false;
            } else
                flag = true;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return flag;

    }
    public static String getCurrentTimeForsend(){
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date mDate = new Date();
        TimeZone tz = TimeZone.getTimeZone("GMT");
        sdf.setTimeZone(tz);
        String date = sdf.format(mDate);
        return date;
    }
    public static long dateToMillisecond(String duration) {
        SimpleDateFormat sdf;
        Date date = null ;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(duration);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long currentMillisec = date.getTime();
        return currentMillisec;

    }

}

package com.whp.android.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.whp.android.Log;


public class DateUtils {

	private static final String TAG = DateUtils.class.getCanonicalName();

	private static final long DATE_BASE_1970 = 621355968000000000L;
	private static final long MILLIS_TO_NANOS = 10000;
	private static final double MILLIS_TO_HOURS = 1000.0 * 3600.0;

	public static String calendarToString(Calendar date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		return dateFormat.format(date.getTime());
	}

	public static String dateToString(Date date) {
		if (date == null) {
			return "";
		} else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			return dateFormat.format(date.getTime());
		}
	}
	
	public static String calendarToStringWithFormat(Calendar date, String strDateFormat) 
	{
		String sRet = null;
		if((date!=null)&&(strDateFormat!=null))
		{
			DateFormat dateFormat = new SimpleDateFormat(strDateFormat);//"yyyy/MM/dd HH:mm:ss"
			sRet =  dateFormat.format(date.getTime());
		}
		return sRet;
	}


	public static String calendarToDateString(Calendar date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return dateFormat.format(date.getTime());
	}

	public static String calendarToTimeString(Calendar date) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.format(date.getTime());
	}

	public static int getMilliseconsFromString(String strTime) {
		int milliseconds = -1;
		String array[] = strTime.split(":");
		if (array.length == 2) {
			milliseconds = Integer.parseInt(array[0]) * 60 * 1000
					+ Integer.parseInt(array[1]) * 1000;
		} else if (array.length == 3) {
			milliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
					+ Integer.parseInt(array[1]) * 60 * 1000
					+ Integer.parseInt(array[2]) * 1000;
		}
		return milliseconds;
	}

	public static Calendar getCalendarFromString(String strMillisecons) {
		Log.d(TAG, "DateUtils.calendarToString : " + strMillisecons);
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(Long.parseLong(strMillisecons));
		return date;
	}

	public static Calendar ticksToCalendar(long ticks) {

		long millis;
		millis = (ticks - DATE_BASE_1970) / MILLIS_TO_NANOS;

		if (millis < 0) {
			return null;
		} else {
			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(millis);
			return date;
		}
	}

	public static long ticksToMillis(long ticks) {
		return ticks / MILLIS_TO_NANOS;
	}

	public static long calendarToTicks(Calendar date) {
		long ticks;
		ticks = DATE_BASE_1970 + date.getTimeInMillis() * MILLIS_TO_NANOS;
		return ticks;
	}

	public static double millisToFractionalHours(long millis) {
		return ((double) millis) / MILLIS_TO_HOURS;
	}
	
	public static String ticksToTimeAsString(long ticks) {
		String strTime	= "";
		
		long hours		= ticks / 10000 / 1000 / 3600 ; 
		long minutes	= (ticks / 10000 / 1000 / 60) % 60 ;  
				
		strTime	= String.format("%02d:%02d", hours, minutes);
				
		return strTime;
	}
	
	public static String millisToTimeAsString(long millis) {
		String strTime	= "";
		
		long hours		= millis / 1000 / 3600 ; 
		long minutes	= (millis / 1000 / 60) % 60 ;  
				
		strTime	= String.format("%02d:%02d", hours, minutes);
				
		return strTime;
	}
	
	public static Calendar getUTCTime() {
		//return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		return Calendar.getInstance();
	}
}

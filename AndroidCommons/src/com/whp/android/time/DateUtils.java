/*
 * Copyright 2013 Walter Hugo Palladino
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.whp.android.time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.whp.android.Log;

/**
 * @author Walter Hugo Palladino
 * @since 06/07/2013
 * 
 */
public class DateUtils {

	private static final String TAG = DateUtils.class.getCanonicalName();

	private static final long DATE_BASE_1970 = 621355968000000000L;
	private static final long MILLIS_TO_NANOS = 10000;
	private static final double MILLIS_TO_HOURS = 1000.0 * 3600.0;

	/**
	 * calendarToString
	 * 
	 * @param date
	 * @return
	 */
	public static String calendarToString (Calendar date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		return dateFormat.format(date.getTime());
	}

	/**
	 * dateToString
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString (Date date) {
		if (date == null) {
			return "";
		} else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			return dateFormat.format(date.getTime());
		}
	}

	/**
	 * calendarToStringWithFormat
	 * 
	 * @param date
	 * @param strDateFormat
	 * @return
	 */
	public static String calendarToStringWithFormat (Calendar date, String strDateFormat) {
		String sRet = null;
		if ((date != null) && (strDateFormat != null)) {
			DateFormat dateFormat = new SimpleDateFormat(strDateFormat);// "yyyy/MM/dd HH:mm:ss"
			sRet = dateFormat.format(date.getTime());
		}
		return sRet;
	}

	/**
	 * calendarToDateString
	 * 
	 * @param date
	 * @return
	 */
	public static String calendarToDateString (Calendar date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		return dateFormat.format(date.getTime());
	}

	/**
	 * calendarToTimeString
	 * 
	 * @param date
	 * @return
	 */
	public static String calendarToTimeString (Calendar date) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.format(date.getTime());
	}

	/**
	 * getMilliseconsFromString
	 * 
	 * @param strTime
	 * @return
	 */
	public static int getMilliseconsFromString (String strTime) {
		int milliseconds = -1;
		String array[] = strTime.split(":");
		if (array.length == 2) {
			milliseconds = Integer.parseInt(array[0]) * 60 * 1000 + Integer.parseInt(array[1]) * 1000;
		} else if (array.length == 3) {
			milliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000 + Integer.parseInt(array[1]) * 60 * 1000
					+ Integer.parseInt(array[2]) * 1000;
		}
		return milliseconds;
	}

	/**
	 * getCalendarFromString
	 * 
	 * @param strMillisecons
	 * @return
	 */
	public static Calendar getCalendarFromString (String strMillisecons) {
		Log.d(TAG, "DateUtils.calendarToString : " + strMillisecons);
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(Long.parseLong(strMillisecons));
		return date;
	}

	/**
	 * ticksToCalendar
	 * 
	 * @param ticks
	 * @return
	 */
	public static Calendar ticksToCalendar (long ticks) {

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

	/**
	 * ticksToMillis
	 * 
	 * @param ticks
	 * @return
	 */
	public static long ticksToMillis (long ticks) {
		return ticks / MILLIS_TO_NANOS;
	}

	/**
	 * calendarToTicks
	 * 
	 * @param date
	 * @return
	 */
	public static long calendarToTicks (Calendar date) {
		long ticks;
		ticks = DATE_BASE_1970 + date.getTimeInMillis() * MILLIS_TO_NANOS;
		return ticks;
	}

	/**
	 * millisToFractionalHours
	 * 
	 * @param millis
	 * @return
	 */
	public static double millisToFractionalHours (long millis) {
		return ((double) millis) / MILLIS_TO_HOURS;
	}

	/**
	 * ticksToTimeAsString
	 * 
	 * @param ticks
	 * @return
	 */
	public static String ticksToTimeAsString (long ticks) {
		String strTime = "";

		long hours = ticks / 10000 / 1000 / 3600;
		long minutes = (ticks / 10000 / 1000 / 60) % 60;

		strTime = String.format("%02d:%02d", hours, minutes);

		return strTime;
	}

	/**
	 * millisToTimeAsString
	 * 
	 * @param millis
	 * @return
	 */
	public static String millisToTimeAsString (long millis) {
		String strTime = "";

		long hours = millis / 1000 / 3600;
		long minutes = (millis / 1000 / 60) % 60;

		strTime = String.format("%02d:%02d", hours, minutes);

		return strTime;
	}

	/**
	 * getUTCTime
	 * 
	 * @return
	 */
	public static Calendar getUTCTime () {
		// return Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		return Calendar.getInstance();
	}

	/**
	 * getDifference
	 * 
	 * @param cal1
	 * @param cal2
	 * @return
	 */
	public static Calendar getDifference (Calendar cal1, Calendar cal2) {
		Calendar cal = Calendar.getInstance();
		long diff = cal2.getTimeInMillis() - cal1.getTimeInMillis();
		cal.setTimeInMillis(diff);
		return cal;
	}

	/**
	 * getDifferenceAsSTring
	 * 
	 * @param cal1
	 * @param cal2
	 * @return
	 */
	public static String getDifferenceAsSTring (Calendar cal1, Calendar cal2) {
		long diff = cal2.getTimeInMillis() - cal1.getTimeInMillis();
		return millisToTimeAsString(diff);
	}

	/**
	 * 
	 * millisencodsToCalendar
	 *
	 * @param millis
	 * @return
	 */
	public static Calendar millisencodsToCalendar (long millis) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(millis);
		return date;
	}

	/**
	 * 
	 * sencodsToCalendar
	 *
	 * @param secs
	 * @return
	 */
	public static Calendar sencodsToCalendar (long secs) {
		Calendar date = Calendar.getInstance();
		date.setTimeInMillis(secs * 1000);
		return date;
	}

}

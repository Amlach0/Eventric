package com.eventric.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * Return date in specified format.
 * @param milliSeconds Date in milliseconds
 * @param format Date format
 * @return String representing date in specified format
 */
fun getDateFromMilli(milliSeconds: Long, format: String): String {
    // Create a DateFormatter object for displaying date in specified format.
    val formatter = SimpleDateFormat(format)

    // Create a calendar object that will convert the date and time value in milliseconds to date.
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = milliSeconds
    return formatter.format(calendar.time)
}

/**
 * Return milliseconds from a formatted date in specified format.
 * @param dateFormat Formatted Date
 * @param format Date format
 * @return Long representing date in milliseconds
 */
fun getMilliFromDate(dateFormat: String?, format: String): Long {
    var date = Date()
    val formatter = SimpleDateFormat(format)
    try {
        date = formatter.parse(dateFormat)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    println("Today is $date")
    return date.time
}
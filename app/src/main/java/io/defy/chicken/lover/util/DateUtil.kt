package io.defy.chicken.lover.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    /*
     * function : change date foramt
     */
    fun parseDate(time: String): String? {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val outputPattern = "MM-dd HH:mm"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)

        var date: Date? = null
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return str
    }
}
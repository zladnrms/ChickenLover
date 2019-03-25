package io.defy.chicken.lover.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

open class NumberUtil {
    companion object {
        /*
        * function : 1 to 0 , 0 to 1
        */
        fun valueInverse(value : Int) : Int{
            if(value == 1)
                return 0
            else
                return 1
        }
    }
}
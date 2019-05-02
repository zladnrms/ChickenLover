package io.defy.chicken.lover.util

import android.annotation.SuppressLint
import android.support.design.internal.BottomNavigationItemView
import android.support.design.widget.BottomNavigationView
import android.support.design.internal.BottomNavigationMenuView
import android.util.Log
import java.lang.reflect.Field;

object BottomNavigationHelper {
    @SuppressLint("RestrictedApi")
    fun disableShiftMode(view: BottomNavigationView) {
        val menuview = view.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuview.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuview, false)
            shiftingMode.isAccessible = false

            for (i in 0..menuview.childCount) {
                val item = view.getChildAt(0) as BottomNavigationItemView
                item.setShifting(false)
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
            Log.e("BNVHelper", "Unable to get shift mode field", e)
        } catch (e: IllegalAccessException) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e)
        }
    }
}
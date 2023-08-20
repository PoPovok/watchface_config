package hu.popoapps.watchface_config

import android.content.ComponentName
import android.content.Context
import android.content.res.Resources
import androidx.preference.PreferenceManager

object ConfigProvider {
    const val COMPLICATION_ID_NUMBER: String = "complicationIdNumber_"

    fun getComplicationIds(applicationContext: Context, javaClass: Class<*>, defaultSize: Int = 0): IntArray {
        val number = PreferenceManager
            .getDefaultSharedPreferences(applicationContext)
            .getInt(COMPLICATION_ID_NUMBER + getComponentNameString(applicationContext, javaClass), defaultSize)
        return createComplicationIdsIntArray(number)
    }

    fun getComponentName(applicationContext: Context, javaClass: Class<*>): ComponentName{
        return ComponentName(applicationContext, javaClass)
    }

    fun createComplicationIdsIntArray(size: Int): IntArray{
        return List(size) { index -> index }.toIntArray()
    }

    fun getComponentNameString(applicationContext: Context, javaClass: Class<*>): String{
        return getComponentName(applicationContext, javaClass).flattenToString()
    }
}
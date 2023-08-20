package hu.popoapps.watchface_config.model

import android.content.ComponentName

data class WatchFaceInfo(
    val packageName: String,
    val className: String,
    val fullName: String,
    val name: String
) {

    constructor(componentName: ComponentName, name: String?) : this(
        fullName = componentName.className,
        className = componentName.className.run {
            substring(getIndexOfSeparation(this) + 1)
        },
        packageName = componentName.className.run {
            substring(0, getIndexOfSeparation(this))
        },
        name = name ?: componentName.className.run {
            substring(getIndexOfSeparation(this) + 1)
        })

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "packageName" to packageName,
            "className" to className,
            "fullName" to fullName,
            "name" to name
        )
    }

    companion object {
        fun getIndexOfSeparation(fullName: String): Int {
            return fullName.lastIndexOf('.')
        }
    }
}
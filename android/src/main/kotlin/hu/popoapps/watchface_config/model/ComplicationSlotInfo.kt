package hu.popoapps.watchface_config.model

data class ComplicationSlotInfo(
    var complicationInfo: ComplicationInfo?,
    val supportedTypes: List<Int>
){
    fun toMap() :Map<String, Any?>{
        return mapOf(
            "complicationInfo" to complicationInfo?.toMap(),
            "supportedTypes" to supportedTypes
        )
    }
}

package hu.popoapps.watchface_config.util

import hu.popoapps.watchface_config.model.ComplicationSlotInfo

class ComplicationInfoReceiver(
    private val complicationList: List<ComplicationSlotInfo>,
    val onAllInfoReceived: (List<ComplicationSlotInfo>) -> Unit
) {
    private var infoToReceiveSize: Int = complicationList.size

    init {
        checkIfAllInfoReceived()
    }

    private fun checkIfAllInfoReceived() {
        if (infoToReceiveSize == 0) {
            onAllInfoReceived(complicationList)
        }
    }

    fun infoReceived() {
        infoToReceiveSize--
        checkIfAllInfoReceived()
    }
}

package es.adrianmmudarra.hablave.data.model

import java.util.*

class Trip(){
    var uuid: String = ""
    var dateTrip: Long = 0
    var stationOrigin: String = ""
    var stationDest: String = ""
    var price: Double = 0.0
    var owner: String = ""
    var hasTicket: Boolean = false
    var traveler1: String? = null
    var traveler2: String? = null
    var traveler3: String? = null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Trip

        if (uuid != other.uuid) return false

        return true
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }


}
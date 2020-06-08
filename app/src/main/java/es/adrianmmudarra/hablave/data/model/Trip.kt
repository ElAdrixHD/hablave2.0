package es.adrianmmudarra.hablave.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class Trip(): Parcelable{
    var uuid: String = ""
    var dateTrip: Long = 0
    var stationOrigin: String = ""
    var stationDest: String = ""
    var provinceOrigin: String = ""
    var provinceDest: String = ""
    var price: Double = 0.0
    var owner: String = ""
    var ownerName: String = ""
    var hasTicket: Boolean = false
    var traveler1: String? = null
    var traveler2: String? = null
    var traveler3: String? = null
    var traveler1Name: String? = null
    var traveler2Name: String? = null
    var traveler3Name: String? = null

    constructor(parcel: Parcel) : this() {
        uuid = parcel.readString()!!
        dateTrip = parcel.readLong()
        stationOrigin = parcel.readString()!!
        stationDest = parcel.readString()!!
        provinceOrigin = parcel.readString()!!
        provinceDest = parcel.readString()!!
        price = parcel.readDouble()
        owner = parcel.readString()!!
        ownerName = parcel.readString()!!
        hasTicket = parcel.readByte() != 0.toByte()
        traveler1 = parcel.readString()
        traveler2 = parcel.readString()
        traveler3 = parcel.readString()
        traveler1Name = parcel.readString()
        traveler2Name = parcel.readString()
        traveler3Name = parcel.readString()
    }

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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uuid)
        parcel.writeLong(dateTrip)
        parcel.writeString(stationOrigin)
        parcel.writeString(stationDest)
        parcel.writeString(provinceOrigin)
        parcel.writeString(provinceDest)
        parcel.writeDouble(price)
        parcel.writeString(owner)
        parcel.writeString(ownerName)
        parcel.writeByte(if (hasTicket) 1 else 0)
        parcel.writeString(traveler1)
        parcel.writeString(traveler2)
        parcel.writeString(traveler3)
        parcel.writeString(traveler1Name)
        parcel.writeString(traveler2Name)
        parcel.writeString(traveler3Name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Trip> {
        const val TAG = "TRIP"
        override fun createFromParcel(parcel: Parcel): Trip {
            return Trip(parcel)
        }

        override fun newArray(size: Int): Array<Trip?> {
            return arrayOfNulls(size)
        }
    }


}
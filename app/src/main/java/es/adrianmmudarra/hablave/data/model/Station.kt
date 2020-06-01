package es.adrianmmudarra.hablave.data.model

import android.os.Parcel
import android.os.Parcelable

class Station() : Parcelable {

    var station: String = ""
    var city: String = ""
    var latitud: String = ""
    var longitud: String = ""

    constructor(parcel: Parcel) : this() {
        station = parcel.readString()!!
        city = parcel.readString()!!
        latitud = parcel.readString()!!
        longitud = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(station)
        parcel.writeString(city)
        parcel.writeString(latitud)
        parcel.writeString(longitud)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Station> {
        const val TAG = "Station"
        override fun createFromParcel(parcel: Parcel): Station {
            return Station(parcel)
        }

        override fun newArray(size: Int): Array<Station?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "$station - $city"
    }
}
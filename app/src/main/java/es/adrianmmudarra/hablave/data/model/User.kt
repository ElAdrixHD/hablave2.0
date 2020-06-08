package es.adrianmmudarra.hablave.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

class User(): Parcelable {
    var uid: String? = ""
    var email: String? = ""
    var nameAndSurname: String = ""
    var gender: String = ""
    var birthday: String = ""

    constructor(parcel: Parcel) : this() {
        uid = parcel.readString()
        email = parcel.readString()
        nameAndSurname = parcel.readString()!!
        gender = parcel.readString()!!
        birthday = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(email)
        parcel.writeString(nameAndSurname)
        parcel.writeString(gender)
        parcel.writeString(birthday)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        const val TAG = "USER"
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }


}
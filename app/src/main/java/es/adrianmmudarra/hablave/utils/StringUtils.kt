package es.adrianmmudarra.hablave.utils

import java.text.SimpleDateFormat
import java.util.*

private const val EMAIL_REGEX = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"

private const val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,16}"

var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

fun String.isEmailValid(): Boolean {
    return EMAIL_REGEX.toRegex().matches(this)
}

fun String.isSecurePassword(): Boolean{
    return PASSWORD_REGEX.toRegex().matches(this)
}


fun String.getAge(): Int? {
    val dob: Calendar = Calendar.getInstance()
    val today: Calendar = Calendar.getInstance()
    dob.time = sdf.parse(this)
    var age: Int = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
    if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
        age--
    }
    return age
}
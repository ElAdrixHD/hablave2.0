package es.adrianmmudarra.hablave.data.model

import java.util.*

data class User(val uid: String, var email: String) {
    var nameAndSurname: String = ""
    var gender: Gender = Gender.MALE
    var birthday: Date = Calendar.getInstance().time

}
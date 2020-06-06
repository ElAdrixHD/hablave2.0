package es.adrianmmudarra.hablave.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toFormatDate(): String{
    return sdf.format(Date(this))
}
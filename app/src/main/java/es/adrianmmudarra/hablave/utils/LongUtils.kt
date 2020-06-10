package es.adrianmmudarra.hablave.utils

import java.text.SimpleDateFormat
import java.util.*

var sdh: SimpleDateFormat = SimpleDateFormat("HH:mm", Locale.US)


fun Long.toFormatDate(): String{
    return sdf.format(Date(this))
}
fun Long.toFormatHour(): String{
    return sdh.format(Date(this))
}
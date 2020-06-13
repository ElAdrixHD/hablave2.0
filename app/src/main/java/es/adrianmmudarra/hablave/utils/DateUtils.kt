package es.adrianmmudarra.hablave.utils

import java.util.*

fun Date.toFormatDate():Long{
    return sdf.format(this).dateToLong()
}
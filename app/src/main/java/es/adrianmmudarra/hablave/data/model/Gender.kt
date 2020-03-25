package es.adrianmmudarra.hablave.data.model

import es.adrianmmudarra.hablave.R

enum class Gender(val text: Int) {
    MALE(R.string.masculino), FEMALE(R.string.femenino), BINARY(R.string.genero_binario);
}
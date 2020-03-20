package es.adrianmmudarra.hablave.ui.base

interface BaseView<T> {
    fun setPresenter(presenter : T)
    fun onToastError(error: Int)
    fun onSnakbarError(error: Int)
    fun onToastError(error: String)
    fun onSnakbarError(error: String)
}
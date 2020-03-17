package es.adrianmmudarra.hablave.ui.application

import android.app.Application

class HablaveApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object{
        lateinit var context : HablaveApplication
            private set
    }
}
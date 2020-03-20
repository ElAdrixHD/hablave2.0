package es.adrianmmudarra.hablave.ui.application

import android.app.Application
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
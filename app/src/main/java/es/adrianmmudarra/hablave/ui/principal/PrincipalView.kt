package es.adrianmmudarra.hablave.ui.principal

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import es.adrianmmudarra.hablave.R

class PrincipalView : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_principal_view, container, false)
    }

    companion object {
        const val TAG = "PrincipalView"
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            PrincipalView().apply {
                arguments = bundle
            }
    }
}

package es.adrianmmudarra.hablave.ui.principal

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.repository.FirebaseAuthRepository
import kotlinx.android.synthetic.main.fragment_principal_view.*

class PrincipalView : Fragment() {

    private var onPrincipalViewInteract: OnPrincipalViewInteract? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onResume() {
        super.onResume()
        onPrincipalViewInteract?.onResumePrincipalView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onPrincipalViewInteract = context as OnPrincipalViewInteract
    }

    override fun onDetach() {
        super.onDetach()
        onPrincipalViewInteract = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_principal_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    interface OnPrincipalViewInteract{
        fun onResumePrincipalView()
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

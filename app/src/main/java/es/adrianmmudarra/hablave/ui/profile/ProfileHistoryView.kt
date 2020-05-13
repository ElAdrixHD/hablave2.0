package es.adrianmmudarra.hablave.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import es.adrianmmudarra.hablave.R

class ProfileHistoryView : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_history_view, container, false)
    }

    companion object {
        const val TAG = "ProfileHistoryView"
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            ProfileHistoryView().apply {
                arguments = bundle
            }
    }
}

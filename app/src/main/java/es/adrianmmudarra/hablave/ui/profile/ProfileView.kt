package es.adrianmmudarra.hablave.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator

import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.adapter.ProfileViewAdapter
import kotlinx.android.synthetic.main.fragment_profile_view.*

class ProfileView : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance= true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profile_view_pager.adapter = ProfileViewAdapter(this)
        TabLayoutMediator(profile_tab_layout,profile_view_pager){ _, _ -> }.attach()
    }

    companion object {
        const val TAG = "ProfileView"
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            ProfileView().apply {
                arguments = bundle
            }
    }
}

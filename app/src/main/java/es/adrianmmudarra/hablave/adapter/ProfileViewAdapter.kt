package es.adrianmmudarra.hablave.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import es.adrianmmudarra.hablave.ui.profile.ProfileDataView

class ProfileViewAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var tmp: Fragment? = null
        when (position){
            0-> tmp = ProfileDataView.newInstance(null)
            1-> tmp = ProfileDataView.newInstance(null)
        }
        return tmp!!
    }
}
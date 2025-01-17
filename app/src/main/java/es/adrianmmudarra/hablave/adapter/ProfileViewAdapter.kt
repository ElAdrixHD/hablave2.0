package es.adrianmmudarra.hablave.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import es.adrianmmudarra.hablave.ui.profile.ProfileDataPresenter
import es.adrianmmudarra.hablave.ui.profile.ProfileDataView
import es.adrianmmudarra.hablave.ui.profile.ProfileHistoryPresenter
import es.adrianmmudarra.hablave.ui.profile.ProfileHistoryView

class ProfileViewAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }


    override fun createFragment(position: Int): Fragment {
        saveState()
        var tmp: Fragment? = null
        when (position){
            0->{
                tmp = ProfileDataView.newInstance(null)
                val presenter = ProfileDataPresenter(tmp)
                tmp.setPresenter(presenter)
            }
            1-> {
                tmp = ProfileHistoryView.newInstance(null)
                val presenter = ProfileHistoryPresenter(tmp)
                tmp.setPresenter(presenter)
            }
        }
        return tmp!!
    }


}
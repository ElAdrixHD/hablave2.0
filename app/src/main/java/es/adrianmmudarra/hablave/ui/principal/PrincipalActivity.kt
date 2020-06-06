package es.adrianmmudarra.hablave.ui.principal

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.snackbar.Snackbar
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.ui.create.CreateTripPresenter
import es.adrianmmudarra.hablave.ui.create.CreateTripView
import es.adrianmmudarra.hablave.ui.login.LoginActivity
import es.adrianmmudarra.hablave.ui.profile.ProfileDataView
import es.adrianmmudarra.hablave.ui.profile.ProfileView
import es.adrianmmudarra.hablave.ui.search.SearchTripContract
import es.adrianmmudarra.hablave.ui.search.SearchTripPresenter
import es.adrianmmudarra.hablave.ui.search.SearchTripView
import es.adrianmmudarra.hablave.ui.searchList.SearchTripListView
import kotlinx.android.synthetic.main.layout_main.*
import kotlinx.android.synthetic.main.layout_principal.*

class PrincipalActivity : AppCompatActivity(), PrincipalView.OnPrincipalViewInteract , ProfileDataView.OnProfileDataInterface, CreateTripView.OnCreateTripInterface, ProfileView.OnProfileViewInterface, SearchTripView.OnSearchTripInterface{

    lateinit var mainCoordinator : CoordinatorLayout
        private set

    private var principalView: PrincipalView? = null
    private var profileView: ProfileView? = null

    private var createTripView: CreateTripView? = null
    private var createTripPresenter: CreateTripPresenter? = null

    private var searchTripView: SearchTripView? = null
    private var searchTripPresenter: SearchTripPresenter? = null

    private var searchListTripView: SearchTripListView? = null

    private var doubleBack = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        initialise()
    }

    private fun initialise() {
        mainCoordinator = main_coordinator
        my_toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(my_toolbar)

        bottom_nav_menu.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.nav_trip -> {
                    if (bottom_nav_menu.selectedItemId != R.id.nav_trip)
                        showPrincipal()
                }
                R.id.nav_search -> {
                    if (bottom_nav_menu.selectedItemId != R.id.nav_search)
                        showSearch()
                }
                R.id.nav_create -> {
                    if (bottom_nav_menu.selectedItemId != R.id.nav_create)
                        showCreate()
                }
                R.id.nav_chat -> showChats()
                R.id.nav_profile -> {
                    if (bottom_nav_menu.selectedItemId != R.id.nav_profile)
                        showProfile()
                }
            }
            return@setOnNavigationItemSelectedListener true
        }

        initPrincipalFragment()
    }

    private fun showProfile() {
        profileView = supportFragmentManager.findFragmentByTag(ProfileView.TAG) as ProfileView?
        if (profileView == null){
            profileView = ProfileView.newInstance(null)
        }
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.contenido, profileView!!, ProfileView.TAG)
            .addToBackStack(null)
            .commit()

    }

    private fun showChats() {
        Snackbar.make(mainCoordinator, "Chats", Snackbar.LENGTH_SHORT).show()
    }

    private fun showCreate() {
        createTripView = supportFragmentManager.findFragmentByTag(CreateTripView.TAG) as CreateTripView?
        if (createTripView == null){
            createTripView = CreateTripView.newInstance(null)
        }
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.contenido, createTripView!!, CreateTripView.TAG)
            .addToBackStack(null)
            .commit()
        createTripPresenter = CreateTripPresenter(createTripView!!)
        createTripView?.setPresenter(createTripPresenter!!)

    }

    private fun showSearch() {
        searchTripView = supportFragmentManager.findFragmentByTag(SearchTripView.TAG) as SearchTripView?
        if (searchTripView == null){
            searchTripView = SearchTripView.newInstance(null)
        }
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.contenido, searchTripView!!, SearchTripView.TAG)
            .addToBackStack(null)
            .commit()

        searchTripPresenter = SearchTripPresenter(searchTripView!!)
        searchTripView?.setPresenter(searchTripPresenter!!)
    }

    private fun showPrincipal() {
        principalView = supportFragmentManager.findFragmentByTag(PrincipalView.TAG) as PrincipalView?
        if (principalView == null){
            principalView = PrincipalView.newInstance(null)
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.contenido, principalView!!, PrincipalView.TAG)
            .addToBackStack(null)
            .commit()

        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun initPrincipalFragment() {
        principalView = supportFragmentManager.findFragmentByTag(PrincipalView.TAG) as PrincipalView?
        if (principalView == null){
            principalView = PrincipalView.newInstance(null)
        }

        supportFragmentManager
            .beginTransaction()
            .add(R.id.contenido, principalView!!, PrincipalView.TAG)
            .commit()
    }

    override fun onBackPressed() {
        if (principalView?.isVisible!!){
            if (doubleBack) {
                super.onBackPressed()
                return
            }

            this.doubleBack = true
            Toast.makeText(this, getString(R.string.pulsar_de_nuevo_para_salir), Toast.LENGTH_SHORT).show()

            Handler().postDelayed({ doubleBack = false }, 2000)
        }else{
            supportFragmentManager.popBackStack()
        }
    }

    override fun onResumePrincipalView() {
        if(bottom_nav_menu.selectedItemId != R.id.nav_trip){
            bottom_nav_menu.selectedItemId = R.id.nav_trip
        }
    }

    override fun onLogOut() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onCancel() {
        onBackPressed()
    }

    override fun onSearchTrip(bundle: Bundle) {
        searchListTripView = supportFragmentManager.findFragmentByTag(SearchTripListView.TAG) as SearchTripListView?
        if (searchListTripView == null){
            searchListTripView = SearchTripListView.newInstance(bundle)
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.contenido, searchListTripView!!, SearchTripListView.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onSuccessCreate() {
        TODO("Not yet implemented")
    }

    override fun onResumeCreateTrip() {
        if(bottom_nav_menu.selectedItemId != R.id.nav_create){
            bottom_nav_menu.selectedItemId = R.id.nav_create
        }
    }

    override fun onResumeProfileView() {
        if(bottom_nav_menu.selectedItemId != R.id.nav_profile){
            bottom_nav_menu.selectedItemId = R.id.nav_profile
        }
    }

    override fun onResumeSearchTrip() {
        if(bottom_nav_menu.selectedItemId != R.id.nav_search){
            bottom_nav_menu.selectedItemId = R.id.nav_search
        }
    }
}

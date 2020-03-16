package es.adrianmmudarra.hablave.ui.principal

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.internal.DescendantOffsetUtils
import es.adrianmmudarra.hablave.R
import kotlinx.android.synthetic.main.layout_main.*
import kotlinx.android.synthetic.main.layout_principal.*

class PrincipalActivity : AppCompatActivity() {

    lateinit var mainCoordinator : CoordinatorLayout
        private set

    private var principalView: PrincipalView? = null

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

        }

        initPrincipalFragment()
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

}

package es.adrianmmudarra.hablave.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.repository.FirebaseAuthRepository
import es.adrianmmudarra.hablave.ui.login.LoginActivity
import es.adrianmmudarra.hablave.ui.principal.PrincipalActivity
import kotlinx.android.synthetic.main.fragment_profile_data_view.*

class ProfileDataView : Fragment(), ProfileDataContract.View {

    private var presenter: ProfileDataContract.Presenter? = null
    private var onProfileDataInterface: OnProfileDataInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onStart() {
        super.onStart()
        presenter?.loadData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_data_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogOut.setOnClickListener {
            presenter?.logOut()
            onProfileDataInterface?.onLogOut()
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onProfileDataInterface = context as OnProfileDataInterface
    }

    override fun onDetach() {
        super.onDetach()
        onProfileDataInterface = null
    }

    interface OnProfileDataInterface{
        fun onLogOut()
    }

    companion object {
       const val TAG = "ProfileDataView"
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            ProfileDataView().apply {
                arguments = bundle
            }
    }

    override fun onNameError(error: Int) {
        tiledProfileName.error = getString(error)
    }

    override fun onClearName() {
        tiledProfileName.error = null
    }

    override fun onDateError(error: Int) {
        tiledProfileBirthday.error = getString(error)
    }

    override fun onClearDate() {
        tiledProfileBirthday.error = null
    }

    override fun onGenderError(error: Int) {
        tiledProfileGender.error = getString(error)
    }

    override fun onClearGender() {
        tiledProfileGender.error = null
    }

    override fun onSuccessGetAuth(name: String, email: String) {
        tiledProfileName.setText(name)
        tiledProfileEmail.setText(email)
    }

    override fun onSuccessGetDatabase(date: String, gender: String) {
        tiledProfileGender.setText(gender)
        tiledProfileBirthday.setText(date)
    }

    override fun setPresenter(presenter: ProfileDataContract.Presenter) {
        this.presenter = presenter
    }

    override fun onToastError(error: Int) {
        Toast.makeText(context, getString(error), Toast.LENGTH_SHORT).show()
    }

    override fun onToastError(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
    }

    override fun onSnakbarError(error: Int) {
        Snackbar.make((activity as PrincipalActivity).mainCoordinator,getString(error), Snackbar.LENGTH_SHORT).show()
    }

    override fun onSnakbarError(error: String) {
        Snackbar.make((activity as PrincipalActivity).mainCoordinator,error, Snackbar.LENGTH_SHORT).show()
    }
}

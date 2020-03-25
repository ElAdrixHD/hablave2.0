package es.adrianmmudarra.hablave.ui.register

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseUser

import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.model.Gender
import es.adrianmmudarra.hablave.data.model.User
import kotlinx.android.synthetic.main.fragment_register_email_view.*
import java.util.*

class RegisterEmailView : Fragment(), RegisterContract.View {

    private var onRegisterEmailViewInteract: OnRegisterEmailViewInteract? = null

    private var presenter: RegisterContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onRegisterEmailViewInteract = context as OnRegisterEmailViewInteract
    }

    override fun onDetach() {
        super.onDetach()
        onRegisterEmailViewInteract = null
        presenter = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_email_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var user: User? = null
        if (arguments != null){
            user = arguments?.getParcelable<User>(User.TAG) as User
            tiledRegisterName.setText(user.nameAndSurname)
            tiledRegisterEmail.setText(user.email)
            tiledRegisterEmail.isEnabled = false
        }
        tiledRegisterBirthday.setOnClickListener{
            clickDataPicker()
        }

        tiledRegisterGender.setOnClickListener{
            val adapter: ArrayAdapter<Gender> = ArrayAdapter(context!!, android.R.layout.simple_expandable_list_item_1, Gender.values())

            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context!!)
                .setSingleChoiceItems(
                    adapter,
                    0
                ) { dialog, item ->

                    (it as TextInputEditText).setText(adapter.getItem(item).toString())
                    dialog.dismiss() }

            alertDialog.create().show()
        }

        btnRegisterCancel.setOnClickListener {
            onRegisterEmailViewInteract?.onCancel()
        }
        btnRegisterConfirm.setOnClickListener {
            if (arguments != null){
                presenter?.checkData(tiledRegisterName.text.toString(),tiledRegisterBirthday.text.toString(), tiledRegisterGender.text.toString(), tiledRegisterEmail.text.toString(), tiledRegisterPassword.text.toString(),user)
            }else{
                presenter?.checkData(tiledRegisterName.text.toString(),tiledRegisterBirthday.text.toString(), tiledRegisterGender.text.toString(), tiledRegisterEmail.text.toString(), tiledRegisterPassword.text.toString())
            }
        }
    }

    companion object {
       const val TAG = "RegisterEmailView"
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            RegisterEmailView().apply {
                arguments = bundle
            }
    }

    interface OnRegisterEmailViewInteract{
        fun onCancel()
        fun onSuccessRegister()
    }

    override fun onNameError(error: Int) {
        tiledRegisterName.error = getString(error)
    }

    override fun onClearName() {
        tiledRegisterName.error = null
    }

    override fun onDateError(error: Int) {
        tiledRegisterBirthday.error = getString(error)
    }

    override fun onClearDate() {
        tiledRegisterBirthday.error = null
    }

    override fun onEmailError(error: Int) {
        tiledRegisterEmail.error = getString(error)
    }

    override fun onClearEmail() {
        tiledRegisterEmail.error = null
    }

    override fun onPasswordError(error: Int) {
        tiledRegisterPassword.error = getString(error)
    }

    override fun onClearPassword() {
        tiledRegisterPassword.error = null
    }

    override fun onGenderError(error: Int) {
        tiledRegisterGender.error = getString(error)
    }

    override fun onClearGender() {
        tiledRegisterGender.error = null
    }

    override fun onSuccessAuthRegister(user: FirebaseUser) {
        presenter?.registerDatabase(user.uid, tiledRegisterEmail.text.toString(), tiledRegisterName.text.toString(), tiledRegisterBirthday.text.toString(), tiledRegisterGender.text.toString())
    }

    override fun onSuccessDatabaseRegister() {
        onRegisterEmailViewInteract?.onSuccessRegister()
    }

    override fun onFailedRegister(error: Int) {
        MaterialAlertDialogBuilder(context, R.style.AlertDialogTheme)
            .setMessage(error)
            .setTitle(R.string.error_al_registrarse)
            .setPositiveButton(android.R.string.ok,null)
            .setIcon(R.drawable.ic_warning_24dp)
            .show()
    }

    override fun showLoading() {
        progressBarRegister.visibility = View.VISIBLE
    }

    override fun disableLoading() {
        progressBarRegister.visibility = View.INVISIBLE
    }

    override fun setPresenter(presenter: RegisterContract.Presenter) {
        this.presenter = presenter
    }

    override fun onToastError(error: Int) {
        Toast.makeText(context, getString(error),Toast.LENGTH_SHORT).show()
    }

    override fun onToastError(error: String) {
        Toast.makeText(context, error,Toast.LENGTH_SHORT).show()
    }

    override fun onSnakbarError(error: Int) {
        Snackbar.make(view!!, getString(error),Snackbar.LENGTH_SHORT).show()
    }

    override fun onSnakbarError(error: String) {
        Snackbar.make(view!!, error,Snackbar.LENGTH_SHORT).show()
    }

    private fun clickDataPicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            tiledRegisterBirthday.setText("${String.format("%02d",dayOfMonth)}-${String.format("%02d",monthOfYear+1)}-$year")

        }, year, month, day)
        dpd.datePicker.maxDate = c.timeInMillis
        dpd.show()
    }
}

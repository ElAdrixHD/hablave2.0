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
import kotlinx.android.synthetic.main.fragment_register_view.*
import java.util.*
import kotlin.collections.ArrayList

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
        return inflater.inflate(R.layout.fragment_register_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var user: User? = null
        if (arguments != null){
            tvRegister.visibility = View.VISIBLE
            tilRegisterPassword.visibility = View.GONE
            user = arguments?.getParcelable<User>(User.TAG) as User
            tiledRegisterName.setText(user.nameAndSurname)
            tiledRegisterEmail.setText(user.email)
            tiledRegisterEmail.isEnabled = false

            btnRegisterCancel.setOnClickListener {
                presenter?.deleteUser()
            }

        }else{
            btnRegisterCancel.setOnClickListener {
                onRegisterEmailViewInteract?.onCancel()
            }
        }
        tiledRegisterBirthday.setOnClickListener{
            clickDataPicker()
        }

        tiledRegisterGender.setOnClickListener{
            val array = ArrayList<String>()
            for (item: Gender in Gender.values()){
                array.add(getString(item.text))
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter(context!!, android.R.layout.simple_expandable_list_item_1, array)

            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context!!)
                .setSingleChoiceItems(
                    adapter,
                    0
                ) { dialog, item ->

                    (it as TextInputEditText).setText(adapter.getItem(item))
                    dialog.dismiss() }

            alertDialog.create().show()
        }

        btnRegisterConfirm.setOnClickListener {
            if (arguments != null){
                presenter?.checkDataGoogle(tiledRegisterName.text.toString(),tiledRegisterBirthday.text.toString(), tiledRegisterGender.text.toString(), tiledRegisterEmail.text.toString(),user)
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
        tilRegisterName.error = getString(error)
    }

    override fun onClearName() {
        tilRegisterName.error = null
    }

    override fun onDateError(error: Int) {
        tilRegisterBirthday.error = getString(error)
    }

    override fun onClearDate() {
        tilRegisterBirthday.error = null
    }

    override fun onEmailError(error: Int) {
        tilRegisterEmail.error = getString(error)
    }

    override fun onClearEmail() {
        tilRegisterEmail.error = null
    }

    override fun onPasswordError(error: Int) {
        tilRegisterPassword.error = getString(error)
    }

    override fun onClearPassword() {
        tilRegisterPassword.error = null
    }

    override fun onGenderError(error: Int) {
        tilRegisterGender.error = getString(error)
    }

    override fun onClearGender() {
        tilRegisterGender.error = null
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

    override fun onSuccessCancel() {
        onRegisterEmailViewInteract?.onCancel()
    }

    override fun onDateUnderAge(dateUnderage: Int) {
        tiledRegisterBirthday.error = getString(dateUnderage)
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

        val dpd = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, myYear, monthOfYear, dayOfMonth ->
            tiledRegisterBirthday.setText("$myYear-${String.format("%02d",monthOfYear+1)}-${String.format("%02d",dayOfMonth)}")

        }, year, month, day)
        dpd.datePicker.maxDate = c.timeInMillis
        dpd.show()
    }
}

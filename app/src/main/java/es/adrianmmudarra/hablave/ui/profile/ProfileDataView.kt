package es.adrianmmudarra.hablave.ui.profile

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.opengl.Visibility
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.model.Gender
import es.adrianmmudarra.hablave.ui.principal.PrincipalActivity
import kotlinx.android.synthetic.main.fragment_profile_data_view.*
import kotlinx.android.synthetic.main.fragment_register_view.*
import java.util.*
import kotlin.collections.ArrayList

class ProfileDataView : Fragment(), ProfileDataContract.View {

    private var presenter: ProfileDataContract.Presenter? = null
    private var onProfileDataInterface: OnProfileDataInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        setHasOptionsMenu(true)
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

        tiledProfileBirthday.setOnClickListener {
            clickDataPicker()
        }

        tiledProfileGender.setOnClickListener {
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

        btnLogOut.setOnClickListener {
            presenter?.logOut()
            onProfileDataInterface?.onLogOut()
        }

        btnProfileSaveData.setOnClickListener {
            presenter?.updateProfile(tiledProfileName.text.toString(), tiledProfileBirthday.text.toString(), tiledProfileGender.text.toString())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.changePasswordProfileMenu -> {
                MaterialAlertDialogBuilder(context!!, R.style.AlertDialogTheme)
                    .setTitle(getString(R.string.cambiar_contrase_a))
                    .setMessage(getString(R.string.cambiar_contrasenia_mensaje))
                    .setPositiveButton(android.R.string.ok){ dialogInterface: DialogInterface, _: Int ->
                        presenter?.changePassword()
                        presenter?.logOut()
                        onProfileDataInterface?.onLogOut()
                        dialogInterface.dismiss()
                    }
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(R.drawable.ic_warning_24dp)
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
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
        tilProfileName.error = getString(error)
    }

    override fun onClearName() {
        tilProfileName.error = null
    }

    override fun onDateError(error: Int) {
        tilProfileBirthday.error = getString(error)
    }

    override fun onClearDate() {
        tilProfileBirthday.error = null
    }

    override fun onGenderError(error: Int) {
        tilProfileGender.error = getString(error)
    }

    override fun showLoading() {
        progressBarProfile.visibility = View.VISIBLE
    }

    override fun disableLoading() {
        progressBarProfile.visibility = View.INVISIBLE
    }

    override fun onClearGender() {
        tilProfileGender.error = null
    }

    override fun onSuccessGetDatabase(
        date: String,
        gender: String,
        name: String,
        email: String
    ) {
        tiledProfileName.setText(name)
        tiledProfileEmail.setText(email)
        tiledProfileGender.setText(gender)
        tiledProfileBirthday.setText(date)
        btnProfileSaveData.isEnabled = true
    }

    override fun onSuccessUpdateDatabase() {
        onSnakbarError(R.string.profile_updated)
    }

    override fun onDateUnderAge(error: Int) {
        tilProfileBirthday.error = getString(error)
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

    private fun clickDataPicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, myYear, monthOfYear, dayOfMonth ->
            tiledProfileBirthday.setText("$myYear-${String.format("%02d",monthOfYear+1)}-${String.format("%02d",dayOfMonth)}")

        }, year, month, day)
        dpd.datePicker.maxDate = c.timeInMillis
        dpd.show()
    }
}

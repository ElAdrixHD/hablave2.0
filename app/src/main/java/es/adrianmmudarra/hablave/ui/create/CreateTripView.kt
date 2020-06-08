package es.adrianmmudarra.hablave.ui.create

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
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.model.Gender
import es.adrianmmudarra.hablave.data.model.Station
import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.ui.principal.PrincipalActivity
import kotlinx.android.synthetic.main.fragment_create_trip_view.*
import kotlinx.android.synthetic.main.fragment_register_view.*
import java.util.*
import kotlin.collections.ArrayList

class CreateTripView : Fragment(), CreateTripContract.View {

    private var presenter: CreateTripContract.Presenter? = null
    private var activity: OnCreateTripInterface? = null

    private val stations : ArrayList<Station> = ArrayList()
    private var adapter: ArrayAdapter<Station>? = null

    private var stationOrigin: Station? = null
    private var stationDest: Station? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onStart() {
        super.onStart()
        presenter?.getStations()
    }

    override fun onResume() {
        super.onResume()
        activity?.onResumeCreateTrip()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_trip_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tiledCreateTripStationOrigin.setOnClickListener {
            adapter = ArrayAdapter(context!!, android.R.layout.simple_expandable_list_item_1, stations)
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context!!)
                .setSingleChoiceItems(
                    adapter,
                    0
                ) { dialog, item ->
                    stationOrigin = adapter!!.getItem(item)
                    (it as TextInputEditText).setText(adapter!!.getItem(item).toString())
                    dialog.dismiss() }

            alertDialog.create().show()
        }
        tiledCreateTripStationDest.setOnClickListener {
            adapter = ArrayAdapter(context!!, android.R.layout.simple_expandable_list_item_1, stations)
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context!!)
                .setSingleChoiceItems(
                    adapter,
                    0
                ) { dialog, item ->
                    stationDest = adapter!!.getItem(item)
                    (it as TextInputEditText).setText(adapter!!.getItem(item).toString())
                    dialog.dismiss() }

            alertDialog.create().show()
        }

        tiledCreateTripDate.setOnClickListener {
            clickDataPicker()
        }

        btnCreateTripCreate.setOnClickListener {
            if (stationOrigin != null && stationDest != null){
                presenter?.createTrip(stationOrigin!!, stationDest!!, tiledCreateTripDate.text.toString(), tiledCreateTripPrice.text.toString(), swCreateTripTicket.isChecked)
            }else{
               onToastError("Selecciona las estaciones")
            }
        }

        btnCreateTripCancel.setOnClickListener {
            activity?.onCancel()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as OnCreateTripInterface
    }

    override fun onDetach() {
        super.onDetach()
        presenter = null
        activity = null
    }

    interface OnCreateTripInterface{
        fun onCancel()
        fun onSuccessCreate(trip: Trip)
        fun onResumeCreateTrip()
    }

    companion object {
        const val TAG = "CreateTripView"
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            CreateTripView().apply {
                arguments = bundle
            }
    }

    override fun onErrorStationOrigin(error: Int) {
        tilCreateTripStationOrigin.error = getString(error)
    }

    override fun onClearStationOrigin() {
        tilCreateTripStationOrigin.error = null
    }

    override fun onErrorStationDest(error: Int) {
        tilCreateTripStationDest.error = getString(error)
    }

    override fun onClearStationDest() {
        tilCreateTripStationDest.error = null
    }

    override fun onErrorDateTrip(error: Int) {
        tilCreateTripDate.error = getString(error)
    }

    override fun onClearDateTrip() {
        tilCreateTripDate.error = null
    }

    override fun onErrorPriceTrip(error: Int) {
        tilCreateTripPrice.error = getString(error)
    }

    override fun onClearPriceTrip() {
        tilCreateTripPrice.error = null
    }

    override fun setStations(stations: ArrayList<Station>) {
        this.stations.clear()
        this.stations.addAll(stations)
        this.adapter?.notifyDataSetChanged()
    }

    override fun onSuccessCreateTrip(trip: Trip) {
        activity?.onSuccessCreate(trip)
    }

    override fun setPresenter(presenter: CreateTripContract.Presenter) {
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
            tiledCreateTripDate.setText("$myYear-${String.format("%02d",monthOfYear+1)}-${String.format("%02d",dayOfMonth)}")

        }, year, month, day)
        dpd.datePicker.minDate = c.add(Calendar.DAY_OF_MONTH,1).let { c.timeInMillis }
        dpd.show()
    }
}
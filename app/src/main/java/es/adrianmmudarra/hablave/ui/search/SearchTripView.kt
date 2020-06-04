package es.adrianmmudarra.hablave.ui.search

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
import es.adrianmmudarra.hablave.data.model.Station
import es.adrianmmudarra.hablave.ui.principal.PrincipalActivity
import kotlinx.android.synthetic.main.fragment_create_trip_view.*
import kotlinx.android.synthetic.main.fragment_search_trip_view.*
import java.util.*
import kotlin.collections.ArrayList

class SearchTripView : Fragment(), SearchTripContract.View {

    private var activity: OnSearchTripInterface? = null
    private var presenter: SearchTripContract.Presenter? = null

    private val stations : ArrayList<Station> = ArrayList()
    private var adapter: ArrayAdapter<Station>? = null

    private var stationOrigin: Station? = null
    private var stationDest: Station? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onResume() {
        super.onResume()
        activity?.onResumeSearchTrip()
    }

    override fun onStart() {
        super.onStart()
        presenter?.loadStations()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as OnSearchTripInterface
    }

    override fun onDetach() {
        super.onDetach()
        activity = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_trip_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tiledSearchTripStationOrigin.setOnClickListener {
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
        tiledSearchTripStationDest.setOnClickListener {
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

        tiledSearchTripDate.setOnClickListener {
            clickDataPicker()
        }

        btnSearchTripCancel.setOnClickListener {
            activity?.onCancel()
        }
    }

    interface OnSearchTripInterface{
        fun onResumeSearchTrip()
        fun onCancel()
    }

    companion object {
        const val TAG = "SearchTripView"
        @JvmStatic
        fun newInstance(bundle: Bundle?) =
            SearchTripView().apply {
                arguments = bundle
            }
    }

    private fun clickDataPicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(context!!, DatePickerDialog.OnDateSetListener { _, myYear, monthOfYear, dayOfMonth ->
            tiledSearchTripDate.setText("$myYear-${String.format("%02d",monthOfYear+1)}-${String.format("%02d",dayOfMonth)}")

        }, year, month, day)
        dpd.datePicker.minDate = c.add(Calendar.DAY_OF_MONTH,1).let { c.timeInMillis }
        dpd.show()
    }

    override fun onErrorStationOrigin(error: Int) {
        tilSearchTripStationOrigin.error = getString(error)
    }

    override fun onClearStationOrigin() {
        tilSearchTripStationOrigin.error = null
    }

    override fun onErrorStationDest(error: Int) {
        tilSearchTripStationDest.error = getString(error)
    }

    override fun onClearStationDest() {
        tilSearchTripStationDest.error = null
    }

    override fun onErrorDateTrip(error: Int) {
        tilSearchTripDate.error = getString(error)
    }

    override fun onClearDateTrip() {
        tilSearchTripDate.error = null
    }

    override fun setStations(station: ArrayList<Station>) {
        this.stations.clear()
        this.stations.addAll(station)
        this.adapter?.notifyDataSetChanged()
    }

    override fun setPresenter(presenter: SearchTripContract.Presenter) {
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
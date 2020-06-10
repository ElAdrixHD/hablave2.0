package es.adrianmmudarra.hablave.ui.confirm

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import es.adrianmmudarra.hablave.HablaveApplication
import es.adrianmmudarra.hablave.R
import es.adrianmmudarra.hablave.data.model.Trip
import es.adrianmmudarra.hablave.ui.principal.PrincipalActivity
import es.adrianmmudarra.hablave.utils.toFormatDate
import kotlinx.android.synthetic.main.fragment_confirm_trip_view.*

class ConfirmTripView : Fragment(), ConfirmTripContract.View{

    private var presenter: ConfirmTripContract.Presenter? = null
    private var activity: OnConfirmTripInterface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onStart() {
        super.onStart()
        presenter?.loadData(arguments?.getParcelable(Trip.TAG)!!)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as OnConfirmTripInterface
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
        return inflater.inflate(R.layout.fragment_confirm_trip_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnConfirmTripCancel.setOnClickListener {
            activity?.onCancel()
        }

        btnConfirmTripDelete.setOnClickListener {
            MaterialAlertDialogBuilder(context!!, R.style.AlertDialogTheme)
                .setTitle(getString(R.string.borrar_viaje))
                .setMessage(getString(R.string.pregunta_viaje_eliminar))
                .setPositiveButton(android.R.string.ok){ _: DialogInterface, _: Int ->
                    presenter?.deleteTrip(arguments?.getParcelable(Trip.TAG)!!)
                    activity?.onDeletedTrip()
                }
                .setNegativeButton(android.R.string.no){ d: DialogInterface, _: Int ->
                    d.dismiss()
                }
                .setIcon(R.drawable.ic_warning_24dp)
                .show()
        }

        btnConfirmTripReserve.setOnClickListener {
            presenter?.reserveTrip(arguments?.getParcelable(Trip.TAG)!!)
        }

        btnConfirmTripCancelReserve.setOnClickListener {
            MaterialAlertDialogBuilder(context!!, R.style.AlertDialogTheme)
                .setTitle(getString(R.string.borrar_reserva))
                .setMessage(getString(R.string.pregunta_reserva_eliminar))
                .setPositiveButton(android.R.string.ok){ _: DialogInterface, _: Int ->
                    presenter?.cancelReserve(arguments?.getParcelable(Trip.TAG)!!)
                }
                .setNegativeButton(android.R.string.no){ d: DialogInterface, _: Int ->
                    d.dismiss()
                }
                .setIcon(R.drawable.ic_warning_24dp)
                .show()
        }

        btnConfirmTripJoinChat.setOnClickListener {
            activity?.onJoinChat(arguments?.getParcelable<Trip>(Trip.TAG)?.uuid!!)
        }

    }

    interface OnConfirmTripInterface{
        fun onCancel()
        fun onDeletedTrip()
        fun onJoinChat(uid:String)
    }

    companion object {
        const val TAG = "ConfirmTripView"
        @JvmStatic
        fun newInstance(bundle: Bundle) =
            ConfirmTripView().apply {
                arguments = bundle
            }
    }

    override fun updatedTrip(trip: Trip) {
        if (this.isVisible){
            tvConfirmTripDate.text = trip.dateTrip.toFormatDate()
            tvConfirmTripCreator.text = trip.ownerName
            tvConfirmTripMate1Name.text = trip.traveler1Name ?: getString(R.string.plaza_libre)
            tvConfirmTripMate2Name.text = trip.traveler2Name ?: getString(R.string.plaza_libre)
            tvConfirmTripMate3Name.text = trip.traveler3Name ?: getString(R.string.plaza_libre)
            tvConfirmTripProvinceOrigin.text = trip.provinceOrigin
            tvConfirmTripProvinceDestination.text = trip.provinceDest
            tvConfirmTripOriginStation.text = trip.stationOrigin
            tvConfirmTripDestinationStation.text = trip.stationDest
            tvConfirmTripPriceGeneral.text = "${trip.price} €"
            tvConfirmTripPrice2Person.text = (trip.price/2).toString()
            tvConfirmTripPrice3Person.text = (trip.price/3).toString()
            tvConfirmTripPrice4Person.text = (trip.price/4).toString()

            if (trip.hasTicket){
                ivConfirmTripHasTicket.visibility = View.VISIBLE
            }else{
                ivConfirmTripHasTicket.visibility = View.GONE
            }

            if (trip.owner == HablaveApplication.context.user?.uid){
                btnConfirmTripDelete.visibility = View.VISIBLE
                btnConfirmTripReserve.visibility = View.GONE
                btnConfirmTripJoinChat.visibility = View.VISIBLE
                btnConfirmTripDelete.isEnabled = !(trip.traveler1 != null|| trip.traveler2 != null || trip.traveler3 != null)
            }else{
                btnConfirmTripDelete.visibility = View.GONE
                btnConfirmTripReserve.visibility = View.VISIBLE
                btnConfirmTripJoinChat.visibility = View.GONE
                if(trip.traveler1 == HablaveApplication.context.user?.uid || trip.traveler2 == HablaveApplication.context.user?.uid || trip.traveler3 == HablaveApplication.context.user?.uid){
                    btnConfirmTripCancelReserve.visibility = View.VISIBLE
                    btnConfirmTripReserve.visibility = View.GONE
                    btnConfirmTripJoinChat.visibility = View.VISIBLE
                }else{
                    btnConfirmTripCancelReserve.visibility = View.GONE
                    btnConfirmTripReserve.visibility = View.VISIBLE
                    btnConfirmTripJoinChat.visibility = View.GONE
                }
            }
        }
    }

    override fun deletedTrip() {
        if (arguments?.getParcelable<Trip>(Trip.TAG)?.owner != HablaveApplication.context.user?.uid){
            if (this.isVisible){
                MaterialAlertDialogBuilder(context!!, R.style.AlertDialogTheme)
                    .setTitle(getString(R.string.trip_deleted))
                    .setMessage(getString(R.string.mensaje_viaje_eliminado))
                    .setPositiveButton(android.R.string.ok){ dialogInterface: DialogInterface, _: Int ->
                        activity?.onDeletedTrip()
                        dialogInterface.dismiss()
                    }
                    .setIcon(R.drawable.ic_warning_24dp)
                    .show()
            }
        }
    }

    override fun onSuccessReserve() {
        MaterialAlertDialogBuilder(context!!, R.style.AlertDialogTheme)
            .setTitle(getString(R.string.trip_reserved))
            .setMessage(getString(R.string.mensaje_plaza_reservada))
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    override fun onErrorReserve() {

    }

    override fun onTripCompleted() {
        MaterialAlertDialogBuilder(context!!, R.style.AlertDialogTheme)
            .setTitle(getString(R.string.trip_completed))
            .setMessage(getString(R.string.mensaje_plaza_completed))
            .setPositiveButton(android.R.string.ok){ dialogInterface: DialogInterface, _: Int ->
                activity?.onDeletedTrip()
                dialogInterface.dismiss()
            }
            .setIcon(R.drawable.ic_warning_24dp)
            .show()
    }

    override fun onSuccessCancelReserve() {
        MaterialAlertDialogBuilder(context!!, R.style.AlertDialogTheme)
            .setTitle(getString(R.string.trip_plaza_libre))
            .setMessage(getString(R.string.mensaje_plaza_libre))
            .setPositiveButton(android.R.string.ok){ dialogInterface: DialogInterface, _: Int ->
                activity?.onDeletedTrip()
                dialogInterface.dismiss()
            }
            .setIcon(R.drawable.ic_warning_24dp)
            .show()
    }

    override fun onErrorCancelReserve() {

    }

    override fun setPresenter(presenter: ConfirmTripContract.Presenter) {
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
package com.mlodi.sicredi.devandroidtest.ui.checkin

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mlodi.sicredi.devandroidtest.R
import com.mlodi.sicredi.devandroidtest.data.api.model.CheckIn
import com.mlodi.sicredi.devandroidtest.databinding.FragmentCheckInBinding
import com.mlodi.sicredi.devandroidtest.ui.checkin.CheckInViewModelSetup.CheckInAction.ShowGenericError
import com.mlodi.sicredi.devandroidtest.ui.checkin.CheckInViewModelSetup.CheckInAction.ShowSuccessCheckIn
import com.mlodi.sicredi.devandroidtest.ui.checkin.CheckInViewModelSetup.CheckInEvent
import com.mlodi.sicredi.devandroidtest.ui.checkin.CheckInViewModelSetup.CheckInState
import com.mlodi.sicredi.devandroidtest.ui.util.Constants.ARGUMENT_EVENT_ID
import com.mlodi.sicredi.devandroidtest.ui.util.Constants.ARGUMENT_SUCCESS_DIALOG_SHOWING
import com.mlodi.sicredi.devandroidtest.util.isValidEmail
import com.mlodi.sicredi.devandroidtest.util.createGenericErrorDialog
import kotlinx.coroutines.launch

class CheckInFragment : Fragment() {

    private var _binding: FragmentCheckInBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CheckInViewModel by viewModels()
    private val successCheckInDialog by lazy{ createSuccessCheckInDialog() }
    private val genericErrorDialog by lazy { requireActivity().createGenericErrorDialog { findNavController().navigateUp() } }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCheckInBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.getBoolean(ARGUMENT_SUCCESS_DIALOG_SHOWING, false)?.let { hasToShow ->
            if (hasToShow)successCheckInDialog.show()
        }

        renderAndExecute()
        init()
    }

    private fun init(){
        binding.checkinSendDataBtn.setOnClickListener {
            binding.checkinUserName.editText?.let{
                if (it.text.isNullOrEmpty()) {
                    it.error = getString(R.string.checkin_user_name_empty_error_text)
                }else {
                    it.error = null
                }
            }
            binding.checkinUserEmail.editText?.let{
                if (it.text.isNullOrEmpty() || !it.text.toString().isValidEmail()) {
                    it.error = getString(R.string.checkin_user_email_empty_error_text)
                }else {
                    it.error = null
                }
            }

            if (binding.checkinUserName.editText?.error != null || binding.checkinUserEmail.editText?.error != null)
                return@setOnClickListener

            arguments?.getString(ARGUMENT_EVENT_ID)?.let{ id ->
                val checkIn = CheckIn(
                    eventId = id,
                    name = binding.checkinUserName.editText?.text.toString(),
                    email = binding.checkinUserEmail.editText?.text.toString()
                )

                viewModel.processEvent(CheckInEvent.OnSendDataButtonClicked(checkIn))
            } ?: run {
                closeCheckIn()
            }
        }
    }

    private fun renderAndExecute(){
        lifecycleScope.launch {
            viewModel.state.observe(viewLifecycleOwner, Observer { state ->
                when(state){
                    is CheckInState.Loading -> toggleLoading(true)
                    is CheckInState.Ready -> toggleLoading(false)
                }
            })
        }

        lifecycleScope.launch {
            viewModel.action.observe(viewLifecycleOwner, Observer { action ->
                when(action){
                    is ShowSuccessCheckIn -> successCheckInDialog.show()
                    is ShowGenericError -> genericErrorDialog.show()
                }
            })
        }
    }

    private fun createSuccessCheckInDialog(): AlertDialog{
        return AlertDialog.Builder(requireActivity()).apply {
            setTitle(R.string.checkin_send_data_success_title_text)
            setMessage(R.string.checkin_send_data_success_desc_text)
            setNeutralButton(R.string.checkin_send_data_success_btn_text) { dialog, _ ->
                closeCheckIn()
                dialog.dismiss()
            }
            setCancelable(false)
        }.create()
    }

    private fun closeCheckIn(){
        findNavController().navigate(R.id.action_checkInFragment_to_eventListFragment)
    }

    private fun toggleLoading(visible: Boolean){
        binding.checkinLoading.visibility = if(visible) View.VISIBLE else View.GONE
        binding.checkinRoot.visibility = if(visible) View.GONE else View.VISIBLE
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (successCheckInDialog.isShowing) outState.putBoolean(ARGUMENT_SUCCESS_DIALOG_SHOWING, true)
    }

    override fun onDestroyView() {
        successCheckInDialog.dismiss()
        genericErrorDialog.dismiss()
        super.onDestroyView()
    }
}
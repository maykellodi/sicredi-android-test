package com.mlodi.sicredi.devandroidtest.ui.eventinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mlodi.sicredi.devandroidtest.R
import com.mlodi.sicredi.devandroidtest.data.api.model.Event
import com.mlodi.sicredi.devandroidtest.databinding.FragmentEventInfoBinding
import com.mlodi.sicredi.devandroidtest.ui.eventinfo.EventInfoViewModelSetup.*
import com.mlodi.sicredi.devandroidtest.ui.eventinfo.EventInfoViewModelSetup.EventInfoEvent.LifeCycleEvent.OnViewLoaded
import com.mlodi.sicredi.devandroidtest.ui.util.Constants.ARGUMENT_EVENT_ID
import kotlinx.coroutines.launch

class EventInfoFragment : Fragment() {

    private var _binding: FragmentEventInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventInfoViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEventInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        renderAndExecute()
        init()
        arguments?.getString(ARGUMENT_EVENT_ID)?.let{ id ->
            viewModel.processEvent(OnViewLoaded(id))
        } ?: run {
            closeEventDetail()
        }
    }

    private fun init(){
        binding.eventInfoCheckinBtn.setOnClickListener {
            viewModel.processEvent(EventInfoEvent.OnCheckInButtonClicked)
        }
    }

    private fun renderAndExecute(){
        lifecycleScope.launch {
            viewModel.state.observe(viewLifecycleOwner, Observer { state ->
                when(state){
                    is EventInfoState.Loading -> toggleLoading(true)
                    is EventInfoState.Failed -> closeEventDetail()
                    is EventInfoState.Ready -> showEvent(state.event)
                }
            })
        }

        lifecycleScope.launch {
            viewModel.action.observe(viewLifecycleOwner, Observer { action ->
                when(action){
                    is EventInfoAction.OpenCheckIn -> openCheckIn(action.eventId)
                    is EventInfoAction.ShowGenericError -> {}
                }
            })
        }
    }

    private fun openCheckIn(eventId: String){
        val bundle = bundleOf(ARGUMENT_EVENT_ID to eventId)
        findNavController().navigate(R.id.action_eventInfoFragment_to_checkInFragment, bundle)
    }

    private fun closeEventDetail(){
        findNavController().navigateUp()
    }

    private fun toggleLoading(visible: Boolean){
        binding.eventInfoLoading.visibility = if(visible) View.VISIBLE else View.GONE
        binding.eventInfoRoot.visibility = if(visible) View.GONE else View.VISIBLE
    }

    private fun showEvent(event: Event){
        toggleLoading(false)
        binding.event = event
    }

}
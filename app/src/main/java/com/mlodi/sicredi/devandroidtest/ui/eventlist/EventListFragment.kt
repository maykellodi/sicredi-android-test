package com.mlodi.sicredi.devandroidtest.ui.eventlist

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
import com.mlodi.sicredi.devandroidtest.databinding.FragmentEventListBinding
import com.mlodi.sicredi.devandroidtest.ui.eventlist.EventListViewModelSetup.*
import com.mlodi.sicredi.devandroidtest.ui.eventlist.EventListViewModelSetup.EventListEvent.LifeCycleEvent
import com.mlodi.sicredi.devandroidtest.ui.util.Constants.ARGUMENT_EVENT_ID
import kotlinx.coroutines.launch

class EventListFragment : Fragment() {

    private var _binding: FragmentEventListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EventListViewModel by viewModels()
    private val eventAdapter by lazy { EventListAdapter(onEventClicked) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentEventListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        renderAndExecute()
        init()
        viewModel.processEvent(LifeCycleEvent.OnViewLoaded)
    }

    private fun init(){
        binding.eventListEventsRecycler.adapter = eventAdapter
        binding.eventListRefreshLayout.setOnRefreshListener {
            viewModel.processEvent(EventListEvent.OnSwipeDown)
        }
    }

    private fun renderAndExecute(){
        lifecycleScope.launch {
            viewModel.state.observe(viewLifecycleOwner, Observer { state ->
                when(state){
                    is EventListState.Loading -> toggleLoading(true)
                    is EventListState.Failed -> toggleLoading(false)
                    is EventListState.Ready -> showEvents(state.eventList)
                }
            })
        }

        lifecycleScope.launch {
            viewModel.action.observe(viewLifecycleOwner, Observer { action ->
                when(action){
                    is EventListAction.OpenEventInfo -> openEventInfo(action.eventId)
                    is EventListAction.ShowGenericError -> {}
                }
            })
        }
    }

    private val onEventClicked: (String) -> Unit = { id ->
        viewModel.processEvent(EventListEvent.OnEventSelected(id))
    }

    private fun openEventInfo(eventId: String){
        val bundle = bundleOf(ARGUMENT_EVENT_ID to eventId)
        findNavController().navigate(R.id.action_eventListFragment_to_eventInfoFragment, bundle)
    }

    private fun toggleLoading(visible: Boolean){
        binding.eventListRefreshLayout.isRefreshing = visible
    }

    private fun showEvents(eventList: List<Event>){
        toggleLoading(false)
        if (eventList.isEmpty()){
            binding.eventListEventsRecycler.visibility = View.GONE
            binding.eventListEmptyLayout.visibility = View.VISIBLE
        }else{
            eventAdapter.eventList = eventList
            binding.eventListEventsRecycler.visibility = View.VISIBLE
            binding.eventListEmptyLayout.visibility = View.GONE
        }
    }
}
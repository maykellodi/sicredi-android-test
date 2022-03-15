package com.mlodi.sicredi.devandroidtest.ui.eventlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mlodi.sicredi.devandroidtest.data.repository.SicrediRepository
import com.mlodi.sicredi.devandroidtest.ui.eventlist.EventListViewModelSetup.EventListAction
import com.mlodi.sicredi.devandroidtest.ui.eventlist.EventListViewModelSetup.EventListState
import com.mlodi.sicredi.devandroidtest.ui.eventlist.EventListViewModelSetup.EventListEvent
import com.mlodi.sicredi.devandroidtest.ui.eventlist.EventListViewModelSetup.EventListEvent.LifeCycleEvent
import com.mlodi.sicredi.devandroidtest.util.SingleLiveEvent
import kotlinx.coroutines.launch

class EventListViewModel: ViewModel(){
    private var _state = MutableLiveData<EventListState>()
    val state: LiveData<EventListState> = _state

    private var _action = SingleLiveEvent<EventListAction>()
    val action: LiveData<EventListAction> = _action

    fun processEvent(event: EventListEvent){
        when(event){
            is LifeCycleEvent.OnViewLoaded -> loadEvents()
            is EventListEvent.OnSwipeDown -> loadEvents()
            is EventListEvent.OnEventSelected -> callEventInfo(event.eventId)
        }
    }

    private fun loadEvents(){
        _state.value = EventListState.Loading

        viewModelScope.launch {
            SicrediRepository.getEvents(
                onSuccess = {
                    _state.value = EventListState.Ready(it ?: emptyList())
                },
                onFailure = {
                    _state.value = EventListState.Failed
                    _action.value = EventListAction.ShowGenericError
                }
            )
        }
    }

    private fun callEventInfo(eventId: String){
        _action.value = EventListAction.OpenEventInfo(eventId)
    }
}


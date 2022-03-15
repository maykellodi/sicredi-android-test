package com.mlodi.sicredi.devandroidtest.ui.eventinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlodi.sicredi.devandroidtest.data.repository.SicrediRepository
import com.mlodi.sicredi.devandroidtest.ui.eventinfo.EventInfoViewModelSetup.EventInfoEvent
import com.mlodi.sicredi.devandroidtest.ui.eventinfo.EventInfoViewModelSetup.EventInfoEvent.LifeCycleEvent.OnViewLoaded
import com.mlodi.sicredi.devandroidtest.util.SingleLiveEvent
import kotlinx.coroutines.launch

class EventInfoViewModel: ViewModel(){
    private var _state = MutableLiveData<EventInfoViewModelSetup.EventInfoState>()
    val state: LiveData<EventInfoViewModelSetup.EventInfoState> = _state

    private var _action = SingleLiveEvent<EventInfoViewModelSetup.EventInfoAction>()
    val action: LiveData<EventInfoViewModelSetup.EventInfoAction> = _action

    fun processEvent(event: EventInfoEvent){
        when(event){
            is OnViewLoaded -> loadEvent(event.eventId)
            is EventInfoEvent.OnCheckInButtonClicked -> callCheckIn("")
        }
    }

    private fun loadEvent(id: String){
        _state.value = EventInfoViewModelSetup.EventInfoState.Loading

        viewModelScope.launch {
            SicrediRepository.getEvent(id,
                onSuccess = { event ->
                    event?.let{
                        _state.value = EventInfoViewModelSetup.EventInfoState.Ready(it)
                    } ?: run {
                        _state.value = EventInfoViewModelSetup.EventInfoState.Failed
                        _action.value = EventInfoViewModelSetup.EventInfoAction.ShowGenericError
                    }
                },
                onFailure = {
                    _state.value = EventInfoViewModelSetup.EventInfoState.Failed
                    _action.value = EventInfoViewModelSetup.EventInfoAction.ShowGenericError
                }
            )
        }
    }

    private fun callCheckIn(eventId: String){
        _action.value = EventInfoViewModelSetup.EventInfoAction.OpenCheckIn(eventId)
    }
}
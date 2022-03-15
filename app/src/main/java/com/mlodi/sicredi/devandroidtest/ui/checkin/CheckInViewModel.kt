package com.mlodi.sicredi.devandroidtest.ui.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlodi.sicredi.devandroidtest.data.api.model.CheckIn
import com.mlodi.sicredi.devandroidtest.data.repository.SicrediRepository
import com.mlodi.sicredi.devandroidtest.ui.checkin.CheckInViewModelSetup.CheckInEvent
import com.mlodi.sicredi.devandroidtest.ui.checkin.CheckInViewModelSetup.CheckInState
import com.mlodi.sicredi.devandroidtest.util.SingleLiveEvent
import kotlinx.coroutines.launch

class CheckInViewModel: ViewModel(){
    private var _state = MutableLiveData<CheckInState>()
    val state: LiveData<CheckInState> = _state

    private var _action = SingleLiveEvent<CheckInViewModelSetup.CheckInAction>()
    val action: LiveData<CheckInViewModelSetup.CheckInAction> = _action

    fun processEvent(event: CheckInEvent){
        when(event){
            is CheckInEvent.OnSendDataButtonClicked -> sendData(event.checkIn)
        }
    }

    private fun sendData(checkIn: CheckIn){
        _state.value = CheckInState.Loading

        viewModelScope.launch {
            SicrediRepository.doCheckIn(checkIn,
                onSuccess = {
                    _state.value = CheckInState.Ready
                    _action.value = CheckInViewModelSetup.CheckInAction.ShowSuccessCheckIn
                },
                onFailure = {
                    _state.value = CheckInState.Ready
                    _action.value = CheckInViewModelSetup.CheckInAction.ShowGenericError
                }
            )
        }
    }
}
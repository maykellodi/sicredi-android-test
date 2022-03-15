package com.mlodi.sicredi.devandroidtest.ui.checkin

import com.mlodi.sicredi.devandroidtest.data.api.model.CheckIn

class CheckInViewModelSetup {

    sealed class CheckInState{
        object Loading: CheckInState()
        object Ready: CheckInState()
    }

    sealed class CheckInEvent{
        data class OnSendDataButtonClicked(val checkIn: CheckIn): CheckInEvent()
    }

    sealed class CheckInAction{
        object ShowSuccessCheckIn: CheckInAction()
        object ShowGenericError: CheckInAction()
    }
}
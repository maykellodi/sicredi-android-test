package com.mlodi.sicredi.devandroidtest.ui.eventinfo

import com.mlodi.sicredi.devandroidtest.data.api.model.Event

class EventInfoViewModelSetup {
    sealed class EventInfoState{
        object Loading: EventInfoState()
        data class Ready(val event: Event): EventInfoState()
        object Failed: EventInfoState()
    }

    sealed class EventInfoEvent{
        object OnCheckInButtonClicked: EventInfoEvent()

        sealed class LifeCycleEvent: EventInfoEvent(){
            data class OnViewLoaded(val eventId: String): LifeCycleEvent()
        }
    }

    sealed class EventInfoAction{
        data class OpenCheckIn(val eventId: String): EventInfoAction()
        object ShowGenericError: EventInfoAction()
    }
}
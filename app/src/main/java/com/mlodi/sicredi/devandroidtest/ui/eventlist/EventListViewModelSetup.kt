package com.mlodi.sicredi.devandroidtest.ui.eventlist

import com.mlodi.sicredi.devandroidtest.data.api.model.Event

class EventListViewModelSetup {
    sealed class EventListState{
        object Loading: EventListState()
        data class Ready(val eventList: List<Event>): EventListState()
        object Failed: EventListState()
    }

    sealed class EventListEvent{
        object OnSwipeDown: EventListEvent()
        data class OnEventSelected(val eventId: String): EventListEvent()

        sealed class LifeCycleEvent: EventListEvent(){
            object OnViewLoaded: LifeCycleEvent()
        }
    }

    sealed class EventListAction{
        data class OpenEventInfo(val eventId: String): EventListAction()
        object ShowGenericError: EventListAction()
    }
}
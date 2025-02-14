package com.emon.m3searchexample

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor() : ViewModel() {

    private val _state: MutableStateFlow<LandingState> = MutableStateFlow(LandingState())
    var state: StateFlow<LandingState> = _state

    fun onEvent(event: LandingEvent) {
        when (event) {
            is LandingEvent.OnTabSelect -> {
                _state.value = _state.value.copy(
                    selectedTab = event.tab
                )
            }
        }
    }
}

sealed class LandingEvent {
    data class OnTabSelect(val tab: String) : LandingEvent()
}

data class LandingState(val selectedTab: String = "Recent")
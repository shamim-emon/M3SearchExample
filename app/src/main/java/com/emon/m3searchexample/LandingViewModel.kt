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

            is LandingEvent.OnSearchClick -> {
                _state.value = _state.value.copy(
                    isSearchBarVisible = true
                )
            }

            is LandingEvent.OnSearchDismiss -> {
                _state.value = _state.value.copy(
                    isSearchBarVisible = false,
                    isSearchBarActive = false
                )
            }

            is LandingEvent.ToggleSearchBarActive -> {
                _state.value = _state.value.copy(
                    isSearchBarActive = event.isSearchBarActive
                )
            }

            is LandingEvent.OnSearchQueryUpdate -> {
                _state.value = _state.value.copy(
                    searchQuery = event.query
                )
            }

        }
    }
}

sealed class LandingEvent {
    data class OnTabSelect(val tab: String) : LandingEvent()
    data object OnSearchClick : LandingEvent()
    data object OnSearchDismiss : LandingEvent()
    data class OnSearchQueryUpdate(val query: String) : LandingEvent()
    data class ToggleSearchBarActive(val isSearchBarActive: Boolean) : LandingEvent()
}

data class LandingState(
    val searchQuery: String = "",
    val isSearchBarActive: Boolean = false,
    val selectedTab: String = "Recent",
    val isSearchBarVisible: Boolean = false,
)
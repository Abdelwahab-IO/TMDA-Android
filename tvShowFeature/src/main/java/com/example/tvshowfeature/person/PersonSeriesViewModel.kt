package com.example.tvshowfeature.person

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitIO.tvshowcomponent.domain.entity.TvShow
import com.bitIO.tvshowcomponent.domain.useCases.GetPersonDetailsUseCase
import com.bitIO.tvshowcomponent.domain.useCases.GetPersonSeriesUseCase
import com.example.sharedComponent.entities.people.PersonDetails
import com.example.sharedui.navigation.PERSON_ID


import com.example.sharedui.uiStates.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PersonSeriesViewModel @Inject constructor(
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val getPersonSeriesUseCase: GetPersonSeriesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val personId: Int = savedStateHandle[PERSON_ID]!!
    private val _personDetailsDetails: MutableState<com.example.sharedui.uiStates.UiState<PersonDetails>> =
        mutableStateOf(com.example.sharedui.uiStates.UiState.Loading())
    val personDetails: State<com.example.sharedui.uiStates.UiState<PersonDetails>>
        get() = _personDetailsDetails
    private val _personSeries: MutableState<com.example.sharedui.uiStates.UiState<List<TvShow>>> =
        mutableStateOf(com.example.sharedui.uiStates.UiState.Loading())
    val personSeries: State<com.example.sharedui.uiStates.UiState<List<TvShow>>>
        get() = _personSeries

    init {
        updateAll()
    }

    fun updateAll() {
        updatePersonDetails()
        updatePersonSeries()
    }

    private fun updatePersonDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = getPersonDetailsUseCase.invoke(personId).toUiState()
            withContext(Dispatchers.Main) { _personDetailsDetails.value = state }

        }
    }

    private fun updatePersonSeries() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = getPersonSeriesUseCase.invoke(personId).toUiState()
            withContext(Dispatchers.Main) { _personSeries.value = state }

        }
    }
}
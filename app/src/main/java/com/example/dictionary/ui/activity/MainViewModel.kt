package com.example.dictionary.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.repository.WordInfoRepository
import com.example.dictionary.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created By Dhruv Limbachiya on 30-11-2021 05:38 PM.
 */

class MainViewModel @Inject constructor(
    private val repository: WordInfoRepository
) : ViewModel() {

    // Backing property of wordsInfoState.
    private var _wordsInfoState = MutableStateFlow(WordInfoState())
    val wordInfoState: StateFlow<WordInfoState> = _wordsInfoState

    private var _uiEvents = MutableSharedFlow<UIEvent>()
    val uiEvents: SharedFlow<UIEvent> = _uiEvents

    private var job: Job? = null

    init {
        searchWord("bank")
    }

    /**
     * Method responsible for getting word info or list of word info either from the network or local database and set data in state flow.
     */
    fun searchWord(query: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            repository.getWordInfo(query).onEach { result ->
                when(result) {
                    is Resource.Loading ->  {
                        _wordsInfoState.value = _wordsInfoState.value.copy(isLoading = true) // set loading state.
                    }
                    is Resource.Success -> {
                        // update the state with data list and disable the loading state.
                        _wordsInfoState.value = _wordsInfoState.value.copy(wordsInfo = result.data,isLoading = false)
                    }
                    is Resource.Error -> {
                        _wordsInfoState.value = _wordsInfoState.value.copy(wordsInfo = result.data ?: emptyList(),isLoading = false)
                        _uiEvents.emit(UIEvent.SnackBarEvent(result.message))
                    }
                }
            }.launchIn(this)
        }
    }

    sealed class UIEvent() {
        data class SnackBarEvent(var message: String? = null) : UIEvent()
    }
}
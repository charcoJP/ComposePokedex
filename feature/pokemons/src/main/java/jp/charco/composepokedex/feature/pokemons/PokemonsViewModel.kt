package jp.charco.composepokedex.feature.pokemons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.charco.composepokedex.core.common.result.Result
import jp.charco.composepokedex.core.common.result.asResult
import jp.charco.composepokedex.core.data.PokeRepository
import jp.charco.composepokedex.core.data.network.response.Pokemon
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PokemonsViewModel @Inject constructor(
    pokeRepository: PokeRepository
) : ViewModel() {

    private val pokemonFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    val uiStateFlow: StateFlow<MainUiState> = pokemonFetchingIndex.flatMapLatest { page ->
        pokeRepository.getPokemonList(
            page = page
        )
    }.asResult()
        .map {
            when (it) {
                is Result.Error -> MainUiState.Error(it.exception)
                Result.Loading -> MainUiState.Loading
                is Result.Success -> MainUiState.Success(it.data)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MainUiState.Loading
        )
}

sealed interface MainUiState {
    data class Success(val pokemonList: List<Pokemon>) : MainUiState
    data class Error(val exception: Throwable?) : MainUiState
    object Loading : MainUiState
}

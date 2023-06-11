package jp.charco.composepokedex.feature.pokemon.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.charco.composepokedex.core.data.PokeRepository
import jp.charco.composepokedex.core.data.network.response.PokemonDetail
import jp.charco.composepokedex.feature.pokemon.detail.navigation.pokemonNumberArg
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    pokeRepository: PokeRepository
) : ViewModel() {

    private val pokemonNumber: String

    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        pokemonNumber = savedStateHandle[pokemonNumberArg] ?: throw IllegalArgumentException()

        viewModelScope.launch {
            runCatching {
                val result = pokeRepository.getPokemonDetail(pokemonNumber)
                _uiState.update { PokemonDetailUiState.Success(result) }
            }.onFailure { e ->
                _uiState.update { PokemonDetailUiState.Error(e) }
            }
        }
    }

    sealed interface PokemonDetailUiState {
        data class Success(val pokemon: PokemonDetail) : PokemonDetailUiState
        data class Error(val exception: Throwable?) : PokemonDetailUiState
        object Loading : PokemonDetailUiState
    }
}
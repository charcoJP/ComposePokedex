package jp.charco.composepokedex.feature.pokemon.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.charco.composepokedex.feature.pokemon.detail.navigation.pokemonNumberArg
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val pokemonNumber: String

    init {
        pokemonNumber = savedStateHandle[pokemonNumberArg] ?: throw IllegalArgumentException()
    }
}
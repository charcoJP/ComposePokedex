package jp.charco.composepokedex.feature.pokemon.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    Text("詳細だよ: ${viewModel.pokemonNumber}")
}
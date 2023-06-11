package jp.charco.composepokedex.feature.pokemon.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import jp.charco.composepokedex.core.ui.theme.fontFamily
import jp.charco.composepokedex.feature.pokemon.detail.PokemonDetailViewModel.PokemonDetailUiState.Error
import jp.charco.composepokedex.feature.pokemon.detail.PokemonDetailViewModel.PokemonDetailUiState.Loading
import jp.charco.composepokedex.feature.pokemon.detail.PokemonDetailViewModel.PokemonDetailUiState.Success

@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        is Error -> Text("Error: ${(uiState as Error).exception?.localizedMessage}")
        Loading -> CircularProgressIndicator()
        is Success -> {
            val pokemon = (uiState as Success).pokemon

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    pokemon.name,
                    style = MaterialTheme.typography.displayMedium,
                    fontFamily = fontFamily
                )
                AsyncImage(
                    model = pokemon.imageUrl,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(8.dp)
                )
                Text("height: ${pokemon.height}")
                Text("weight: ${pokemon.weight}")
                Text("weight: ${pokemon.types.joinToString { it.type.name }}")
            }
        }
    }
}
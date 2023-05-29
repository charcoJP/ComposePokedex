package jp.charco.composepokedex.feature.pokemons

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import jp.charco.composepokedex.core.data.network.response.Pokemon

@Composable
fun PokemonsScreen(
    contentPadding: PaddingValues,
    viewModel: PokemonsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    Contents(uiState, contentPadding)
}

@Composable
fun Contents(uiState: MainUiState, contentPadding: PaddingValues) {
    when (uiState) {
        is MainUiState.Error -> Text("エラー")
        MainUiState.Loading -> CircularProgressIndicator()
        is MainUiState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
            ) {
                items(uiState.pokemonList) {
                    PokemonRow(it)
                }
            }
        }
    }
}

@Composable
private fun PokemonRow(pokemon: Pokemon) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            )
    ) {
        Row {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier
                    .height(100.dp)
                    .padding(end = 16.dp)
            )
            Column {
                Text(text = "No.${pokemon.number}", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = pokemon.name, style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}

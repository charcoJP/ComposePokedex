package jp.charco.composepokedex.feature.pokemons

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.palette.graphics.Palette
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import jp.charco.composepokedex.core.data.network.response.Pokemon

@Composable
fun PokemonsScreen(
    navController: NavController,
    viewModel: PokemonsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiStateFlow.collectAsStateWithLifecycle()
    Contents(uiState)
}

@Composable
fun Contents(uiState: MainUiState) {
    when (uiState) {
        is MainUiState.Error -> Text("エラー")
        MainUiState.Loading -> CircularProgressIndicator()
        is MainUiState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
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
    var bitmap: Bitmap? by remember { mutableStateOf(null) }
    val palette = remember(bitmap) { bitmap?.let { Palette.from(it).generate() } }
    val cardBackgroundColor = palette?.dominantSwatch?.rgb?.let { Color(it) } ?: Color.White

    OutlinedCard(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 4.dp
            )
    ) {
        Row {
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = pokemon.imageUrl)
                    .allowHardware(false)
                    .listener(
                        onSuccess = { _, result ->
                            bitmap = result.drawable.toBitmap()
                        }
                    )
                    .build()
            )
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .background(color = cardBackgroundColor)
                    .height(100.dp)
                    .width(100.dp)
                    .padding(8.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .align(Alignment.CenterVertically),
            ) {
                Text(text = "No.${pokemon.number}", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = pokemon.name, style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}

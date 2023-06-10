package jp.charco.composepokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.charco.composepokedex.core.ui.theme.ComposePokedexTheme
import jp.charco.composepokedex.core.ui.theme.fontFamily
import jp.charco.composepokedex.feature.pokemon.detail.navigation.navigateToPokemonDetail
import jp.charco.composepokedex.feature.pokemon.detail.navigation.pokemonDetailNavGraph
import jp.charco.composepokedex.feature.pokemons.navigation.pokemonsNavGraph
import jp.charco.composepokedex.feature.pokemons.navigation.pokemonsNavigationRoute

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            ComposePokedexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = { TopBar() }) {
                        NavHost(
                            startDestination = pokemonsNavigationRoute,
                            navController = navController,
                            modifier = Modifier.padding(it)
                        ) {
                            pokemonsNavGraph(onPokemonClick = { pokemonNumber ->
                                navController.navigateToPokemonDetail(pokemonNumber)
                            })
                            pokemonDetailNavGraph()
                        }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar() {
    TopAppBar(title = { Text("ComposePokedex", fontFamily = fontFamily) })
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposePokedexTheme {
    }
}
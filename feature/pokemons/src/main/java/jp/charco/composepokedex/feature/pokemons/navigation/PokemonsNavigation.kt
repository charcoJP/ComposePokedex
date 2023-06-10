package jp.charco.composepokedex.feature.pokemons.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import jp.charco.composepokedex.feature.pokemons.PokemonsScreen

const val pokemonsNavigationRoute = "pokemons"

fun NavGraphBuilder.pokemonsNavGraph(onPokemonClick: (String) -> Unit) {
    composable(route = pokemonsNavigationRoute) {
        PokemonsScreen(onPokemonClick)
    }
}
package jp.charco.composepokedex.feature.pokemons.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import jp.charco.composepokedex.feature.pokemons.PokemonsScreen

const val pokemonsNavigationRoute = "pokemons"

fun NavGraphBuilder.pokemonsNavGraph(navController: NavController) {
    composable(route = pokemonsNavigationRoute) {
        PokemonsScreen(navController)
    }
}
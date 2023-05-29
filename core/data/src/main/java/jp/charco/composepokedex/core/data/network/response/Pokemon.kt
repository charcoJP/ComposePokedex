package jp.charco.composepokedex.core.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val name: String,
    private val url: String
) {
    val number = url.split("/".toRegex()).dropLast(1).last()

    val imageUrl: String
        get() {
            val index = url.split("/".toRegex()).dropLast(1).last()
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$index.png"
        }
}

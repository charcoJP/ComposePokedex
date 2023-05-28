package jp.charco.composepokedex.core.common.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: ComposePokedexDispatchers)

enum class ComposePokedexDispatchers {
    IO,
}

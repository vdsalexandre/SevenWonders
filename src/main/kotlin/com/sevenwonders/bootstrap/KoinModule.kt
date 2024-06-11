package com.sevenwonders.bootstrap


import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val appModule = module {
//    single<WalletRepository> { WalletAdapter() }
//    single<BlockchainRepository> { BlockchainAdapter() }
}

fun Application.configKoinModule() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}
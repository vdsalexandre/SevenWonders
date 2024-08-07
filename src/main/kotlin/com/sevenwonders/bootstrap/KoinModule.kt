package com.sevenwonders.bootstrap


import com.sevenwonders.api.spi.CardRepository
import com.sevenwonders.api.spi.CityRepository
import com.sevenwonders.domain.service.CardServiceAdapter
import com.sevenwonders.domain.service.CityServiceAdapter
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

val appModule = module {
    singleOf(::CardServiceAdapter) { bind<CardRepository>() }
    singleOf(::CityServiceAdapter) { bind<CityRepository>() }
}

fun Application.configKoinModule() {
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}

package com.sevenwonders

import com.sevenwonders.api.resource.configureCardsResource
import com.sevenwonders.api.resource.configureCitiesResource
import com.sevenwonders.api.resource.configureStaticResource
import com.sevenwonders.bootstrap.configKoinModule
import com.sevenwonders.bootstrap.configureDatabases
import com.sevenwonders.bootstrap.configureSerialization
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configKoinModule()
//    configureSecurity()
    configureCardsResource()
    configureCitiesResource()
    configureStaticResource()
}

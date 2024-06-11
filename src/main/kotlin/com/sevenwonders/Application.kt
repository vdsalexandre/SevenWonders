package com.sevenwonders

import com.sevenwonders.api.resource.configureCardsResource
import com.sevenwonders.bootstrap.configureSerialization
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
//    configureDatabases()
//    configureSecurity()
    configureCardsResource()
}

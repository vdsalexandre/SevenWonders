package com.sevenwonders

import com.sevenwonders.plugins.configureDatabases
import com.sevenwonders.plugins.configureRouting
import com.sevenwonders.plugins.configureSecurity
import com.sevenwonders.plugins.configureSerialization
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureDatabases()
    configureSecurity()
    configureRouting()
}

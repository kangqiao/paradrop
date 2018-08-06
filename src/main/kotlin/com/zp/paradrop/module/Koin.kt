package com.zp.paradrop.module

import com.zp.paradrop.module.Property.WHO
import org.koin.dsl.module.applicationContext
import org.koin.standalone.KoinComponent
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory


// model class with injected property
data class HelloModel(val who: String)

// Service interface
interface HelloService {
    fun hello(): String
}

// service class with injected helloModel instance
class HelloServiceImpl(val helloModel: HelloModel) : HelloService {

    override fun hello() = "Hello ${helloModel.who}"
}

// HelloApplication
class HelloApplication : KoinComponent {

    // Inject service
    val helloService by inject<HelloService>()

    init {
        println(helloService.hello())
    }
}

// Koin module
val HelloModule = applicationContext {
    bean { HelloModel(getProperty(WHO)) }
    bean { HelloServiceImpl(get()) as HelloService }
}

// properties
object Property {
    val WHO = "WHO"
}

fun main(vararg args: String) {
    startKoin(listOf(HelloModule), extraProperties = mapOf(WHO to "Koin :)"))
    HelloApplication()
}
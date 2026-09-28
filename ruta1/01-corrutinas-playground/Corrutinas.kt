/*
 * Codelab: Introducción a las corrutinas en el Playground de Kotlin
 * Pega este archivo en https://play.kotlinlang.org y ejecútalo.
 * Cada función ejemploN() corresponde a una sección del codelab;
 * descomenta en main() la que quieras probar.
 */
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    // ejemplo1Sincrono()
    // ejemplo2Launch()
    // ejemplo3Async()
    // ejemplo4Descomposicion()
    // ejemplo5Excepciones()
    // ejemplo6Cancelacion()
    // ejemplo7Jerarquia()
    ejemplo8Dispatchers()
}

// ---------- Funciones de suspensión que simulan una solicitud de red ----------
suspend fun getForecast(): String {
    delay(1000)
    return "Sunny"
}

suspend fun getTemperature(): String {
    delay(1000)
    return "30\u00b0C"
}

suspend fun getTemperatureConError(): String {
    delay(500)
    throw AssertionError("Temperature is invalid")
}

// 1. Código síncrono: una tarea después de otra (~2 s)
fun ejemplo1Sincrono() {
    val time = measureTimeMillis {
        runBlocking {
            println("Weather forecast")
            println(getForecast())
            println(getTemperature())
        }
    }
    println("Execution time: ${time / 1000.0} seconds")
}

// 2. launch(): "dispara y olvida"; las dos tareas corren a la vez (~1 s)
fun ejemplo2Launch() {
    val time = measureTimeMillis {
        runBlocking {
            println("Weather forecast")
            launch { printForecast() }
            launch { printTemperature() }
            println("Have a good day!") // se imprime antes: launch no bloquea
        }
    }
    println("Execution time: ${time / 1000.0} seconds")
}

suspend fun printForecast() {
    delay(1000)
    println("Sunny")
}

suspend fun printTemperature() {
    delay(1000)
    println("30\u00b0C")
}

// 3. async(): devuelve un Deferred con un resultado que obtenemos con await()
fun ejemplo3Async() {
    runBlocking {
        println("Weather forecast")
        val forecast: Deferred<String> = async { getForecast() }
        val temperature: Deferred<String> = async { getTemperature() }
        println("${forecast.await()} ${temperature.await()}")
        println("Have a good day!")
    }
}

// 4. Descomposición paralela con coroutineScope()
fun ejemplo4Descomposicion() {
    runBlocking {
        println("Weather forecast")
        println(getWeatherReport())
        println("Have a good day!")
    }
}

suspend fun getWeatherReport() = coroutineScope {
    val forecast = async { getForecast() }
    val temperature = async { getTemperature() }
    "${forecast.await()} ${temperature.await()}"
}

// 5. Excepciones: se capturan dentro del alcance para no cancelar todo
fun ejemplo5Excepciones() {
    runBlocking {
        println("Weather forecast")
        println(getWeatherReportConErrores())
        println("Have a good day!")
    }
}

suspend fun getWeatherReportConErrores() = coroutineScope {
    val forecast = async { getForecast() }
    val temperature = async {
        try {
            getTemperatureConError()
        } catch (e: AssertionError) {
            println("Caught exception $e")
            "{ No temperature found }"
        }
    }
    "${forecast.await()} ${temperature.await()}"
}

// 6. Cancelación: cancelamos la temperatura si tarda demasiado
fun ejemplo6Cancelacion() {
    runBlocking {
        println("Weather forecast")
        println(getWeatherReportConCancelacion())
        println("Have a good day!")
    }
}

suspend fun getWeatherReportConCancelacion() = coroutineScope {
    val forecast = async { getForecast() }
    val temperature = async { getTemperature() }
    delay(200)
    temperature.cancel()
    "${forecast.await()}"
}

// 7. Jerarquía de Jobs: cancelar al padre cancela a los hijos
fun ejemplo7Jerarquia() {
    runBlocking {
        val padre = launch {
            launch { delay(1000); println("Hijo 1 terminado") }
            launch { delay(1000); println("Hijo 2 terminado") }
        }
        delay(500)
        padre.cancel()
        println("Padre cancelado: isCancelled=${padre.isCancelled}")
    }
}

// 8. Dispatchers: withContext() cambia el subproceso de ejecución
fun ejemplo8Dispatchers() {
    runBlocking {
        println("${Thread.currentThread().name} - runBlocking function")
        launch {
            println("${Thread.currentThread().name} - launch function")
            withContext(Dispatchers.Default) {
                println("${Thread.currentThread().name} - withContext function")
                delay(1000)
                println("10 results found.")
            }
            println("${Thread.currentThread().name} - end of launch function")
        }
        println("Loading...")
    }
}

package Kotlin.Operaciones

import Kotlin.People
import Kotlin.Persona
import kotlin.system.measureNanoTime

/**
 * Posición en la que se encuentra la persona dentro de la lista
 */
var posicion = 0

/**
 * Tiempo que tardan los métodos en ejecutarse
 */
var tiempo = 0L

/**
 * Método para calcular el tiempo que tarda en realizar la búsqueda Binaria, retorna a la persona que coincida con el nombre a buscar
 */
fun tiempo_Busqueda_Binaria(nombre: String, lista: MutableList<People>): People {
    var resultado: People
    tiempo = measureNanoTime {
        resultado = busqueda_Binaria(nombre, lista)!!
    }

    return resultado
}

/**
 * Método para calcular el tiempo que tarda en realizar la búsqueda Lineal, retorna a la persona que coincida con el nombre a buscar
 */
fun tiempo_Busqueda_Lineal(nombre: String, lista: MutableList<People>): People {
    var resultado: People
    tiempo = measureNanoTime {
        resultado = busqueda_Lineal(nombre, lista)!!
    }

    return resultado
}

/**
 * Método para calcular el tiempo que se tarda en realizar la búsqueda mediante una función de Kotlin, retorna una lista con las personas filtradas
 */
fun tiempo_Busqueda_Kotlin(nombre: String, lista: MutableList<People>): List<People> {
    var resultado: List<People>
    tiempo = measureNanoTime {
        resultado = busqueda_Kotlin(nombre, lista)!!
    }

    return resultado
}


/**
 * Método de Búsqueda Lineal - Recorre la MutableList elemento por elemento hasta encontrar a la persona que coincida con el nombre registrado
 */
fun busqueda_Lineal(nombre: String, lista: MutableList<People>): People? {
    var persona: People? = null
    for ((i, valor) in lista.withIndex()) {
        if (valor.nombre == nombre) {
            persona = valor
            posicion = i
        }
    }

    return persona
}

/**
 * Método de Búsqueda Binaria - Recorre la MutableList utilizando tres indicadores(inferior, centro y superior) para agilizar la búsqueda
 */
fun busqueda_Binaria(nombre: String, lista: MutableList<People>): People? {
    var n = lista.size
    var inferior = 0
    var centro = 0
    var superior = n - 1

    while (inferior <= superior) {
        centro = (superior + inferior) / 2
        if (nombre == lista[centro].nombre) {
            posicion = centro
            return lista[centro]
        } else if (nombre < lista[centro].nombre) {
            superior = centro - 1
        } else {
            inferior = centro + 1
        }
    }
    return null
}

/**
 * Método de Búsqueda Kotlin - Realiza el filtrado de los datos mediante el uso de una función nativa de kotlin
 */
fun busqueda_Kotlin(nombre: String, lista: MutableList<People>): List<People> =
    lista.filter {
        it.nombre.contains(nombre)
    }
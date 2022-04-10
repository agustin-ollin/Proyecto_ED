package Kotlin

import kotlin.system.measureNanoTime

/**
 * Variable de tiempo, es el tiempo que tarda en ejecutarse cada método de ordenamiento al momento de ser llamado
 */
var tiempo = 0L

/**
 * Método para calcular el tiempo que se tarda en ejecutar el ordenamiento de los datos de una MutableList mediante Quicksort
 */
fun tiempo_Quicksort(lista: MutableList<Persona>): MutableList<Persona> {
    var resultado: MutableList<Persona>
    tiempo = measureNanoTime {
        resultado = Quicksort(lista)
    }

    return resultado
}

/**
 * Método para calcular el tiempo que se tarda en ejecutar el ordenamiento de los datos de una MutableList mediante Bubblesort
 */
fun tiempo_Bubblesort(lista: MutableList<Persona>): MutableList<Persona> {
    var resultado: MutableList<Persona>
    tiempo = measureNanoTime {
        resultado = Bubblesort(lista)
    }

    return resultado
}

/**
 * Método para calcular el tiempo que se tarda en ejecutar el ordenamiento de los datos de una MutableList mediante Shellsort
 */
fun tiempo_Shellsort(lista: MutableList<Persona>): MutableList<Persona> {
    var resultado: MutableList<Persona>
    tiempo = measureNanoTime {
        resultado = Shellsort(lista)
    }

    return resultado
}

/**
 * Método para calcular el tiempo que se tarda en ejecutar el ordenamiento de los datos de una MutableList mediante Mergesort
 */
fun tiempo_Mergesort(lista: MutableList<Persona>): MutableList<Persona> {
    var resultado: MutableList<Persona>
    tiempo = measureNanoTime {
        resultado = Mergesort(lista)
    }

    return resultado
}


/**
 * QuickSort -> Ordenamiento Rápido
 */
fun Quicksort(items: MutableList<Persona>): MutableList<Persona> {
    if (items.count() < 1) return items
    val pivot = items[items.count() / 2]
    val equal = items.filter { it.nombre == pivot.nombre }
    val less = items.filter { it.nombre < pivot.nombre }
    val greater = items.filter { it.nombre > pivot.nombre }
    return (Quicksort(less.toMutableList()) + equal + Quicksort(greater.toMutableList())) as MutableList<Persona>
}

/**
 * BubbleSort -> Ordenamiento Burbuja Mejorado
 */
fun Bubblesort(lista: MutableList<Persona>): MutableList<Persona> {
    var bandera = true
    for (i in 0 until lista.size - 1) {
        if (!bandera) break

        bandera = false
        for (j in 0 until lista.size - 1) {
            if (lista[j].nombre > lista[j + 1].nombre) {
                bandera = true
                val aux = lista[j]
                lista[j] = lista[j + 1]
                lista[j + 1] = aux
            }
        }
    }
    return lista
}

/**
 * ShellSort -> Ordenamiento de Tipo Concha
 */
fun Shellsort(arr: MutableList<Persona>): MutableList<Persona> {
    val n = arr.size
    var gap = n / 2
    while (gap > 0) {
        var i = gap
        while (i < n) {
            val temp = arr[i]
            var j = i
            while (j >= gap && arr[j - gap].nombre > temp.nombre) {
                arr[j] = arr[j - gap]
                j -= gap
            }
            arr[j] = temp
            i += 1
        }
        gap /= 2
    }
    return arr
}

/**
 * MergeSort -> Ordenamiento mediante combinación de listas
 */
fun Mergesort(lista: MutableList<Persona>): MutableList<Persona> {
    if (lista.size <= 1) {
        return lista
    }

    val middle = lista.size / 2
    var left = lista.subList(0, middle)
    var right = lista.subList(middle, lista.size)

    return merge(Mergesort(left), Mergesort(right))
}

/**
 * Método para fusionar las listas resultantes ordenadas
 */
fun merge(left: MutableList<Persona>, right: MutableList<Persona>): MutableList<Persona> {
    var index_Left = 0
    var index_Right = 0
    var newList: MutableList<Persona> = mutableListOf()

    while (index_Left < left.count() && index_Right < right.count()) {
        if (left[index_Left].nombre <= right[index_Right].nombre) {
            newList.add(left[index_Left])
            index_Left++
        } else {
            newList.add(right[index_Right])
            index_Right++
        }
    }

    while (index_Left < left.size) {
        newList.add(left[index_Left])
        index_Left++
    }
    while (index_Right < right.size) {
        newList.add(right[index_Right])
        index_Right++
    }
    return newList
}

package Kotlin

import java.io.File
import java.io.FileNotFoundException
import java.util.*
import javax.swing.JOptionPane

/**
 * Clase Lista - Es la clase donde se cargan las MutableList con la información de las Personas
 */
class Lista {
    var lista: MutableList<People> = mutableListOf()

    // Archivos .txt con información separada por comas(,)
    val archivo: File = File("src/main/java/Kotlin/Datos/DatosClientes.txt") // -> Contiene 100 Registros
    val archivo_Mayor: File = File("src/main/java/Kotlin/Datos/DatosClientes2.txt") // -> Contiene 1500 Registros

    /**
     * Constructor sin parámetro pero con valor por defecto(true)
     */
    constructor() {

    }
}
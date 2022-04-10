package Kotlin.Operaciones

import java.io.*

/**
 * Función para generar un archivo de acuerdo a su nombre, extensión y contenido que se escribirá
 */
fun crearArchivo(contenido: String, nombre: String, extension: String){
    val file = File(nombre + extension)
    val fw = FileWriter(file, true)
    fw.write(contenido)
    fw.close()
}
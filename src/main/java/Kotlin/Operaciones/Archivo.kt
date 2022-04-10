package Kotlin.Operaciones

import java.io.*

fun crearArchivo(contenido: String, nombre: String, extension: String){
    val file = File(nombre + extension)
    val fw = FileWriter(file, true)
    fw.write(contenido)
    fw.close()
}
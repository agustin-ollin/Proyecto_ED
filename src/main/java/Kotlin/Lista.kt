package Kotlin

import java.io.File
import java.io.FileNotFoundException
import java.util.*
import javax.swing.JOptionPane

/**
 * Clase Lista - Es la clase donde se cargan las MutableList con la información de las Personas
 */
class Lista {
    var lista: MutableList<Persona> = mutableListOf()
        get() = field
        set(value) {
            field = value
        }

    // Archivos .txt con información separada por comas(,)
    val archivo: File = File("src/main/java/Kotlin/Datos/DatosClientes.txt") // -> Contiene 100 Registros
    val archivo_Mayor: File = File("src/main/java/Kotlin/Datos/DatosClientes2.txt") // -> Contiene 1500 Registros

    /**
     * Constructor con un valor de tipo Boolean como Parámetro
     */
    constructor(valor: Boolean) {
        cambiar_Contenido(valor)
    }

    /**
     * Constructor sin parámetro pero con valor por defecto(true)
     */
    constructor() {
        cambiar_Contenido(true)
    }

    /**
     * Método Cargar Datos, sirve para guardar la información de un archivo en la MutableList de la clase
     */
    fun cargar_Datos(archivo: File) {
        lista = mutableListOf()

        try {
            var scanner = Scanner(archivo)
            while (scanner.hasNextLine()) {
                var linea: String = scanner.nextLine()
                var delimitar = Scanner(linea)
                delimitar.useDelimiter("\\s*,\\s*")
                var persona = Persona()

                persona.nombre = delimitar.next()
                persona.numero_Compras = delimitar.next().toInt()
                persona.categoria = delimitar.next()
                persona.correo = delimitar.next()

                lista.add(persona)
            }
            scanner.close()
        } catch (e: FileNotFoundException) {
            JOptionPane.showMessageDialog(null, "Archivo no encontrado")
        }
    }

    /**
     * Método Cambiar Contenido, sirve para decidir el archivo que será cargado en la MutableList
     */
    fun cambiar_Contenido(bandera: Boolean) {
        if (bandera) {
            cargar_Datos(archivo)
        } else {
            cargar_Datos(archivo_Mayor)
        }
    }
}
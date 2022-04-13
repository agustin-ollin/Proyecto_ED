package Kotlin.FirebaseUtils

import Kotlin.Models.Collections
import com.google.cloud.firestore.Firestore
import java.util.concurrent.ExecutionException
import javax.swing.table.DefaultTableModel


class RFirebase {
    private var bd: Firestore? = null
    private var key = false

    constructor(){
        val connectionFirebase = FirebaseConnection()
        bd = connectionFirebase.init()
    }

    /**
     * Método Cambiar Contenido, sirve para decidir la colección que será cargada
     */
    fun changeContenido(bandera: Boolean, model: DefaultTableModel): Boolean{
        var key = false
        if (bandera) {
            key = readFirebase(model, Collections.DATAONE.collect)
        } else {
            key = readFirebase(model, Collections.DATAALL.collect)
        }

        return key
    }

    fun readFirebase(model: DefaultTableModel, collect: String): Boolean {
        key = false
        try {
            // asynchronously retrieve multiple documents
            val future = bd!!.collection(collect).get()
            // future.get() blocks on response
            val documents = future.get().documents
            if (documents.size > 0) {
                println("Leyendo Datos: ")
                for (document in documents) {
                    println(document.id + " => " + document.data["Nombre"])
                    val p = arrayOf(
                        document.data["Nombre"],
                        document.data["Compras"],
                        document.data["Categoria"],
                        document.data["Correo"]
                    )
                    model.addRow(p)
                }
            } else {
                println("No hay datos que leer")
            }
            key = true
        } catch (ex: InterruptedException) {
            System.err.println(ex.message)
        } catch (ex: ExecutionException) {
            System.err.println(ex.message)
        }
        return key
    }
}
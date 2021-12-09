package Kotlin

/**
 * Clase Persona, se registran los datos comunes como el nombre, número de compras que realiza, la categoría del producto y el correo de la persona
 */
class Persona(){
    var nombre: String = ""

    var numero_Compras: Int = 0

    var categoria: String = ""

    var correo: String = ""

    /**
     * Método toString sobreescrito
     */
    override fun toString(): String {
        return "$nombre', numero_Compras=$numero_Compras, categoria='$categoria', correo='$correo'"
    }


}

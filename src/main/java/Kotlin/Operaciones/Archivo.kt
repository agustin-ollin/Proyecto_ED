package Kotlin.Operaciones

import Kotlin.Models.DataPeople
import com.fasterxml.jackson.databind.ObjectMapper

import com.fasterxml.jackson.dataformat.xml.XmlMapper

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVPrinter
import java.io.*
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLOutputFactory
import javax.xml.stream.XMLStreamWriter


/**
 * Función para generar un archivo de acuerdo a su nombre, extensión y contenido que se escribirá
 */
fun crearArchivo(contenido: String, nombre: String, extension: String) {
    val file = File(nombre + extension)
    val fw = FileWriter(file, true)
    fw.write(contenido)
    fw.close()
}

/**
 * Función para generar archivo CSV
 */
fun crearArchivoCSV(lista: MutableList<DataPeople>, nombre: String, extension: String) {
    val printWriter: Appendable = PrintWriter(nombre + extension, "UTF-8")
    val csvPrinter: CSVPrinter =
        CSVFormat.EXCEL.withHeader("Posición", "Tiempo", "Nombre", "Cantidad Compras", "Categoría", "Correo")
            .print(printWriter)

    lista.forEach {
        csvPrinter.printRecord(it.posicion, it.tiempo, it.nombre, it.compras, it.categoria, it.correo)
    }
    csvPrinter.flush()
    csvPrinter.close()
}

/**
 * Función para leer archivo csv
 */
fun leerCSV(nombreArchivo: String) {
    val inputStream: InputStream = FileInputStream(nombreArchivo)
    val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
    val reader: Reader = BufferedReader(inputStreamReader)
    // Especifica el encabezado del csv
    val csvParser =
        CSVFormat.EXCEL.withHeader("Posición", "Tiempo", "Nombre", "Cantidad Compras", "Categoría", "Correo")
            .parse(reader)

    // Almacene cada fila de registros en la lista
    val list = csvParser.records

    // lista de bucles variables
    for (i in list.indices) {
        println(list[i]["Posición"] + ":" + list[i]["Tiempo"] + ":" + list[i]["Nombre"] + ":" + list[i]["Cantidad Compras"] + ":" + list[i]["Categoría"] + ":" + list[i]["Correo"])
    }
}

/**
 * Función para crear archivo CSV
 */
fun crearArchivoXML(lista: MutableList<DataPeople>, nombre: String, extension: String) {
    val xmlMapper: ObjectMapper = XmlMapper()
    val file = File(nombre + extension)
    val fw = FileWriter(file, true)

    lista.forEach {
        val xml = xmlMapper.writeValueAsString(it)
        xmlMapper.writeValue(file, it)
    }
}

/*
fun crearArchivoXML(lista: MutableList<DataPeople>, nombre: String, extension: String){
    var xmlInputFactory: XMLInputFactory = XMLInputFactory.newFactory();
    var xmlOutputFactory: XMLOutputFactory = XMLOutputFactory.newFactory();
    var out: StringWriter = StringWriter();
    var sw: XMLStreamWriter = xmlOutputFactory.createXMLStreamWriter(out);

    var mapper: XmlMapper = XmlMapper(xmlInputFactory);

    sw.writeStartDocument();
    sw.writeStartElement("root");

    lista.forEach {
        mapper.writeValue(sw, it);
        sw.writeComment("People");
        sw.writeEndElement();
        sw.writeEndDocument();
    }
}*/

/**
 * Función para leer archivo XML
 */
fun leerXML() {

}

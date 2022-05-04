import Kotlin.*;
import Kotlin.FirebaseUtils.RFirebase;
import Kotlin.Models.DataPeople;
import Kotlin.Operaciones.ArchivoKt;
import Kotlin.Operaciones.Metodos_DeBusquedaKt;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Ventana Búsqueda, se realizan las operaciones para comprobar el tiempo que tardan los métodos en encontrar valores en una MutableList
 */
public class Ventana_Busqueda {
    // Componentes de la Ventana
    private JPanel panel;
    private JTable tabla;
    private JTextField buscar_TextField;
    private JButton kotlinButton;
    private JButton linealButton;
    private JButton binariaButton;
    private JScrollPane scroll_Tabla;
    private JPanel penel_BotonesJtextField;
    private JTextField textField_Nombre;
    private JTextField textField_Compras;
    private JTextField textField_Categoria;
    private JTextField textField_Correo;
    private JPanel panel_Datos;
    private JLabel etiqueta_Nombre;
    private JLabel etiqueta_Compras;
    private JLabel etiqueta_Categoria;
    private JLabel etiqueta_Correo;
    private JLabel etiqueta_Posicion;
    private JLabel posicion;
    private JLabel etiqueta_Tiempo;
    private JLabel tiempo;
    private JLabel etiqueta_Nanosegundos;
    private JLabel etiqueta_Datos;
    private JRadioButton menor_Radio;
    private JRadioButton mayor_Radio;
    private JLabel etiqueta_Cantidad;
    private JLabel etiqueta_Metodos;
    private JButton guardarButton;
    private JTable tablaImportar;
    private JComboBox extensionCmboBox;
    private JTextField textFieldNombreArchivo;
    private JLabel etqArchivo;
    private JLabel etqNombreExtension;
    private JButton exportarButton;
    private JLabel etqTitle;

    // Grupo de Radio Botones
    private ButtonGroup grupo_RadioButton = new ButtonGroup();

    // Componentes para la JTable
    private String columnas[] = {"Nombre", "N° Compras", "Categoría", "Correo"};
    private Object[][] matriz = new Object[][]{};
    ;
    private DefaultTableModel modelo = new DefaultTableModel(matriz, columnas);

    // Componentes para JTable para datos a importar
    private String columnasImport[] = {"Posición", "Tiempo de Búsqueda", "Nombre", "N° Compras", "Categoría", "Correo"};
    private Object[][] matrizImport = new Object[][]{};
    private DefaultTableModel modeloImport = new DefaultTableModel(matrizImport, columnasImport);

    // Componente para ComboBox
    private String extensiones[] = {".txt",".csv",".xml"};

    // Lista a implementar
    private Lista lista = new Lista();

    // Atributo para determinar el tipo de lista a generar
    private boolean estado = true;

    // Carga de la base de datos en Firebase
    private static RFirebase crudf;

    /**
     * Método constructor para inicializar y aplicar funcionalidad a los componentes de la ventana
     */
    public Ventana_Busqueda(RFirebase f) {
        // Inicialización de Componentes
        agrupar_RadioButton();
        agregarComboBox();
        eventosButtons();

        tablaImportar.setModel(modeloImport);
        tabla.setModel(modelo);
        crudf = f;
    }

    /**
     * Método para cargar los datos de Firestore
     *
     * @param key Valor boolean para seleccionar la colección a cargar
     */
    private void readFirebase(boolean key) {

        lista.setLista(crudf.changeContenido(key));
        lista.setLista(Metodos_DeOrdenamientoKt.tiempo_Quicksort(lista.getLista()));
        inicializarDatos(lista);
    }

    /**
     * Método para limpiar la interfaz gráfica
     */
    private void limpiarInterfaz() {
        tiempo.setText("0");
        posicion.setText("0");
        buscar_TextField.setText("");
        textField_Correo.setText("");
        textField_Categoria.setText("");
        textField_Compras.setText("");
        textField_Nombre.setText("");
        textFieldNombreArchivo.setText("");
        buscar_TextField.setText("");
    }

    /**
     * Método para asignarle los eventos a JButton, radioButton y comboBox
     */
    private void eventosButtons() {
        // ActionListener para el botón de búsqueda por Kotlin
        kotlinButton.addActionListener(e -> {
            try {
                if (buscar_TextField.getText().isEmpty() || buscar_TextField.getText() == "") {
                    throw new NullPointerException("Ingrese los datos a buscar");
                }
                List<People> list = Metodos_DeBusquedaKt.tiempo_Busqueda_Kotlin(buscar_TextField.getText(), lista.getLista());
                importar_Datos(list.get(0), Metodos_DeBusquedaKt.getPosicion());
                posicion.setText("NC");

            } catch (NullPointerException nul) {
                generar_MensajesError("No se encontró en la lista" + nul.getMessage());
            }
        });

        // ActionListener para el botón de búsqueda Lineal
        linealButton.addActionListener(e -> {
            try {
                People persona = Metodos_DeBusquedaKt.tiempo_Busqueda_Lineal(buscar_TextField.getText(), lista.getLista());
                importar_Datos(persona, Metodos_DeBusquedaKt.getPosicion());
            } catch (NullPointerException nul) {
                generar_MensajesError("No se encontró en la lista" + nul.getMessage());
            }
        });

        // ActionListener para el botón de búsqueda Binaria
        binariaButton.addActionListener(e -> {
            try {
                People persona = Metodos_DeBusquedaKt.tiempo_Busqueda_Binaria(buscar_TextField.getText(), lista.getLista());
                importar_Datos(persona, Metodos_DeBusquedaKt.getPosicion());
            } catch (NullPointerException nul) {
                generar_MensajesError("No se encontró en la lista" + nul.getMessage());
            }
        });

        // ActionListener para selección de 100 datos en tabla
        menor_Radio.addActionListener(e -> {
            estado = true;
            modelo.setRowCount(0);
            readFirebase(estado);
        });

        // ActionListener para selección de 1500 datos en tabla
        mayor_Radio.addActionListener(e -> {
            estado = false;
            modelo.setRowCount(0);
            readFirebase(estado);
        });

        // ActionListener para agregar datos a la tabla a Importar
        guardarButton.addActionListener(e -> {
            if (!textField_Nombre.getText().equals("") && !textField_Correo.getText().equals("") && !textField_Categoria.getText().equals("") && !textField_Compras.getText().equals("")) {
                insertarEnJTable();
                limpiarInterfaz();
            } else {
                generar_MensajesError("Realice primero la búsqueda de datos");
            }
        });

        // ActionListener para exportar los datos a un archivo
        exportarButton.addActionListener(e -> {
            if (tablaImportar.getRowCount() != 0 && !textFieldNombreArchivo.getText().equals("")) {
                importarArchivo();
                limpiarInterfaz();
                modeloImport.setRowCount(0);
            } else {
                generar_MensajesError("No se han agregado datos a la tabla");
            }
        });
    }

    /**
     * Método para importar datos a un archivo
     */
    private void importarArchivo() {
        switch (extensionCmboBox.getSelectedItem().toString()){
            case ".txt":{
                String contenido = obtenerDatosDeJTable();
                ArchivoKt.crearArchivo(contenido, textFieldNombreArchivo.getText(), extensionCmboBox.getSelectedItem().toString());
                JOptionPane.showMessageDialog(null, "Los datos se cargaron correctamente", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
            }
            break;
            case ".csv":{
                ArchivoKt.crearArchivoCSV(obtenerDatosDeJTableAList(), textFieldNombreArchivo.getText(), extensionCmboBox.getSelectedItem().toString());
                JOptionPane.showMessageDialog(null, "Los datos se cargaron correctamente", "INFORMACIÓN", JOptionPane.INFORMATION_MESSAGE);
                ArchivoKt.leerCSV(textFieldNombreArchivo.getText() + extensionCmboBox.getSelectedItem().toString());
            }
            break;
            case ".xml":{

            }
            break;
        }
    }

    /**
     * Método para obtener el contenido de una JTable y guardarlo en un String
     *
     * @return
     */
    private String obtenerDatosDeJTable() {
        int row = tablaImportar.getRowCount();
        String contenido = "--------------------------------------------------";

        for (int i = 0; i < row; i++) {
            contenido += "\nPosición: " + tablaImportar.getValueAt(i, 0).toString() +
                    "\nTiempo de Búsqueda: " + tablaImportar.getValueAt(i, 1).toString() +
                    "\nNombre: " + tablaImportar.getValueAt(i, 2).toString() +
                    "\nN° Compras: " + tablaImportar.getValueAt(i, 3).toString() +
                    "\nCategoría: " + tablaImportar.getValueAt(i, 4).toString() +
                    "\nCorreo: " + tablaImportar.getValueAt(i, 5).toString() +
                    "\n--------------------------------------------------";
        }

        return contenido;
    }

    /**
     * Método para guargar los datos de la tabla a importar en un ArrayList de DataPeople
     * @return ArrayList
     */
    private ArrayList<DataPeople> obtenerDatosDeJTableAList(){
        ArrayList<DataPeople> list = new ArrayList<DataPeople>();

        int row = tablaImportar.getRowCount();
        for (int i = 0; i < row; i++) {
            DataPeople dp = new DataPeople(tablaImportar.getValueAt(i, 0).toString(), tablaImportar.getValueAt(i, 1).toString()
                    , tablaImportar.getValueAt(i, 2).toString(), tablaImportar.getValueAt(i, 3).toString(),
                    tablaImportar.getValueAt(i, 4).toString(), tablaImportar.getValueAt(i, 5).toString());

            list.add(dp);
        }

        return list;
    }

    /**
     * Ingresa los datos a la tabla a importar en archivo
     */
    private void insertarEnJTable() {
        Object[] temp = {posicion.getText(), tiempo.getText(), textField_Nombre.getText(), textField_Compras.getText(), textField_Categoria.getText(), textField_Correo.getText()};
        modeloImport.addRow(temp);
    }


    /**
     * Método para agregar las extensiones de archivos que se pueden generar
     */
    private void agregarComboBox() {
        for (String e : extensiones) {
            extensionCmboBox.addItem(e);
        }
    }

    /**
     * Guarda los valores de una lista a la JTable
     *
     * @param list Lista de la que obtendremos una MutableList
     */
    private void inicializarDatos(@NotNull Lista list) {
        for (int i = 0; i < list.getLista().size(); i++) {
            Object[] temp = {list.getLista().get(i).getNombre(),
                    list.getLista().get(i).getCompras(),
                    list.getLista().get(i).getCategoria(),
                    list.getLista().get(i).getCorreo()};
            modelo.addRow(temp);
        }
    }

    /**
     * Método main para ejecutar la ventana
     *
     * @param args Parámetros de clase
     */
    public static void main(String[] args, RFirebase firebase) {
        JFrame ventana = new JFrame("Nueva Ventana");
        ventana.setContentPane(new Ventana_Busqueda(firebase).panel);
        ventana.setUndecorated(true);
        ventana.pack();
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        generar_JMenuBar(ventana, args);
    }

    /**
     * Método para mostrar en pantalla los datos de la persona a buscar
     *
     * @param persona Persona encontrada
     * @param pos     Posición de la MutableList donde se encontró a la persona
     */
    private void importar_Datos(People persona, int pos) {
        textField_Nombre.setText(persona.getNombre());
        textField_Categoria.setText(persona.getCategoria());
        textField_Compras.setText(persona.getCompras());
        textField_Correo.setText(persona.getCorreo());

        posicion.setText(String.valueOf(pos));
        tiempo.setText(String.valueOf(Metodos_DeBusquedaKt.getTiempo()));
    }

    /**
     * Método para generar los mensajes de error
     *
     * @param mensaje Mensaje de la Excepción generada
     */
    private void generar_MensajesError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Método para agregar un JMenuBar a la ventana
     *
     * @param ventana Ventana a la que será agregado
     * @param args    Parámetros de la clase
     */
    public static void generar_JMenuBar(JFrame ventana, String[] args) {
        JMenuBar menubar = new JMenuBar();
        ventana.setJMenuBar(menubar);

        JSeparator separador = new JSeparator();

        JMenu opciones = new JMenu("Opciones");
        menubar.add(opciones);

        JMenuItem busqueda = new JMenuItem("Métodos de Ordenamiento");
        JMenuItem salir = new JMenuItem("Salir");

        busqueda.setIcon(Imagenes.Companion.getOrdenar());
        salir.setIcon(Imagenes.Companion.getSalir());

        opciones.add(busqueda);
        opciones.add(separador);
        opciones.add(salir);

        // ActionListener para finalizar la ejecución del programa
        salir.addActionListener(e -> System.exit(0));

        // ActionListener para ejecutar la ventana de métodos de ordenamiento
        busqueda.addActionListener(e -> {
            ventana.setVisible(false);
            Ventana_Ordenamiento.main(args, crudf);
        });
    }

    /**
     * Método para agrupar los RadioButton en un grupo
     */
    private void agrupar_RadioButton() {
        grupo_RadioButton.add(menor_Radio);
        grupo_RadioButton.add(mayor_Radio);
    }
}
import Kotlin.FirebaseUtils.RFirebase;
import Kotlin.Imagenes;
import Kotlin.Lista;
import Kotlin.Metodos_DeOrdenamientoKt;
import Kotlin.People;
import mdlaf.MaterialLookAndFeel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Ventana de Ordenamiento, se realiza el ordenamiento de datos a través de diferentes métodos con el fin de comparar
 * que tan rápidos son con respecto a los demás métodos
 */
public class Ventana_Ordenamiento {
    // Componentes de la Ventana
    private JPanel panel;
    private JTable tabla_DatosDesordenados;
    private JRadioButton menor_radio;
    private JRadioButton mayor_radio;
    private JTable tabla_DatosOrdenados;
    private JPanel panel_Botones;
    private JLabel etiqueta_Tabla1;
    private JScrollPane scroll_Tabla1;
    private JSeparator separador_uno;
    private JLabel etiqueta_CantidadDatos;
    private JLabel etiqueta_Tabla2;
    private JScrollPane scroll_Tabla2;
    private JPanel panel_Resultados;
    private JLabel etiqueta_Tiempo;
    private JLabel nanosegundos;
    private JButton quicksort;
    private JButton bubblesort;
    private JButton shellsort;
    private JButton mergesort;
    private JLabel tiempo;

    // Componentes para los JTable
    private String columnas[] = {"Nombre", "N° Compras", "Categoría", "Correo"};
    private Object[][] matriz;
    private DefaultTableModel modelo = new DefaultTableModel(matriz, columnas);

    private Object[][] matriz_DatosOrdenados;
    private DefaultTableModel modelo_DatosOrdenados = new DefaultTableModel(matriz_DatosOrdenados, columnas);

    // Lista a implementar
    private Lista lista = new Lista();

    // Grupo de Radio Botones
    private ButtonGroup grupo_RadioButton = new ButtonGroup();

    // Tiempo que tarda en ejecutarse una operación
    private Long tiempo_Ejecucion;

    // Atributo para identificar el tipo de lista a generar
    private Boolean estado = true;

    // Carga de la base de datos en Firebase
    private static RFirebase crudf;

    /**
     * Método constructor sin parámetros y con declaraciones internas sobre el funcionamiento de la ventana
     */
    public Ventana_Ordenamiento(RFirebase f) {
        // Inicialización de Componentes
        mayor_radio.setSelected(false);
        menor_radio.setSelected(false);
        loadTableData();
        agrupar_RadioButton();
        eventsButtons();
        crudf = f;
    }

    /**
     * Método para cargar los datos de Firestore
     *
     * @param key Valor boolean para seleccionar la colección a cargar
     */
    private void readFirebase(boolean key) {
        lista.setLista(crudf.changeContenido(key));
        inicializarDatos(lista);
    }

    /**
     * Método para realizar los eventos de JButton y RadioButton
     */
    private void eventsButtons() {
        // ActionListener del botón Quicksort
        quicksort.addActionListener(e -> {
            reiniciar_ValoresDeOrdenamiento();
            lista.setLista(Metodos_DeOrdenamientoKt.tiempo_Quicksort(lista.getLista()));
            tiempo_Ejecucion = Metodos_DeOrdenamientoKt.getTiempo();
            realizar_Ordenamiento(lista);
            tiempo.setText(tiempo_Ejecucion.toString());
        });

        // ActionListener del botón Bubblesort
        bubblesort.addActionListener(e -> {
            reiniciar_ValoresDeOrdenamiento();
            lista.setLista(Metodos_DeOrdenamientoKt.tiempo_Bubblesort(lista.getLista()));
            tiempo_Ejecucion = Metodos_DeOrdenamientoKt.getTiempo();
            realizar_Ordenamiento(lista);
            tiempo.setText(tiempo_Ejecucion.toString());
        });

        // ActionListener del botón Shellsort
        shellsort.addActionListener(e -> {
            reiniciar_ValoresDeOrdenamiento();
            lista.setLista(Metodos_DeOrdenamientoKt.tiempo_Shellsort(lista.getLista()));
            tiempo_Ejecucion = Metodos_DeOrdenamientoKt.getTiempo();
            realizar_Ordenamiento(lista);
            tiempo.setText(tiempo_Ejecucion.toString());
        });

        // ActionListener del botón Mergesort
        mergesort.addActionListener(e -> {
            reiniciar_ValoresDeOrdenamiento();
            lista.setLista(Metodos_DeOrdenamientoKt.tiempo_Mergesort(lista.getLista()));
            tiempo_Ejecucion = Metodos_DeOrdenamientoKt.getTiempo();
            realizar_Ordenamiento(lista);
            tiempo.setText(tiempo_Ejecucion.toString());
        });

        // ActionListener para la selección de lista con 100 datos
        menor_radio.addActionListener(e -> {
            estado = true;
            modelo.setRowCount(0);
            modelo_DatosOrdenados.setRowCount(0);
            readFirebase(estado);
        });

        // ActionListener para la selección de lista con 1500 datos
        mayor_radio.addActionListener(e -> {
            estado = false;
            modelo.setRowCount(0);
            modelo_DatosOrdenados.setRowCount(0);
            readFirebase(estado);
        });
    }

    /**
     * Método para inicializar componentes en las JTables
     */
    private void loadTableData() {
        tabla_DatosDesordenados.setModel(modelo);
        tabla_DatosOrdenados.setModel(modelo_DatosOrdenados);
    }

    /**
     * Carga los datos de una lista en la tabla con los datos desordenados
     *
     * @param list Lista de la que se obtendrá su MutableList
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
     * Método para agrupar los RadioButton en un grupo
     */
    private void agrupar_RadioButton() {
        grupo_RadioButton.add(menor_radio);
        grupo_RadioButton.add(mayor_radio);
    }

    /**
     * Método para reiniciar la tabla de datos ordenados
     */
    private void reiniciar_ValoresDeOrdenamiento() {
        modelo_DatosOrdenados.setRowCount(0);
        tiempo_Ejecucion = 0L;
        lista.setLista(cargarTablaALista());
    }

    /**
     * Método para cargar los datos de la tabla a la lista
     *
     * @return
     */
    private ArrayList<People> cargarTablaALista() {
        int row = tabla_DatosDesordenados.getRowCount();
        ArrayList<People> list = new ArrayList<People>();
        for (int i = 0; i < row; i++) {
            People p = new People(tabla_DatosDesordenados.getValueAt(i, 0).toString(), tabla_DatosDesordenados.getValueAt(i, 1).toString(), tabla_DatosDesordenados.getValueAt(i, 2).toString(), tabla_DatosDesordenados.getValueAt(i, 3).toString());
            list.add(p);
        }

        return list;
    }

    /**
     * Método para registrar los valores de una MutableList en la tabla de datos Ordenados
     *
     * @param list
     */
    private void realizar_Ordenamiento(Lista list) {
        for (int i = 0; i < list.getLista().size(); i++) {
            Object[] temp = {list.getLista().get(i).getNombre(),
                    list.getLista().get(i).getCompras(),
                    list.getLista().get(i).getCategoria(),
                    list.getLista().get(i).getCorreo()};
            modelo_DatosOrdenados.addRow(temp);
        }
    }

    /**
     * Método main de la ventana para poder ejecutarla
     *
     * @param args Parámetros de clase
     */
    public static void main(String[] args, RFirebase firebase) {

        JFrame ventana = new JFrame("Nueva Ventana");
        ventana.setContentPane(new Ventana_Ordenamiento(firebase).panel);
        ventana.setUndecorated(true);
        ventana.pack();
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        generar_JMenuBar(ventana, args);
    }

    /**
     * Método para agregar y generar funcionalidad a JMenuBar
     *
     * @param ventana Ventana a la que se le agregará el JMenuBar
     * @param args    Parámetro de clase
     */
    public static void generar_JMenuBar(JFrame ventana, String[] args) {
        JMenuBar menubar = new JMenuBar();
        ventana.setJMenuBar(menubar);

        JMenu opciones = new JMenu("Opciones");
        menubar.add(opciones);

        JSeparator separador = new JSeparator();

        JMenuItem busqueda = new JMenuItem("Métodos de Búsqueda");
        JMenuItem salir = new JMenuItem("Salir");

        busqueda.setIcon(Imagenes.Companion.getBuscar());
        salir.setIcon(Imagenes.Companion.getSalir());

        opciones.add(busqueda);
        opciones.add(separador);
        opciones.add(salir);

        // ActionListener para finalizar con la ejecución del programa
        salir.addActionListener(e -> System.exit(0));

        // ActionListener para ir a la ventana de búsqueda
        busqueda.addActionListener(e -> {
            ventana.setVisible(false);
            Ventana_Busqueda.main(args, crudf);
        });
    }
}
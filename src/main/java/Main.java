import Kotlin.FirebaseUtils.RFirebase;
import Kotlin.Imagenes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase Main, es la primera ventana que aparece, se implementó para que al inicio se seleccione el tipo de operaciones
 * con las que se quiere empezar
 */
public class Main extends JFrame {
    private JButton button_Ordenamiento = new JButton();
    private JPanel panel = new JPanel();
    private JButton button_Busqueda = new JButton();
    private JLabel etiqueta_Orden = new JLabel("Ordenamiento");
    private JLabel etiqueta_Busqueda = new JLabel("Búsqueda");
    private RFirebase firebase;
    /**
     * Método constructor con la inicialización de todos los componentes de la ventana
     */
    public Main() {
        // Configuración inicial
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setUndecorated(true);
        this.setContentPane(panel);
        this.setBackground(new Color(0, 0, 0, 205));
        panel.setLayout(null);
        setSize(new Dimension(630, 350));
        setLocationRelativeTo(null);
        setVisible(true);

        // Métodos de inicialización
        componentes_DelPanel();
        agregar_Imagenes();
        eventos();

        firebase = new RFirebase();
    }

    /**
     * Se cargan los JButton al panel principal y se les modifican sus atributos
     */
    private void componentes_DelPanel() {
        panel.add(etiqueta_Orden);
        panel.add(etiqueta_Busqueda);

        // Asignar posición y tamaño a los JLabel
        etiqueta_Orden.setBounds(10, 5, 300, 30);
        etiqueta_Busqueda.setBounds(320, 5, 300, 30);

        // Color de letra
        etiqueta_Busqueda.setForeground(Color.WHITE);
        etiqueta_Orden.setForeground(Color.WHITE);

        // Configuración de la fuente
        etiqueta_Orden.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        etiqueta_Busqueda.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));

        // Agregar JButton al panel
        panel.add(button_Ordenamiento);
        panel.add(button_Busqueda);

        // Asignar posición y tamaño a los JButton
        button_Ordenamiento.setBounds(10, 40, 300, 300);
        button_Busqueda.setBounds(320, 40, 300, 300);

        // Asignar Background
        button_Busqueda.setBackground(new Color(12, 9, 9, 252));
        button_Ordenamiento.setBackground(new Color(12, 9, 9, 252));

    }

    /**
     * Método para agregar imágenes a los botones
     */
    private void agregar_Imagenes() {
        button_Ordenamiento.setIcon(new ImageIcon(Imagenes.Companion.getMetodos_Ordenamiento().getImage().getScaledInstance(button_Ordenamiento.getWidth(), button_Ordenamiento.getHeight(), Image.SCALE_SMOOTH)));
        button_Busqueda.setIcon(new ImageIcon(Imagenes.Companion.getMetodos_Busqueda().getImage().getScaledInstance(button_Busqueda.getWidth(), button_Busqueda.getHeight(), Image.SCALE_SMOOTH)));
    }

    /**
     * Método para asignar eventos a los JButton
     */
    private void eventos() {
        // Botón para ir a la ventana de métodos de ordenamiento
        button_Ordenamiento.addActionListener(e -> {
            String[] args = new String[0];
            setVisible(false);
            Ventana_Ordenamiento.main(args, firebase);
        });

        // Botón para ir a la ventana de métodos de búsqueda
        button_Busqueda.addActionListener(e -> {
            String[] args = new String[0];
            setVisible(false);
            Ventana_Busqueda.main(args, firebase);
        });
    }

    /**
     * Método main para ejecutar la clase
     *
     * @param args Argumentos de la clase
     */
    public static void main(String[] args) {
        Main ventana = new Main();
    }
}

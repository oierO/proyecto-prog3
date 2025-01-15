package gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class VentanaAjustes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame ventanaPrincipal; // Referencia a la ventana principal
	private Properties config; // Propiedades para guardar el estado del modo oscuro
	private final String CONFIG_FILE = "config.properties"; // Ruta del archivo de configuración
	private JCheckBox chkModoOscuro;
	private Map<Component, Color[]> coloresPredeterminados; // Map para guardar los colores originales
	private Locale currentLocale;
	private ResourceBundle bundle;
	private String sTitulo, sModOs, sDesNot, sNotDes, sNotHab;

	public VentanaAjustes(JFrame ventanaPrincipal, Locale locale) {
		this.ventanaPrincipal = ventanaPrincipal;
		config = new Properties();
		coloresPredeterminados = new HashMap<>();

		// Idioma
		currentLocale = locale;
		bundle = ResourceBundle.getBundle("VentanaAjustesBundle", currentLocale);
		sTitulo = bundle.getString("sTitulo");
		sModOs = bundle.getString("sModOs");
		sDesNot = bundle.getString("sDesNot");
		sNotDes = bundle.getString("sNotDes");
		sNotHab = bundle.getString("sNotHab");

		// Cargar configuración
		boolean modoOscuro = cargarConfiguracion();

		setTitle(sTitulo);
		setSize(300, 200);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(2, 1));

		// Checkbox para activar/desactivar el modo oscuro
		chkModoOscuro = new JCheckBox(sModOs, modoOscuro);
		chkModoOscuro.addActionListener(e -> {
			boolean activar = chkModoOscuro.isSelected();
			aplicarModoOscuro(activar);
			guardarConfiguracion(activar); // Guardar la preferencia en el archivo
		});

		// Checkbox para habilitar/deshabilitar notificaciones
		JCheckBox chkNotificaciones = new JCheckBox(sDesNot);
		chkNotificaciones.addActionListener(e -> {
			if (chkNotificaciones.isSelected()) {
				JOptionPane.showMessageDialog(null, sNotDes);
			} else {
				JOptionPane.showMessageDialog(null, sNotHab);
			}
		});

		add(chkModoOscuro);
		add(chkNotificaciones);

		// Aplicar el estado inicial del modo oscuro
		aplicarModoOscuro(modoOscuro);

		setVisible(true);
	}

	private void aplicarModoOscuro(boolean activar) {
		// Definir colores para modo oscuro y claro
		Color fondoOscuro = Color.DARK_GRAY;
		Color textoOscuro = Color.WHITE;
		@SuppressWarnings("unused")
		Color fondoClaro = Color.WHITE;
		@SuppressWarnings("unused")
		Color textoClaro = Color.BLACK;
		Color fondoBotonClaro = activar ? new Color(100, 100, 100) : new Color(220, 220, 220); // Botones más claritos

		if (activar) {
			// Guardar colores predeterminados antes de cambiarlos
			if (coloresPredeterminados.isEmpty()) {
				guardarColoresPredeterminados(ventanaPrincipal.getContentPane());
			}
			// Aplicar modo oscuro
			cambiarColores(ventanaPrincipal.getContentPane(), fondoOscuro, textoOscuro, fondoBotonClaro);
		} else {
			// Restaurar colores predeterminados
			restaurarColoresPredeterminados(ventanaPrincipal.getContentPane());
		}
	}

	private void cambiarColores(Component componente, Color fondo, Color texto, Color fondoBotonClaro) {
		// Excluir el checkbox de modo oscuro para que no cambie de color
		if (componente == chkModoOscuro) {
			return;
		}

		// Verificar recursivamente si el componente pertenece a PanelParking
		if (esParteDePanelParking(componente)) {
			return; // No modificar los colores de los componentes en PanelParking
		}

		// Aplicar colores personalizados para botones fuera de PanelParking
		if (componente instanceof JButton) {
			componente.setBackground(fondoBotonClaro);
			componente.setForeground(texto);
		} else if (componente instanceof JComponent) {
			componente.setBackground(fondo);
			componente.setForeground(texto);
		}

		if (componente instanceof Container) {
			for (Component child : ((Container) componente).getComponents()) {
				cambiarColores(child, fondo, texto, fondoBotonClaro);
			}
		}
	}

	private boolean esParteDePanelParking(Component componente) {
		Component parent = componente.getParent();
		while (parent != null) {
			if (parent instanceof PanelParking) {
				return true; // Encontró un ancestro que es PanelParking
			}
			parent = parent.getParent();
		}
		return false;
	}

	private void guardarColoresPredeterminados(Component componente) {
		if (!coloresPredeterminados.containsKey(componente)) {
			coloresPredeterminados.put(componente,
					new Color[] { componente.getBackground(), componente.getForeground() });
		}

		if (componente instanceof Container) {
			for (Component child : ((Container) componente).getComponents()) {
				guardarColoresPredeterminados(child);
			}
		}
	}

	private void restaurarColoresPredeterminados(Component componente) {
		if (coloresPredeterminados.containsKey(componente)) {
			Color[] colores = coloresPredeterminados.get(componente);
			componente.setBackground(colores[0]);
			componente.setForeground(colores[1]);
		}

		if (componente instanceof Container) {
			for (Component child : ((Container) componente).getComponents()) {
				restaurarColoresPredeterminados(child);
			}
		}
	}

	private boolean cargarConfiguracion() {
		try (InputStream input = new FileInputStream(CONFIG_FILE)) {
			config.load(input);
			return Boolean.parseBoolean(config.getProperty("modoOscuro", "false"));
		} catch (IOException e) {
			System.err.println("No se pudo cargar la configuración: " + e.getMessage());
			return false; // Valor por defecto
		}
	}

	private void guardarConfiguracion(boolean modoOscuro) {
		try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
			config.setProperty("modoOscuro", Boolean.toString(modoOscuro));
			config.store(output, "Configuración de VentanaAjustes");
		} catch (IOException e) {
			System.err.println("No se pudo guardar la configuración: " + e.getMessage());
		}
	}
}

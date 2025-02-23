package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import domain.Usuario;
import domain.Vehiculo; // Importa la clase Vehiculo
import main.DeustoTaller;

public class PanelSesion extends JPanel {
	private static final long serialVersionUID = 1L;
	private Locale currentLocale;
	private ResourceBundle bundle;
	private JLabel saludo, ultimaSesion, labelUsernameTitulo, labelNombreTitulo, labelApellidoTitulo;
	private JLabel labelVehiculosTitulo, sinVehiculos;
	private JButton botonGestor;

	public PanelSesion(JFrame ventanaGrafica, Locale locale) {
		// Idioma
		currentLocale = locale;
		bundle = ResourceBundle.getBundle("PanelSesionBundle", currentLocale);

		// Fuente grande para todo el texto
//		Font fuenteGrande = new Font("Arial", Font.PLAIN, 18);

		// Configurar el diseño principal como BorderLayout
		setLayout(new BorderLayout());

		// Obtener información del usuario
		Usuario usuario = DeustoTaller.getSesion();

		// **Parte superior (NORTH): Saludo, última sesión y botón**
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS)); // Apilado vertical
		saludo = new JLabel(bundle.getString("saludo") + usuario.getNombre() + "!");
//		saludo.setFont(fuenteGrande);
		saludo.setAlignmentX(CENTER_ALIGNMENT);
		panelSuperior.add(saludo);

		String ultimaSesionTexto = usuario.gethUltimaSesion()
				.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
		ultimaSesion = new JLabel(bundle.getString("ultimaSesion") + ultimaSesionTexto);
//		ultimaSesion.setFont(fuenteGrande);
		ultimaSesion.setAlignmentX(CENTER_ALIGNMENT);
		panelSuperior.add(ultimaSesion);

		botonGestor = new JButton(bundle.getString("botonGestor"));
//		botonGestor.setFont(fuenteGrande);
		botonGestor.setAlignmentX(CENTER_ALIGNMENT);
		botonGestor.addActionListener(e -> {
			DeustoTaller.getVSesion().openGestor(); // Abrir el gestor
			ventanaGrafica.dispose(); // Cerrar la ventana actual
		});
		panelSuperior.add(botonGestor);

		// Agregar el panel superior al área NORTH
		add(panelSuperior, BorderLayout.NORTH);

		// **Parte central (CENTER): Información del usuario y vehículos**
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS)); // Apilado vertical

		// Panel para "Username"
		JPanel panelUsername = new JPanel();
		panelUsername.setLayout(new BoxLayout(panelUsername, BoxLayout.X_AXIS)); // Diseño horizontal

		labelUsernameTitulo = new JLabel(bundle.getString("labelUsernameTitulo")); // Etiqueta del título
//		labelUsernameTitulo.setFont(new Font("Arial", Font.BOLD, 18)); // Fuente en negrita

		JLabel labelUsernameValor = new JLabel(usuario.getUsername()); // Etiqueta del valor
//		labelUsernameValor.setFont(new Font("Arial", Font.PLAIN, 18)); // Fuente normal

		panelUsername.add(labelUsernameTitulo);
		panelUsername.add(labelUsernameValor);
		panelCentral.add(panelUsername);

		// Panel para "Nombre"
		JPanel panelNombre = new JPanel();
		panelNombre.setLayout(new BoxLayout(panelNombre, BoxLayout.X_AXIS));

		labelNombreTitulo = new JLabel(bundle.getString("labelNombreTitulo"));
//		labelNombreTitulo.setFont(new Font("Arial", Font.BOLD, 18));

		JLabel labelNombreValor = new JLabel(usuario.getNombre());
//		labelNombreValor.setFont(new Font("Arial", Font.PLAIN, 18));

		panelNombre.add(labelNombreTitulo);
		panelNombre.add(labelNombreValor);
		panelCentral.add(panelNombre);

		// Panel para "Apellido"
		JPanel panelApellido = new JPanel();
		panelApellido.setLayout(new BoxLayout(panelApellido, BoxLayout.X_AXIS));

		labelApellidoTitulo = new JLabel(bundle.getString("labelApellidoTitulo"));
//		labelApellidoTitulo.setFont(new Font("Arial", Font.BOLD, 18));

		JLabel labelApellidoValor = new JLabel(usuario.getApellido());
//		labelApellidoValor.setFont(new Font("Arial", Font.PLAIN, 18));

		panelApellido.add(labelApellidoTitulo);
		panelApellido.add(labelApellidoValor);
		panelCentral.add(panelApellido);

		// Panel para "Vehículos"
		JPanel panelVehiculosTitulo = new JPanel();
		panelVehiculosTitulo.setLayout(new BoxLayout(panelVehiculosTitulo, BoxLayout.X_AXIS));

		labelVehiculosTitulo = new JLabel(bundle.getString("labelVehiculosTitulo"));
//		labelVehiculosTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		panelVehiculosTitulo.add(labelVehiculosTitulo);
		panelCentral.add(panelVehiculosTitulo);

		// Listar los vehículos
		List<Vehiculo> vehiculos = usuario.getVehiculos();
		if (vehiculos.isEmpty()) {
			sinVehiculos = new JLabel(bundle.getString("sinVehiculos"));
//		    sinVehiculos.setFont(new Font("Arial", Font.PLAIN, 18));
			panelCentral.add(sinVehiculos);
		} else {
			JPanel panelVehiculo = new JPanel();
			/*
			 * panelVehiculo.setLayout(new BoxLayout(panelVehiculo, BoxLayout.X_AXIS));
			 * 
			 * JLabel labelVehiculoInfo = new JLabel("- " + vehiculo.getModelo() + " (" +
			 * vehiculo.getMarca() + ", " + vehiculo.getMatricula() + ")");
			 * labelVehiculoInfo.setFont(new Font("Arial", Font.PLAIN, 18));
			 * panelVehiculo.add(labelVehiculoInfo);
			 */
			ModeloVehiculos modelo = new ModeloVehiculos(vehiculos, currentLocale);
			JTable tablaVehiculos = new JTable(modelo);
			JScrollPane scroll = new JScrollPane(tablaVehiculos);
			panelVehiculo.add(scroll);

			panelCentral.add(panelVehiculo);

			tablaVehiculos.getTableHeader().setDefaultRenderer(new TableCellRenderer() {

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					JLabel l = new JLabel(value.toString());
					l.setOpaque(true);
					if (column == 0) {
						l.setHorizontalAlignment(JLabel.CENTER);
						l.setBackground(Color.RED);
					} else if (column == 1) {
						l.setHorizontalAlignment(JLabel.CENTER);
						l.setBackground(Color.LIGHT_GRAY);
					} else if (column == 2) {
						l.setHorizontalAlignment(JLabel.CENTER);
						l.setBackground(Color.GREEN);
					} else if (column == 3) {
						l.setHorizontalAlignment(JLabel.CENTER);
						l.setBackground(Color.YELLOW);

					}
					return l;
				}
			});
			tablaVehiculos.setDefaultRenderer(Object.class, new TableCellRenderer() {

				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					JLabel l = new JLabel(value.toString());
					l.setOpaque(true);
					if (row % 2 == 0) {
						l.setBackground(Color.CYAN);
					}

					return l;
				}
			});

		}

		// Agregar el panel central al área CENTER
		add(panelCentral, BorderLayout.CENTER);
	}
}

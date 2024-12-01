package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import javax.swing.*;
import domain.Usuario;
import domain.Vehiculo; // Importa la clase Vehiculo
import main.DeustoTaller;

public class PanelSesion extends JPanel {
	private static final long serialVersionUID = 1L;

	public PanelSesion(JFrame ventanaGrafica) {
		// Fuente grande para todo el texto
		Font fuenteGrande = new Font("Arial", Font.PLAIN, 18);

		// Configurar el diseño principal como BorderLayout
		setLayout(new BorderLayout());

		// Obtener información del usuario
		Usuario usuario = DeustoTaller.getSesion();

		// **Parte superior (NORTH): Saludo, última sesión y botón**
		JPanel panelSuperior = new JPanel();
		panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS)); // Apilado vertical
		JLabel saludo = new JLabel("¡Hola, " + usuario.getNombre() + "!");
		saludo.setFont(fuenteGrande);
		saludo.setAlignmentX(CENTER_ALIGNMENT);
		panelSuperior.add(saludo);

		String ultimaSesionTexto = usuario.gethUltimaSesion()
				.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
		JLabel ultimaSesion = new JLabel("Último inicio de sesión: " + ultimaSesionTexto);
		ultimaSesion.setFont(fuenteGrande);
		ultimaSesion.setAlignmentX(CENTER_ALIGNMENT);
		panelSuperior.add(ultimaSesion);

		JButton botonGestor = new JButton("Acceder al gestor de sesión");
		botonGestor.setFont(fuenteGrande);
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

		JLabel labelUsernameTitulo = new JLabel("Username: "); // Etiqueta del título
		labelUsernameTitulo.setFont(new Font("Arial", Font.BOLD, 18)); // Fuente en negrita

		JLabel labelUsernameValor = new JLabel(usuario.getUsername()); // Etiqueta del valor
		labelUsernameValor.setFont(new Font("Arial", Font.PLAIN, 18)); // Fuente normal

		panelUsername.add(labelUsernameTitulo);
		panelUsername.add(labelUsernameValor);
		panelCentral.add(panelUsername);

		// Panel para "Nombre"
		JPanel panelNombre = new JPanel();
		panelNombre.setLayout(new BoxLayout(panelNombre, BoxLayout.X_AXIS));

		JLabel labelNombreTitulo = new JLabel("Nombre: ");
		labelNombreTitulo.setFont(new Font("Arial", Font.BOLD, 18));

		JLabel labelNombreValor = new JLabel(usuario.getNombre());
		labelNombreValor.setFont(new Font("Arial", Font.PLAIN, 18));

		panelNombre.add(labelNombreTitulo);
		panelNombre.add(labelNombreValor);
		panelCentral.add(panelNombre);

		// Panel para "Apellido"
		JPanel panelApellido = new JPanel();
		panelApellido.setLayout(new BoxLayout(panelApellido, BoxLayout.X_AXIS));

		JLabel labelApellidoTitulo = new JLabel("Apellido: ");
		labelApellidoTitulo.setFont(new Font("Arial", Font.BOLD, 18));

		JLabel labelApellidoValor = new JLabel(usuario.getApellido());
		labelApellidoValor.setFont(new Font("Arial", Font.PLAIN, 18));

		panelApellido.add(labelApellidoTitulo);
		panelApellido.add(labelApellidoValor);
		panelCentral.add(panelApellido);

		// Panel para "Vehículos"
		JPanel panelVehiculosTitulo = new JPanel();
		panelVehiculosTitulo.setLayout(new BoxLayout(panelVehiculosTitulo, BoxLayout.X_AXIS));

		JLabel labelVehiculosTitulo = new JLabel("Vehículos: ");
		labelVehiculosTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		panelVehiculosTitulo.add(labelVehiculosTitulo);
		panelCentral.add(panelVehiculosTitulo);

		// Listar los vehículos
		List<Vehiculo> vehiculos = usuario.getVehiculos();
		if (vehiculos.isEmpty()) {
		    JLabel sinVehiculos = new JLabel("No hay vehículos registrados.");
		    sinVehiculos.setFont(new Font("Arial", Font.PLAIN, 18));
		    panelCentral.add(sinVehiculos);
		} else {
			JPanel panelVehiculo = new JPanel();
	        /*panelVehiculo.setLayout(new BoxLayout(panelVehiculo, BoxLayout.X_AXIS));

	        JLabel labelVehiculoInfo = new JLabel("- " + vehiculo.getModelo() + " (" + vehiculo.getMarca() + ", "
	                + vehiculo.getMatricula() + ")");
	        labelVehiculoInfo.setFont(new Font("Arial", Font.PLAIN, 18));
	        panelVehiculo.add(labelVehiculoInfo);
	        */
	        ModeloVehiculos modelo= new ModeloVehiculos(vehiculos);
	        JTable tablaVehiculos= new JTable(modelo);
	        JScrollPane scroll= new JScrollPane(tablaVehiculos);
	        panelVehiculo.add(scroll);
	        
	        panelCentral.add(panelVehiculo);
		    
		}

		// Agregar el panel central al área CENTER
		add(panelCentral, BorderLayout.CENTER);
	}
}

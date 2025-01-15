package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import db.ConsultasVentanaPregunta; // Import the database class

public class VentanaPregunta extends JFrame {
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private String usuario;
	private ConsultasVentanaPregunta consulta; // Instance of the database class

	public VentanaPregunta(String usuario) {
		setTitle("Enviar Pregunta");
		setSize(400, 300);
		setLocationRelativeTo(null);
		this.usuario = usuario;
		this.consulta = new ConsultasVentanaPregunta(); // Initialize the database class

		// Panel principal
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));

		// Panel 1: Tema
		JPanel panelTema = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTema = new JLabel("Tema de la pregunta:");
		String[] temas = { "Cuenta", "Servicios", "Otro" };
		JComboBox<String> comboTemas = new JComboBox<>(temas);
		panelTema.add(lblTema);
		panelTema.add(comboTemas);

		// Panel 2: Pregunta
		JPanel panelPregunta = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblPregunta = new JLabel("Escribe tu pregunta:");
		JTextArea taPregunta = new JTextArea(5, 25);
		taPregunta.setLineWrap(true);
		taPregunta.setWrapStyleWord(true);
		JScrollPane scrollPregunta = new JScrollPane(taPregunta);
		panelPregunta.add(lblPregunta);
		panelPregunta.add(scrollPregunta);

		// Panel 3: Botón Enviar
		JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JButton btnEnviar = new JButton("Enviar Pregunta");
		btnEnviar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tema = (String) comboTemas.getSelectedItem();
				String pregunta = taPregunta.getText().trim();
				if (!pregunta.isEmpty()) {
					consulta.enviarPregunta(tema, pregunta, usuario, VentanaPregunta.this);
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Por favor, escribe una pregunta.");
				}
			}
		});
		panelBoton.add(btnEnviar);

		// Añadir los paneles al panel principal
		panelPrincipal.add(panelTema);
		panelPrincipal.add(panelPregunta);
		panelPrincipal.add(panelBoton);

		// Añadir el panel principal al JFrame
		add(panelPrincipal);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

}
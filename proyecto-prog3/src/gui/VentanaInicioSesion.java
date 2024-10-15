package gui;

import java.awt.BorderLayout;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import main.DeustoTaller;

public class VentanaInicioSesion extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pCentro, pSur, pNorte, pEste, pOeste, ptextUsuario, pTextContrasenia;
	private JButton btnIniciarSesion, btnCerrarSesion, btnRegistrarse;
	private JLabel lblTitulo, lblUsuario, lblContrasenia;
	private JTextField textUsuario;
	private JPasswordField textContrasenia;

	public VentanaInicioSesion() {

		pCentro = new JPanel(new GridLayout(4, 1));
		pSur = new JPanel();
		pNorte = new JPanel();
		pEste = new JPanel();
		pOeste = new JPanel();
		ptextUsuario = new JPanel(new GridLayout(2, 1));
		pTextContrasenia = new JPanel(new GridLayout(2, 2));

		lblTitulo = new JLabel("DEUSTO TALLER");
		lblUsuario = new JLabel("Usuario:");
		lblContrasenia = new JLabel("Contraseña:");

		btnCerrarSesion = new JButton("Cerrar sesion");
		btnIniciarSesion = new JButton("Iniciar sesion");
		btnRegistrarse = new JButton("Registrarse");

		textUsuario = new JTextField();
		textContrasenia = new JPasswordField();

		pNorte.add(lblTitulo);

		Font fuente = new Font(getName(), Font.BOLD, 20);
		lblTitulo.setFont(fuente);

		Font fuentebtn = new Font(getName(), Font.BOLD, 16);
		lblUsuario.setFont(fuentebtn);
		lblContrasenia.setFont(fuentebtn);
		btnCerrarSesion.setFont(fuentebtn);
		btnCerrarSesion.setEnabled(false);
		btnIniciarSesion.setFont(fuentebtn);

		getContentPane().add(pCentro, BorderLayout.CENTER);
		getContentPane().add(pSur, BorderLayout.SOUTH);
		getContentPane().add(pNorte, BorderLayout.NORTH);
		getContentPane().add(pEste, BorderLayout.WEST);
		getContentPane().add(pOeste, BorderLayout.EAST);

		pSur.add(btnIniciarSesion);
		btnIniciarSesion.addActionListener((e) -> {
			String usuario = textUsuario.getText();
			String contrasenia = textContrasenia.getText();
			if (DeustoTaller.login(usuario, contrasenia)) {
				JOptionPane.showMessageDialog(null,
						String.format("%s, Has iniciado sesión correctamente", DeustoTaller.getSesion().getNombre()));
				new VentanaGrafica();
				setVisible(false);
				textUsuario.setEditable(false);
				textContrasenia.setEditable(false);
				btnIniciarSesion.setEnabled(false);
				btnCerrarSesion.setEnabled(true);
				setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				addWindowListener(new WindowAdapter() {

					@Override
					public void windowClosing(WindowEvent e) {
						setVisible(false);
					}

				});

			} else {
				JOptionPane.showMessageDialog(null, "Incorrecto", "ERROR", JOptionPane.WARNING_MESSAGE);
			}
		});

		pSur.add(btnCerrarSesion);
		btnCerrarSesion.addActionListener((e) -> {
			logout();

		});
		
		pSur.add(btnRegistrarse);
		btnRegistrarse.addActionListener((e) -> {
			new VentanaRegistrarse();

		});

		ptextUsuario.add(textUsuario);
		pTextContrasenia.add(textContrasenia);

		pCentro.add(lblUsuario);
		pCentro.add(ptextUsuario);
		pCentro.add(lblContrasenia);
		pCentro.add(pTextContrasenia);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setTitle("DeustoTaller - Gestor de Sesión");
		setIconImage(new ImageIcon(getClass().getResource("/res/credential-icon.png")).getImage());
		setVisible(true);
	}

	public void openGestor() {
		setVisible(true);

	}

	public static void logout() {
		int confirmacion = JOptionPane.showInternalConfirmDialog(null, "¿Seguro que quieres cerrar sesión?",
				"Cerrar Sesión", JOptionPane.YES_NO_OPTION);
		switch (confirmacion) {
		case JOptionPane.YES_OPTION:
			System.exit(0);
		default:
		}
	}
}

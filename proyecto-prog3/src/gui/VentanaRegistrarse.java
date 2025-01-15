package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import db.ConsultasRegistrarse;
import domain.Usuario;

public class VentanaRegistrarse extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pCentro, pSur, pNorte, pEste, pOeste, pTextNombre, pTextApellido, ptextDni, ptextUsuario,
			pTextContrasenia;
	private JButton btnRegistrar, btnCancelar;
	private JLabel lblTitulo, lblNombre, lblApellido, lblDni, lblUsuario, lblContrasenia;
	private JTextField textNombre, textApellido, textDni, textUsuario;
	private JPasswordField textContrasenia;
	private Locale currentLocale;
	private ResourceBundle bundle;

	public VentanaRegistrarse(Locale locale) {

		pCentro = new JPanel(new GridLayout(5, 1));
		pSur = new JPanel();
		pNorte = new JPanel();
		pEste = new JPanel();
		pOeste = new JPanel();
		pTextNombre = new JPanel(new GridLayout(2, 1));
		pTextApellido = new JPanel(new GridLayout(2, 1));
		ptextDni = new JPanel(new GridLayout(2, 1));
		ptextUsuario = new JPanel(new GridLayout(2, 1));
		pTextContrasenia = new JPanel(new GridLayout(2, 1));

		// Idioma
		currentLocale = locale;
		bundle = ResourceBundle.getBundle("VentanaRegistrarseBundle", currentLocale);

		// labels
		lblTitulo = new JLabel(bundle.getString("lblTitulo"));
		lblNombre = new JLabel(bundle.getString("lblNombre"));
		lblApellido = new JLabel(bundle.getString("lblApellido"));
		lblDni = new JLabel(bundle.getString("lblDni"));
		lblUsuario = new JLabel(bundle.getString("lblUsuario"));
		lblContrasenia = new JLabel(bundle.getString("lblContrasenia"));

		// Botones
		btnRegistrar = new JButton(bundle.getString("btnRegistrar"));
		btnCancelar = new JButton(bundle.getString("btnCancelar"));

		lblTitulo.setIcon(new ImageIcon("resources/images/app-icon.png"));
		lblTitulo.setFont(VentanaInicioSesion.fuenteTitulo);
		lblTitulo.setOpaque(true);
		lblTitulo.setBackground(new Color(136, 174, 208));
		lblTitulo.setBorder(BorderFactory.createRaisedBevelBorder());

		textNombre = new JTextField();
		textApellido = new JTextField();
		textDni = new JTextField();
		textUsuario = new JTextField();
		textContrasenia = new JPasswordField();

		pNorte.add(lblTitulo);

		Font fuente = new Font(getName(), Font.BOLD, 20);
		lblTitulo.setFont(fuente);

		Font fuentebtn = new Font(getName(), Font.BOLD, 16);
		btnCancelar.setFont(fuentebtn);
		btnRegistrar.setFont(fuentebtn);

		pSur.add(btnRegistrar);
		pSur.add(btnCancelar);

		getContentPane().add(pCentro, BorderLayout.CENTER);
		getContentPane().add(pSur, BorderLayout.SOUTH);
		getContentPane().add(pNorte, BorderLayout.NORTH);
		getContentPane().add(pEste, BorderLayout.WEST);
		getContentPane().add(pOeste, BorderLayout.EAST);

		pTextNombre.add(textNombre);
		pTextApellido.add(textApellido);
		ptextDni.add(textDni);
		ptextUsuario.add(textUsuario);
		pTextContrasenia.add(textContrasenia);

		pCentro.add(lblNombre);
		pCentro.add(pTextNombre);
		pCentro.add(lblApellido);
		pCentro.add(pTextApellido);
		pCentro.add(lblDni);
		pCentro.add(ptextDni);
		pCentro.add(lblUsuario);
		pCentro.add(ptextUsuario);
		pCentro.add(lblContrasenia);
		pCentro.add(pTextContrasenia);

		btnCancelar.addActionListener((e) -> {
			new VentanaInicioSesion(currentLocale);
			dispose();
		});

		btnRegistrar.addActionListener((e) -> {
			String nombre = textNombre.getText();
			String apellido = textApellido.getText();
			String dni = textDni.getText();
			String usuario = textUsuario.getText();
			String contrasenia = new String(textContrasenia.getPassword()); // Para obtener el texto del JPasswordField

			if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || usuario.isEmpty() || contrasenia.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.");
			} else {
				if (ConsultasRegistrarse.existeUsuario(usuario.toString()) == false) {
					Usuario user = new Usuario(usuario, nombre, apellido, LocalDateTime.now());
					ConsultasRegistrarse.guardarUsuario(user);
					ConsultasRegistrarse.guardarCredenciales(usuario, contrasenia);

				} else {
					JOptionPane.showMessageDialog(null, "Usuario ya registrado");

				}
			}
		});

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setTitle("DeustoTaller - Gestor de Sesión");
		setIconImage(new ImageIcon("resources/images/credential-icon.png").getImage());
		setVisible(true);
	}

}
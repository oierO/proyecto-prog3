package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import domain.Usuario;
import main.DeustoTaller;

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
	public VentanaRegistrarse() {
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

		lblTitulo = new JLabel("DEUSTO TALLER");
		lblTitulo.setIcon(new ImageIcon("resources/images/app-icon.png"));
		lblTitulo.setFont(VentanaInicioSesion.fuenteTitulo);
		lblTitulo.setOpaque(true);
		lblTitulo.setBackground(new Color(136, 174, 208));
		lblTitulo.setBorder(BorderFactory.createRaisedBevelBorder());
		lblNombre = new JLabel("Nombre:");
		lblApellido = new JLabel("Apellido:");
		lblDni = new JLabel("Dni:");
		lblUsuario = new JLabel("Usuario:");
		lblContrasenia = new JLabel("Contraseña:");

		btnRegistrar = new JButton("Registrar");
		btnCancelar = new JButton("Cancelar");

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
			new VentanaInicioSesion();
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
				if (this.existeUsuario(usuario.toString())==false) {
					Usuario user = new Usuario(usuario, nombre, apellido,LocalDateTime.now());
					guardarUsuario(user);
					guardarCredenciales(usuario, contrasenia);
					
				}else {
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

	public void guardarUsuario(Usuario usuario) {
		String sql = "INSERT INTO USUARIO (username, nombre, apellido, hUltimaSesion) VALUES (?,?,?,?)";
		try {
			PreparedStatement ps = DeustoTaller.getCon().prepareStatement(sql);
			ps.setString(1, usuario.getUsername());
			ps.setString(2, usuario.getNombre());
			ps.setString(3, usuario.getApellido());
			ps.setString(4,DeustoTaller.toTimeStamp(usuario.gethUltimaSesion()).toString());
			ps.execute();
			ps.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public boolean existeUsuario(String username) {
		boolean existe = false;
		String sql = "SELECT * FROM USUARIO WHERE username = ?";
		PreparedStatement ps = null;
		try {
			ps = DeustoTaller.getCon().prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				existe=true;
			}
			rs.close();
			ps.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existe;
	}
	
	public void guardarCredenciales(String username, String password) {
		String sql = "INSERT INTO CREDENCIALES (username, password) VALUES (?,?)";
		try {
			PreparedStatement ps = DeustoTaller.getCon().prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.execute();
			ps.close();
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
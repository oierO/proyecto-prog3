package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.BorderFactory;
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
		pTextContrasenia = new JPanel(new GridLayout(2, 1));

		lblTitulo = new JLabel("DeustoTaller");
		lblTitulo.setIcon(new ImageIcon("resources/images/app-icon.png"));
		lblUsuario = new JLabel("Usuario");
		Image img = new ImageIcon("resources/images/user.png").getImage();
		img = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		lblUsuario.setIcon(new ImageIcon(img));
		lblContrasenia = new JLabel("Contraseña");
		img = new ImageIcon("resources/images/pass.png").getImage();
		img = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		lblContrasenia.setIcon(new ImageIcon(img));

		btnCerrarSesion = new JButton("Cerrar sesion");
		btnIniciarSesion = new JButton("Iniciar sesion");
		btnRegistrarse = new JButton("Registrarse");

		pNorte.add(lblTitulo);

		Font fuente = new Font("Bahnschrift", Font.BOLD, 30);
		lblTitulo.setFont(fuente);
		lblTitulo.setOpaque(true);
		lblTitulo.setBackground(new Color(136, 174, 208));
		lblTitulo.setBorder(BorderFactory.createRaisedBevelBorder());

		Font fuentebtn = new Font("Bahnschrift", Font.BOLD, 16);
		lblUsuario.setFont(fuentebtn);
		lblContrasenia.setFont(fuentebtn);
		btnCerrarSesion.setFont(fuentebtn);
		btnCerrarSesion.setVisible(false);
		btnIniciarSesion.setFont(fuentebtn);
		btnRegistrarse.setFont(fuentebtn);

		getContentPane().add(pCentro, BorderLayout.CENTER);
		getContentPane().add(pSur, BorderLayout.SOUTH);
		getContentPane().add(pNorte, BorderLayout.NORTH);
		getContentPane().add(pEste, BorderLayout.WEST);
		getContentPane().add(pOeste, BorderLayout.EAST);

		pSur.add(btnIniciarSesion);
		btnIniciarSesion.addActionListener((e) -> {
		    String usuario = textUsuario.getText();
		    String contrasenia = String.valueOf(textContrasenia.getPassword());
		    if (DeustoTaller.login(usuario, contrasenia)) {
		        JOptionPane.showMessageDialog(null,
		                String.format("%s, Has iniciado sesión correctamente", DeustoTaller.getSesion().getNombre()));
		        
		        // Pasa el usuario a VentanaGrafica
		        new VentanaGrafica(usuario); 
		        setVisible(false);
		        textUsuario.setEditable(false);
		        textContrasenia.setEditable(false);
		        btnIniciarSesion.setVisible(false);
		        btnCerrarSesion.setVisible(true);
		        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		        addWindowListener(new WindowAdapter() {

		            @Override
		            public void windowClosing(WindowEvent e) {
		                setVisible(false);
		            }

		        });

		    } else {
		        Toolkit.getDefaultToolkit().beep();
		        JOptionPane.showMessageDialog(null,
		                "Los datos de inicio de sesión no son correctos\n Por favor, intentelo de nuevo.",
		                "Error al conectar con DeustoTaller", JOptionPane.WARNING_MESSAGE);
		    }
		});

		pSur.add(btnCerrarSesion);
		btnCerrarSesion.addActionListener((e) -> {
			logout();

		});

		pSur.add(btnRegistrarse);
		btnRegistrarse.addActionListener((e) -> {
			new VentanaRegistrarse();
			dispose();

		});

		textUsuario = new JTextField(20);
		textContrasenia = new JPasswordField();
		ptextUsuario.add(textUsuario);
		pTextContrasenia.add(textContrasenia);
		if (DeustoTaller.debug == true) {
			textContrasenia.setText("deustotaller");
			textUsuario.setText("deustotaller");
		}
		pCentro.add(lblUsuario);
		pCentro.add(ptextUsuario);
		pCentro.add(lblContrasenia);
		pCentro.add(pTextContrasenia);

		KeyListener eventoEnter = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (btnIniciarSesion.isVisible()) {
						btnIniciarSesion.doClick();
					} else {
						btnCerrarSesion.doClick();
					}
				}
				super.keyPressed(e);
			}

		};
		this.addKeyListener(eventoEnter);
		this.setFocusable(true);
		textUsuario.addKeyListener(eventoEnter);
		textContrasenia.addKeyListener(eventoEnter);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setTitle("DeustoTaller - Gestor de Sesión");
		setIconImage(new ImageIcon("resources/images/credential-icon.png").getImage());
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
			try {
				DeustoTaller.getCon().close();
			} catch (SQLException e) {
				System.out.println("Error al conectarse a la base de datos" + e.getErrorCode());
			}
			System.exit(0);
		default:
		}
	}
	
	
}

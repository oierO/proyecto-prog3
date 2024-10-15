package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaInicioSesion extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pCentro, pSur, pNorte , pEste, pOeste, ptextUsuario, pTextContrasenia;
	private JButton btnIniciarSesion , btnCerrarSesion;
	private JLabel lblTitulo, lblUsuario, lblContrasenia;
	private JTextField textUsuario;
	private JPasswordField textContrasenia;
	private JFrame vActual;

	public VentanaInicioSesion() {

		pCentro = new JPanel(new GridLayout(4,1));
		pSur = new JPanel();
		pNorte = new JPanel();
		pEste = new JPanel();
		pOeste = new JPanel();
		ptextUsuario = new JPanel(new GridLayout(2,1));
		pTextContrasenia = new JPanel(new GridLayout(2,2));
		vActual= this;//Lo que hacemos es decirle a la ventana cual es la actual y que haga desde ahi
		
		lblTitulo= new JLabel("DEUSTO TALLER");
		lblUsuario = new JLabel("Usuario:");
		lblContrasenia= new JLabel("Contraseña:");
		
		btnCerrarSesion = new JButton("Cerrar sesion");
		btnIniciarSesion = new JButton("Iniciar sesion");
		
		textUsuario = new JTextField();
		textContrasenia= new JPasswordField();
				
		pNorte.add(lblTitulo);
		
		Font fuente = new Font(getName(), Font.BOLD, 20);
		lblTitulo.setFont(fuente);
		
		Font fuentebtn = new Font(getName(), Font.BOLD, 16);
		lblUsuario.setFont(fuentebtn);
		lblContrasenia.setFont(fuentebtn);
		btnCerrarSesion.setFont(fuentebtn);
		btnIniciarSesion.setFont(fuentebtn);


		getContentPane().add(pCentro,BorderLayout.CENTER);
		getContentPane().add(pSur, BorderLayout.SOUTH);
		getContentPane().add(pNorte,BorderLayout.NORTH);
		getContentPane().add(pEste,BorderLayout.WEST);
		getContentPane().add(pOeste,BorderLayout.EAST);
		
		
		pSur.add(btnIniciarSesion);
		btnIniciarSesion.addActionListener((e)->{
			String usuario = textUsuario.getText();
			String contrasenia=textContrasenia.getText();
			if (usuario.equals("deustotaller") && contrasenia.equals("deustotaller")) {
				JOptionPane.showMessageDialog(null, "Has iniciado sesión correctamente");
				vActual.dispose();
				new VentanaGrafica();
				
			}else {
				JOptionPane.showMessageDialog(null, "Incorrecto","ERROR",JOptionPane.WARNING_MESSAGE);
			}
		});
		
		pSur.add(btnCerrarSesion);
		btnCerrarSesion.addActionListener((e)->{
			System.exit(0);
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
		setTitle("DeustoTaller");
		setVisible(true);
	}
	
}

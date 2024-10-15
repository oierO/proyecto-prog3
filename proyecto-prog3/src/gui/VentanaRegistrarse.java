package gui;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaRegistrarse extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pCentro, pSur, pNorte, pEste, pOeste, pTextNombre, pTextApellido, ptextUsuario, pTextContrasenia;
	private JButton btnRegistrar, btnCancelar;
	private JLabel lblTitulo, lblNombre, lblApellido, lblUsuario, lblContrasenia;
	private JTextField textNombre, textApellido, textUsuario;
	private JPasswordField textContrasenia;






	public VentanaRegistrarse() {

		pCentro = new JPanel(new GridLayout(4, 1));
		pSur = new JPanel();
		pNorte = new JPanel();
		pEste = new JPanel();
		pOeste = new JPanel();
		pTextNombre = new JPanel(new GridLayout(4, 1));
		pTextApellido = new JPanel(new GridLayout(4, 2));
		ptextUsuario = new JPanel(new GridLayout(4, 3));
		pTextContrasenia = new JPanel(new GridLayout(4, 4));
		
		lblTitulo = new JLabel("DEUSTO TALLER");
		lblNombre = new JLabel("Usuario:");
		lblApellido = new JLabel("Usuario:");
		lblUsuario = new JLabel("Usuario:");
		lblContrasenia = new JLabel("Contraseña:");
		
		btnRegistrar = new JButton("Registrar");
		btnCancelar = new JButton("Cancelar");
		
		textNombre = new JTextField();
		textApellido= new JTextField();
		textUsuario = new JTextField();
		textContrasenia = new JPasswordField();
		
		pNorte.add(lblTitulo);
		
		Font fuente = new Font(getName(), Font.BOLD, 20);
		lblTitulo.setFont(fuente);

	

		
	}
	
	

}

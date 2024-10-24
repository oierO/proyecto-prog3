package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaRegistrarse extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pCentro, pSur, pNorte, pEste, pOeste, pTextNombre, pTextApellido, ptextDni, ptextUsuario, pTextContrasenia;
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
		lblNombre = new JLabel("Nombre:");
		lblApellido = new JLabel("Apellido:");
		lblDni = new JLabel("Dni:");
		lblUsuario = new JLabel("Usuario:");
		lblContrasenia = new JLabel("Contraseña:");
		
		btnRegistrar = new JButton("Registrar");
		btnCancelar = new JButton("Cancelar");
		
		textNombre = new JTextField();
		textApellido= new JTextField();
		textDni = new JTextField();
		textUsuario = new JTextField();
		textContrasenia = new JPasswordField();
		
		String archivoCSV = "C:\\Users\\diaz.inigo\\git\\proyecto-prog3\\proyecto-prog3\\src\\usuarios.csv";
		
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
		
		btnCancelar.addActionListener((e)->{
			new VentanaInicioSesion();
			dispose();
		});
		
		btnRegistrar.addActionListener((e) -> {
		    String nombre = textNombre.getText();
		    String apellido = textApellido.getText();
		    String dni = textDni.getText();
		    String usuario = textUsuario.getText();
		    String contrasenia = new String(textContrasenia.getPassword()); // Para obtener el texto del JPasswordField

		    // Verificar que los campos no estén vacíos
		    if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || usuario.isEmpty() || contrasenia.isEmpty()) {
		        JOptionPane.showMessageDialog(null, "Por favor, completa todos los campos.");
		    } else {
		    	if (usuarioExiste(archivoCSV, usuario)) {
		    		// Falta que de un error si existe el usuario
		    	} else {
		    		try (FileWriter writer = new FileWriter(archivoCSV, true)) { // true para agregar, no sobrescribir
		    			writer.append(nombre);
		    			writer.append(",");
		    			writer.append(apellido);
		    			writer.append(",");
		    			writer.append(dni);
		    			writer.append(",");
		    			writer.append(usuario);
		            	writer.append(",");
		            	writer.append(contrasenia);
		            	writer.append("\n");

		           		JOptionPane.showMessageDialog(null, "Datos guardados correctamente.");

		          		textNombre.setText("");
		          		textApellido.setText("");
		          		textDni.setText("");
		          		textUsuario.setText("");
		          		textContrasenia.setText("");

		    		} catch (IOException ex) {
		    			ex.printStackTrace();
		    			JOptionPane.showMessageDialog(null, "Error al guardar los datos.");
		    		}
		    	}
		    }
		});
		


		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
		setTitle("DeustoTaller - Gestor de Sesión");
		setIconImage(new ImageIcon(getClass().getResource("/res/credential-icon.png")).getImage());
		setVisible(true);
	}
	private boolean usuarioExiste(String archivoCSV, String usuario) {
	    try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
	        String linea;
	        while ((linea = br.readLine()) != null) {
	            String[] datos = linea.split(",");
	            if (datos.length > 3 && datos[3].equals(usuario)) { 
	                return true;
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return false; // Usuario no encontrado
	}
}
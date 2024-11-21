package main;

import java.time.LocalDateTime;

import java.util.*;
import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import domain.Usuario;
import gui.VentanaInicioSesion;

public class DeustoTaller {
	private static Usuario usuarioSesion;
	private static Connection con;
	private static VentanaInicioSesion VSesion;
	@Deprecated
	private static HashMap<String, String> credenciales = new HashMap<String, String>();
	@Deprecated
	private static HashMap<String, Usuario> usuarios = new HashMap<String, Usuario>();// Almacen de usuarios y
																						// credenciales (Temporal)

	public static void main(String[] args) {
		conectarBD();
		SwingUtilities.invokeLater(() -> VSesion = new VentanaInicioSesion());
	}

	private static void conectarBD() {
		try {
			con = DriverManager.getConnection("jdbc:sqlite:resources/db/database.db");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(VSesion, "Error al cargar la base de datos:\n" + e, "Error de Inicialización",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	public static boolean login(String username, String password) {
		try {
			String sql = "SELECT password FROM CREDENCIALES WHERE username=?;";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, username);
			ResultSet passCred = st.executeQuery();
			if (passCred.getString("password").equals(password)) {
				setUsuarioSesion(Usuario.fromDB(username));
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(VSesion, e);
			return false;
		}

	}

	public static Connection getCon() {
		return con;
	}

	protected static Usuario getUsuarioSesion() {
		return usuarioSesion;
	}

	public static VentanaInicioSesion getVSesion() {
		return VSesion;
	}

	protected static void setUsuarioSesion(Usuario usuarioSesion) {
		DeustoTaller.usuarioSesion = usuarioSesion;
		usuarioSesion.sethUltimaSesion(LocalDateTime.now());

	}

	@Deprecated
	protected static HashMap<String, String> getCredenciales() {
		return credenciales;
	}

	@Deprecated
	protected static HashMap<String, Usuario> getUsuarios() {
		return usuarios;
	}

	public static Usuario getSesion() {
		return usuarioSesion;
	}
}

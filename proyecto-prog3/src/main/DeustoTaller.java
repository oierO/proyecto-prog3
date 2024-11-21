package main;

import java.time.LocalDateTime;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import domain.Usuario;
import gui.VentanaInicioSesion;

public class DeustoTaller {
	private static Usuario usuarioSesion;
	private static Connection con;
	private static String locDB;
	private static VentanaInicioSesion VSesion;
	public static Boolean debug;
	@Deprecated
	private static HashMap<String, String> credenciales = new HashMap<String, String>();
	@Deprecated
	private static HashMap<String, Usuario> usuarios = new HashMap<String, Usuario>();// Almacen de usuarios y
																						// credenciales (Temporal)

	public static void main(String[] args) {
		try {
			FileInputStream fPropiedades = new FileInputStream(new File("config/deustotaller.properties"));
			Properties propiedades = new Properties();
			propiedades.load(fPropiedades);
			debug = Boolean.parseBoolean(propiedades.getProperty("debug", "false"));
			locDB = "jdbc:sqlite:"+propiedades.getProperty("ubicacionBD", "resources/db/database.db");
			fPropiedades.close();
		} catch (IOException e) {
			System.out.println("Error al cargar el fichero de propiedades.");
		}
		conectarBD();
		SwingUtilities.invokeLater(() -> VSesion = new VentanaInicioSesion());
	}

	private static void conectarBD() {
		try {
			con = DriverManager.getConnection(locDB);
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
		} catch (NullPointerException e) {
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

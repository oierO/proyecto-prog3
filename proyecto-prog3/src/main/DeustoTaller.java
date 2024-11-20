package main;

import java.time.LocalDateTime;

import java.util.*;

import javax.swing.SwingUtilities;

import domain.Usuario;
import gui.VentanaInicioSesion;

public class DeustoTaller {
	private static Usuario usuarioSesion;
	private static VentanaInicioSesion VSesion;
	private static HashMap<String, String> credenciales = new HashMap<String, String>();
	private static HashMap<String, Usuario> usuarios = new HashMap<String, Usuario>();// Almacen de usuarios y
																						// credenciales (Temporal)

	public static void main(String[] args) {
		credenciales.put("deustotaller", "deustotaller"); // Carga de usuarios temporal
		usuarios.put("deustotaller", new Usuario("deustotaller", "Admin", "Taller"));
		credenciales.put("manolito24", "manolito24");
		usuarios.put("manolito24", (new Usuario("manolito24", "Manolo", "Lopez")));
		SwingUtilities.invokeLater(() -> VSesion = new VentanaInicioSesion());
	}

	public static boolean login(String username, String password) {
		try {
			if (getCredenciales().get(username).equals(password) == true) {
				setUsuarioSesion(usuarios.get(username));
				return true;
			} else {
				return false;
			}
		} catch (NullPointerException e) {
			return false;
		}

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

	protected static HashMap<String, String> getCredenciales() {
		return credenciales;
	}

	protected static HashMap<String, Usuario> getUsuarios() {
		return usuarios;
	}

	public static Usuario getSesion() {
		return usuarioSesion;
	}
}

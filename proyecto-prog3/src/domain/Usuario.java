package domain;

import java.time.LocalDateTime;
import main.DeustoTaller;
import java.util.*;
import java.sql.*;

public class Usuario {

	public LocalDateTime gethUltimaSesion() {
		return hUltimaSesion;
	}

	public void sethUltimaSesion(LocalDateTime hUltimaSesion) {
		this.hUltimaSesion = hUltimaSesion;
	}

	protected String username;
	protected String nombre;
	protected String apellido;
	protected LocalDateTime hUltimaSesion;
	protected ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();

	public static Usuario fromDB(String username) {
		try {
			Connection con = DeustoTaller.getCon();
			String sql = "SELECT * FROM USUARIO WHERE username=?;";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, username);
			ResultSet resultado = st.executeQuery();
			String nom = resultado.getString("nombre");
			String usernom = resultado.getString("username");
			String apellido = resultado.getString("apellido");
			LocalDateTime hora = resultado.getTimestamp("hultimasesion").toLocalDateTime();
			return new Usuario(nom, usernom, apellido, hora);

		} catch (SQLException e) {
			System.out.println("Error al obtener usuario" + e);
			throw new ClassCastException();
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String usuario) {
		if (!usuario.equals(null)) {
			this.username = usuario;
		} else {
			throw new NullPointerException("Usuario establecido es vacio!");
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String name) {
		if (!name.equals(null)) {
			this.nombre = name;
		} else {
			this.nombre = "N/A";
		}

	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String surname) {
		if (!surname.equals(null)) {
			this.apellido = surname;
		} else {
			this.apellido = "N/A";
		}
	}

	public Usuario(String usuario, String nombre, String apellido, LocalDateTime hlastsession) {
		this.setUsername(usuario);
		this.setNombre(nombre);
		this.setApellido(apellido);
		this.sethUltimaSesion(hlastsession);
	}

	public ArrayList<Vehiculo> getVehiculos() {
		return vehiculos;
	}
}

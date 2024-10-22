package domain;

import java.time.LocalDateTime;
import java.util.*;

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

	public Usuario(String usuario, String nombre, String apellido) {
		this.setUsername(usuario);
		this.setNombre(nombre);
		this.setApellido(apellido);
	}

	public ArrayList<Vehiculo> getVehiculos() {
		return vehiculos;
	}
}

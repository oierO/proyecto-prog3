package domain;

import java.time.LocalDate;
import java.util.*;

public class Usuario {

	public LocalDate getfUltimaSesion() {
		return fUltimaSesion;
	}

	public void setfUltimaSesion(LocalDate fUltimaSesion) {
		this.fUltimaSesion = fUltimaSesion;
	}

	protected String username;
	protected String nombre;
	protected String apellido;
	protected LocalDate fUltimaSesion;
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

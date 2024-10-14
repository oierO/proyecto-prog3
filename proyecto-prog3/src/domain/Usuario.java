package domain;

import java.util.*;

public class Usuario {

	protected String username;
	protected String nombre;
	protected String apellido;
	protected ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		while (!username.equals(null)) {
			this.username = username;
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		while (!nombre.equals(null)) {
			this.nombre = nombre;
		}

	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		while (!apellido.equals(null)) {
			this.apellido = apellido;
		}
	}

	public Usuario(String username, String nombre, String apellido) {
		this.setUsername(username);
		this.setNombre(nombre);
		this.setApellido(apellido);
	}

	public ArrayList<Vehiculo> getVehiculos() {
		return vehiculos;
	}
}

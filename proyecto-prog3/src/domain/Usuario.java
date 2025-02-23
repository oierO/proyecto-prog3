package domain;

import java.time.LocalDateTime;
import main.DeustoTaller;
import java.util.*;
import java.sql.*;

public class Usuario {
	
	//CONSULTA BD

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
	protected HashSet<String> matriculas = new HashSet<String>();

	public static Usuario fromDB(String username) {
		try {
			Connection con = DeustoTaller.getCon();
			String sql = "SELECT * FROM USUARIO WHERE username=?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, username);
			ResultSet resultado = st.executeQuery();
			String nom = resultado.getString("nombre");
			String usernom = resultado.getString("username");
			String apellido = resultado.getString("apellido");
			LocalDateTime hora = resultado.getTimestamp("hultimasesion").toLocalDateTime();
			resultado.close();
			sql = "SELECT * FROM VEHICULO WHERE propietario=?";
			st = con.prepareStatement(sql);
			st.setString(1, username);
			resultado = st.executeQuery();
			ArrayList<Vehiculo> vehiculos = new ArrayList<Vehiculo>();
			HashSet<String> matriculas = new HashSet<String>();
			try {
				vehiculos.add(Vehiculo.fromResultSet(resultado));
				matriculas.add(Vehiculo.fromResultSet(resultado).getMatricula());
				resultado.next();
				while (resultado.next()) {
					vehiculos.add(Vehiculo.fromResultSet(resultado));
					matriculas.add(Vehiculo.fromResultSet(resultado).getMatricula());
				}
			} catch (NullPointerException e) {
				// No hace nada
			}

			return new Usuario(nom, usernom, apellido, hora, vehiculos,matriculas);

		} catch (SQLException e) {
			System.out.println("Error al obtener usuario" + e);
			e.printStackTrace();
			throw new ClassCastException();
		}

	}

	public HashSet<String> getMatriculas() {
		return matriculas;
	}

	private void setMatriculas(HashSet<String> matriculas) {
		this.matriculas = matriculas;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String usuario) {
		if (usuario != null) {
			this.username = usuario;
		} else {
			throw new NullPointerException("Usuario establecido es vacio!");
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String name) {
		if (name != null) {
			this.nombre = name;
		} else {
			this.nombre = "N/A";
		}

	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String surname) {
		if (surname != null) {
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

	public Usuario(String username, String nombre, String apellido, LocalDateTime hUltimaSesion,
			ArrayList<Vehiculo> vehiculos) {
		this.setUsername(username);
		this.setNombre(nombre);
		this.setApellido(apellido);
		this.sethUltimaSesion(hUltimaSesion);
		this.vehiculos = vehiculos;
	}
	
	public Usuario(String username, String nombre, String apellido, LocalDateTime hUltimaSesion,
			ArrayList<Vehiculo> vehiculos,HashSet<String> licenses) {
		this.setUsername(username);
		this.setNombre(nombre);
		this.setApellido(apellido);
		this.sethUltimaSesion(hUltimaSesion);
		this.vehiculos = vehiculos;
		setMatriculas(licenses);
	}
	

	public ArrayList<Vehiculo> getVehiculos() {
		return vehiculos;
	}
}

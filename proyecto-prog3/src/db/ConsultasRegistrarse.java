package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Usuario;
import main.DeustoTaller;

public class ConsultasRegistrarse {
	public static void guardarUsuario(Usuario usuario) {
		String sql = "INSERT INTO USUARIO (username, nombre, apellido, hUltimaSesion) VALUES (?,?,?,?)";
		try {
			PreparedStatement ps = DeustoTaller.getCon().prepareStatement(sql);
			ps.setString(1, usuario.getUsername());
			ps.setString(2, usuario.getNombre());
			ps.setString(3, usuario.getApellido());
			ps.setString(4, DeustoTaller.toTimeStamp(usuario.gethUltimaSesion()).toString());
			ps.execute();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public static boolean existeUsuario(String username) {
		boolean existe = false;
		String sql = "SELECT * FROM USUARIO WHERE username = ?";
		PreparedStatement ps = null;
		try {
			ps = DeustoTaller.getCon().prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				existe = true;
			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existe;
	}

	public static void guardarCredenciales(String username, String password) {
		String sql = "INSERT INTO CREDENCIALES (username, password) VALUES (?,?)";
		try {
			PreparedStatement ps = DeustoTaller.getCon().prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.execute();
			ps.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}

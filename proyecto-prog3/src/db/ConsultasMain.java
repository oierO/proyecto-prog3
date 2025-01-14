package db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import domain.Usuario;
import main.DeustoTaller;

public class ConsultasMain {
	
	public static void conectarBD() {
		try {
			DeustoTaller.setCon(DriverManager.getConnection(DeustoTaller.getLocDB()));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DeustoTaller.getVSesion(), "Error al cargar la base de datos:\n" + e, "Error de Inicialización",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
	
	public static boolean login(String username, String password) {
		try {
			String sql = "SELECT password FROM CREDENCIALES WHERE username=?;";
			PreparedStatement st = DeustoTaller.getCon().prepareStatement(sql);
			st.setString(1, username);
			ResultSet passCred = st.executeQuery();
			if (passCred.getString("password").equals(password)) {
				DeustoTaller.setUsuarioSesion(Usuario.fromDB(username));
				passCred.close();
				return true;
			} else {
				passCred.close();
				return false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DeustoTaller.getVSesion(), e);
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
}

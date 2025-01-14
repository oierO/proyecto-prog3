package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import domain.PlazaParking;
import domain.Vehiculo;
import main.DeustoTaller;
import gui.PanelParking;
import gui.ReservaParking;


public class ConsultasParking {
	private static DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static void liberarPlaza() {
		try {
			String sql = "DELETE FROM RESERVA_PARKING WHERE planta=? AND identificador=? AND caducidad=?";
			PreparedStatement stmt = DeustoTaller.getCon().prepareStatement(sql);
			stmt.setString(1, PanelParking.getPlazaSel().getPlanta());
			stmt.setString(2, PanelParking.getPlazaSel().getIdentificador());
			stmt.setString(3, PanelParking.getPlazaSel().getFechaCaducidad().format(formateador));
			stmt.executeUpdate();
			stmt.close();
			JOptionPane.showMessageDialog(DeustoTaller.getVSesion(), PanelParking.getLocalized("sPlazaLiberada"));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DeustoTaller.getVSesion(), PanelParking.getLocalized("sErrorLiberar") + e.getLocalizedMessage());
		}
	}
	
	public static void reservarPlazaBD() {
		String sql = "INSERT INTO RESERVA_PARKING VALUES(?,?,?,?)";
		try {
			PreparedStatement stmt = DeustoTaller.getCon().prepareStatement(sql);
			stmt.setString(1, ReservaParking.getPlanta());
			stmt.setString(2, ReservaParking.getPlaza());
			Vehiculo coche = (Vehiculo) ReservaParking.getlistaVehiculos().getSelectedItem();
			stmt.setString(3, coche.getMatricula());
			DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			stmt.setString(4, ReservaParking.getSelectorFecha().getDateTimeStrict().format(formateador));
			stmt.execute();
			stmt.close();
			JOptionPane.showMessageDialog(DeustoTaller.getVSesion(), ReservaParking.getLocalized("sRerserBien"));
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DeustoTaller.getVSesion(), ReservaParking.getLocalized("sRerserBien") + e,
					ReservaParking.getLocalized("sError"), JOptionPane.ERROR_MESSAGE);
		}

	}
	
	public static ResultSet obtenerResultPlaza(String plaza, String planta) {
		try {
			String sql = String.format("SELECT * FROM RESERVAS_VIGENTES WHERE planta='%s' AND identificador='%s'",
					planta, plaza);
			Connection con = DeustoTaller.getCon();
			Statement stm = con.createStatement();
			ResultSet resultado = stm.executeQuery(sql);
			resultado.close();
			return resultado;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DeustoTaller.getVSesion().getContentPane(),
					"Ha ocurrido un error al acceder a la base de datos\n" + e);
			return null;
		}
	}
	
	public static PlazaParking plazafromBD(String plaza, String planta) {
		ResultSet resultado = obtenerResultPlaza(plaza, planta);
		try {
			String sql1 = "SELECT * FROM VEHICULO WHERE matricula=?";
			PreparedStatement stmt = DeustoTaller.getCon().prepareStatement(sql1);
			stmt.setString(1, resultado.getString("vehiculo"));
			ResultSet vResult = stmt.executeQuery();
			Vehiculo vehicle = Vehiculo.fromResultSet(vResult);
			PlazaParking pParking = new PlazaParking(planta, plaza, vehicle,
					resultado.getTimestamp("caducidad").toLocalDateTime());
			resultado.getStatement().close();
			stmt.close();
			return pParking;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DeustoTaller.getVSesion().getContentPane(),
					"Ha ocurrido un error al tratar la base de datos\n" + e);
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
}

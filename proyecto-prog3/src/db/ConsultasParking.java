package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

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
}

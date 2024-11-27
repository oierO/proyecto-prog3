package gui;

import java.awt.Color;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.HashMap;

import javax.swing.JOptionPane;

import domain.PlazaParking;
import domain.Vehiculo;
import main.DeustoTaller;

public class RendererParking {

	public static Color getColor(String plaza, String planta) {
		ResultSet resultado = obtenerResultPlaza(plaza, planta);
		try {
			if (resultado.getString("identificador") != null) {
				resultado.getStatement().close();
				return Color.RED;
			} else {
				return Color.GREEN;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DeustoTaller.getVSesion().getContentPane(),
					"Ha ocurrido un error al obtener el valor en la base de datos.\n" + e);
			return null;
		}

	}

	private static ResultSet obtenerResultPlaza(String plaza, String planta) {
		try {
			String sql = String.format("SELECT * FROM RESERVAS_VIGENTES WHERE planta='%s' AND identificador='%s'",
					planta, plaza);
			Connection con = DeustoTaller.getCon();
			Statement stm = con.createStatement();
			ResultSet resultado = stm.executeQuery(sql);
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
			Vehiculo vehicle;
			try {
				Date formateador = (new SimpleDateFormat("yyyy-MM-dd")).parse(vResult.getString("fmatricula"));
				vehicle = new Vehiculo(vResult.getString("matricula"), vResult.getString("marca"),
						vResult.getString("modelo"), vResult.getInt("ano"), LocalDate.ofEpochDay(formateador.toInstant().getEpochSecond()/86400));
			} catch (ParseException e) {
				JOptionPane.showMessageDialog(DeustoTaller.getVSesion().getContentPane(),
						"Ha ocurrido un error al tratar los datos\n" + e);
				vehicle = null;
			}
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

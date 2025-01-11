package gui;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import db.ConsultasParking;
import main.DeustoTaller;

public class RendererParking {

	public static Color getColor(String plaza, String planta) {
		ResultSet resultado = ConsultasParking.obtenerResultPlaza(plaza, planta);
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

}

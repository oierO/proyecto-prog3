package gui;

import java.awt.Color;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JOptionPane;

import main.DeustoTaller;

public class RendererParking {

	public static Color getColor(String plaza, String planta) {
		try {
			String sql = String.format("SELECT * FROM PLAZAS_DISPONIBLES WHERE planta='%s' AND identificador='%s';",planta,plaza);
			Connection con = DeustoTaller.getCon();
			Statement stm = con.createStatement();
			System.out.println(planta);
			if (stm.executeUpdate(sql)==1) {
				return Color.RED;
			} else {
				return Color.GREEN;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(DeustoTaller.getVSesion().getContentPane(), "Ha ocurrido un error al acceder a la base de datos\n"+e);
			return null;
		}
		
	}

}

package gui;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;

import javax.swing.DefaultButtonModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RendererParking {

	public static Color getColor(String plaza, Integer planta) {
		String floor = PanelParking.getPlantasparking().get(planta);
		PanelParking.getMapaParkings().put("Planta 1", new HashMap<>());
		switch (PanelParking.getMapaParkings().get(floor).get(plaza)) {
		case null:
			return Color.GREEN;
		default:
			return Color.RED;
		}

	}

}

package gui;

import java.awt.Color;

import java.util.HashMap;

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

package domain;

import java.time.LocalDateTime;

import gui.PanelParking;

public class PlazaParking {

	private String planta;
	private String identificador;
	private Vehiculo vehiculo;
	private LocalDateTime fechaCaducidad;

	public String getPlanta() {
		return planta;
	}

	public String getIdentificador() {
		return identificador;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public LocalDateTime getFechaCaducidad() {
		return fechaCaducidad;
	}

	protected void setPlanta(String floor) {
		if (PanelParking.getPlantasparking().contains(floor)) {
			this.planta = floor;
		}
	}

	protected void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	protected void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}

	protected void setFechaCaducidad(LocalDateTime fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public PlazaParking(String planta, String identificador, Vehiculo vehiculo, LocalDateTime fechaCaducidad) {
		super();
		setPlanta(planta);
		this.identificador = identificador;
		this.vehiculo = vehiculo;
		this.fechaCaducidad = fechaCaducidad;
	}

}

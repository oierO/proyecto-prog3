package domain;

import java.time.*;

public class Vehiculo {
	protected String matricula;
	protected String marca;

	protected void setMatricula(String matricula) {
		while (!matricula.equals(null)) {
			this.matricula = matricula;
		}

	}

	protected void setMarca(String marca) {
		while (!marca.equals(null)) {
			this.marca = marca;
		}

	}

	protected void setModelo(String modelo) {
		while (!modelo.equals(null)) {
			this.modelo = modelo;
		}

	}

	protected void setAnoModelo(LocalDate anoModelo) {
		this.anoModelo = anoModelo;
	}

	protected void setfMatricula(LocalDate fMatricula) {
		this.fMatricula = fMatricula;
	}

	protected String modelo;
	protected LocalDate anoModelo;
	protected LocalDate fMatricula;

	public String getMarca() {
		return marca;
	}

	public String getModelo() {
		return modelo;
	}

	public LocalDate getAnoModelo() {
		return anoModelo;
	}

	public LocalDate getfMatricula() {
		return fMatricula;
	}

	public String getMatricula() {
		return matricula;
	}

	public Vehiculo(String matricula, String marca, String modelo, LocalDate anoModelo, LocalDate fMatricula) {
		this.setMatricula(matricula);
		this.setMarca(marca);
		this.setModelo(modelo);
		this.setAnoModelo(anoModelo);
		this.setfMatricula(fMatricula);
	}

}

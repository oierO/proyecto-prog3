package domain;

import java.time.*;

public class Vehiculo {
	protected String matricula;
	protected String marca;
	protected String modelo;
	protected Integer anoModelo;
	protected LocalDate fMatricula;

	protected void setMatricula(String matricula) {
		if (matricula != null) {
			this.matricula = matricula;
		}

	}

	protected void setMarca(String marca) {
		if (marca != null) {
			this.marca = marca;
		}

	}

	protected void setModelo(String modelo) {
		if (modelo != null) {
			this.modelo = modelo;
		}

	}

	protected void setAnoModelo(Integer anoModelo) {
		this.anoModelo = anoModelo;
	}

	protected void setfMatricula(LocalDate fMatricula) {
		this.fMatricula = fMatricula;
	}

	public String getMarca() {
		return marca;
	}

	public String getModelo() {
		return modelo;
	}

	public Integer getAnoModelo() {
		return anoModelo;
	}

	public LocalDate getfMatricula() {
		return fMatricula;
	}

	public String getMatricula() {
		return matricula;
	}

	public Vehiculo(String matricula, String marca, String modelo, Integer anoModelo, LocalDate fMatricula) {
		this.setMatricula(matricula);
		this.setMarca(marca);
		this.setModelo(modelo);
		this.setAnoModelo(anoModelo);
		this.setfMatricula(fMatricula);
	}

}

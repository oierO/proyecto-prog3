package domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.Date;

import javax.swing.JOptionPane;

import main.DeustoTaller;

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

	public static Vehiculo fromResultSet(ResultSet resultado) {
		try {
			Date formateador = (new SimpleDateFormat("yyyy-MM-dd")).parse(resultado.getString("fmatricula"));
			Vehiculo vehicle = new Vehiculo(resultado.getString("matricula"), resultado.getString("marca"),
					resultado.getString("modelo"), resultado.getInt("ano"),
					LocalDate.ofEpochDay(formateador.toInstant().getEpochSecond() / 86400));
			return vehicle;
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(DeustoTaller.getVSesion().getContentPane(),
					"Ha ocurrido un error al tratar los datos\n" + e);
			return null;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(DeustoTaller.getVSesion().getContentPane(),
					"Ha ocurrido un error al tratar la base de datos\n" + e);
			return null;
		}
	}

	public Vehiculo(String matricula, String marca, String modelo, Integer anoModelo, LocalDate fMatricula) {
		this.setMatricula(matricula);
		this.setMarca(marca);
		this.setModelo(modelo);
		this.setAnoModelo(anoModelo);
		this.setfMatricula(fMatricula);
	}

	@Override
	public String toString() {
		return String.format("%s ~ %s %s", getMatricula(), getMarca(), getModelo());
	}

}

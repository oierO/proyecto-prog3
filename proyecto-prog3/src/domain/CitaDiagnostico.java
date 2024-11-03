package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CitaDiagnostico {
	
	String nombre;
	int telefono;
	LocalDate fechaDePedido;
	LocalDate fechaDeRealizacion;
	String diagnosticos;
	
	
	
	
	public CitaDiagnostico(String nombre,int telefono ,LocalDate fechaDePedido, LocalDate fechaDeRealizacion,
			String diagnosticos) {
		super();
		this.nombre = nombre;
		this.telefono = telefono;
		this.fechaDePedido = fechaDePedido;
		this.fechaDeRealizacion = fechaDeRealizacion;
		this.diagnosticos = diagnosticos;
	}
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public LocalDate getfechaDePedido() {
		return fechaDePedido;
	}
	public void setfechaDePedido(LocalDate fechaDePedido) {
		this.fechaDePedido = fechaDePedido;
	}
	public LocalDate fechaDeRealizacion() {
		return fechaDeRealizacion;
	}
	public void setfechaDeRealizacion(LocalDate fechaDeRealizacion) {
		this.fechaDeRealizacion = fechaDeRealizacion;
	}
	public String getDiagnosticos() {
		return diagnosticos;
	}
	public void setDiagnosticos(String diagnosticos) {
		this.diagnosticos = diagnosticos;
	}


	@Override
	public String toString() {
		return "Cita [Nombre=" + nombre + ", Fecha de Pedido =" + fechaDePedido + ", Fecha de realización="
				+ fechaDeRealizacion + ", diagnosticos=" + diagnosticos + "]";
	}
	
	

}

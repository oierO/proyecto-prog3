package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class CitaDiagnostico {
	
private	String nombre;
private	int telefono;
private LocalDate fechaDePedido;
private	LocalDate fechaDeRealizacion;
private	ArrayList<String> serviciosElegidos;
private String comentario;
 	
	
	
	
	public CitaDiagnostico(String nombre,int telefono ,LocalDate fechaDePedido, LocalDate fechaDeRealizacion,
			ArrayList<String> serviciosElegidos,String comentario) {
		super();
		this.nombre = nombre;
		this.telefono = telefono;
		this.fechaDePedido = fechaDePedido;
		this.fechaDeRealizacion = fechaDeRealizacion;
		this.serviciosElegidos = serviciosElegidos;
		this.comentario = comentario;
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


	public int getTelefono() {
		return telefono;
	}


	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}


	public ArrayList<String> getServiciosElegidos() {
		return serviciosElegidos;
	}


	public void setServiciosElegidos(ArrayList<String> serviciosElegidos) {
		this.serviciosElegidos = serviciosElegidos;
	}


	public String getComentario() {
		return comentario;
	}


	public void setComentario(String comentario) {
		this.comentario = comentario;
	}


	@Override
	public String toString() {
		return "CitaDiagnostico [nombre=" + nombre + ", telefono=" + telefono + ", fechaDePedido=" + fechaDePedido
				+ ", fechaDeRealizacion=" + fechaDeRealizacion + ", serviciosElegidos=" + serviciosElegidos
				+ ", comentario=" + comentario + "]";
	}


	

	
	



}

package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class PedidoServicios {

	private String nombre;
	private int telefono;
	private LocalDate fechaDePedido;
	private LocalDate fechaDeRealizacion;
	private ArrayList<String> serviciosElegidos;
	private String informacionAdicional;

	public PedidoServicios(String nombre, int telefono, LocalDate fechaDePedido, LocalDate fechaDeRealizacion,
			ArrayList<String> serviciosElegidos, String informacionAdicional) {
		super();
		this.nombre = nombre;
		this.telefono = telefono;
		this.fechaDePedido = fechaDePedido;
		this.fechaDeRealizacion = fechaDeRealizacion;
		this.serviciosElegidos = serviciosElegidos;
		this.informacionAdicional = informacionAdicional;
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

	public LocalDate getfechaDeRealizacion() {
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

	public String getinformacionAdicional() {
		return informacionAdicional;
	}

	public void setinformacionAdicional(String informacionAdicional) {
		this.informacionAdicional = informacionAdicional;
	}

	@Override
	public String toString() {
		return "[nombre=" + nombre + ", telefono=" + telefono + ", fechaDePedido=" + fechaDePedido
				+ ", fechaDeRealizacion=" + fechaDeRealizacion + ", serviciosElegidos=" + serviciosElegidos
				+ ", Información adicional=" + informacionAdicional + "]";
	}

}

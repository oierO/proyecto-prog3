package domain;

public class Pieza {
	private int id;
	private String codigo;
	private String nombrePieza;
	private String descripcion;
	private String fabricante;
	private float precio;
	private int cantidadAlmacen;

	public Pieza() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pieza(int id, String codigo, String nombrePieza, String descripcion, String fabricante, float precio,
			int cantidadAlmacen) {
		super();
		this.id = id;
		this.codigo = codigo;
		this.nombrePieza = nombrePieza;
		this.descripcion = descripcion;
		this.fabricante = fabricante;
		this.precio = precio;
		this.cantidadAlmacen = cantidadAlmacen;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombrePieza() {
		return nombrePieza;
	}

	public void setNombrePieza(String nombrePieza) {
		this.nombrePieza = nombrePieza;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getCantidadAlmacen() {
		return cantidadAlmacen;
	}

	public void setCantidadAlmacen(int cantidadAlmacen) {
		this.cantidadAlmacen = cantidadAlmacen;
	}

	@Override
	public String toString() {
		return "Pieza [id=" + id + ", codigo=" + codigo + ", nombrePieza=" + nombrePieza + ", descripcion="
				+ descripcion + ", fabricante=" + fabricante + ", precio=" + precio + ", cantidadAlmacen="
				+ cantidadAlmacen + "]";
	}

}

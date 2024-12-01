package domain;

public class ServicioDisponible {
	private String cod_ser;
	private String nom_ser;
	private String descripcion;
	
	public ServicioDisponible(String cod_ser, String nom_ser, String descripcion) {
		super();
		this.cod_ser = cod_ser;
		this.nom_ser = nom_ser;
		this.descripcion = descripcion;
	}

	public String getCod_ser() {
		return cod_ser;
	}

	public void setCod_ser(String cod_ser) {
		this.cod_ser = cod_ser;
	}

	public String getNom_ser() {
		return nom_ser;
	}

	public void setNom_ser(String nom_ser) {
		this.nom_ser = nom_ser;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	
	@Override
	public String toString() {
		return "[código de servicio= " + cod_ser + ", nombre del servicio= " + nom_ser + ", descripción= " + descripcion + "]";
	}
	
	
	

}

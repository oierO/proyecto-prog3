package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import domain.Pieza;
import gui.PanelAlmacen;
import main.DeustoTaller;

public class ConsultasAlmacen {

	public ConsultasAlmacen() {
	}

	public void crearTablaPiezas() {
		Connection conn = DeustoTaller.getCon();
		String sql = "CREATE TABLE IF NOT EXISTS PIEZA (" + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " codigo TEXT NOT NULL," + " nombrePieza TEXT NOT NULL," + " descripcion TEXT,"
				+ " fabricante TEXT NOT NULL," + " precio REAL NOT NULL," + " cantidadAlmacen INTEGER NOT NULL" + ");";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertarPiezas(List<Pieza> listaPiezas) {
		String sql = "INSERT INTO Pieza (codigo, nombrePieza, descripcion, fabricante, precio, cantidadAlmacen) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		Connection conn = DeustoTaller.getCon();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			for (Pieza pieza : listaPiezas) {
				ps.setString(1, pieza.getCodigo());
				ps.setString(2, pieza.getNombrePieza());
				ps.setString(3, pieza.getDescripcion());
				ps.setString(4, pieza.getFabricante());
				ps.setFloat(5, pieza.getPrecio());
				ps.setInt(6, pieza.getCantidadAlmacen());
				// try {
				ps.execute();
				// } catch (SQLException e) {
				// }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Pieza> cargarBD() {
		String sql = "SELECT * FROM Pieza";
		try {
			Statement st = DeustoTaller.getCon().createStatement();
			ResultSet resultado = st.executeQuery(sql);
			ArrayList<Pieza> lPiezas = new ArrayList<Pieza>();

			// Verificar si el ResultSet tiene al menos un registro
			if (resultado.next()) {
				// Añadir el primer registro
				lPiezas.add(new Pieza(resultado.getInt("id"), resultado.getString("codigo"),
						resultado.getString("nombrePieza"), resultado.getString("descripcion"),
						resultado.getString("fabricante"), resultado.getFloat("precio"),
						resultado.getInt("cantidadAlmacen")));

				// Continuar con el resto de los registros (si los hay)
				while (resultado.next()) {
					lPiezas.add(new Pieza(resultado.getInt("id"), resultado.getString("codigo"),
							resultado.getString("nombrePieza"), resultado.getString("descripcion"),
							resultado.getString("fabricante"), resultado.getFloat("precio"),
							resultado.getInt("cantidadAlmacen")));
				}
			}

			return lPiezas;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al obtener la base de datos" + e.getLocalizedMessage());
			return null;
		}
	}

	public void updateBD(Pieza pieza) {
		String sql = String.format("UPDATE Pieza SET cantidadAlmacen=cantidadAlmacen-%s WHERE codigo='%s'",
				pieza.getCantidadAlmacen(), pieza.getCodigo());
		try {
			Statement st = DeustoTaller.getCon().createStatement();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {
			PanelAlmacen p = new PanelAlmacen(null);
			JOptionPane.showMessageDialog(null, "Error al modificar el valor del almacen." + e.getLocalizedMessage());
		}
	}

	public static void cargarFabricantes(javax.swing.JComboBox<String> comboBox) {
		String sql = "SELECT DISTINCT fabricante FROM Pieza"; // Consulta SQL

		try {
			java.sql.Connection conn = DeustoTaller.getCon(); // Obtenemos conexión
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery(); // Ejecutamos la consulta

			while (rs.next()) { // Iteramos por los resultados
				String fabricante = rs.getString("fabricante"); // Obtenemos cada fabricante
				comboBox.addItem(fabricante); // Lo añadimos al JComboBox
			}

			System.out.println("Fabricantes cargados exitosamente.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cargarNombres(javax.swing.JComboBox<String> combobox) {
		String sql = "SELECT DISTINCT nombrePieza FROM Pieza"; // Consulta SQL

		try {
			java.sql.Connection conn = DeustoTaller.getCon();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String nombre = rs.getString("nombrePieza");
				combobox.addItem(nombre);
			}
			System.out.println("Nombres cargados exitosamente ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
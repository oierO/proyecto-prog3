package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellRenderer;

import domain.Pieza;
import main.DeustoTaller;

public class PanelAlmacen extends JPanel {
	private static final long serialVersionUID = 1L;
	private ModeloAlmacen modeloTabla;
	private JTable tabla;
	private static int cont = 0;
	private JTextField txtFiltro;
	private JComboBox<String> cbTipo, cbFabricante;
	private JLabel lbltxtFiltro, lblcbTipo, lblcbFabricante;

	public PanelAlmacen() {
		this.setLayout(new BorderLayout());
		JPanel pTabla = new JPanel();
		JPanel panelFiltro = new JPanel(new FlowLayout());

		lbltxtFiltro = new JLabel("Descripción: ");
		panelFiltro.add(lbltxtFiltro);
		txtFiltro = new JTextField(5);
		panelFiltro.add(txtFiltro);

		lblcbTipo = new JLabel("Tipo: ");
		panelFiltro.add(lblcbTipo);

		// Creando combobox
		cbTipo = new JComboBox<String>();

		cbFabricante = new JComboBox<String>();

//		for(String f: fabricantes) {
//			cbFabricante.addItem(f);
//		}

		panelFiltro.add(cbTipo);

		lblcbFabricante = new JLabel("Fabricante: ");
		panelFiltro.add(lblcbFabricante);

		panelFiltro.add(cbFabricante);
		JButton botonBorrarFiltrado = new JButton("Borrar filtrado");
		panelFiltro.add(botonBorrarFiltrado);
		modeloTabla = new ModeloAlmacen(null);
		tabla = new JTable(modeloTabla);
		JScrollPane scroll = new JScrollPane(tabla);
		pTabla.add(scroll);

		// Creando panel para que aparezca la informacion
		JPanel pInfor = new JPanel();
		cargarFabricantes(cbFabricante);
		cbFabricante.setSelectedIndex(-1);
		cargarNombres(cbTipo);
		cbTipo.setSelectedIndex(-1);
		;// MouseListener
		tabla.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int selec = tabla.getSelectedRow();
					if (selec != -1) {
						int id = (int) tabla.getValueAt(selec, 0);
						String codigo = tabla.getValueAt(selec, 1).toString();
						String nombrePieza = tabla.getValueAt(selec, 2).toString();
						String descripcion = tabla.getValueAt(selec, 3).toString();
						String fabricante = tabla.getValueAt(selec, 4).toString();
						float precio = (float) tabla.getValueAt(selec, 5);
						int cantidadAlmacen = (int) tabla.getValueAt(selec, 6);

						Pieza p = new Pieza(id, codigo, nombrePieza, descripcion, fabricante, precio, cantidadAlmacen);
						new EspecificacionesPieza(p);

					}

				}

			}

		});

		this.add(panelFiltro, BorderLayout.NORTH);
		this.add(pInfor, BorderLayout.SOUTH);
		this.add(pTabla, BorderLayout.CENTER);
		if (cont == 0) {
			creartablaPiezas();
			insertarPiezas(cargarTabla());
			cont++;
		} else {
			ModeloAlmacen modeloTabla = new ModeloAlmacen(cargarBD());
			tabla.setModel(modeloTabla);
		}
		tabla.getTableHeader().setReorderingAllowed(false);// Para que no se puedan mover las columnas
		tabla.setDefaultRenderer(Object.class, new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				// Component c= getTableCellRendererComponent(table, value, isSelected,
				// hasFocus, row, column);
				JLabel l = new JLabel(value.toString());
				l.setOpaque(true);
				if (row % 2 == 0) {
					l.setBackground(Color.LIGHT_GRAY);
				}
				if (column == 6) {
					if (Integer.parseInt(l.getText()) == 0) {
						l.setBackground(Color.RED);
					} else if (Integer.parseInt(l.getText()) < 100) {
						l.setBackground(Color.ORANGE);

					} else if (Integer.parseInt(l.getText()) < 200) {
						l.setBackground(Color.YELLOW);

					} else {
						l.setBackground(Color.GREEN);
					}

				}
				if (isSelected) {
					l.setBackground(new Color(184, 207, 229));
				}
				return l;
			}
		});
		tabla.getTableHeader().setDefaultRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel l = new JLabel(value.toString());
				l.setOpaque(true);
				if (column == 0) {
					l.setHorizontalAlignment(JLabel.CENTER);
					l.setBackground(Color.BLUE);
				} else if (column == 1) {
					l.setHorizontalAlignment(JLabel.CENTER);
					l.setBackground(Color.LIGHT_GRAY);
				} else if (column == 2) {
					l.setHorizontalAlignment(JLabel.CENTER);
					l.setBackground(Color.CYAN);
				} else if (column == 3) {
					l.setHorizontalAlignment(JLabel.CENTER);
					l.setBackground(Color.MAGENTA);
				} else if (column == 4) {
					l.setHorizontalAlignment(JLabel.CENTER);
					l.setBackground(Color.ORANGE);
				} else if (column == 5) {
					l.setHorizontalAlignment(JLabel.CENTER);
					l.setBackground(Color.PINK);
				} else {
					l.setHorizontalAlignment(JLabel.CENTER);
					l.setBackground(Color.WHITE);
				}

				return l;
			}
		});

		txtFiltro.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				filtrarPiezas();

			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				filtrarPiezas();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});
		cbFabricante.addActionListener((e) -> {
			String fabricanteSeleccion = (String) cbFabricante.getSelectedItem();
			String tipo = (String) cbTipo.getSelectedItem();
			ArrayList<Pieza> lp = new ArrayList<Pieza>();
			for (Pieza p : cargarBD()) {
				if (p.getFabricante().equals(fabricanteSeleccion) && p.getNombrePieza().equals(tipo)) {
					lp.add(p);
				}
			}
			modeloTabla = new ModeloAlmacen(lp);
			tabla.setModel(modeloTabla);

		});
		cbTipo.addActionListener((e) -> {
			String tipoSeleccion = (String) cbTipo.getSelectedItem();
			String fabricante = (String) cbFabricante.getSelectedItem();
			ArrayList<Pieza> lp = new ArrayList<Pieza>();
			for (Pieza p : cargarBD()) {
				if (p.getNombrePieza().equals(tipoSeleccion) && p.getFabricante().equals(fabricante)) {
					lp.add(p);
				}
			}
			modeloTabla = new ModeloAlmacen(lp);
			tabla.setModel(modeloTabla);

		});

		botonBorrarFiltrado.addActionListener((e) -> {
			cbTipo.setSelectedIndex(-1);
			cbFabricante.setSelectedIndex(-1);
			txtFiltro.setText("");
			modeloTabla = new ModeloAlmacen(cargarBD());
			tabla.setModel(modeloTabla);

		});
	}

	public JTable getTabla() {
		return tabla;
	}

	public ModeloAlmacen getModeloTabla() {
		return modeloTabla;
	}

	private void creartablaPiezas() {
		String sql = "CREATE TABLE IF NOT EXISTS PIEZA (" + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ " codigo TEXT NOT NULL," + " nombrePieza TEXT NOT NULL," + " descripcion TEXT,"
				+ " fabricante TEXT NOT NULL," + " precio REAL NOT NULL," + " cantidadAlmacen INTEGER NOT NULL" + ");";
		try (PreparedStatement ps = DeustoTaller.getCon().prepareStatement(sql)) {
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void insertarPiezas(List<Pieza> listaPiezas) {
		String sql = "INSERT INTO Pieza (codigo, nombrePieza, descripcion, fabricante, precio, cantidadAlmacen) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = DeustoTaller.getCon().prepareStatement(sql)) {
			for (Pieza pieza : listaPiezas) {
				ps.setString(1, pieza.getCodigo());
				ps.setString(2, pieza.getNombrePieza());
				ps.setString(3, pieza.getDescripcion());
				ps.setString(4, pieza.getFabricante());
				ps.setFloat(5, pieza.getPrecio());
				ps.setInt(6, pieza.getCantidadAlmacen());
				try {
					ps.executeUpdate();
				} catch (SQLException e) {
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private List<Pieza> cargarBD() {
		String sql = "SELECT * FROM Pieza";
		try {
			Statement st = DeustoTaller.getCon().createStatement();
			ResultSet resultado = st.executeQuery(sql);
			ArrayList<Pieza> lPiezas = new ArrayList<Pieza>();
			lPiezas.add(
					new Pieza(resultado.getInt("id"), resultado.getString("codigo"), resultado.getString("nombrePieza"),
							resultado.getString("descripcion"), resultado.getString("fabricante"),
							resultado.getFloat("precio"), resultado.getInt("cantidadAlmacen")));
			resultado.next();
			while (resultado.next()) {
				lPiezas.add(new Pieza(resultado.getInt("id"), resultado.getString("codigo"),
						resultado.getString("nombrePieza"), resultado.getString("descripcion"),
						resultado.getString("fabricante"), resultado.getFloat("precio"),
						resultado.getInt("cantidadAlmacen")));
			}
			return lPiezas;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al obtener la base de datos" + e.getLocalizedMessage());
			return null;
		}
	}

	public  List<Pieza> cargarTabla() {
		File f = new File("piezas_coche_almacen_1000.csv");
		List<Pieza> lp = new ArrayList<Pieza>();
		try {
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] datos = linea.split(";");
				try {
					int id = Integer.parseInt(datos[0]);
					String codigo = datos[1];
					String nombre = datos[2];
					String descripcion = datos[3];
					String fabricante = datos[4];
					float precio = Float.parseFloat(datos[5]);
					int cantidad = Integer.parseInt(datos[6]);
					Pieza p = new Pieza(id, codigo, nombre, descripcion, fabricante, precio, cantidad);
					lp.add(p);

					modeloTabla = new ModeloAlmacen(lp);
					tabla.setModel(modeloTabla);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lp;
	}

	private void filtrarPiezas() {
		// modeloTabla.setRowCount(0);

		/*
		 * cargarTabla().forEach(c -> { if
		 * (c.getNombrePieza().contains(txtFiltro.getText())) { modeloTabla.addRow(new
		 * Object[] { c.getId(), c.getCodigo(), c.getNombrePieza(), c.getDescripcion(),
		 * c.getFabricante(), c.getPrecio(), c.getCantidadAlmacen() }); } });
		 * tabla.setModel(modeloTabla);
		 */

		ArrayList<Pieza> lp = (ArrayList<Pieza>) cargarTabla();
		if (txtFiltro.getText().equals("")) {
			modeloTabla = new ModeloAlmacen(lp);

		} else {

			ArrayList<Pieza> lpFiltradas = new ArrayList<Pieza>();
			for (Pieza p : lp) {
				if ((p.getDescripcion().toLowerCase()).contains(txtFiltro.getText().toLowerCase())) {
					lpFiltradas.add(p);
				}
			}
			modeloTabla = new ModeloAlmacen(lpFiltradas);
		}
		tabla.setModel(modeloTabla);

	}

	private static void cargarFabricantes(JComboBox<String> comboBox) {
		String sql = "SELECT DISTINCT fabricante FROM Pieza"; // Consulta SQL

		try {
			Connection conn = DeustoTaller.getCon(); // Obtenemos conexión
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

	private static void cargarNombres(JComboBox<String> combobox) {
		String sql = "SELECT DISTINCT nombrePieza FROM Pieza"; // Consulta SQL

		try {
			Connection conn = DeustoTaller.getCon();
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

	public void updateBD(Pieza pieza) {
		String sql = String.format("UPDATE Pieza SET cantidadAlmacen=cantidadAlmacen-%s WHERE codigo='%s'",
				pieza.getCantidadAlmacen(), pieza.getCodigo());
		try {
			Statement st = DeustoTaller.getCon().createStatement();
			st.executeUpdate(sql);
			st.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al modificar el valor del almacen." + e.getLocalizedMessage());
		}
	}

	public void refrescar() {
		cbTipo.setSelectedIndex(-1);
		cbFabricante.setSelectedIndex(-1);
		txtFiltro.setText("");
		modeloTabla = new ModeloAlmacen(cargarBD());
		tabla.setModel(modeloTabla);
	}

}

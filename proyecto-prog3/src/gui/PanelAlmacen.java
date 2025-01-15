package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellRenderer;

import db.ConsultasAlmacen;
import domain.Pieza;

public class PanelAlmacen extends JPanel {
	private static final long serialVersionUID = 1L;
	private ModeloAlmacen modeloTabla;
	private JTable tabla;
	private static int cont = 0;
	private JTextField txtFiltro;
	private JComboBox<String> cbTipo, cbFabricante;
	private JLabel lbltxtFiltro, lblcbTipo, lblcbFabricante;
	private Locale currentLocale;
	private ResourceBundle bundle;
	private JButton botonBorrarFiltrado;
    private ConsultasAlmacen consultas;

	
	public PanelAlmacen(Locale locale) {
        this.consultas = new ConsultasAlmacen();
		this.setLayout(new BorderLayout());
		JPanel pTabla = new JPanel();
		JPanel panelFiltro = new JPanel(new FlowLayout());
		
		//Idioma
		currentLocale = locale;
		bundle = ResourceBundle.getBundle("PanelAlmacenBundle",currentLocale);

		lbltxtFiltro = new JLabel(bundle.getString("lbltxtFiltro"));
		panelFiltro.add(lbltxtFiltro);
		txtFiltro = new JTextField(5);
		panelFiltro.add(txtFiltro);

		lblcbTipo = new JLabel(bundle.getString("lblcbTipo"));
		panelFiltro.add(lblcbTipo);

		// Creando combobox
		cbTipo = new JComboBox<String>();

		cbFabricante = new JComboBox<String>();


		panelFiltro.add(cbTipo);

		lblcbFabricante = new JLabel(bundle.getString("lblcbFabricante"));
		panelFiltro.add(lblcbFabricante);

		panelFiltro.add(cbFabricante);
		botonBorrarFiltrado = new JButton(bundle.getString("botonBorrarFiltrado"));
		panelFiltro.add(botonBorrarFiltrado);
		modeloTabla = new ModeloAlmacen(null, locale);
		tabla = new JTable(modeloTabla);
		JScrollPane scroll = new JScrollPane(tabla);
		pTabla.add(scroll);

		// Creando panel para que aparezca la informacion
		JPanel pInfor = new JPanel();
		ConsultasAlmacen.cargarFabricantes(cbFabricante);
		cbFabricante.setSelectedIndex(-1);
		ConsultasAlmacen.cargarNombres(cbTipo);
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
						new EspecificacionesPieza(p,currentLocale);

					}

				}

			}

		});

		this.add(panelFiltro, BorderLayout.NORTH);
		this.add(pInfor, BorderLayout.SOUTH);
		this.add(pTabla, BorderLayout.CENTER);
		if (cont == 0) {
			consultas.crearTablaPiezas();
			consultas.insertarPiezas(cargarTabla());
			cont++;
		} else {
			ModeloAlmacen modeloTabla = new ModeloAlmacen(consultas.cargarBD(),currentLocale);
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
			String tipo = null;
			if(cbTipo.getSelectedIndex()!=-1)
				tipo = (String) cbTipo.getSelectedItem();
			ArrayList<Pieza> lp = new ArrayList<Pieza>();
			for (Pieza p : consultas.cargarBD()) {
				if (p.getFabricante().equals(fabricanteSeleccion)) {
					if(tipo==null) {
						lp.add(p);
					}else  if (p.getNombrePieza().equals(tipo)){
						lp.add(p);
					}
				}
			}
			modeloTabla = new ModeloAlmacen(lp,currentLocale);
			tabla.setModel(modeloTabla);

		});
		cbTipo.addActionListener((e) -> {
			String tipoSeleccion = (String) cbTipo.getSelectedItem();
			String fabricante = null;
			if(cbFabricante.getSelectedIndex() != -1) {
				fabricante = (String) cbFabricante.getSelectedItem();
			}
			ArrayList<Pieza> lp = new ArrayList<Pieza>();
			for (Pieza p : consultas.cargarBD()) {
				if (p.getNombrePieza().equals(tipoSeleccion) ) {
					if(fabricante == null) {
						lp.add(p);
					}else if(p.getFabricante().equals(fabricante)) {
						lp.add(p);
					}
				}
			}
			modeloTabla = new ModeloAlmacen(lp,currentLocale);
			tabla.setModel(modeloTabla);

		});

		botonBorrarFiltrado.addActionListener((e) -> {
			cbTipo.setSelectedIndex(-1);
			cbFabricante.setSelectedIndex(-1);
			txtFiltro.setText("");
			modeloTabla = new ModeloAlmacen(consultas.cargarBD(),currentLocale);
			tabla.setModel(modeloTabla);

		});
	}

	public JTable getTabla() {
		return tabla;
	}

	public ModeloAlmacen getModeloTabla() {
		return modeloTabla;
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

					modeloTabla = new ModeloAlmacen(lp,currentLocale);
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


		ArrayList<Pieza> lp = (ArrayList<Pieza>) cargarTabla();
		if (txtFiltro.getText().equals("")) {
			modeloTabla = new ModeloAlmacen(lp,currentLocale);

		} else {

			ArrayList<Pieza> lpFiltradas = new ArrayList<Pieza>();
			for (Pieza p : lp) {
				if ((p.getDescripcion().toLowerCase()).contains(txtFiltro.getText().toLowerCase())) {
					lpFiltradas.add(p);
				}
			}
			modeloTabla = new ModeloAlmacen(lpFiltradas,currentLocale);
		}
		tabla.setModel(modeloTabla);
		configurarRendererResaltado();

	}
	
	private void configurarRendererResaltado() {
	    String filtro = txtFiltro.getText().toLowerCase();

	    tabla.getColumnModel().getColumn(3).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
	        JLabel label = new JLabel();
	        label.setOpaque(true);

	        // Configurar colores de fondo/selección
	        if (isSelected) {
	            label.setBackground(table.getSelectionBackground());
	            label.setForeground(table.getSelectionForeground());
	        } else {
	            label.setBackground(table.getBackground());
	            label.setForeground(table.getForeground());
	        }

	        // Resaltar texto si hay coincidencias
	        if (value != null && !filtro.isEmpty()) {
	            String descripcion = value.toString();
	            int index = descripcion.toLowerCase().indexOf(filtro);
	            if (index != -1) {
	                // Crear HTML para resaltar coincidencias
	                StringBuilder sb = new StringBuilder("<html>");
	                for (int i = 0; i < descripcion.length(); i++) {
	                    if (i >= index && i < index + filtro.length()) {
	                        sb.append("<span style='color:red;'>").append(descripcion.charAt(i)).append("</span>");
	                    } else {
	                        sb.append(descripcion.charAt(i));
	                    }
	                }
	                sb.append("</html>");
	                label.setText(sb.toString());
	            } else {
	                label.setText(descripcion);
	            }
	        } else {
	            label.setText(value != null ? value.toString() : "");
	        }

	        return label;
	    });
	}


	public void updateBD(Pieza pieza) {
        consultas.updateBD(pieza);
	}

	public void refrescar() {
		cbTipo.setSelectedIndex(-1);
		cbFabricante.setSelectedIndex(-1);
		txtFiltro.setText("");
		modeloTabla = new ModeloAlmacen(consultas.cargarBD(),currentLocale);
		tabla.setModel(modeloTabla);
	}

}
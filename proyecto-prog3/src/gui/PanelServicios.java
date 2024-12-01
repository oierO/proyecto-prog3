package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import domain.PedidoServicios;
import domain.Pieza;
import domain.ServicioDisponible;
import main.DeustoTaller;

public class PanelServicios extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ModeloAlmacen modeloPiezasUsuario;
	protected JTable tablaPreguntas;
	private ArrayList<String> serviciosDisponiblesNombre;
	private ArrayList<ServicioDisponible> listaDeServiciosDisponibles;
	private ArrayList<PedidoServicios> listaPedidoServicios;
	private String idioma;

	public PanelServicios() {
		String[] lServicios = new String[] { "Taller", "Comprar Piezas" };
		this.setLayout(new BorderLayout());
		JPanel botones = new JPanel();
		botones.setLayout(new GridLayout(lServicios.length, 1));
		botones.setBorder(new TitledBorder("Operaciones"));
		this.add(botones, BorderLayout.WEST);

		// Para los servicios
		listaDeServiciosDisponibles = new ArrayList<ServicioDisponible>();
		cargarServicios(listaDeServiciosDisponibles);

		System.out.println("\n--Visualizando las listas de servicios disponibles--\n");
		System.out.println(listaDeServiciosDisponibles.size());

		// Lista de pedidos de servicios que ha hecho el cliente en durante esta sesion
		listaPedidoServicios = new ArrayList<PedidoServicios>();

		// Servicios disponibles
		serviciosDisponiblesNombre = new ArrayList<String>();

		for (ServicioDisponible servicioDisponible : listaDeServiciosDisponibles) {
			serviciosDisponiblesNombre.add(servicioDisponible.getNom_ser());
		}

		// Datos de prueba
//		serviciosDisponiblesNombre.add("Cambio de aceite y filtro");
//		serviciosDisponiblesNombre.add("Revisión y reparación del sistema de frenos");
//		serviciosDisponiblesNombre.add("Reparación de sistemas de suspensión y dirección");
//		serviciosDisponiblesNombre.add("Reparación y mantenimiento del sistema de escape");
//		serviciosDisponiblesNombre.add("Servicio de diagnóstico electrónico");
//		serviciosDisponiblesNombre.add("Cambio de neumáticos y alineación");
//		serviciosDisponiblesNombre.add("Reparación y recarga de sistemas de aire acondicionado");
//		serviciosDisponiblesNombre.add("Reparación de sistemas de transmisión");
//		serviciosDisponiblesNombre.add("Reemplazo y reparación de sistemas de iluminación");
//		serviciosDisponiblesNombre.add("Servicio de mantenimiento preventivo");

		JPanel panelDerechoServicios = new JPanel();
		panelDerechoServicios.setLayout(new BorderLayout());
		this.add(panelDerechoServicios);

		for (int i = 0; i < lServicios.length; i++) {
			JButton boton = new JButton(lServicios[i]);
			boton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String operacion = boton.getText();
					panelDerechoServicios.removeAll();

					if (operacion.equals("Taller")) {

						// Panel para servicios disponibles

						JPanel PanelServiciosDisponibles = new JPanel();
						JPanel panelCentro = new JPanel();

						PanelServiciosDisponibles.setLayout(new BorderLayout());
						Border panelDiagnosticoBorder = BorderFactory.createTitledBorder("¿Qué necesitas?");
						PanelServiciosDisponibles.setBorder(panelDiagnosticoBorder);
						PanelServiciosDisponibles.add(panelCentro, BorderLayout.CENTER);

						panelCentro.setLayout(new GridLayout(serviciosDisponiblesNombre.size(), 1));

						// Añadir los servicios disponibles al panel del centro
						for (int i = 0; i < serviciosDisponiblesNombre.size(); i++) {
							JCheckBox jcheckBox = new JCheckBox(serviciosDisponiblesNombre.get(i), false);
							panelCentro.add(jcheckBox);

						}

						// Eligo un idioma
						idioma = "Español";

						// Panel para los botones
						JPanel panelBotonesServicio = new JPanel();

						// Boton de reservar
						// Si el usuario pulsa este boton dependiendo de si ha elegido algún servicio o
						// no
						// aparece un formulario
						JButton botonReservar = new JButton("RESERVAR CITA");
						botonReservar.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								ArrayList<String> listaServiciosSeleccionados = new ArrayList<String>();
								for (Component componente : panelCentro.getComponents()) {
									if (componente instanceof JCheckBox) {
										JCheckBox jCheckBox = (JCheckBox) componente;
										if (jCheckBox.isSelected()) {
											listaServiciosSeleccionados.add(jCheckBox.getText());

										}
									}
								}

								if (!listaServiciosSeleccionados.isEmpty()) {
									System.out.println("\n---Esto es de panel de servicios---\n");
									System.out.println("El usuario " + VentanaGrafica.getUsuario()
											+ " ha seleccionado estos servicios: ");
									for (String diagnostico : listaServiciosSeleccionados) {
										System.out.println("- " + diagnostico);
									}
									new VentanaPedidoServicios(VentanaGrafica.getUsuario(), listaPedidoServicios,
											listaServiciosSeleccionados, idioma);
									// Refrescar el panel
									PanelServiciosDisponibles.revalidate();
									PanelServiciosDisponibles.repaint();
								} else {
									System.out.println("\n---Esto es de panel de servicios---\n");
									System.out.println("El usuario " + VentanaGrafica.getUsuario()
											+ " no ha seleccionado ningún servicio");
								}

							}
						});

						panelBotonesServicio.add(botonReservar);

						// Boton para visualizar pedidos del usuario
						JButton botonVisualizarPedidos = new JButton("Visualizar Pedidos");

						botonVisualizarPedidos.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								if (listaPedidoServicios.isEmpty()) {
									System.out.println("\n---Esto es de panel de servicios---\n");
									System.out.println(
											"El usuario " + VentanaGrafica.getUsuario() + " no tiene ningún pedido.");
								} else {
									System.out.println("\n---Esto es de panel de servicios---\n");
									System.out.println(
											"El usuario " + VentanaGrafica.getUsuario() + "ha hecho estos pedidos: ");
									for (PedidoServicios pedido : listaPedidoServicios) {
										System.out.println(pedido);
									}
								}

							}
						});

						panelBotonesServicio.add(botonVisualizarPedidos);

						panelDerechoServicios.add(PanelServiciosDisponibles, BorderLayout.CENTER);
						panelDerechoServicios.add(panelBotonesServicio, BorderLayout.SOUTH);

					} else if (operacion.equals("Comprar Piezas")) {
						JOptionPane.showMessageDialog(null, "Bienvenido a la ventana de compras de piezas",
								"Venta de peizas", JOptionPane.INFORMATION_MESSAGE);
						// Panel para piezas
						JPanel panelPiezas = new JPanel();
						panelPiezas.setLayout(new BorderLayout());
						JPanel pCentro = new JPanel(new GridLayout(2, 1));
						panelPiezas.add(pCentro, BorderLayout.CENTER);
						PanelAlmacen panel1 = new PanelAlmacen();

						ArrayList<Pieza> compra = new ArrayList<Pieza>();
						modeloPiezasUsuario = new ModeloAlmacen(compra);
						JTable tablaUsuario = new JTable(modeloPiezasUsuario);
						JScrollPane scrollUsuario = new JScrollPane(tablaUsuario);
						JTable tabla = ((PanelAlmacen) panel1).getTabla();
						pCentro.add(panel1);
						pCentro.add(scrollUsuario);

						JPanel panelBotones = new JPanel();
						JButton botonComprar = new JButton("Comprar Piezas");
						JButton botonQuitarProducto = new JButton("Quitar pieza");
						JButton botonFinalizar = new JButton("Finalizar Compra");

						panelBotones.add(botonComprar);
						panelBotones.add(botonQuitarProducto);
						panelBotones.add(botonFinalizar);

						botonComprar.addActionListener(c -> {
							int fila = tabla.getSelectedRow();
							if (fila == -1) {
								JOptionPane.showMessageDialog(null, "Primero debes seleccionado una fila",
										"ERROR EN SELECCIÓN", JOptionPane.ERROR_MESSAGE);

							} else {
								String cantidad = JOptionPane.showInputDialog(null, "¿Cuantas piezas desea comprar?",
										"Cantidad piezas", JOptionPane.QUESTION_MESSAGE);
								int id = (int) tabla.getValueAt(fila, 0);
								String codigo = (String) tabla.getValueAt(fila, 1);
								String nombrePieza = (String) tabla.getValueAt(fila, 2);
								String descripcion = (String) tabla.getValueAt(fila, 3);
								String fabricante = (String) tabla.getValueAt(fila, 4);
								float precio = (float) tabla.getValueAt(fila, 5);
								try {
									if (Integer.parseInt(cantidad) <= 0) {
										JOptionPane.showMessageDialog(DeustoTaller.getVSesion(),
												"No puedes realizar una compra de estas unidades.");
									} else if (Integer.parseInt(cantidad) > (Integer) tabla.getValueAt(fila, 6)) {
										JOptionPane.showMessageDialog(DeustoTaller.getVSesion(),
												"No hay suficientes unidades disponibles para realizar esta transaccion.");
									} else {
										compra.add(new Pieza(id, codigo, nombrePieza, descripcion, fabricante, precio,
												Integer.parseInt(cantidad)));
									}
								} catch (NumberFormatException e2) {
									JOptionPane.showMessageDialog(DeustoTaller.getVSesion(), "El dato introducido no es un valor valido.");
								}
								
								modeloPiezasUsuario = new ModeloAlmacen(compra);
								tablaUsuario.setModel(modeloPiezasUsuario);

							}

						});
						botonQuitarProducto.addActionListener(c -> {
							int fila = tablaUsuario.getSelectedRow();
							if (fila == -1) {
								JOptionPane.showMessageDialog(null, "Primero debes seleccionado una fila",
										"ERROR EN SELECCIÓN", JOptionPane.ERROR_MESSAGE);

							} else {
								compra.remove(fila);
								modeloPiezasUsuario = new ModeloAlmacen(compra);
								tablaUsuario.setModel(modeloPiezasUsuario);
								JOptionPane.showMessageDialog(null, "Producto eliminado de la compra", "COMPRA",
										JOptionPane.INFORMATION_MESSAGE);
							}

						});
						botonFinalizar.addActionListener(c -> {
							int i = 0;
							float precioTotal = 0;
							while (i < compra.size()) {
								Pieza p = compra.get(i);
								precioTotal = precioTotal + (p.getPrecio() * p.getCantidadAlmacen());
								panel1.updateBD(p);
								panel1.refrescar();
								compra.remove(i);

							}
							modeloPiezasUsuario = new ModeloAlmacen(compra);
							tablaUsuario.setModel(modeloPiezasUsuario);
							JOptionPane.showMessageDialog(null, precioTotal + "€", "El precio total es",
									JOptionPane.INFORMATION_MESSAGE);

						});

						panelPiezas.add(panelBotones, BorderLayout.SOUTH);

						panelDerechoServicios.add(panelPiezas);

					}

					// Refrescar el panel
					panelDerechoServicios.revalidate();
					panelDerechoServicios.repaint();
				}
			});
			botones.add(boton);
		}

	}

	private static void cargarServicios(ArrayList<ServicioDisponible> listaServiciosDisponibles) {
		String sql = "SELECT * FROM SERVICIOS_DISPONIBLES";

		try {
			Connection conn = DeustoTaller.getCon();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String cod_ser = rs.getString("COD_SER");
				String nomb_ser = rs.getString("NOM_SER");
				String descripcion = rs.getString("DESCRIPCION");
				listaServiciosDisponibles.add(new ServicioDisponible(cod_ser, nomb_ser, descripcion));

			}
			System.out.println("\n--Esto es de panel de servicios--\n");
			System.out.println("Servicios cargados exitosamente ");
		} catch (SQLException e) {
			System.out.println("\n--Esto es de panel de servicios--\n");
			System.out.println("Error en cargar datos");
			e.printStackTrace();
		}

	}

}

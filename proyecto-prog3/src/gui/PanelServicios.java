package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
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
	private JLabel lblPrecio;
	private float totalLblPrecio;
	public PanelServicios() {
		totalLblPrecio = 0;
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
						JButton botonComprar = new JButton("Comprar ");
						JButton botonQuitarProducto = new JButton("Quitar ");
						JButton botonFinalizar = new JButton("Finalizar");
						JButton botonRecursivad= new JButton("Piezas recursivas");
						lblPrecio= new JLabel();

						panelBotones.add(botonComprar);
						panelBotones.add(botonQuitarProducto);
						panelBotones.add(botonFinalizar);
						panelBotones.add(botonRecursivad);
						panelBotones.add(lblPrecio);

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
										try {
											float total = precio * Integer.parseInt(cantidad);
											totalLblPrecio = totalLblPrecio + total;
											lblPrecio.setText(String.format("El precio total es de: %.2f €",totalLblPrecio));	
										}catch(NumberFormatException ex) {
											JOptionPane.showMessageDialog(DeustoTaller.getVSesion(),
													"La cantidad introducida no tiene el formato adecuado.");
										}
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
								float precio= (float) tablaUsuario.getValueAt(fila, 5);
								int cantidad= (int) tablaUsuario.getValueAt(fila, 6);
								float total= precio*cantidad;
								System.out.println(total);
								totalLblPrecio=totalLblPrecio-total;
								lblPrecio.setText(String.format("Precio total: %.2f €",totalLblPrecio));
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
						
						
						botonRecursivad.addActionListener(c->{
						double presupuesto= Double.parseDouble(JOptionPane.showInputDialog(null, "Inserte el prupuesto", "Presupuesto", JOptionPane.QUESTION_MESSAGE));
						PanelAlmacen p1= panel1;
						JComboBox<String>fa= new JComboBox<String>();
						PanelAlmacen.cargarFabricantes(fa);
						JOptionPane.showConfirmDialog(null, fa, "Elija un fabricante", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
						String sefa= (String) fa.getSelectedItem();
						//List<Pieza>piezas= p1.cargarTabla();
						//System.out.println(piezas);
						combinaciones(compra, presupuesto,sefa);
						int posicion= Integer.parseInt(JOptionPane.showInputDialog(null, "Introduzca un numero", "Numero al azar para que aparezcan las piezas", JOptionPane.PLAIN_MESSAGE));
						List<Pieza>pi= combinaciones(p1.cargarTabla(), presupuesto, sefa).get(posicion);
						System.out.println(pi);
						modeloPiezasUsuario= new ModeloAlmacen(pi);
						tablaUsuario.setModel(modeloPiezasUsuario);
							
							
						});
							
						tablaUsuario.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
							
							@Override
							public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
									int row, int column) {
								JLabel l= new JLabel(value.toString());
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
						tablaUsuario.setDefaultRenderer(Object.class, new TableCellRenderer() {
							
							@Override
							public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
									int row, int column) {
								JLabel l= new JLabel(value.toString());
								l.setOpaque(true);
								if(row%2==0) {
									l.setBackground(Color.YELLOW);
								}if(column==6) {
									l.setBackground(Color.GREEN);
								}
								
								return l;
							}
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
	private static List<List<Pieza>>combinaciones(List<Pieza>compra,double importe,String fabricante){
		List<List<Pieza>>result= new ArrayList<List<Pieza>>();
		combinacionesR(compra, importe, 0, new ArrayList<Pieza>(), result,fabricante);
		
		
		return result;
	}
	
	private static void combinacionesR(List<Pieza>compra,double importe,double suma,List<Pieza>temp,List<List<Pieza>>result,String fabricante) {
		 if(suma>=importe&&temp.size()==5) {
			List<Pieza> copia= new ArrayList<Pieza>(temp);
			if(!result.contains(copia)) {
				result.add(new ArrayList<Pieza>(temp));
			}
		}else {
			for(int i=0;i<compra.size();i++) {
				suma=0;
				suma= suma+compra.get(i).getPrecio();
				if(compra.get(i).getFabricante().equals(fabricante)) {
					temp.add(compra.get(i));
					combinacionesR(compra, importe, suma, temp, result,fabricante);
					int pos= temp.size()-1;
					suma=suma-compra.get(pos).getPrecio();
					temp.remove(temp.size()-1);
					
				}
				
			}
		}
		
		
	}
}

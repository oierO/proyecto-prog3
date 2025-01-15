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
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
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
	private ArrayList<Pieza> compra;
	private JLabel lblPrecio;
	private float totalLblPrecio;
	private Locale currentLocale;
	private ResourceBundle bundle;
	private String[] lServicios;
	private String sTaller,sComprarPieazas,sOperaciones;
	private String sElUsuario,sHaSeleccionado,sNoHaSeleccionado,sNoPedido,sPedido;
	private JButton botonVisualizarPedidos;
	private JButton  botonReservar;
	private String sNecesitas;
	private String sBienvenidos;
	private String sVentaDePiezas;
	private JButton botonComprar,botonQuitarProducto,botonFinalizar,botonRecursivad;
	private String sPrimeroDebesFila,sErrorEnSeleccion,sCuantasComprar,sCantidadPiezas;
	private String sNoPuedesUnidades,sNoSuficiente,sPrecioTotal,sCantidadFormato,sDatoInvalido;
	private String sProductoEliminado,sCompra,sPrimeroDebesProducto,sError,sInsertPresupuesto,sPresupuesto;
	private String sSi,sNo,sCancelar,sOk;
	private String sEligaFabricante,sElNPiezas,sElijaNumero,sEleccion;
	private VentanaVisualiazarPedidos ventanaVisualiazarPedidos;
	
	public PanelServicios(String usuario,Locale locale) {
		//Idioma 
		currentLocale = locale;
		bundle = ResourceBundle.getBundle("panelServiciosBundle",currentLocale);
		sTaller = bundle.getString("sTaller");
		sComprarPieazas = bundle.getString("sComprarPieazas");
		sOperaciones = bundle.getString("sOperaciones");
		sElUsuario = bundle.getString("sElUsuario");
		sHaSeleccionado = bundle.getString("sHaSeleccionado");
		sNoHaSeleccionado = bundle.getString("sNoHaSeleccionado");
		sNecesitas = bundle.getString("sNecesitas");
		sPedido = bundle.getString("sPedido");
		sNoPedido = bundle.getString("sNoPedido");
		sBienvenidos = bundle.getString("sBienvenidos");
		sVentaDePiezas = bundle.getString("sVentaDePiezas");
		sPrimeroDebesFila = bundle.getString("sPrimeroDebesFila");
		sErrorEnSeleccion = bundle.getString("sErrorEnSeleccion");
		sCuantasComprar = bundle.getString("sCuantasComprar");
		sCantidadPiezas = bundle.getString("sCantidadPiezas");
		sNoPuedesUnidades = bundle.getString("sNoPuedesUnidades");
		sNoSuficiente = bundle.getString("sNoSuficiente");
		sPrecioTotal = bundle.getString("sPrecioTotal"); 
		sCantidadFormato = bundle.getString("sCantidadFormato"); 
		sDatoInvalido = bundle.getString("sDatoInvalido"); 
		sProductoEliminado = bundle.getString("sProductoEliminado"); 
		sCompra = bundle.getString("sCompra"); 
		sPrimeroDebesProducto = bundle.getString("sPrimeroDebesProducto"); 
		sError = bundle.getString("sError"); 
		sInsertPresupuesto = bundle.getString("sInsertPresupuesto");
		sPresupuesto =  bundle.getString("sPresupuesto");
		sEligaFabricante = bundle.getString("sEligaFabricante");
		sElNPiezas = bundle.getString("sElNPiezas");
		sSi = bundle.getString("sSi");
		sNo = bundle.getString("sNo");
		sCancelar = bundle.getString("sCancelar");
		sOk = bundle.getString("sOk");
		sElijaNumero = bundle.getString("sElijaNumero");
		sEleccion = bundle.getString("sEleccion");
		
		//cambiar el texto de los botones de JOptionPane
		 UIManager.put("OptionPane.yesButtonText", sSi);
	     UIManager.put("OptionPane.noButtonText", sNo);
	     UIManager.put("OptionPane.cancelButtonText", sCancelar);
	     UIManager.put("OptionPane.okButtonText", sOk);
	    
		
		
		
		totalLblPrecio = 0;
		lServicios = new String[] { sTaller, sComprarPieazas };
		this.setLayout(new BorderLayout());
		JPanel botones = new JPanel();
		botones.setLayout(new GridLayout(lServicios.length, 1));
		botones.setBorder(new TitledBorder(sOperaciones));
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

					if (operacion.equals(lServicios[0])) {

						// Panel para servicios disponibles

						JPanel PanelServiciosDisponibles = new JPanel();
						JPanel panelCentro = new JPanel();

						PanelServiciosDisponibles.setLayout(new BorderLayout());
						Border panelDiagnosticoBorder = BorderFactory.createTitledBorder(sNecesitas);
						PanelServiciosDisponibles.setBorder(panelDiagnosticoBorder);
						PanelServiciosDisponibles.add(panelCentro, BorderLayout.CENTER);

						panelCentro.setLayout(new GridLayout(serviciosDisponiblesNombre.size(), 1));

						// Añadir los servicios disponibles al panel del centro
						for (int i = 0; i < serviciosDisponiblesNombre.size(); i++) {
							JCheckBox jcheckBox = new JCheckBox(serviciosDisponiblesNombre.get(i), false);
							panelCentro.add(jcheckBox);

						}

						// Panel para los botones
						JPanel panelBotonesServicio = new JPanel();

						// Boton de reservar
						// Si el usuario pulsa este boton dependiendo de si ha elegido algún servicio o
						// no
						// aparece un formulario
						JButton botonReservar = new JButton(bundle.getString("botonReservar"));
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
//									System.out.println("El usuario " + VentanaGrafica.getUsuario()
//											+ " ha seleccionado estos servicios: ");
									System.out.printf("%s %s %s", sElUsuario, VentanaGrafica.getUsuario(),sHaSeleccionado);
									
									for (String diagnostico : listaServiciosSeleccionados) {
										System.out.println("- " + diagnostico);	 	
									}
									new VentanaPedidoServicios(VentanaGrafica.getUsuario(), listaPedidoServicios,
											listaServiciosSeleccionados, locale);
									// Refrescar el panel
									PanelServiciosDisponibles.revalidate();
									PanelServiciosDisponibles.repaint();
								} else {
									System.out.println("\n---Esto es de panel de servicios---\n");
//									System.out.println("El usuario " + VentanaGrafica.getUsuario()
//											+ " no ha seleccionado ningún servicio");
									System.out.printf("%s %s %s", sElUsuario, VentanaGrafica.getUsuario(),sNoHaSeleccionado);

								}

							}
						});

						panelBotonesServicio.add(botonReservar);

						// Boton para visualizar pedidos del usuario
						botonVisualizarPedidos = new JButton(bundle.getString("botonVisualizarPedidos"));

						botonVisualizarPedidos.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								if (listaPedidoServicios.isEmpty()) {
									System.out.println("\n---Esto es de panel de servicios---\n");
//									System.out.println(
//											"El usuario " + VentanaGrafica.getUsuario() + " no tiene ningún pedido.");
									System.out.printf("%s %s %s", sElUsuario, VentanaGrafica.getUsuario(),sNoPedido);

								} else {
									System.out.println("\n---Esto es de panel de servicios---\n");
//									System.out.println(
//											"El usuario " + VentanaGrafica.getUsuario() + "ha hecho estos pedidos: ");
									System.out.printf("%s %s %s", sElUsuario, VentanaGrafica.getUsuario(),sPedido);

									for (PedidoServicios pedido : listaPedidoServicios) {
										System.out.println(pedido);
									}
								}
								ventanaVisualiazarPedidos = new VentanaVisualiazarPedidos(usuario, listaPedidoServicios, currentLocale);
							}
						});

						panelBotonesServicio.add(botonVisualizarPedidos);

						panelDerechoServicios.add(PanelServiciosDisponibles, BorderLayout.CENTER);
						panelDerechoServicios.add(panelBotonesServicio, BorderLayout.SOUTH);

					} else if (operacion.equals(lServicios[1])) {
						JOptionPane.showMessageDialog(null, sBienvenidos,
								sVentaDePiezas, JOptionPane.INFORMATION_MESSAGE);
						// Panel para piezas
						JPanel panelPiezas = new JPanel();
						panelPiezas.setLayout(new BorderLayout());
						JPanel pCentro = new JPanel(new GridLayout(2, 1));
						panelPiezas.add(pCentro, BorderLayout.CENTER);
						PanelAlmacen panel1 = new PanelAlmacen(currentLocale);

						compra = new ArrayList<Pieza>();
						modeloPiezasUsuario = new ModeloAlmacen(compra,locale);
						JTable tablaUsuario = new JTable(modeloPiezasUsuario);
						JScrollPane scrollUsuario = new JScrollPane(tablaUsuario);
						JTable tabla = ((PanelAlmacen) panel1).getTabla();
						pCentro.add(panel1);
						pCentro.add(scrollUsuario);

						JPanel panelBotones = new JPanel();
						botonComprar = new JButton(bundle.getString("botonComprar"));
						botonQuitarProducto = new JButton(bundle.getString("botonQuitarProducto"));
						botonFinalizar = new JButton(bundle.getString("botonFinalizar"));
						botonRecursivad= new JButton(bundle.getString("botonRecursivad"));
						lblPrecio= new JLabel();

						panelBotones.add(botonComprar);
						panelBotones.add(botonQuitarProducto);
						panelBotones.add(botonFinalizar);
						panelBotones.add(botonRecursivad);
						panelBotones.add(lblPrecio);

						botonComprar.addActionListener(c -> {
							int fila = tabla.getSelectedRow();
							if (fila == -1) {
								JOptionPane.showMessageDialog(null, sPrimeroDebesFila,
										sErrorEnSeleccion, JOptionPane.ERROR_MESSAGE);

							} else {
								String cantidad = JOptionPane.showInputDialog(null, sCuantasComprar,
										sCantidadPiezas, JOptionPane.QUESTION_MESSAGE);
								int id = (int) tabla.getValueAt(fila, 0);
								String codigo = (String) tabla.getValueAt(fila, 1);
								String nombrePieza = (String) tabla.getValueAt(fila, 2);
								String descripcion = (String) tabla.getValueAt(fila, 3);
								String fabricante = (String) tabla.getValueAt(fila, 4);
								float precio = (float) tabla.getValueAt(fila, 5);
								try {
									if (Integer.parseInt(cantidad) <= 0) {
										JOptionPane.showMessageDialog(DeustoTaller.getVSesion(),
												sNoPuedesUnidades);
									} else if (Integer.parseInt(cantidad) > (Integer) tabla.getValueAt(fila, 6)) {
										JOptionPane.showMessageDialog(DeustoTaller.getVSesion(),
												sNoSuficiente);
									} else {
										compra.add(new Pieza(id, codigo, nombrePieza, descripcion, fabricante, precio,
												Integer.parseInt(cantidad)));
										try {
											float total = precio * Integer.parseInt(cantidad);
											totalLblPrecio = totalLblPrecio + total;
											lblPrecio.setText(String.format("%s %.2f €",sPrecioTotal,totalLblPrecio));	
										}catch(NumberFormatException ex) {
											JOptionPane.showMessageDialog(DeustoTaller.getVSesion(),
													sCantidadFormato);
										}
									}
								} catch (NumberFormatException e2) {
									JOptionPane.showMessageDialog(DeustoTaller.getVSesion(), "");
								}
								
								modeloPiezasUsuario = new ModeloAlmacen(compra,locale);
								tablaUsuario.setModel(modeloPiezasUsuario);

							}

						});
						botonQuitarProducto.addActionListener(c -> {
							int fila = tablaUsuario.getSelectedRow();
							if (fila == -1) {
								JOptionPane.showMessageDialog(null, sPrimeroDebesFila,
										sErrorEnSeleccion, JOptionPane.ERROR_MESSAGE);

							} else {
								float precio= (float) tablaUsuario.getValueAt(fila, 5);
								int cantidad= (int) tablaUsuario.getValueAt(fila, 6);
								float total= precio*cantidad;
								System.out.println(total);
								totalLblPrecio=totalLblPrecio-total;
								lblPrecio.setText(String.format("%s %.2f €",sPrecioTotal,totalLblPrecio));
								compra.remove(fila);
								modeloPiezasUsuario = new ModeloAlmacen(compra,locale);
								tablaUsuario.setModel(modeloPiezasUsuario);
								JOptionPane.showMessageDialog(null, sProductoEliminado, sCompra,
										JOptionPane.INFORMATION_MESSAGE);
								
								
							}

						});
						botonFinalizar.addActionListener(c -> {
							if(!compra.isEmpty()) {
								modeloPiezasUsuario = new ModeloAlmacen(compra,locale);
								tablaUsuario.setModel(modeloPiezasUsuario);
								System.out.println(lblPrecio.getText());
								JOptionPane.showMessageDialog(null, lblPrecio.getText() + "€", sPrecioTotal,
										JOptionPane.INFORMATION_MESSAGE);
								new Recibo_Compra(compra,currentLocale);
								
							}else {
								JOptionPane.showMessageDialog(null, sPrimeroDebesProducto, sError, JOptionPane.WARNING_MESSAGE);
							}
							
				
							
							

						});
						
						
						botonRecursivad.addActionListener(c->{
						double presupuesto= Double.parseDouble(JOptionPane.showInputDialog(null, sInsertPresupuesto, sPresupuesto, JOptionPane.QUESTION_MESSAGE));
						PanelAlmacen p1= panel1;
						JComboBox<String>fa= new JComboBox<String>();
						PanelAlmacen.cargarFabricantes(fa);
						JOptionPane.showConfirmDialog(null, fa, sEligaFabricante, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
						String sefa= (String) fa.getSelectedItem();
						List<Pieza>piezas= p1.cargarTabla();
						System.out.println(piezas);
						//int posicion= Integer.parseInt(JOptionPane.showInputDialog(null, "Introduzca un numero", "Numero al azar para que aparezcan las piezas", JOptionPane.PLAIN_MESSAGE));
						List<List<Pieza>>pi= combinaciones(piezas, presupuesto, sefa);
						System.out.println( sElNPiezas+pi.size());
						int pos=Integer.parseInt(JOptionPane.showInputDialog(null, sElijaNumero+ pi.size(), sEleccion,JOptionPane.PLAIN_MESSAGE ));
						System.out.println(pos);
						List<Pieza>compra= pi.get(pos);
						System.out.println(compra);
						modeloPiezasUsuario= new ModeloAlmacen(compra,locale);
						tablaUsuario.setModel(modeloPiezasUsuario);
						for(int i=0;i<compra.size();i++) {
							compra.get(i).setCantidadAlmacen(1);
							totalLblPrecio= totalLblPrecio+compra.get(i).getPrecio();
							
						}
						lblPrecio.setText(String.format("%s %.2f €",sPrecioTotal,totalLblPrecio));
							
							
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
	private static List<List<Pieza>>combinaciones(List<Pieza>piezas,double importe,String fabricante){
		List<List<Pieza>>result= new ArrayList<>();
		List<Pieza> l = new ArrayList<Pieza>();
		for(int i=0;i<piezas.size() && l.size()<4;i++) {
			Pieza p = piezas.get(i);
			if(p.getFabricante().equals(fabricante)) {
				l.add(p);
			}
		}
		
		
		combinacionesR2(l, importe, 0, new ArrayList<>(), result,fabricante);
		for(List<Pieza> lista: result) {
			System.out.print("[");
			for(Pieza p: lista) {
				System.out.print(p.getNombrePieza()+" ");
			}
			System.out.println("]");
		}
		return result;
	}
	
	
	private static void combinacionesR2(List<Pieza>piezasR,double importe,double suma,List<Pieza>temp,List<List<Pieza>>result,String fabricante) {
		if(temp.size()>4 || suma > importe) 
			return;
		else if(temp.size()==4 || suma>importe-100){
			Comparator<Pieza> c = new Comparator<Pieza>() {
				
				@Override
				public int compare(Pieza o1, Pieza o2) {
					return Integer.compare(o1.getId(), o2.getId());
				}
			};
			List<Pieza> copia = new ArrayList<Pieza>(temp);
			copia.sort(c);
			if(!result.contains(temp)) {
				result.add(new ArrayList<Pieza>(temp));
				
			}
		}else {
			for(int i=0;i<piezasR.size();i++) {
				if(!temp.contains(piezasR.get(i))) {
					temp.add(piezasR.get(i));
					combinacionesR2(piezasR, importe, suma+piezasR.get(i).getPrecio(), temp, result, fabricante);
					temp.remove(temp.size()-1);
				}
			}
		}
	}
	
	
	
}

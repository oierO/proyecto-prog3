package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import domain.PedidoServicios;
import domain.Pieza;
import main.DeustoTaller;


public class VentanaGrafica extends JFrame {

	private static final long serialVersionUID = 1L;
	private ModeloAlmacen modeloTabla,modeloPiezasUsuario;
	private JTable tabla;
	private JTextField txtFiltro;
	private JComboBox<String> cbTipo, cbFabricante;
	private JLabel lbltxtFiltro, lblcbTipo, lblcbFabricante;
	private String usuario;
	protected JTable tablaPreguntas;
	private ArrayList<String> serviciosDisponibles;
	private ArrayList<PedidoServicios> listaPedidoServicios;
	private String idioma;

	public VentanaGrafica(String usuario) {
		setSize(800, 500);
		setLocationRelativeTo(null);
		setTitle("DeustoTaller");
		JTabbedPane menuPestanas = new JTabbedPane();

		this.usuario = usuario;

		// Pestaña Servicios
		String[] lServicios = new String[] { "Taller", "Comprar Piezas"};
		JPanel pServicios = new JPanel();
		pServicios.setLayout(new BorderLayout());
		JPanel botones = new JPanel();
		botones.setLayout(new GridLayout(lServicios.length, 1));
		botones.setBorder(new TitledBorder("Operaciones"));
		pServicios.add(botones, BorderLayout.WEST);

		// Para los servicios

		//Lista de pedidos de servicios que ha hecho el cliente	en durante esta sesion					
		listaPedidoServicios = new ArrayList<PedidoServicios>();
				
		//Servicios disponibles 
		serviciosDisponibles = new ArrayList<String>();
		serviciosDisponibles.add("Cambio de aceite y filtro");
		serviciosDisponibles.add("Revisión y reparación del sistema de frenos");
		serviciosDisponibles.add("Reparación de sistemas de suspensión y dirección");
		serviciosDisponibles.add("Reparación y mantenimiento del sistema de escape");
		serviciosDisponibles.add("Servicio de diagnóstico electrónico");
		serviciosDisponibles.add("Cambio de neumáticos y alineación");
		serviciosDisponibles.add("Reparación y recarga de sistemas de aire acondicionado");
		serviciosDisponibles.add("Reparación de sistemas de transmisión");
		serviciosDisponibles.add("Reemplazo y reparación de sistemas de iluminación");
		serviciosDisponibles.add("Servicio de mantenimiento preventivo");
				
		JPanel panelDerechoServicios = new JPanel();
		panelDerechoServicios.setLayout(new BorderLayout());
		pServicios.add(panelDerechoServicios);

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
						
						
						
						panelCentro.setLayout(new GridLayout(serviciosDisponibles.size(),1));
						
						//Añadir los servicios disponibles al panel del centro
						for(int i=0;i<serviciosDisponibles.size();i++) {
							JCheckBox jcheckBox = new JCheckBox(serviciosDisponibles.get(i),false);
							panelCentro.add(jcheckBox);
							
						}
				
						//Eligo un idioma 
						idioma = "Español";
						
			
						//Panel para los botones 
						JPanel panelBotonesServicio = new JPanel();
					
						//Boton de reservar 
						//Si el usuario pulsa este boton dependiendo de si ha elegido algún servicio o no
						//aparece un formulario 
						JButton botonReservar = new JButton("RESERVAR CITA");
						botonReservar.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								ArrayList<String> listaServiciosSeleccionados = new ArrayList<String>();
								for(Component componente : panelCentro.getComponents()) {
									if(componente instanceof JCheckBox) {
										JCheckBox jCheckBox = (JCheckBox) componente;
										if(jCheckBox.isSelected()) {
											listaServiciosSeleccionados.add(jCheckBox.getText());
										
										}
									}									
								}
					
								if (!listaServiciosSeleccionados.isEmpty()) {
									System.out.println("\n---Esto es de panel de servicios---\n");
									System.out.println("El usuario " + usuario +" ha seleccionado estos servicios: ");
									for (String diagnostico : listaServiciosSeleccionados) {
										System.out.println("- "+diagnostico);
									}
									new VentanaPedidoServicios(listaPedidoServicios,listaServiciosSeleccionados,idioma);
									// Refrescar el panel
									PanelServiciosDisponibles.revalidate();
									PanelServiciosDisponibles.repaint();
								} else {
									System.out.println("\n---Esto es de panel de servicios---\n");
									System.out.println("El usuario " + usuario + " no ha seleccionado ningún servicio");
								}


							}
						});
						
						panelBotonesServicio.add(botonReservar);
						
						//Boton para visualizar pedidos del usuario
						JButton botonVisualizarPedidos = new JButton("Visualizar Pedidos"); 
						
						botonVisualizarPedidos.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								if(listaPedidoServicios.isEmpty()) {
									System.out.println("\n---Esto es de panel de servicios---\n");
									System.out.println("El usuario "+ usuario + " no tiene ningún pedido.");
								} else {
									System.out.println("\n---Esto es de panel de servicios---\n");
									System.out.println("El usuario " + usuario + "ha hecho estos pedidos: ");
									for(PedidoServicios pedido : listaPedidoServicios ) {
										System.out.println(pedido);										
									}
								}
								
							}
						});
						
						panelBotonesServicio.add(botonVisualizarPedidos);

						panelDerechoServicios.add(PanelServiciosDisponibles, BorderLayout.CENTER);
						panelDerechoServicios.add(panelBotonesServicio, BorderLayout.SOUTH);

					} else if (operacion.equals("Comprar Piezas")) {

						// Panel para piezas
						JPanel panelPiezas = new JPanel();
						panelPiezas.setLayout(new BorderLayout());
						JPanel pCentro= new JPanel(new GridLayout(2, 1));
						panelPiezas.add(pCentro,BorderLayout.CENTER);
						
						ArrayList<Pieza>compra= new ArrayList<Pieza>();
						modeloPiezasUsuario= new ModeloAlmacen(compra);
						JTable tablaUsuario= new JTable(modeloPiezasUsuario);
						JScrollPane scrollUsuario= new JScrollPane(tablaUsuario);
						JScrollPane scrollTotal= new JScrollPane(tabla);
						pCentro.add(scrollTotal);
						pCentro.add(scrollUsuario);
						
						JPanel panelBotones = new JPanel();
						JButton botonComprar = new JButton("Comprar Piezas");
						JButton botonQuitarProducto= new JButton("Quitar pieza");
						JButton botonFinalizar= new JButton("Finalizar Compra");
						
						panelBotones.add(botonComprar);
						panelBotones.add(botonQuitarProducto);
						panelBotones.add(botonFinalizar);
						

						botonComprar.addActionListener(c ->{
							int fila= tabla.getSelectedRow();
							if(fila==-1) {
								JOptionPane.showMessageDialog(null, "Primero debes seleccionado una fila", "ERROR EN SELECCIÓN", JOptionPane.ERROR_MESSAGE);
								
							}else {
								String cantidad= JOptionPane.showInputDialog(null, "¿Cuantas piezas desea comprar?", "Cantidad piezas", JOptionPane.QUESTION_MESSAGE);
								int id= (int) tabla.getValueAt(fila, 0);
								String codigo= (String) tabla.getValueAt(fila, 1);
								String nombrePieza= (String) tabla.getValueAt(fila, 2);
								String descripcion= (String) tabla.getValueAt(fila, 3);
								String fabricante= (String) tabla.getValueAt(fila, 4);
								float precio= (float) tabla.getValueAt(fila, 5);
								
								
								compra.add(new Pieza(id, codigo, nombrePieza, descripcion, fabricante, precio, Integer.parseInt(cantidad)));
								
								modeloPiezasUsuario= new ModeloAlmacen(compra);
								tablaUsuario.setModel(modeloPiezasUsuario);
										
								
							}
							
						});
						botonQuitarProducto.addActionListener(c->{
							int fila= tablaUsuario.getSelectedRow();
							if(fila==-1) {
								JOptionPane.showMessageDialog(null, "Primero debes seleccionado una fila", "ERROR EN SELECCIÓN", JOptionPane.ERROR_MESSAGE);
								
							}else {
								compra.remove(fila);
								modeloPiezasUsuario= new ModeloAlmacen(compra);
								tablaUsuario.setModel(modeloPiezasUsuario);
								JOptionPane.showMessageDialog(null, "Producto eliminado de la compra", "COMPRA", JOptionPane.INFORMATION_MESSAGE);
							}
							
							
							
						});
						botonFinalizar.addActionListener(c->{
							int i=0;
							float precioTotal=0;
							while(i<compra.size()) {
								Pieza p= compra.get(i);
								precioTotal= precioTotal+(p.getPrecio()*p.getCantidadAlmacen());
								compra.remove(i);
								
							}
							modeloPiezasUsuario= new ModeloAlmacen(compra);
							tablaUsuario.setModel(modeloPiezasUsuario);
							JOptionPane.showMessageDialog(null, precioTotal+"€", "El precio total es", JOptionPane.INFORMATION_MESSAGE);
							
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

		JPanel pAlmacen = new JPanel(new BorderLayout());
		JPanel pTabla = new JPanel();
		JPanel panelFiltro = new JPanel(new FlowLayout());

		lbltxtFiltro = new JLabel("Filtro por título: ");
		panelFiltro.add(lbltxtFiltro);
		txtFiltro = new JTextField(5);
				panelFiltro.add(txtFiltro);

		lblcbTipo = new JLabel("Filtro por tipo: ");
		panelFiltro.add(lblcbTipo);
		
		

		// Creando combobox
		cbTipo = new JComboBox<String>();
		
		cbFabricante = new JComboBox<String>();

//		for(String f: fabricantes) {
//			cbFabricante.addItem(f);
//		}
		
		panelFiltro.add(cbTipo);

		lblcbFabricante = new JLabel("Filtro por fabricante: ");
		panelFiltro.add(lblcbFabricante);

		panelFiltro.add(cbFabricante);
		JButton botonBorrarFiltrado= new JButton("Borrar filtrado");
		panelFiltro.add(botonBorrarFiltrado);
		modeloTabla = new ModeloAlmacen(null);
		tabla = new JTable(modeloTabla);
		JScrollPane scroll = new JScrollPane(tabla);
		pTabla.add(scroll);

		// Creando panel para que aparezca la informacion
		JPanel pInfor = new JPanel();
		cargarFabricantes(cbFabricante);
		cargarNombres(cbTipo);
		;// MouseListener
		tabla.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int selec= tabla.getSelectedRow();
				if(selec!=-1) {
					int id= (int) tabla.getValueAt(selec, 0);
					String codigo=tabla.getValueAt(selec, 1).toString();
					String nombrePieza=tabla.getValueAt(selec, 2).toString();
					String descripcion=tabla.getValueAt(selec, 3).toString();
					String fabricante=tabla.getValueAt(selec, 4).toString();
					float precio=(float) tabla.getValueAt(selec, 5);
					int cantidadAlmacen=(int) tabla.getValueAt(selec, 6);
					
					Pieza p= new Pieza(id, codigo, nombrePieza, descripcion, fabricante, precio, cantidadAlmacen);
					new EspecificacionesPieza(p);
					
					
					
				}
			}
			
		});
		
		
		
	
		

		pAlmacen.add(panelFiltro, BorderLayout.NORTH);
		pAlmacen.add(pInfor, BorderLayout.SOUTH);
		pAlmacen.add(pTabla, BorderLayout.CENTER);
		cargarTabla();
		creartablaPiezas();
		insertarPiezas(cargarTabla());
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
					if (Integer.parseInt(l.getText()) < 100) {
						l.setBackground(Color.RED);

					} else if (Integer.parseInt(l.getText()) < 200) {
						l.setBackground(Color.YELLOW);

					} else {
						l.setBackground(Color.GREEN);
					}
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
		cbFabricante.addActionListener((e)->{
			String fabricanteSeleccion =(String) cbFabricante.getSelectedItem();
			ArrayList<Pieza>lp= new ArrayList<Pieza>();
			for(Pieza p: cargarTabla()) {
				if(p.getFabricante().equals(fabricanteSeleccion)) {
					lp.add(p);
				}
			}
			modeloTabla= new ModeloAlmacen(lp);
			tabla.setModel(modeloTabla);
			
			
		});
		cbTipo.addActionListener((e)->{
			String tipoSeleccion= (String) cbTipo.getSelectedItem();
			ArrayList<Pieza>lp= new ArrayList<Pieza>();
			for(Pieza p:cargarTabla()) {
				if(p.getNombrePieza().equals(tipoSeleccion)) {
					lp.add(p);
				}
			}
			modeloTabla= new ModeloAlmacen(lp);
			tabla.setModel(modeloTabla);
			
		});
		botonBorrarFiltrado.addActionListener((e)->{
			modeloTabla= new ModeloAlmacen(cargarTabla());
			tabla.setModel(modeloTabla);
			
		});

		// Pestaña Parking
		JPanel pParking = new PanelParking();
		JPanel pUsuario = new PanelSesion(this); // Pasamos la referencia de la ventana gráfica

		// Pestaña Preferencias
		String[] lPreferencias = new String[] { "Notificaciones", "Historial", "Valoraciones", "Soporte" };
		JPanel pSettings = new JPanel();
		pSettings.setLayout(new BorderLayout());

		// Panel de botones de la izquierda
		JPanel botonesPrefer = new JPanel();
		botonesPrefer.setLayout(new GridLayout(lPreferencias.length, 1));
		botonesPrefer.setBorder(new TitledBorder("Operaciones"));
		pSettings.add(botonesPrefer, BorderLayout.WEST);

		// Panel derecho
		JPanel panelDerechoPreferencias = new JPanel(new BorderLayout());
		pSettings.add(panelDerechoPreferencias, BorderLayout.CENTER);

		for (String pref : lPreferencias) {
			JButton botonPref = new JButton(pref);
			botonPref.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String operacion = botonPref.getText();
					panelDerechoPreferencias.removeAll();
					if (operacion.equals("Valoraciones")) {
						// Pestañas de Valoracion
						JTabbedPane pestanaValoracion = new JTabbedPane();

						// Panel para la sección de Valoraciones
						JPanel panelValoraciones = new JPanel(new BorderLayout());

						// Panel de radio buttons de calificación
						JPanel panelCalificacion = new JPanel(new GridLayout(1, 5, 10, 10));
						ButtonGroup grupoCalificacion = new ButtonGroup();

						for (int i = 1; i <= 5; i++) {
							JRadioButton radioCalificacion = new JRadioButton(String.valueOf(i));
							grupoCalificacion.add(radioCalificacion);
							panelCalificacion.add(radioCalificacion);
						}
						panelValoraciones.add(panelCalificacion, BorderLayout.NORTH);

						// Panel de comentario adicional
						JPanel panelComentario = new JPanel(new BorderLayout());
						panelComentario.setBorder(new TitledBorder("Comentario Adicional"));
						JTextArea campoComentario = new JTextArea(5, 20);
						campoComentario.setLineWrap(true);
						campoComentario.setWrapStyleWord(true);
						JScrollPane scrollComentario = new JScrollPane(campoComentario);
						panelComentario.add(scrollComentario, BorderLayout.CENTER);
						panelValoraciones.add(panelComentario, BorderLayout.CENTER);

						// Botón de enviar
						JButton botonEnviar = new JButton("Enviar");
						JPanel panelEnviar = new JPanel();
						panelEnviar.add(botonEnviar);
						panelValoraciones.add(panelEnviar, BorderLayout.SOUTH);

						// ActionListener del botón enviar
						botonEnviar.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// Obtener la calificación seleccionada
								int valoracion = -1;
								for (Component comp : panelCalificacion.getComponents()) {
									if (comp instanceof JRadioButton) {
										JRadioButton radio = (JRadioButton) comp;
										if (radio.isSelected()) {
											valoracion = Integer.parseInt(radio.getText());
											break;
										}
									}
								}

								// Obtener el comentario adicional
								String comentario = campoComentario.getText().trim();
								// Validación: verificar que se haya seleccionado una calificación

								if (valoracion == -1) {
									JOptionPane.showMessageDialog(panelDerechoPreferencias,
											"Por favor, seleccione una calificación.");
									return;
								}
								// Insertar la valoración y el comentario en la base de datos

								try (Connection connection = DeustoTaller.getCon()) {
									String sql = "INSERT INTO VALORACIONES (valoracion, comentario, usuario) VALUES (?, ?, ?)";
									try (PreparedStatement stmt = connection.prepareStatement(sql)) {
										stmt.setInt(1, valoracion);
										stmt.setString(2, comentario);
										stmt.setString(3, usuario);
										stmt.executeUpdate();
										JOptionPane.showMessageDialog(panelDerechoPreferencias,
												"¡Valoración enviada con éxito!");
									}
								} catch (SQLException ex) {
									ex.printStackTrace();
									JOptionPane.showMessageDialog(panelDerechoPreferencias,
											"Error al enviar la valoración. Intente nuevamente.");
								}
							}
						});
						// Agregar panel de Valoraciones al panel derecho de preferencias
						pestanaValoracion.add("Valoracion", panelValoraciones);
						if (usuario.equals("deustotaller")) {
							pestanaValoracion.add("Estadisticas de Valoracion", crearPanelValoraciones());
						}

						panelDerechoPreferencias.add(pestanaValoracion);

					} else if (operacion.equals("Soporte")) {
						// Panel para la sección de Soporte
						JTabbedPane pestanaSoporte = new JTabbedPane();

						// Pestaña de Preguntas Frecuentes
						JPanel panelPF = new JPanel();
						panelPF.setLayout(new BoxLayout(panelPF, BoxLayout.Y_AXIS)); // Usamos BoxLayout para apilar las
																						// preguntas
						// Agregar preguntas frecuentes con respuestas (se pueden poblar desde la base
						// de datos)
						String[][] preguntasFrecuentes = { {
								"¿Cómo puedo solicitar una cita para una reparación o revisión?",
								"Puede solicitar una cita llamando a nuestro número de contacto o usando nuestro sistema de citas en línea a través de nuestra página web. También puede visitarnos directamente y programar una cita con uno de nuestros asesores." },
								{ "¿Cuáles son los horarios de atención del taller?",
										"Nuestro taller está abierto de lunes a viernes de 9:00 a 18:00. Los sábados ofrecemos atención de 9:00 a 13:00, y los domingos estamos cerrados." },
								{ "¿Puedo llevar mi coche a reparar sin cita previa?",
										"Sí, puede traer su coche sin cita previa. Sin embargo, le recomendamos hacer una cita para asegurarse de que haya disponibilidad y evitar tiempos de espera prolongados." },
								{ "¿Ofrecen servicio de recogida y entrega del vehículo?",
										"Sí, ofrecemos servicio de recogida y entrega a domicilio dentro de un área determinada. Contáctenos para más detalles sobre este servicio y verificar si está disponible en su ubicación." },
								{ "¿Qué debo hacer si no puedo recoger mi coche el mismo día que lo reparan?",
										"Si no puede recoger su coche el mismo día, por favor, notifíquenos con antelación. Podemos programar un horario alternativo para la entrega o incluso organizar un servicio de entrega a domicilio, sujeto a disponibilidad." },
								{ "¿Ofrecen garantía sobre las reparaciones realizadas?",
										"Sí, ofrecemos una garantía de 12 meses en todas las reparaciones y piezas que instalamos. Si tiene algún problema con la reparación dentro de ese periodo, puede traer su coche de vuelta y lo revisaremos sin costo adicional." },
								{ "¿Cómo puedo pagar por los servicios del taller?",
										"Aceptamos pagos en efectivo, tarjeta de débito o crédito, y transferencias bancarias. También puede pagar utilizando sistemas de pago móviles." },
								{ "¿Ofrecen servicios de mantenimiento preventivo?",
										"Sí, ofrecemos paquetes de mantenimiento preventivo, que incluyen revisión de los sistemas clave de su coche, cambios de aceite, filtros, frenos, y más." },
								{ "¿Puedo dejar mi coche en el taller durante todo el día?",
										"Sí, puede dejar su coche durante el día, y nosotros nos encargaremos de las reparaciones mientras usted está ocupado. Le proporcionaremos una estimación de tiempo antes de la reparación." },
								{ "¿Hay servicios disponibles para vehículos eléctricos?",
										"Sí, ofrecemos servicios de mantenimiento y reparación para vehículos eléctricos, incluyendo revisiones de batería, motor eléctrico, y otros sistemas específicos." },
								{ "¿Cuánto tiempo tardará la reparación de mi vehículo?",
										"El tiempo de reparación depende de la complejidad del problema. Podemos darle una estimación después de realizar un diagnóstico inicial." },
								{ "¿Realizan inspecciones antes de realizar una reparación?",
										"Sí, antes de cualquier reparación, realizamos una inspección completa para asegurarnos de que no haya otros problemas ocultos." },
								{ "¿Cómo puedo saber si mi coche necesita una revisión?",
										"Si notas ruidos extraños, luces de advertencia en el tablero o problemas con el rendimiento del motor, es recomendable hacer una revisión." },
								{ "¿Ofrecen servicios de pintura y reparación de carrocería?",
										"Sí, ofrecemos servicios completos de pintura y reparación de carrocería, desde rayaduras hasta daños más graves." },
								{ "¿Cómo puedo obtener un presupuesto para una reparación?",
										"Puede traer su coche al taller para un diagnóstico, o contactarnos por teléfono o email para recibir una cotización aproximada." },
								{ "¿Puedo esperar en el taller mientras reparan mi coche?",
										"Sí, tenemos una sala de espera donde puede quedarse mientras realizamos reparaciones rápidas o mantenimientos." },
								{ "¿Cuál es el costo promedio de un cambio de aceite?",
										"El costo depende del tipo de aceite y la marca de su coche. Le proporcionaremos un presupuesto personalizado tras la revisión." },
								{ "¿Qué tipos de vehículos reparan en su taller?",
										"Reparamos una amplia gama de vehículos, incluyendo coches, camiones ligeros, SUVs y vehículos eléctricos." },
								{ "¿Tienen piezas originales o de marca?",
										"Sí, ofrecemos piezas originales y de marca. También tenemos opciones de piezas alternativas de calidad si lo prefiere." },
								{ "¿Cómo puedo programar una cita para mantenimiento preventivo?",
										"Puede llamar a nuestro número de contacto o utilizar nuestra plataforma en línea para agendar una cita en el horario que más le convenga." } };
						// Recorrer las preguntas frecuentes y agregarlas al panel
						for (String[] preguntaRespuesta : preguntasFrecuentes) {
							// Crear panel para cada pregunta y respuesta
							JPanel panelPregunta = new JPanel();
							panelPregunta.setLayout(new BoxLayout(panelPregunta, BoxLayout.Y_AXIS));
							panelPregunta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añadir un poco
																										// de espacio
																										// para que no
																										// se vea
																										// apretado

							// Crear JLabel para la pregunta
							JLabel preguntaLabel = new JLabel(preguntaRespuesta[0]);
							preguntaLabel.setFont(new Font("Arial", Font.BOLD, 14));
							panelPregunta.add(preguntaLabel);

							// Crear JLabel para la respuesta
							JLabel respuestaLabel = new JLabel("<html>" + preguntaRespuesta[1] + "</html>");
							respuestaLabel.setFont(new Font("Arial", Font.PLAIN, 12));
							respuestaLabel.setForeground(Color.GRAY);
							panelPregunta.add(respuestaLabel);

							// Añadir el panel de pregunta-respuesta al panel principal
							panelPF.add(panelPregunta);
						}
						// Colocar el JScrollPane para permitir el desplazamiento de las preguntas
						JScrollPane scrollPane = new JScrollPane(panelPF);
						scrollPane.setPreferredSize(new Dimension(500, 300)); // Ajustar el tamaño del JScrollPane

						// Pestaña de Contacto
						JPanel panelContacto = new JPanel(new BorderLayout());
						JLabel lblEmail = new JLabel("Correo electrónico: contacto@taller.com");
						JLabel lblTelefono = new JLabel("Teléfono: 123-456-789");
						JButton btnContactarDirectamente = new JButton("Contactar Directamente");
						btnContactarDirectamente.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								// Abrir la ventana para enviar una pregunta
								new VentanaPregunta(usuario);
							}

						});

						panelContacto.add(lblEmail, BorderLayout.NORTH);
						panelContacto.add(lblTelefono, BorderLayout.CENTER);
						panelContacto.add(btnContactarDirectamente, BorderLayout.SOUTH);

						// Añadir este panel a la pestaña de soporte
						JTabbedPane tabbedPane = new JTabbedPane();
						tabbedPane.add("Contacto", panelContacto);
						pestanaSoporte.addTab("Preguntas Frecuentes", scrollPane);
						pestanaSoporte.addTab("Contacto", panelContacto);

						if (usuario.equals("deustotaller")) {
							pestanaSoporte.add("Administrar Preguntas", crearPanelAdministrarPreguntas());
						}

						// Agregar panel de Soporte al panel derecho de preferencias
						panelDerechoPreferencias.add(pestanaSoporte, BorderLayout.CENTER);
					}
					// Refrescar el panel derecho
					panelDerechoPreferencias.revalidate();
					panelDerechoPreferencias.repaint();
				}

				private JPanel crearPanelAdministrarPreguntas() {
					JPanel panelAdmin = new JPanel(new BorderLayout());
					// Obtener las preguntas desde la base de datos
					String[][] preguntas = getPreguntasUsuarios();
					// Si no hay preguntas, mostrar un mensaje
					if (preguntas == null || preguntas.length == 0) {
						panelAdmin.add(new JLabel("No hay preguntas pendientes."), BorderLayout.CENTER);
						return panelAdmin;
					}
					// Crear la tabla para mostrar las preguntas
					String[] columnNames = { "ID", "Pregunta", "Usuario" };
					DefaultTableModel model = new DefaultTableModel(preguntas, columnNames);

					tablaPreguntas = new JTable(model);
					tablaPreguntas.getTableHeader().setReorderingAllowed(false);
					JScrollPane scrollPane = new JScrollPane(tablaPreguntas);
					panelAdmin.add(scrollPane, BorderLayout.CENTER);
					// Área para escribir respuestas
					JTextArea areaRespuesta = new JTextArea(5, 30);
					areaRespuesta.setLineWrap(true);
					areaRespuesta.setWrapStyleWord(true);
					JScrollPane scrollRespuesta = new JScrollPane(areaRespuesta);
					panelAdmin.add(scrollRespuesta, BorderLayout.SOUTH);
					// Botón para enviar respuesta
					JButton btnEnviarRespuesta = new JButton("Responder");
					btnEnviarRespuesta.addActionListener(e -> {
						int filaSeleccionada = tablaPreguntas.getSelectedRow();
						if (filaSeleccionada != -1) {
							String idPregunta = (String) tablaPreguntas.getValueAt(filaSeleccionada, 0);
							String respuesta = areaRespuesta.getText();
							if (!respuesta.isEmpty()) {
								responderPregunta(idPregunta, respuesta); // Guardar respuesta en la base de datos
								JOptionPane.showMessageDialog(null, "Respuesta enviada correctamente.");
								areaRespuesta.setText(""); // Limpiar el área de respuesta
							} else {
								JOptionPane.showMessageDialog(null, "La respuesta no puede estar vacía.");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Debe seleccionar una pregunta.");
						}
					});
					panelAdmin.add(btnEnviarRespuesta, BorderLayout.NORTH);
					return panelAdmin;
				}

				// Método para guardar la respuesta en la base de datos
				private void responderPregunta(String idPregunta, String respuesta) {
					try (Connection con = DeustoTaller.getCon()) {
						String sql = "UPDATE PREGUNTAS SET respuesta = ? WHERE id = ?";
						PreparedStatement ps = con.prepareStatement(sql);
						ps.setString(1, respuesta);
						ps.setInt(2, Integer.parseInt(idPregunta));
						ps.executeUpdate();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			});
			botonesPrefer.add(botonPref);
		}

		menuPestanas.add("Servicios", pServicios);
		menuPestanas.add("Almacen", pAlmacen);
		menuPestanas.add("Parking", pParking);
		menuPestanas.add("Preferencias", pSettings);
		menuPestanas.add("Sesión", pUsuario);
		add(menuPestanas);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				VentanaInicioSesion.logout();
			}
		});
		setIconImage(new ImageIcon("resources/images/app-icon.png").getImage());
		setVisible(true);
	}

	// Metodo para obtener las valoraciones de los usuarios desde la base de datos
	public String[][] getValoracionesUsuarios() {
		ArrayList<String[]> valoracionesList = new ArrayList<>();

		try (Connection con = DeustoTaller.getCon()) {
			String sql = "SELECT valoracion, comentario, usuario FROM VALORACIONES";

			// Preparar el PreparedStatement para obtener un ResultSet con cursor de solo
			// avance
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			// Iterar sobre el ResultSet y almacenar las preguntas en la lista
			while (rs.next()) {
				String[] valoraciones = new String[3];
				valoraciones[0] = String.valueOf(rs.getInt("valoracion")); // Valoracion
				valoraciones[1] = rs.getString("comentario"); // Comentario
				valoraciones[2] = rs.getString("usuario"); // Usuario
				valoracionesList.add(valoraciones);
			}
			// Convertir la lista a un arreglo bidimensional
			String[][] valoraciones = new String[valoracionesList.size()][3];
			for (int i = 0; i < valoracionesList.size(); i++) {
				valoraciones[i] = valoracionesList.get(i);
			}

			return valoraciones;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private JPanel crearPanelValoraciones() {
		JPanel panelAdmin = new JPanel(new BorderLayout());
		// Obtener las valoraciones desde la base de datos
		String[][] valoraciones = getValoracionesUsuarios();
		// Si no hay valoraciones, mostrar un mensaje
		if (valoraciones == null || valoraciones.length == 0) {
			panelAdmin.add(new JLabel("No hay valoraciones registradas."), BorderLayout.CENTER);
			return panelAdmin;
		}
		// Crear las columnas de la tabla
		String[] columnNames = { "Calificación", "Comentario", "Usuario" };
		// Crear el modelo de la tabla con los datos de las valoraciones
		DefaultTableModel model = new DefaultTableModel(valoraciones, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Hacer que las celdas no sean editables
			}
		};
		// Crear la tabla y configurarla
		JTable tablaValoraciones = new JTable(model);
		tablaValoraciones.getTableHeader().setReorderingAllowed(false); // Desactiva el movimiento de columnas
		tablaValoraciones.setFillsViewportHeight(true); // Ajustar el contenido al tamaño del panel
		tablaValoraciones.setRowHeight(25); // Ajustar el alto de las filas
		tablaValoraciones.getColumnModel().getColumn(0).setPreferredWidth(50); // Ancho de la columna "Calificación"
		tablaValoraciones.getColumnModel().getColumn(1).setPreferredWidth(300); // Ancho de la columna "Comentario"
		tablaValoraciones.getColumnModel().getColumn(2).setPreferredWidth(100); // Ancho de la columna "Usuario"
		// Añadir la tabla dentro de un JScrollPane para desplazamiento
		JScrollPane scrollPane = new JScrollPane(tablaValoraciones);
		panelAdmin.add(scrollPane, BorderLayout.CENTER);
		return panelAdmin;
	}

	// Método para obtener las preguntas de los usuarios desde la base de datos
	public String[][] getPreguntasUsuarios() {
		ArrayList<String[]> preguntasList = new ArrayList<>();

		try (Connection con = DeustoTaller.getCon()) {
			String sql = "SELECT id, pregunta, usuario FROM PREGUNTAS WHERE respuesta IS NULL";

			// Preparar el PreparedStatement para obtener un ResultSet con cursor de solo
			// avance
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			// Iterar sobre el ResultSet y almacenar las preguntas en la lista
			while (rs.next()) {
				String[] pregunta = new String[3];
				pregunta[0] = String.valueOf(rs.getInt("id")); // ID
				pregunta[1] = rs.getString("pregunta"); // Pregunta
				pregunta[2] = rs.getString("usuario"); // Usuario
				preguntasList.add(pregunta);
			}
			// Convertir la lista a un arreglo bidimensional
			String[][] preguntas = new String[preguntasList.size()][3];
			for (int i = 0; i < preguntasList.size(); i++) {
				preguntas[i] = preguntasList.get(i);
			}
			return preguntas;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
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
				ps.executeUpdate(); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private List<Pieza> cargarTabla() {
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
		//modeloTabla.setRowCount(0);

		
		  /*cargarTabla().forEach(c -> { if
		  (c.getNombrePieza().contains(txtFiltro.getText())) {
		  modeloTabla.addRow(new Object[] { c.getId(), c.getCodigo(),
		  c.getNombrePieza(), c.getDescripcion(), c.getFabricante(), c.getPrecio(),
		  c.getCantidadAlmacen() }); } });
		 tabla.setModel(modeloTabla);*/
		
		ArrayList<Pieza> lp = (ArrayList<Pieza>) cargarTabla();
		if(txtFiltro.getText().equals("")) {
			modeloTabla = new ModeloAlmacen(lp);
			
		}else {
		
			ArrayList<Pieza> lpFiltradas = new ArrayList<Pieza>();
			for(Pieza p: lp) {
				if(p.getDescripcion().contains(txtFiltro.getText())) {
					lpFiltradas.add(p);
				}
			}
			modeloTabla = new ModeloAlmacen(lpFiltradas);
		}
		tabla.setModel(modeloTabla);

	}
	
	
    private static void cargarFabricantes(JComboBox<String> comboBox) {
        String sql = "SELECT DISTINCT fabricante FROM Pieza"; // Consulta SQL

        try {Connection conn = DeustoTaller.getCon(); // Obtenemos conexión
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();  // Ejecutamos la consulta

            while (rs.next()) { // Iteramos por los resultados
                String fabricante = rs.getString("fabricante"); // Obtenemos cada fabricante
                comboBox.addItem(fabricante); // Lo añadimos al JComboBox
            }

            System.out.println("Fabricantes cargados exitosamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void cargarNombres(JComboBox<String>combobox) {
    	String sql = "SELECT DISTINCT nombrePieza FROM Pieza"; // Consulta SQL
    	
    	try {Connection conn= DeustoTaller.getCon();
			PreparedStatement ps= conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()) {
				String nombre= rs.getString("nombrePieza");
				combobox.addItem(nombre);
			}
			System.out.println("Nombres cargados exitosamente ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

}

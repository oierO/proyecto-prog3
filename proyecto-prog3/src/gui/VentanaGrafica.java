package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import javax.swing.table.TableCellRenderer;

import domain.CitaDiagnostico;
import domain.Pieza;

public class VentanaGrafica extends JFrame {

	private static final long serialVersionUID = 1L;
	private ModeloAlmacen modeloTabla;
	private JTable tabla;
	private String nombre;
	private int telefono;
	private LocalDate fechaDePedido;
	private LocalDate fechaDeRealizacion;
	private String diagnosticos;
	private CitaDiagnostico citaDiagnostico;
	private JTextField txtFiltro;
	
	public VentanaGrafica() {
		setSize(600, 400);
		setLocationRelativeTo(null);
		setTitle("DeustoTaller");
		JTabbedPane menuPestanas = new JTabbedPane();

		// Pestaña Servicios
		String[] lServicios = new String[] { "Taller", "Piezas", "Diagnóstico" };
		JPanel pServicios = new JPanel();
		pServicios.setLayout(new BorderLayout());
		JPanel botones = new JPanel();
		botones.setLayout(new GridLayout(lServicios.length, 1));
		botones.setBorder(new TitledBorder("Operaciones"));
		pServicios.add(botones, BorderLayout.WEST);

		



	
		//Para los servicios
		
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
					


					// Cuando un panel está activo los otros no serán visibles
					if (operacion.equals("Diagnóstico")) {
						
						// Panel para diagnósticos
						ArrayList<CitaDiagnostico> listaCitasDiagnosticos = new ArrayList<CitaDiagnostico>(); //No se usa??
						JCheckBox checkBox1 = new JCheckBox("Motor", false);
						JCheckBox checkBox2 = new JCheckBox("Chapa", false);
						JCheckBox checkBox3 = new JCheckBox("Retrovisor", false);
						JCheckBox checkBox4 = new JCheckBox("Otros", false);

						JPanel panelDiagnostico = new JPanel();
						JPanel panelNorte = new JPanel();

						JTextArea textoOtros = new JTextArea();
						textoOtros.setRows(30);
						textoOtros.setColumns(60);
						textoOtros.setLineWrap(true);
						textoOtros.setVisible(false);

						JScrollPane panelTexto = new JScrollPane(textoOtros);
						panelTexto.setVisible(false);

						panelDiagnostico.setLayout(new BorderLayout());
						Border panelDiagnosticoBorder = BorderFactory.createTitledBorder("¿Qué problema tiene?");
						panelDiagnostico.setBorder(panelDiagnosticoBorder);
						panelDiagnostico.add(panelNorte, BorderLayout.NORTH);
						panelDiagnostico.add(panelTexto);

						panelNorte.add(checkBox1);
						panelNorte.add(checkBox2);
						panelNorte.add(checkBox3);
						panelNorte.add(checkBox4);
						panelDiagnostico.setVisible(true);

						checkBox4.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								JToggleButton boton = (JToggleButton) e.getSource();
								if (boton.isSelected()) {
									panelTexto.setVisible(true);
									textoOtros.setVisible(true);
									repaint();
								} else if (!boton.isSelected()) {
									panelTexto.setVisible(false);
									textoOtros.setVisible(false);
									repaint();
								}
							}
						});

						JButton botonReservar = new JButton("RESERVAR CITA");
						botonReservar.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								ArrayList<String> listaDiagnosticoSeleccionado = new ArrayList<String>();
								if (checkBox1.isSelected()) {
									listaDiagnosticoSeleccionado.add(checkBox1.getText());
								}
								if (checkBox2.isSelected()) {
									listaDiagnosticoSeleccionado.add(checkBox2.getText());
								}
								if (checkBox3.isSelected()) {
									listaDiagnosticoSeleccionado.add(checkBox3.getText());
								}
								if (checkBox4.isSelected()) {
									listaDiagnosticoSeleccionado.add(checkBox4.getText());

								}

								if (!listaDiagnosticoSeleccionado.isEmpty()) {
									System.out.println("El usuario ha seleccionado estos diagnósticos: ");
									for (String diagnostico : listaDiagnosticoSeleccionado) {
										System.out.println(diagnostico);
									}
								} else {
									System.out.println("El usuario no ha seleccionado ningún diagnóstico");
								}
								new VentanaCitaDiagnostico();

					
							}
						});
							
					

						
						

						
						panelDerechoServicios.add(panelDiagnostico,BorderLayout.CENTER);
						panelDerechoServicios.add(botonReservar,BorderLayout.EAST);
						
					} else if (operacion.equals("Piezas")) {
						
						// Panel para piezas
						JPanel panelPiezas = new JPanel();
						panelPiezas.setLayout(new BorderLayout());
						
						JPanel panelDescripcion = new JPanel();
						JTextArea descripcion = new JTextArea();
						descripcion.setRows(20);
						descripcion.setColumns(40);
						descripcion.setLineWrap(true);
						descripcion.setText("Hola ");
						panelDescripcion.setEnabled(false);
						
						panelDescripcion.add(descripcion);
						panelPiezas.add(panelDescripcion);
						
						JPanel panelBotones = new JPanel();
						JButton botonComprar = new JButton("Comprar Piezas");
						JButton botonVender = new JButton("Vender Piezas");
						panelBotones.add(botonComprar);
						panelBotones.add(botonVender);
						
						
						botonComprar.addActionListener(c -> System.out.println("El usuario quiere comprar piezas."));
						botonVender.addActionListener(v -> System.out.println("El usuario quiere vender piezas."));
						panelPiezas.add(panelBotones,BorderLayout.SOUTH);
						
						
						
						panelDerechoServicios.add(panelPiezas);
						

					} else if (operacion.equals("Taller")) {

						// Panel para Taller
						JPanel panelTaller = new JPanel();
						JButton botonTaller = new JButton("Taller");
						botonTaller.addActionListener(t -> System.out.println("Taller"));
						panelTaller.add(botonTaller);
						
						
						
						
						
						
						panelDerechoServicios.add(panelTaller);
						
					}
					
					//Refrescar el panel
					panelDerechoServicios.revalidate();
					panelDerechoServicios.repaint();
				}
			});
			botones.add(boton);
		}
		
		
		
		

		JPanel pAlmacen = new JPanel();
		JPanel pTabla= new JPanel();
		JPanel panelFiltro= new JPanel();
		panelFiltro.add(new JLabel("Filtro por título: "));
		txtFiltro= new JTextField(20);
		panelFiltro.add(txtFiltro);
		modeloTabla = new ModeloAlmacen(null);
		tabla = new JTable(modeloTabla);
		JScrollPane scroll = new JScrollPane(tabla);
		pTabla.add(scroll);
		pAlmacen.add(panelFiltro,BorderLayout.NORTH);
		pAlmacen.add(pTabla,BorderLayout.CENTER);
		cargarTabla();
		tabla.getTableHeader().setReorderingAllowed(false);//Para que no se puedan mover las columnas
		tabla.setDefaultRenderer(Object.class, new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				//Component c= getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				JLabel l= new JLabel(value.toString());
				l.setOpaque(true);
				if(row%2==0) {
					l.setBackground(Color.LIGHT_GRAY);	
				}
				if(column==6) {
					if(Integer.parseInt(l.getText())<100) {
						l.setBackground(Color.RED);
						
					}else if(Integer.parseInt(l.getText())<200) {
						l.setBackground(Color.YELLOW);
						
					}else {
						l.setBackground(Color.GREEN);
					}
				}
				
				return l;
			}
		});
		tabla.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				JLabel l= new JLabel(value.toString());
				l.setOpaque(true);
				if(column==0) {
					l.setHorizontalAlignment(JLabel.CENTER);
					l.setBackground(Color.BLUE);	
				}else if(column==1) {
					l.setHorizontalAlignment(JLabel.CENTER);
					l.setBackground(Color.LIGHT_GRAY);
				}else if(column==2) {
					l.setHorizontalAlignment(JLabel.CENTER);
					l.setBackground(Color.CYAN);
				}else if(column==3) {
					l.setHorizontalAlignment(JLabel.CENTER);
					l.setBackground(Color.MAGENTA);
				}else if(column==4) {
					l.setHorizontalAlignment(JLabel.CENTER);
					l.setBackground(Color.ORANGE);
				}else if(column==5) {
					l.setHorizontalAlignment(JLabel.CENTER);
					l.setBackground(Color.PINK);
				}else {
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
				// TODO Auto-generated method stub
				
			}
		});
		
		
		

		// Pestaña Parking
				JPanel pParking = new PanelParking();
				JPanel pUsuario = new PanelSesion(this); // Pasamos la referencia de la ventana gráfica
				
				//Pestaña Preferencias
				String[] lPreferencias = new String[] {"Notificaciones", "Historial", "Valoraciones", "Soporte"};
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
				               
				                //ActionListener del botón enviar
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
				                            JOptionPane.showMessageDialog(panelDerechoPreferencias, "Por favor, seleccione una calificación.");
				                            return;
				                        }
				                        // Insertar la valoración y el comentario en la base de datos
				                        String dbPath = "jdbc:sqlite:C:\\Users\\diaz.inigo\\git\\proyecto-prog3\\proyecto-prog3\\resources\\db\\Valoraciones.db";
				                      
				                        try (Connection connection = DriverManager.getConnection(dbPath)) {
				                            String sql = "INSERT INTO Valoraciones (valoracion, comentario) VALUES (?, ?)";
				                            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				                                stmt.setInt(1, valoracion);
				                                stmt.setString(2, comentario);
				                                stmt.executeUpdate();
				                                JOptionPane.showMessageDialog(panelDerechoPreferencias, "¡Valoración enviada con éxito!");
				                            }
				                        } catch (SQLException ex) {
				                            ex.printStackTrace();
				                            JOptionPane.showMessageDialog(panelDerechoPreferencias, "Error al enviar la valoración. Intente nuevamente.");
				                        }
				                    }
				                });
				                // Agregar panel de Valoraciones al panel derecho de preferencias
				                panelDerechoPreferencias.add(panelValoraciones, BorderLayout.CENTER);
				            
				            } else if (operacion.equals("Soporte")) {
				                // Panel para la sección de Soporte
				                JTabbedPane panelSoporte = new JTabbedPane();
				                
				                // Pestaña de Preguntas Frecuentes
				                JPanel panelPF = new JPanel();
				                panelPF.setLayout(new BoxLayout(panelPF, BoxLayout.Y_AXIS));  // Usamos BoxLayout para apilar las preguntas
				                // Agregar preguntas frecuentes con respuestas (se pueden poblar desde la base de datos)
				                String[][] preguntasFrecuentes = {
				                    {"¿Cómo puedo solicitar una cita para una reparación o revisión?",
				                        "Puede solicitar una cita llamando a nuestro número de contacto o usando nuestro sistema de citas en línea a través de nuestra página web. También puede visitarnos directamente y programar una cita con uno de nuestros asesores."},
				                    {"¿Cuáles son los horarios de atención del taller?",
				                        "Nuestro taller está abierto de lunes a viernes de 9:00 a 18:00. Los sábados ofrecemos atención de 9:00 a 13:00, y los domingos estamos cerrados."},
				                    {"¿Puedo llevar mi coche a reparar sin cita previa?",
				                        "Sí, puede traer su coche sin cita previa. Sin embargo, le recomendamos hacer una cita para asegurarse de que haya disponibilidad y evitar tiempos de espera prolongados."},
				                    {"¿Ofrecen servicio de recogida y entrega del vehículo?",
				                        "Sí, ofrecemos servicio de recogida y entrega a domicilio dentro de un área determinada. Contáctenos para más detalles sobre este servicio y verificar si está disponible en su ubicación."},
				                    {"¿Qué debo hacer si no puedo recoger mi coche el mismo día que lo reparan?",
				                        "Si no puede recoger su coche el mismo día, por favor, notifíquenos con antelación. Podemos programar un horario alternativo para la entrega o incluso organizar un servicio de entrega a domicilio, sujeto a disponibilidad."},
				                    {"¿Ofrecen garantía sobre las reparaciones realizadas?",
				                        "Sí, ofrecemos una garantía de 12 meses en todas las reparaciones y piezas que instalamos. Si tiene algún problema con la reparación dentro de ese periodo, puede traer su coche de vuelta y lo revisaremos sin costo adicional."},
				                    {"¿Cómo puedo pagar por los servicios del taller?",
				                        "Aceptamos pagos en efectivo, tarjeta de débito o crédito, y transferencias bancarias. También puede pagar utilizando sistemas de pago móviles."},
				                    {"¿Ofrecen servicios de mantenimiento preventivo?",
				                        "Sí, ofrecemos paquetes de mantenimiento preventivo, que incluyen revisión de los sistemas clave de su coche, cambios de aceite, filtros, frenos, y más."},
				                    {"¿Puedo dejar mi coche en el taller durante todo el día?",
				                        "Sí, puede dejar su coche durante el día, y nosotros nos encargaremos de las reparaciones mientras usted está ocupado. Le proporcionaremos una estimación de tiempo antes de la reparación."},
				                    {"¿Hay servicios disponibles para vehículos eléctricos?",
				                        "Sí, ofrecemos servicios de mantenimiento y reparación para vehículos eléctricos, incluyendo revisiones de batería, motor eléctrico, y otros sistemas específicos."},
				                    {"¿Cuánto tiempo tardará la reparación de mi vehículo?",
				                        "El tiempo de reparación depende de la complejidad del problema. Podemos darle una estimación después de realizar un diagnóstico inicial."},
				                    {"¿Realizan inspecciones antes de realizar una reparación?",
				                        "Sí, antes de cualquier reparación, realizamos una inspección completa para asegurarnos de que no haya otros problemas ocultos."},
				                    {"¿Cómo puedo saber si mi coche necesita una revisión?",
				                       	"Si notas ruidos extraños, luces de advertencia en el tablero o problemas con el rendimiento del motor, es recomendable hacer una revisión."},
				                    {"¿Ofrecen servicios de pintura y reparación de carrocería?",
				          				"Sí, ofrecemos servicios completos de pintura y reparación de carrocería, desde rayaduras hasta daños más graves."},
				                    {"¿Cómo puedo obtener un presupuesto para una reparación?",
				                       	"Puede traer su coche al taller para un diagnóstico, o contactarnos por teléfono o email para recibir una cotización aproximada."},
				                    {"¿Puedo esperar en el taller mientras reparan mi coche?",
				           				"Sí, tenemos una sala de espera donde puede quedarse mientras realizamos reparaciones rápidas o mantenimientos."},
				                    {"¿Cuál es el costo promedio de un cambio de aceite?",
				                        "El costo depende del tipo de aceite y la marca de su coche. Le proporcionaremos un presupuesto personalizado tras la revisión."},
				                    {"¿Qué tipos de vehículos reparan en su taller?",
				                        "Reparamos una amplia gama de vehículos, incluyendo coches, camiones ligeros, SUVs y vehículos eléctricos."},
				                    {"¿Tienen piezas originales o de marca?",
				                        "Sí, ofrecemos piezas originales y de marca. También tenemos opciones de piezas alternativas de calidad si lo prefiere."},
				                    {"¿Cómo puedo programar una cita para mantenimiento preventivo?",
				                        "Puede llamar a nuestro número de contacto o utilizar nuestra plataforma en línea para agendar una cita en el horario que más le convenga."}
				                };
				                // Recorrer las preguntas frecuentes y agregarlas al panel
				                for (String[] preguntaRespuesta : preguntasFrecuentes) {
				                    // Crear panel para cada pregunta y respuesta
				                    JPanel panelPregunta = new JPanel();
				                    panelPregunta.setLayout(new BoxLayout(panelPregunta, BoxLayout.Y_AXIS));
				                    panelPregunta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añadir un poco de espacio para que no se vea apretado
				                    
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
				                scrollPane.setPreferredSize(new Dimension(500, 300));  // Ajustar el tamaño del JScrollPane
				                
				                // Pestaña de Contacto
				                JPanel panelContacto = new JPanel(new BorderLayout());
				                JLabel lblEmail = new JLabel("Correo electrónico: contacto@taller.com");
				                JLabel lblTelefono = new JLabel("Teléfono: 123-456-789");
				                JButton btnContactarDirectamente = new JButton("Contactar Directamente");
				                btnContactarDirectamente.addActionListener(new ActionListener() {
				                    @Override
				                    public void actionPerformed(ActionEvent e) {
				                        // Abrir la ventana para enviar una pregunta
				                        new VentanaPregunta();
				                    }
				                });
				                panelContacto.add(lblEmail, BorderLayout.NORTH);
				                panelContacto.add(lblTelefono, BorderLayout.CENTER);
				                panelContacto.add(btnContactarDirectamente, BorderLayout.SOUTH);
				                
				                // Añadir este panel a la pestaña de soporte
				                JTabbedPane tabbedPane = new JTabbedPane();
				                tabbedPane.add("Contacto", panelContacto);
				                panelSoporte.addTab("Preguntas Frecuentes", scrollPane);
				                panelSoporte.addTab("Contacto", panelContacto);
				                
				                // Agregar panel de Soporte al panel derecho de preferencias
				                panelDerechoPreferencias.add(panelSoporte, BorderLayout.CENTER);
				            }
				            // Refrescar el panel derecho
				            panelDerechoPreferencias.revalidate();
				            panelDerechoPreferencias.repaint();
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

	private  List<Pieza> cargarTabla() {
		File f = new File("piezas_coche_almacen_1000.csv");
		List<Pieza> lp = new ArrayList<Pieza>();
		try {
			Scanner sc = new Scanner(f);
			while (sc.hasNextLine()) {
				String linea = sc.nextLine();
				String[] datos = linea.split(";");
				try {
					int id = Integer.parseInt(datos[0]);
					String codigo= datos[1];
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lp;
	} 
	private void filtrarPiezas() {
		modeloTabla.setRowCount(0);
		
		cargarTabla().forEach(c -> {
			if (c.getNombrePieza().contains(this.txtFiltro.getText())) {
				modeloTabla.addRow(
					new Object[] {c.getId(), c.getCodigo(), c.getNombrePieza(), c.getDescripcion(),c.getFabricante(),c.getPrecio(),c.getCantidadAlmacen()} );
			}
		});
		
		
		
		
	}
	
	//Para sacar lo datos del formualrio
	
//	 private void abrirVentanaCitaDiagnotico() {
//	        VentanaCitaDiagnostico ventanaCitaDiagnostico = new VentanaCitaDiagnostico(this);
//	        ventanaCitaDiagnostico.setVisible(true);
//	    }
	
	
	public String setNombre(String nombreRecivido) {
		nombre= nombreRecivido;
		
		return nombreRecivido;
	}
	
	public int setTelefono(String telefonoRecivido) {
		telefono = Integer.parseInt(telefonoRecivido);
		return telefono;
	} 
	
	public LocalDate setLocalDate(String fechaRecivido) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		fechaDeRealizacion = LocalDate.parse(fechaRecivido, formato);
		return fechaDeRealizacion;
	}
	
	public String setDiagnostico(String diagnosticoRecivido) {
		diagnosticos = diagnosticoRecivido;
		return diagnosticos;
	}
	
	
}

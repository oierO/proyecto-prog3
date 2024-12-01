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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import main.DeustoTaller;

public class VentanaGrafica extends JFrame {

	private static final long serialVersionUID = 1L;
	private static String usuario;

	public static String getUsuario() {
		return usuario;
	}

	private static void setUsuario(String usuario) {
		VentanaGrafica.usuario = usuario;
	}

	protected JTable tablaPreguntas;

	public VentanaGrafica(String usuario) {
		setSize(800, 500);
		setLocationRelativeTo(null);
		setTitle("DeustoTaller");
		JTabbedPane menuPestanas = new JTabbedPane();

		setUsuario(usuario);

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
//								new VentanaPregunta(usuario);
								System.out.println("\n---Esto es panel de preferencias---\n");
								System.out.println("El usuario quiere enviar una pregunta");
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

		menuPestanas.add("Servicios", new PanelServicios());
		menuPestanas.add("Almacen", new PanelAlmacen());
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
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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

}

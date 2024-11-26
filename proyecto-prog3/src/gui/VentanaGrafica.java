package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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

		                // Agregar panel de Valoraciones al panel derecho de preferencias
		                panelDerechoPreferencias.add(panelValoraciones, BorderLayout.CENTER);

		            } else if (operacion.equals("Soporte")) {
		                // Panel para la sección de Soporte
		                JTabbedPane panelSoporte = new JTabbedPane();

		                // Pestaña de Preguntas Frecuentes
		                JPanel panelPF = new JPanel();
		                JLabel textPF = new JLabel("Preguntas frecuentes");
		                panelPF.add(new JScrollPane(textPF));

		                // Pestaña de Contacto
		                JPanel panelContacto = new JPanel(new BorderLayout());
		                JLabel labelContacto = new JLabel("<html>Contacto: soporte@deustotaller.com<br>Tel: 123-456-789</html>");
		                panelContacto.add(labelContacto, BorderLayout.NORTH);

		                panelSoporte.addTab("Preguntas Frecuentes", panelPF);
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

package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.*;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import domain.Pieza;

public class VentanaGrafica extends JFrame {

	private static final long serialVersionUID = 1L;
	private ModeloAlmacen modeloTabla;
	private JTable tabla;
	

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

		//Panel para diagnósticos
		JCheckBox checkBox1 = new JCheckBox("Motor",false);
		JCheckBox checkBox2 = new JCheckBox("Chapa",false);
		JCheckBox checkBox3 = new JCheckBox("Retrovisor",false);
		JCheckBox checkBox4 = new JCheckBox("Otros",false);

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
		panelDiagnostico.add(panelNorte,BorderLayout.NORTH);
		panelDiagnostico.add(panelTexto);
		
		panelNorte.add(checkBox1);
		panelNorte.add(checkBox2);
		panelNorte.add(checkBox3);
		panelNorte.add(checkBox4);
		panelDiagnostico.setVisible(true);
		
		checkBox4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JToggleButton boton  = (JToggleButton) e.getSource();
				if(boton.isSelected()) {
					panelTexto.setVisible(true);
					textoOtros.setVisible(true);
					repaint();
				} else if (!boton.isSelected()){
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
			}
		});

		JPanel panelReservar = new JPanel();
		panelReservar.add(botonReservar);
		panelReservar.setVisible(true);
		pServicios.add(panelReservar, BorderLayout.EAST);
		//Los paneles de Diagnóstico son visibles por defecto
		
		
		//Panel para piezas
		JPanel panelPiezas = new JPanel();
		JButton botonPieza = new JButton("Piezas");
		botonPieza.addActionListener(p-> System.out.println("Piezas"));
		panelPiezas.add(botonPieza);
		panelPiezas.setVisible(false);

		
		//Panel para Taller
		JPanel panelTaller = new JPanel();
		JButton botonTaller = new JButton("Taller");
		botonTaller.addActionListener(t-> System.out.println("Taller"));
		panelTaller.add(botonTaller);
		panelTaller.setVisible(false);
	
		
		//Panel centro
		JPanel panelCentroServicio = new JPanel();
		panelCentroServicio.setLayout(new BorderLayout());
		panelCentroServicio.add(panelDiagnostico);
		panelCentroServicio.add(panelPiezas,BorderLayout.NORTH);
		panelCentroServicio.add(panelTaller,BorderLayout.WEST);
		pServicios.add(panelCentroServicio);
		
		
		for (int i = 0; i < lServicios.length; i++) {
			JButton boton = new JButton(lServicios[i]);
			boton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String operacion = boton.getText();
					repaint(); // Vuelve a renderizar el panel cuando un boton es pulsado.
					
					//Cuando un panel está activo los otros no serán  visibles
					if (operacion.equals("Diagnóstico")) {
						panelDiagnostico.setVisible(true);
						panelReservar.setVisible(true);
						panelPiezas.setVisible(false);
						panelTaller.setVisible(false);
						
						
					}else if (operacion.equals("Piezas")) {
						panelPiezas.setVisible(true);
						panelDiagnostico.setVisible(false);
						panelReservar.setVisible(false);
						panelTaller.setVisible(false);
						
					} else if(operacion.equals("Taller")) {
						panelTaller.setVisible(true);
						panelPiezas.setVisible(false);
						panelDiagnostico.setVisible(false);
						panelReservar.setVisible(false);
						
					}
				}
			});
			botones.add(boton);
		}

		JPanel pAlmacen = new JPanel();
		modeloTabla= new ModeloAlmacen(null);
		tabla= new JTable(modeloTabla);
		JScrollPane scroll= new JScrollPane(tabla);
		pAlmacen.add(scroll);
		cargarTabla(); //FIXME
		


		// Pestaña Parking

		JPanel pParking = new JPanel();
		JPanel pSettings = new JPanel();
		JPanel pUsuario = new PanelSesion(this); // Pasamos la referencia de la ventana gráfica

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
		
		setIconImage(new ImageIcon(getClass().getResource("/res/app-icon.png")).getImage());
		setVisible(true);
	}
	private void cargarTabla() {
		File f= new File("piezas_coche_almacen_1000.csv");
		List<Pieza>lp= new ArrayList<Pieza>();
		try {
			Scanner sc= new Scanner(f);
			while(sc.hasNextLine()) {
				String linea= sc.nextLine();
				String[] datos= linea.split(";");
				int id= Integer.parseInt(datos[0]);
				String nombre= datos[1];
				String descripcion= datos[2];
				String fabricante= datos[3];
				float precio=Float.parseFloat(datos[4]);
				int cantidad= Integer.parseInt(datos[5]);
				Pieza p= new Pieza(id, linea, nombre, descripcion, fabricante, precio, cantidad);
				lp.add(p);
				
				modeloTabla= new ModeloAlmacen(lp);
				tabla.setModel(modeloTabla);
				
				
				
				
				
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

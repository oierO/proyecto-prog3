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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import javax.swing.*;

import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import main.DeustoTaller;

public class VentanaGrafica extends JFrame {

	private static final long serialVersionUID = 1L;
	private static String usuario;
	private static Locale currentLocale;
	private static ResourceBundle bundle;
	private static String sTitulo,sComAd,sSelCal,sVEnviBien,sVEnviMal,sValoracion;
	private static String sNotificaciones,sHistorial,sValoraciones,sSoporte,sEstadis,sCorreoElectronico,sCorreo;
	private static String[] lPreferencias;
	private static String sOperaciones,sTelefono,sTelN,sContacto,sPreFre,sAdmPre,sUsuario,sPregunta,sResBien;
	private static JButton botonEnviar,btnContactarDirectamente,btnEnviarRespuesta;
	private static JLabel noPreLabel,noValLabel;
	private static String sResVacia,sSelctPre,sServicios,sAlmacen,sParking,sPreferencias,sSesion,sCalificacion,sComentario;

	    private PanelPreferencias panelPreferencias; // Campo para almacenar la referencia

	    public static String getUsuario() {
	        return usuario;
	    }

	    private static void setUsuario(String usuario) {
	        VentanaGrafica.usuario = usuario;
	    }

	    protected JTable tablaPreguntas;

	    public VentanaGrafica(String usuario, Locale locale) {
	        setSize(800, 500);
	        setLocationRelativeTo(null);

	        JTabbedPane menuPestanas = new JTabbedPane();

	        setUsuario(usuario);

	        // Idioma
	        currentLocale = locale;
	        bundle = ResourceBundle.getBundle("VentanaGraficaBundle", currentLocale);
	        sTitulo = bundle.getString("sTitulo");
	        setTitle(sTitulo);
	        sHistorial = bundle.getString("sHistorial");
	        sValoraciones = bundle.getString("sValoraciones");
	        sSoporte = bundle.getString("sSoporte");
	        sOperaciones = bundle.getString("sOperaciones");
	        sComAd = bundle.getString("sComAd");
	        sSelCal = bundle.getString("sSelCal");
	        sVEnviBien = bundle.getString("sVEnviBien");
	        sVEnviMal = bundle.getString("sVEnviMal");
	        sValoracion = bundle.getString("sValoracion");
	        sCorreoElectronico = bundle.getString("sCorreoElectronico");
	        sCorreo = bundle.getString("sCorreo");
	        sTelefono = bundle.getString("sTelefono");
	        sTelN = bundle.getString("sTelN");
	        sContacto = bundle.getString("sContacto");
	        sPreFre = bundle.getString("sPreFre");
	        sAdmPre = bundle.getString("sAdmPre");
	        sPregunta = bundle.getString("sPregunta");
	        sUsuario = bundle.getString("sUsuario");
	        sResBien = bundle.getString("sResBien");
	        sResVacia = bundle.getString("sResVacia");
	        sSelctPre = bundle.getString("sSelctPre");
	        sServicios = bundle.getString("sServicios");
	        sAlmacen = bundle.getString("sAlmacen");
	        sParking = bundle.getString("sParking");
	        sPreferencias = bundle.getString("sPreferencias");
	        sSesion = bundle.getString("sSesion");
	        sCalificacion = bundle.getString("sCalificacion");
	        sComentario = bundle.getString("sComentario");

	        // Pestañas principales
	        JPanel pParking = new PanelParking(currentLocale);
	        JPanel pUsuario = new PanelSesion(this, currentLocale);
	        panelPreferencias = new PanelPreferencias(bundle, usuario); // Crear la instancia

	        menuPestanas.add(sServicios, new PanelServicios(currentLocale));
	        menuPestanas.add(sAlmacen, new PanelAlmacen(currentLocale));
	        menuPestanas.add(sParking, pParking);
	        menuPestanas.add(sPreferencias, panelPreferencias); // Añadir a las pestañas
	        menuPestanas.add(sSesion, pUsuario);
	        add(menuPestanas);
	        
	     // Panel superior con botones circulares
	        JPanel panelSuperior = new JPanel();
	        panelSuperior.setLayout(new FlowLayout(FlowLayout.RIGHT));

	        // Botón Usuario con imagen
	        BotonCircular botonUsuario = new BotonCircular("C:\\Users\\diaz.inigo\\git\\proyecto-prog3\\proyecto-prog3\\resources\\images\\user.png", 20);
	        botonUsuario.addActionListener(e -> new VentanaUsuario());
	        panelSuperior.add(botonUsuario);

	        // Botón Ajustes sin imagen
	        BotonCircular botonAjustes = new BotonCircular("C:\\Users\\diaz.inigo\\git\\proyecto-prog3\\proyecto-prog3\\resources\\images\\app-icon.png", 20);
	        botonAjustes.addActionListener(e -> new VentanaAjustes(this));
	        panelSuperior.add(botonAjustes);

	        add(panelSuperior, BorderLayout.NORTH);


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

	    // Método para interactuar con PanelPreferencias
	    public void actualizarPreferencias() {
	        panelPreferencias.actualizarContenido();
	    }
	}

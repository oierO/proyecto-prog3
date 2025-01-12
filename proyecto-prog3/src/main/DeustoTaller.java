package main;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import db.ConsultasMain;
import domain.Usuario;
import gui.VentanaInicioSesion;

public class DeustoTaller {
	private static Usuario usuarioSesion;
	private static Connection con;
	private static String locDB;
	private static VentanaInicioSesion VSesion;
	public static Boolean debug;
	private static Locale locale;
	
	public static void main(String[] args) {
		System.out.println(toTimeStamp(LocalDateTime.now()));
		try {
			FileInputStream fPropiedades = new FileInputStream(new File("config/deustotaller.properties"));
			Properties propiedades = new Properties();
			propiedades.load(fPropiedades);
			debug = Boolean.parseBoolean(propiedades.getProperty("debug", "false"));
			locDB = "jdbc:sqlite:" + propiedades.getProperty("ubicacionBD", "resources/db/database.db");
			fPropiedades.close();
		} catch (IOException e) {
			System.out.println("Error al cargar el fichero de propiedades.");
		}
		ConsultasMain.conectarBD();
		Thread comprobarBD = new Thread() { //Hilo comprobación estado de la BD (Evitar cierre conexiones)

			@Override
			public void run() {
				while (!Thread.interrupted()) {
					try {
						if (DeustoTaller.getCon().isClosed()) {
							ConsultasMain.conectarBD();
						}
						System.out.println("A dormir");
						Thread.sleep(30000);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(DeustoTaller.getVSesion(), "Error al intentar reconectarse a la base de datos.");
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}

		};
		comprobarBD.start();
		locale = Locale.getDefault();
		SwingUtilities.invokeLater(() -> VSesion = new VentanaInicioSesion(locale));
		
		
	}

	public static String getLocDB() {
		return locDB;
	}

	public static void setCon(Connection con) {
		DeustoTaller.con = con;
	}

	public static Connection getCon() {
		return con;
	}


	public static VentanaInicioSesion getVSesion() {
		return VSesion;
	}

	public static void setUsuarioSesion(Usuario usuarioSesion) {
		DeustoTaller.usuarioSesion = usuarioSesion;
		usuarioSesion.sethUltimaSesion(LocalDateTime.now());

	}

	//Devuelve el objeto del Usuario cuya sesión esta iniciada
	public static Usuario getSesion() {
		return usuarioSesion;
	}

	// Devuelve el Timestamp de un LocalDateTime que reciba (Necesario para BD) 
	public static Timestamp toTimeStamp(LocalDateTime fecha) {
		return new Timestamp(fecha.toInstant(ZoneOffset.ofTotalSeconds(3600)).toEpochMilli());
	}
}

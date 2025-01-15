package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import domain.PedidoServicios;
import main.DeustoTaller;

public class ConsultasVisualizarPedidos {
	public static void guardarLaLista(ArrayList<PedidoServicios> listaServiciosPedidos) {
		String insertSQL1 = "INSERT INTO Pedido (username,tel,fecha_pedido,fecha_realizacion,informacion_adicional) VALUES (?,?,?,?,?)";
		String insertSQL2 = "INSERT INTO CONTIENE (id_pedido,servicio) VALUES (?,?)";
		try {
			Connection conn = DeustoTaller.getCon();
		
			
			PreparedStatement preparedStmt1 = conn.prepareStatement(insertSQL1);
			PreparedStatement preparedStmt2 = conn.prepareStatement(insertSQL2);
			for(PedidoServicios p:listaServiciosPedidos) {
		
		        LocalDate localDateFechPed = p.getfechaDePedido();
		        LocalDate localDateFechRea = p.getfechaDeRealizacion();

		        // Formatear la fecha como texto en formato ISO 8601
		        String fechaTexto1 = localDateFechPed.format(DateTimeFormatter.ISO_DATE);
		        String fechaTexto2 = localDateFechRea.format(DateTimeFormatter.ISO_DATE);
		        
				preparedStmt1.setString(1, p.getNombre());
				preparedStmt1.setInt(2, p.getTelefono());
				preparedStmt1.setString(3, fechaTexto1);
				preparedStmt1.setString(4, fechaTexto2);
				preparedStmt1.setString(5, p.getinformacionAdicional());
				preparedStmt1.executeUpdate();
				
				 // Obtiene el ID del elemento insertado
                ResultSet generatedKeys = preparedStmt1.getGeneratedKeys();
                
                if (generatedKeys.next()) {
                    int idPrincipal = generatedKeys.getInt(1);

                    // Inserta los atributos asociados
                    for (String servicio : p.getServiciosElegidos()) {
                    	preparedStmt2.setInt(1, idPrincipal);
                    	preparedStmt2.setString(2, servicio);
                    	preparedStmt2.executeUpdate();
                    }
		   
                }
			}		
			
		} catch (Exception e) {
			
			System.out.println("Error en guardar la lista");
			e.printStackTrace();
		}
		
		
		
	}

}

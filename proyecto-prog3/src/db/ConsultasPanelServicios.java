package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;

import domain.ServicioDisponible;
import main.DeustoTaller;

public class ConsultasPanelServicios {

    public static void cargarServicios(ArrayList<ServicioDisponible> listaServiciosDisponibles) {
        String sql = "SELECT * FROM SERVICIOS_DISPONIBLES";
        try (Connection conn = DeustoTaller.getCon();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
    
    public static void cargarFabricantes(JComboBox<String> fabricante) {
    	   String sql = "SELECT DISTINCT fabricante FROM Piezas";

           try (Connection conn = DeustoTaller.getCon();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

               while (rs.next()) {
                   fabricante.addItem(rs.getString("fabricante"));
               }
               System.out.println("\n--Esto es de panel de servicios--\n");
               System.out.println("Fabricantes cargados exitosamente ");
           } catch (SQLException e) {
               System.out.println("\n--Esto es de panel de servicios--\n");
               System.out.println("Error en cargar datos");
               e.printStackTrace();
           }

       }
}
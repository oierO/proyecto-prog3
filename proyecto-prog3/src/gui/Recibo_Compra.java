package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import domain.Pieza;

public class Recibo_Compra extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Recibo_Compra(ArrayList<Pieza>compra) {
		JPanel clientePanel = new JPanel(new GridLayout(4, 2, 5, 5));
        clientePanel.setBorder(BorderFactory.createTitledBorder("Información del Cliente"));
        
        clientePanel.add(new JLabel("Nombre:"));
        clientePanel.add(new JLabel());
        clientePanel.add(new JLabel("Apellidos:"));
        clientePanel.add(new JLabel());
        clientePanel.add(new JLabel("Fecha:"));
        clientePanel.add(new JLabel());
        clientePanel.add(new JLabel("Código Postal:"));
        clientePanel.add(new JLabel());

        // Panel central con la lista de piezas compradas
        JPanel piezasPanel = new JPanel(new BorderLayout());
        piezasPanel.setBorder(BorderFactory.createTitledBorder("Piezas Compradas"));
        
        DefaultListModel<String> piezasModel = new DefaultListModel<>();
        for (Pieza pieza : compra) {
            piezasModel.addAll((Collection<? extends String>) pieza);
        }
        JList<String> piezasList = new JList<>(piezasModel);
        JScrollPane scrollPane = new JScrollPane(piezasList);

        piezasPanel.add(scrollPane, BorderLayout.CENTER);

        // Botón de cierre
        JButton cerrarButton = new JButton("Cerrar");
        cerrarButton.addActionListener(e -> dispose());

        // Agregar componentes al JDialog
        add(clientePanel, BorderLayout.NORTH);
        add(piezasPanel, BorderLayout.CENTER);
        add(cerrarButton, BorderLayout.SOUTH);
        
        setVisible(true);
	}
	
	public static void main(String[] args) {
		new Recibo_Compra(null);
	}
}


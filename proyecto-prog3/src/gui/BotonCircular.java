package gui;

import javax.swing.*;
import java.awt.*;

public class BotonCircular extends JButton { //Ayudado con ChatGPT

    private static final long serialVersionUID = 1L;
    private ImageIcon imagen;

    public BotonCircular(String rutaImagen, int radio) {
        setContentAreaFilled(false);
        setPreferredSize(new Dimension(radio * 2, radio * 2)); // Radio fijo
        if (rutaImagen != null && !rutaImagen.isEmpty()) {
            imagen = new ImageIcon(rutaImagen);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.GRAY);
        } else {
            g.setColor(getBackground());
        }
        g.fillOval(0, 0, getWidth(), getHeight());

        if (imagen != null) {
            g.drawImage(imagen.getImage(), getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2, this);
        }

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawOval(0, 0, getWidth() - 1, getHeight() - 1);
    }

    @Override
    public boolean contains(int x, int y) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = Math.min(getWidth(), getHeight()) / 2;
        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(radius, 2);
    }
}

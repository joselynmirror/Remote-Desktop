package com.bus.rmi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public final class OverlayDrawing extends JFrame {
    private static OverlayDrawing instance;
    private ArrayList<Point> points;

    public static OverlayDrawing getInstance() {
        if (instance == null) {
            instance = new OverlayDrawing();
        }
        return instance;
    }

    public OverlayDrawing() {
        setTitle("Dibujo en Superposición");
        setSize(1920, 1080);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); // Sin bordes ni decoraciones
        setBackground(new Color(0, 0, 0, 0)); // Transparente
        setAlwaysOnTop(true); // Siempre en la parte superior
        setOpacity(0.5f); // Opcional: Ajusta la opacidad
        setLayout(null);

        points = new ArrayList<>();

        // Asegúrate de cerrar la aplicación correctamente
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Coloca la ventana en el centro de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);
    }

    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
        repaint();
    }

    public void removePoints() {
        points.clear();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        // Habilita el anti-aliasing para mejor calidad
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define el grosor del trazo
        g2d.setStroke(new BasicStroke(10)); // Cambia el grosor aquí

        g2d.setColor(Color.RED);
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}

package simulator.view;

import simulator.control.Controller;
import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class Viewer extends JComponent implements SimulatorObserver {

    private Controller ctrl;
    private int centerX;
    private int centerY;
    private double scale;
    private List<Body> bodies;
    private boolean showHelp;
    private boolean showVectors;

    public Viewer(Controller ctrl){
        initGUI();
        ctrl.addObserver(this);
    }

    private void initGUI() {
        // TODO add border with title
        bodies = new ArrayList<>();
        scale = 1.0;
        showHelp = true;
        showVectors = true;
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            // ...
            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyChar()) {
                    case '-':
                        scale = scale * 1.1;
                        repaint();
                        break;
                    case '+':
                        scale = Math.max(1000.0, scale / 1.1);
                        repaint();
                        break;
                    case '=':
                        autoScale();
                        repaint();
                        break;
                    case 'h':
                        showHelp = !showHelp;
                        repaint();
                        break;
                    case 'v':
                        showVectors = !showVectors;
                        repaint();
                        break;
                    default:
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            // ...
            @Override
            public void mouseEntered(MouseEvent e) {
                requestFocus();
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
            // use ’gr’ to draw not ’g’ --- it gives nicer results
        Graphics2D gr = (Graphics2D) g;
        gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // calculate the center
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        // TODO draw a cross at center
        // TODO draw bodies (with vectors if _showVectors is true)
        // TODO draw help if _showHelp is true
    }


    private void autoScale() {
        double max = 1.0;
        for (Body b : bodies) {
            Vector2D p = b.getPosition();
            max = Math.max(max, Math.abs(p.getX()));
            max = Math.max(max, Math.abs(p.getY()));
        }
        double size = Math.max(1.0, Math.min(getWidth(), getHeight()));
        scale = max > size ? 4.0 * max / size : 1.0;
    }


    // This method draws a line from (x1,y1) to (x2,y2) with an arrow.
    // The arrow is of height h and width w.
    // The last two arguments are the colors of the arrow and the line

    private void drawLineWithArrow(Graphics g, int x1, int y1, int x2, int y2, int w, int h, Color lineColor, Color arrowColor) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - w, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;
        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;
        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;
        int[] xpoints = { x2, (int) xm, (int) xn };
        int[] ypoints = { y2, (int) ym, (int) yn };
        g.setColor(lineColor);
        g.drawLine(x1, y1, x2, y2);
        g.setColor(arrowColor);
        g.fillPolygon(xpoints, ypoints, 3);
    }



    @Override
    public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {

    }

    @Override
    public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {

    }

    @Override
    public void onBodyAdded(List<Body> bodies, Body b) {

    }

    @Override
    public void onAdvance(List<Body> bodies, double time) {

    }

    @Override
    public void onDeltaTimeChanged(double dt) {

    }

    @Override
    public void onForceLawsChanged(String fLawsDesc) {

    }
}

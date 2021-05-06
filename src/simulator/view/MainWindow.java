package simulator.view;

import simulator.control.Controller;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private Controller ctrl;

    public MainWindow(Controller controller){
        super("Physics Simulator");
        ctrl=controller;
        initGUI();
    }

    private void initGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        // TODO complete this method to build the GUI

        ControlPanel controlPanel = new ControlPanel(ctrl);
        mainPanel.add(controlPanel, BorderLayout.PAGE_START);

        JPanel boxPanel = new JPanel(new BoxLayout(this, BoxLayout.Y_AXIS ));
        mainPanel.add(boxPanel, BorderLayout.CENTER);

        BodiesTable bodiesTable = new BodiesTable(ctrl);
        boxPanel.add(bodiesTable);

        Viewer viewer = new Viewer(ctrl);
        boxPanel.add(viewer);

        StatusBar statusBar = new StatusBar(ctrl);
        mainPanel.add(statusBar, BorderLayout.PAGE_END);

        mainPanel.setOpaque(true);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,500);


    }
}

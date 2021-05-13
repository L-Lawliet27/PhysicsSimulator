package simulator.view;

import simulator.control.Controller;

import javax.swing.*;
import javax.swing.border.LineBorder;
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

        //Control Panel--------------------------------------------
        ControlPanel controlPanel = new ControlPanel(ctrl);
        mainPanel.add(controlPanel, BorderLayout.PAGE_START);
        //Control Panel--------------------------------------------

        JPanel boxPanel = new JPanel();
        boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS ));

        mainPanel.add(boxPanel, BorderLayout.CENTER);

        //Bodies Table---------------------------------------------
        BodiesTable bodiesTable = new BodiesTable(ctrl);
        boxPanel.add(bodiesTable);
        //Bodies Table---------------------------------------------

        //Viewer---------------------------------------------------
        Viewer viewer = new Viewer(ctrl);
        boxPanel.add(viewer);
        //Viewer---------------------------------------------------

        //Status Bar-----------------------------------------------
        StatusBar statusBar = new StatusBar(ctrl);
        mainPanel.add(statusBar, BorderLayout.PAGE_END);
        //Status Bar------------------------------------------------

        mainPanel.setOpaque(true);

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(700,600);
        viewer.autoScale();

    }
}

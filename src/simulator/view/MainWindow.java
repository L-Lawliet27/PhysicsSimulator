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
    }
}

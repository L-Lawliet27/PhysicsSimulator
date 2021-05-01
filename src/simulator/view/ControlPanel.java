package simulator.view;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class ControlPanel extends JPanel implements SimulatorObserver {

    private Controller ctrl;
    private boolean stopped;

    private JToolBar toolBar;
    private JFileChooser fChoose;
    private JButton fileButton;
    private JButton forceButton;

    public ControlPanel(Controller controller){
        ctrl = controller;
        stopped = true;
        initGUI();
        ctrl.addObserver(this);
    }

    private void initGUI() {
        // TODO build the tool bar by adding buttons, etc.

        toolBar = new JToolBar();
        toolBar.setFloatable(false);
        this.add(toolBar, BorderLayout.PAGE_START);


        //File Button
        fChoose = new JFileChooser();
        File currDirectory = new File(ControlPanel.class.getResource("./").getPath());
        fChoose.setCurrentDirectory(currDirectory);

        fileButton = new JButton();
        fileButton.setToolTipText("Select a File to Load into the Simulator");
        fileButton.setIcon(createImageIcon("resources/icons/open.png"));
        fileButton.addActionListener(e -> loadFile());
        toolBar.add(fileButton);
        //File Button


        //Force Laws Button
        forceButton = new JButton();
        forceButton.setToolTipText("Select a Force Law to apply into the Simulator");
        forceButton.setIcon(createImageIcon("resources/icons/physics.png"));
        forceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ForceDialog(ctrl);
            }
        });


    }

    private void loadFile(){
        int op = fChoose.showOpenDialog(this.getParent());
        if(op==JFileChooser.APPROVE_OPTION){
            try {
                File getFile = fChoose.getSelectedFile();
                ctrl.reset();
                ctrl.loadBodies(new FileInputStream(getFile));

            }catch (FileNotFoundException e){
                System.out.println("Couldn't Open file - " + e.getMessage());
            }
        }
    }


    private Icon createImageIcon(String source) {
        return new ImageIcon(Toolkit.getDefaultToolkit().createImage(source));
    }

    private void run_sim(int n){
        if(n>0 && !stopped){
            try{
                ctrl.run(n);
            }catch (Exception e){
                // TODO show the error in a dialog box
                // TODO enable all buttons
                stopped = true;
            }

            SwingUtilities.invokeLater(() -> run_sim(n-1));
        }else {
            stopped=true;
            // TODO enable all buttons
        }
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

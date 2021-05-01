package simulator.view;

import org.json.JSONObject;
import simulator.control.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForceDialog extends JDialog {

    private Controller ctrl;
    private JSONObject info;
    private int check;
    private final String[] columns = {"Key", "Value", "Description"};

    public ForceDialog(Controller controller){
//        super(parent, true);
        ctrl = controller;
        check = 0;
        initGUI();
    }

    private void initGUI() {
        setTitle("Force Laws Selection");
        JPanel forceMain = new JPanel(new BorderLayout());

        JPanel desc = new JPanel();
        desc.add(new JLabel("Select a Force Law and Provide Values for the Parameters in the Value Column"));
        forceMain.add(desc, BorderLayout.PAGE_START);


        JPanel paramPanel = new JPanel();

        ForceTable tableModel = new ForceTable();
        JTable paramTable = new JTable(tableModel);
        tableModel.setColumnIdentifiers(columns);


        JComboBox comboBox = new JComboBox();

        for (JSONObject f: ctrl.getForceLawsInfo()) {
            comboBox.addItem(f.get("desc"));
        }


        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox auxBox = (JComboBox) e.getSource();
                Object selectedForce = auxBox.getSelectedItem();

                for (JSONObject f : ctrl.getForceLawsInfo()) {
                    assert selectedForce != null;
                    if(selectedForce.equals(f.get("desc"))){
                        String[][] data = new String[3][3];

                        if(f.has("G")) {
                           data[0][0] = "G";
                           data[0][1] = "";
                           data[0][2] = f.get("G").toString();
                           info = f;
                           check = 1;

                        }else if (f.has("c") && f.has("g")) {
                            data[0][0] = "c";
                            data[0][1] = "";
                            data[0][2] = f.get("c").toString();

                            data[1][0] = "g";
                            data[1][1] = "";
                            data[1][2] = f.get("g").toString();
                            info = f;
                            check = 2;
                        }

                        tableModel.addRow(data);
                    }
                }

            }
        });


        JPanel optionPanel = new JPanel();
        optionPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JButton okButton = new JButton("OK");
        okButton.setVisible(true);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(ForceTable.changed) {
                    if (check == 2) {
                        double c = (double) tableModel.getValueAt(0, 1);
                        double g = (double) tableModel.getValueAt(1, 1);
                        info.put("c", c);
                        info.put("g", g);
                    } else if (check == 1) {
                        double G = (double) tableModel.getValueAt(0, 1);
                        info.put("G", G);
                    }
                    ForceTable.resetC();
                }

                ctrl.setForceLaws(info);
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setVisible(true);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        optionPanel.add(okButton);
        optionPanel.add(cancelButton);

        paramPanel.add(new ScrollPane().add(paramTable));
        forceMain.add(paramPanel, BorderLayout.CENTER);
        forceMain.add(optionPanel, BorderLayout.PAGE_END);

        this.add(forceMain);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);


    }


}

package simulator.view;

import javafx.scene.control.ComboBox;
import org.json.JSONObject;
import simulator.control.Controller;
import simulator.model.ForceLaws;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForceDialog extends JDialog {

    private Controller ctrl;
    private JSONObject info;
    private int check;
    private boolean visible;


    private JComboBox<String> comboBox;

    public ForceDialog(Controller controller){
//        super(parent, true);
        ctrl = controller;
        check = 0;
        visible = false;
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
        paramTable.setPreferredSize(new Dimension(300,200));


        comboBox = new JComboBox<>();

        for (JSONObject f: ctrl.getForceLawsInfo()) {
            comboBox.addItem(f.get("desc").toString());
        }
        comboBox.setSelectedIndex(2);

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                @SuppressWarnings("unchecked")
                JComboBox<String> auxBox = (JComboBox<String>) e.getSource();
                Object selectedForce = auxBox.getSelectedItem();

                for (JSONObject f : ctrl.getForceLawsInfo()) {
                    assert selectedForce != null;

                    if(selectedForce.equals(f.get("desc").toString())){
                        JSONObject fd = f.getJSONObject("data");
                        tableModel.clearRows();
                        if(fd.has("G")) {
                            tableModel.setValueAt("G",0,0);
                            tableModel.setValueAt("",0,1);
                            tableModel.setValueAt(fd.get("G"),0,2);
                           info = f;
                           check = 1;

                        }else if (fd.has("c") && fd.has("g")) {
                            tableModel.setValueAt("c",0,0);
                            tableModel.setValueAt("",0,1);
                            tableModel.setValueAt(fd.get("c"),0,2);

                            tableModel.setValueAt("g",1,0);
                            tableModel.setValueAt("",1,1);
                            tableModel.setValueAt(fd.get("g"),1,2);

                            info = f;
                            check = 2;
                        }else{
                            tableModel.setValueAt("",0,0);
                            tableModel.setValueAt("",0,1);
                            tableModel.setValueAt("",0,2);

                            info = f;
                            check = 0;
                        }
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
                        String tVal1 = String.valueOf(tableModel.getValueAt(0, 1));
                        String tVal2 = String.valueOf(tableModel.getValueAt(1, 1));

                        if(!tVal1.isEmpty()){
                            double c = Double.parseDouble(tVal1);
                            info.getJSONObject("data").put("c",c);
                        }else{
                            info.getJSONObject("data").remove("c");
                        }

                        if(!tVal2.isEmpty()){
                            double g = Double.parseDouble(tVal2);
                            info.getJSONObject("data").put("g",g);
                        }else{
                            info.getJSONObject("data").remove("g");
                        }

                    } else if (check == 1) {

                        String tVal = String.valueOf(tableModel.getValueAt(0, 1));
                        if(!tVal.isEmpty()) {
                            double G = Double.parseDouble(tVal);
                            info.getJSONObject("data").put("G", G);
                        }else{
                            info.getJSONObject("data").remove("G");
                        }
                    }

                    ForceTable.resetC();
                }

                try {
                    ctrl.setForceLaws(info);
                }catch (Exception x){
                    JOptionPane.showMessageDialog(ForceDialog.this, "Couldn't Load Force - " + x.getMessage());
                }finally {
                    setV();
                }

            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setVisible(true);
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setV();
            }
        });

        optionPanel.add(comboBox);
        optionPanel.add(okButton);
        optionPanel.add(cancelButton);


        paramPanel.add(new JScrollPane(paramTable));

        forceMain.add(new JScrollPane(paramPanel), BorderLayout.CENTER);
        forceMain.add(optionPanel, BorderLayout.PAGE_END);
        forceMain.setVisible(true);

        this.add(forceMain);
        this.pack();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setV();
    }

    private void setV(){
        visible = !visible;
        this.setVisible(visible);
    }
}

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
    private boolean visible;

    private NewtonTableModel newtonTableModel;
    private MovingTableModel movingTableModel;


    private JComboBox<String> comboBox;

    public ForceDialog(Controller controller){
//        super(parent, true);
        ctrl = controller;
        check = 0;
        visible = true;
        initGUI();
    }

    private void initGUI() {
        setTitle("Force Laws Selection");
        JPanel forceMain = new JPanel(new BorderLayout());

        JPanel desc = new JPanel();
        desc.add(new JLabel("Select a Force Law and Provide Values for the Parameters in the Value Column"));
        forceMain.add(desc, BorderLayout.PAGE_START);


        JPanel paramPanel = new JPanel();

        JTable noForceTable = new JTable(new NoForceTableModel()); //Basically, a placeholder
        JScrollPane scrollPane = new JScrollPane(noForceTable);
        scrollPane.setPreferredSize(new Dimension(300,200));
//        JTable paramTable = new JTable(tableModel);
//        paramTable.setPreferredSize();


        comboBox = new JComboBox<>();

        for (JSONObject f: ctrl.getForceLawsInfo()) {
            comboBox.addItem(f.get("desc").toString());

            JSONObject fd = f.getJSONObject("data");

            if(fd.has("G")){
                newtonTableModel = new NewtonTableModel(fd.get("G").toString());
            }

            if(fd.has("c") && fd.has("g")){
                movingTableModel = new MovingTableModel(fd.get("c").toString(), fd.get("g").toString());
            }
        }

        JTable newtonTable = new JTable(newtonTableModel);
        JTable movingFixedTable = new JTable(movingTableModel);

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

                        if(fd.has("G")) {
                            scrollPane.setViewportView(newtonTable);
                            check = 1;

                        }else if (fd.has("c") && fd.has("g")) {
                            scrollPane.setViewportView(movingFixedTable);
                            check = 2;
                        }else{
                            scrollPane.setViewportView(noForceTable);
                            check = 0;
                        }
                        info = f;
                    }//if
                }//for
            }//actionPerformed
        });


        JPanel optionPanel = new JPanel();
        optionPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JButton okButton = new JButton("OK");
        okButton.setVisible(true);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (check == 2) {
                    String tVal1 = movingTableModel.getValueC();
                    String tVal2 = movingTableModel.getValueG();

                    setJsonData(tVal1,"c");
                    setJsonData(tVal2,"g");

                } else if (check == 1) {
                    String tVal = newtonTableModel.getValue();
                    setJsonData(tVal,"G");
                }

                try {
                    ctrl.setForceLaws(info);
                }catch (Exception x){
                    JOptionPane.showMessageDialog(ForceDialog.this, "Couldn't Load Force - " + x.getMessage());
                }finally {
                    setV();
                }//finally
            }//actionperformed
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


        paramPanel.add(scrollPane);
        forceMain.add(new JScrollPane(paramPanel), BorderLayout.CENTER);
        forceMain.add(optionPanel, BorderLayout.PAGE_END);
        forceMain.setVisible(true);

        this.add(forceMain);
        this.pack();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
//        this.setVisible(false);
        setV();
    }

    public void setV(){
        visible = !visible;
        this.setVisible(visible);
    }


    private void setJsonData(String tVal, String jsonKey){
        if(!tVal.isEmpty()) {
            double val = Double.parseDouble(tVal);
            info.getJSONObject("data").put(jsonKey, val);
        }
        else{
            info.getJSONObject("data").put(jsonKey, tVal);
        }
    }
}

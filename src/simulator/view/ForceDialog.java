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
    private boolean visible;

    private JTable forceTable;
    private ForceTableModel forceTableModel;
    private JComboBox<String> comboBox;

    public ForceDialog(Controller controller){
        ctrl = controller;
        visible = true;
        initGUI();
    }

    private void initGUI() {
        setTitle("Force Laws Selection");
        JPanel forceMain = new JPanel(new BorderLayout());

        JPanel desc = new JPanel();
        desc.add(new JLabel("Select a Force Law and Provide Values for the Parameters in the Value Column (default values are used" +
                "for parameters with no value)"));
        forceMain.add(desc, BorderLayout.PAGE_START);


        forceTableModel = new ForceTableModel();
        forceTable = new JTable(forceTableModel);

        JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setViewportView(forceTable);
        scrollPane.setPreferredSize(new Dimension(300,200));

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
                        forceTableModel = new ForceTableModel(f);
                        forceTable.setModel(forceTableModel);
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
                try {
                    JSONObject force = forceTableModel.setValues(info);
                    ctrl.setForceLaws(force);
                }catch (Exception x){
                    JOptionPane.showMessageDialog(ForceDialog.this, "Couldn't Load Force - " + x.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    setV();
                }//finally
            }//actionPerformed
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

        forceMain.add(scrollPane, BorderLayout.CENTER);
        forceMain.add(new JScrollPane(optionPanel), BorderLayout.PAGE_END);
        forceMain.setVisible(true);

        this.add(forceMain);
        this.pack();
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setV();
    }

    public void setV(){
        visible = !visible;
        this.setVisible(visible);
    }


}

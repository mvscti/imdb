/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import control.DatabaseController;
/**
 *
 * @author MARCUS VINICIUS
 */
public class PerformCountAggregationWithCriteriaScreen extends javax.swing.JFrame {

    /**
     * Creates new form PerformCountAggregationWithCriteriaScreen
     */
    public PerformCountAggregationWithCriteriaScreen() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        tableNamesCombo = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        tablesColumnsNameCombo = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        creiteriaTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Perform Count Aggregation Operation with Criteria");

        jLabel1.setText("Table");

        DatabaseController dc= new DatabaseController();
        tableNamesCombo.setModel(new javax.swing.DefaultComboBoxModel<>(dc.getTablesName()));
        tableNamesCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableNamesComboActionPerformed(evt);
            }
        });

        jLabel2.setText("Column");

        tablesColumnsNameCombo.setModel(new javax.swing.DefaultComboBoxModel<>(dc.getColumnsByTablename(this.tableNamesCombo.getSelectedItem().toString())));
        tablesColumnsNameCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tablesColumnsNameComboActionPerformed(evt);
            }
        });

        jButton1.setText("Show total");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Value");

        creiteriaTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creiteriaTxtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tableNamesCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tablesColumnsNameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(creiteriaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(jButton1))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)))
                .addContainerGap(136, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tableNamesCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tablesColumnsNameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(creiteriaTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(43, 43, 43)
                .addComponent(jButton1)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tableNamesComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableNamesComboActionPerformed
        // TODO add your handling code here:
        DatabaseController dc= new DatabaseController();
        this.tablesColumnsNameCombo.setEnabled(true);
        String[] columnNames=dc.getColumnsByTablename(this.tableNamesCombo.getSelectedItem().toString());
        this.tablesColumnsNameCombo.removeAllItems();
        this.tablesColumnsNameCombo.setModel(new javax.swing.DefaultComboBoxModel<>((columnNames)));
    }//GEN-LAST:event_tableNamesComboActionPerformed

    private void tablesColumnsNameComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tablesColumnsNameComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tablesColumnsNameComboActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jLabel4.setText("SELECT COUNT(*) FROM "+this.tableNamesCombo.getSelectedItem().toString()+" WHERE "+this.tablesColumnsNameCombo.getSelectedItem().toString()+"='"+this.creiteriaTxt.getText()+"'");
        DatabaseController dc= new DatabaseController();
        long init, end, difference;
        init = System.currentTimeMillis();
        int total=dc.getTotalOfOccurrences(this.tableNamesCombo.getSelectedItem().toString(), this.tablesColumnsNameCombo.getSelectedItem().toString(), creiteriaTxt.getText());
        end = System.currentTimeMillis(); 
        difference=end-init;
        javax.swing.JOptionPane.showMessageDialog(this,"The search has found "+total+" records in "+(difference / 1000)+" seconds","Result",javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void creiteriaTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creiteriaTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_creiteriaTxtActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField creiteriaTxt;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JComboBox<String> tableNamesCombo;
    private javax.swing.JComboBox<String> tablesColumnsNameCombo;
    // End of variables declaration//GEN-END:variables
}

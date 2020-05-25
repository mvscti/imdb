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
public class DeleteRecordScreen extends javax.swing.JFrame {

    /**
     * Creates new form DeleteRecordScreen
     */
    public DeleteRecordScreen() {
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
        tablesNameCombo = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Delete Record from Table");

        jLabel1.setText("Table");

        tablesNameCombo.setModel(new javax.swing.DefaultComboBoxModel<>(dc.getTablesName()));
        tablesNameCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tablesNameComboActionPerformed(evt);
            }
        });

        jButton1.setText("Remove");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tablesNameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(111, 111, 111)
                        .addComponent(jButton1)))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tablesNameCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String [] keysColumnsName=dc.getPrimaryKeyFromTable(this.tablesNameCombo.getSelectedItem().toString());
        String response[]=new String[keysColumnsName.length];
        for (int i=0; i<keysColumnsName.length; i++){
            while(response[i]==null || response[i].isEmpty())
                response[i]=javax.swing.JOptionPane.showInputDialog("Enter with a value for "+keysColumnsName[i]+" column");
                if (response[i].isEmpty()) javax.swing.JOptionPane.showMessageDialog(this,"Bad value for "+keysColumnsName[i],"Error",javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        long init, end, difference;
        init = System.currentTimeMillis();
        boolean status=dc.deleteRow(this.tablesNameCombo.getSelectedItem().toString(), response);
        end = System.currentTimeMillis(); 
        difference=end-init;
        if (status)
            javax.swing.JOptionPane.showMessageDialog(this,"The record was removed in "+(difference / 1000)+" seconds","Result",javax.swing.JOptionPane.INFORMATION_MESSAGE);
        else
            javax.swing.JOptionPane.showMessageDialog(this,"No record found. The search took "+(difference / 1000)+" seconds","Result",javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tablesNameComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tablesNameComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tablesNameComboActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JComboBox<String> tablesNameCombo;
    // End of variables declaration//GEN-END:variables
    private DatabaseController dc=new DatabaseController();
}

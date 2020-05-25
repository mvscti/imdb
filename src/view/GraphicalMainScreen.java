/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;
import control.FileInput;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;




/**
 *
 * @author MARCUS VINICIUS
 */
public class GraphicalMainScreen extends javax.swing.JFrame {
    private static boolean dumpFileLoaded=false;
    /**
     * Creates new form LoadSqlFile
     */
    public GraphicalMainScreen() {
        initComponents();
    }
    
    /**
     * * Creates new form LoadSqlFile
     * @param loaded define se o arquivo de dump já fora lido
     */
    public GraphicalMainScreen(boolean loaded) {
        initComponents();
        if (loaded){
            jMenuItem2.setEnabled(true);
            jMenuItem3.setEnabled(true);
            jMenuItem4.setEnabled(true);
            jMenuItem5.setEnabled(true);
            jMenuItem9.setEnabled(true);
            jMenuItem10.setEnabled(true);
            jMenu5.setEnabled(true);
            jMenu6.setEnabled(true);
            jMenuItem8.setEnabled(true);
            jMenuItem6.setEnabled(true);
            jMenuItem7.setEnabled(true);
            jMenuItem1.setEnabled(false);
            jMenuItem1.setVisible(false);
        }
    }
    
    
    public class ImportFileThread extends Thread{
        private String fileName;
        public ImportFileThread(String fileName){
            this.fileName=fileName;
        }
        final Runnable showLoadingScreen = new Runnable(){
            @Override
            public void run() {
                
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(GraphicalMainScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        };
        
        @Override
        public synchronized void run(){
            LoadingScreen ls=new LoadingScreen();
            try {
            
                ls.setVisible(true);  
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GraphicalMainScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            FileInput f= new FileInput(this.fileName);
            Thread t=new Thread(f);
            t.start();
            synchronized(t){
                try {
                    t.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(GraphicalMainScreen.class.getName()).log(Level.SEVERE, null, ex);
                }
                ls.setVisible(false);
                ls.dispose();
                jMenuItem2.setEnabled(true);
                jMenuItem3.setEnabled(true);
                jMenuItem4.setEnabled(true);
                jMenuItem5.setEnabled(true);
                jMenuItem9.setEnabled(true);
                jMenuItem10.setEnabled(true);
                jMenu5.setEnabled(true);
                jMenu6.setEnabled(true);
                jMenuItem8.setEnabled(true);
                jMenuItem6.setEnabled(true);
                jMenuItem7.setEnabled(true);
                jMenuItem1.setEnabled(false);
                jMenuItem1.setVisible(false);
            }
            
        }
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        About = new javax.swing.JMenuItem();

        jMenuItem3.setText("jMenuItem3");

        jMenu2.setText("File");
        jMenuBar2.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar2.add(jMenu3);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("IMDB SImulator");

        jMenu1.setMnemonic('f');
        jMenu1.setText("Options");
        jMenu1.setToolTipText("");

        jMenuItem1.setMnemonic('c');
        jMenuItem1.setText("Load dump file");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenu5.setText("Perform INNER JOIN Operation");
        jMenu5.setEnabled(false);

        jMenuItem4.setText("Perform INNER JOIN Operation with Primary Keys");
        jMenuItem4.setEnabled(false);
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem4);

        jMenuItem8.setText("Perform INNER JOIN Operation for any Columns");
        jMenuItem8.setEnabled(false);
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem8);

        jMenu1.add(jMenu5);

        jMenu6.setText("Perform OUTER JOIN Operation");
        jMenu6.setEnabled(false);

        jMenuItem2.setText("Perform LEFT JOIN Operation");
        jMenuItem2.setEnabled(false);
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem2);

        jMenuItem10.setText("Perform RIGHT JOIN Operation");
        jMenuItem10.setEnabled(false);
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem10);

        jMenu1.add(jMenu6);

        jMenuItem5.setText("Perform Count Aggregation Operation from any existing tables");
        jMenuItem5.setEnabled(false);
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setText("Perform Count Aggregation Operation with critearia");
        jMenuItem6.setEnabled(false);
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuItem9.setText("Perform Battery Testing");
        jMenuItem9.setEnabled(false);
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem9);

        jMenuItem7.setText("Delete Record from Table");
        jMenuItem7.setEnabled(false);
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        jMenuBar1.add(jMenu1);

        jMenu4.setText("Help");

        About.setText("About");
        About.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutActionPerformed(evt);
            }
        });
        jMenu4.add(About);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 634, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        int returnVal = this.jFileChooser1.showOpenDialog(this);
        if (returnVal == this.jFileChooser1.APPROVE_OPTION) {
            File file=this.jFileChooser1.getSelectedFile();
            String extension=file.getName().substring(file.getName().lastIndexOf(".") + 1);            
            if (!extension.equalsIgnoreCase("sql")) javax.swing.JOptionPane.showMessageDialog(this,"Failed load data. The file must be a SQL script","Error",javax.swing.JOptionPane.ERROR_MESSAGE);
            else{
                ImportFileThread thread= new ImportFileThread(file.getAbsolutePath());
                Thread t=new Thread(thread);
                t.start();
            }
        } else {
            System.out.println("File access cancelled by user.");
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void AboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutActionPerformed
        // TODO add your handling code here:
        AboutFrame aboutFrame=new AboutFrame();
        aboutFrame.setVisible(true);
    }//GEN-LAST:event_AboutActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        PerformInnerJoinOperationScreen screen = new PerformInnerJoinOperationScreen();
        screen.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        PerformLeftJoinOperationScreen screen=new PerformLeftJoinOperationScreen();
        screen.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        PerformCountAggregationScreen ns=new PerformCountAggregationScreen();
        ns.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        PerformCountAggregationWithCriteriaScreen pcas= new PerformCountAggregationWithCriteriaScreen();
        pcas.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        DeleteRecordScreen screen= new DeleteRecordScreen();
        screen.setVisible(true);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        PerformInnerJoinOperationWithNonPrimaryKeysScreen screen=new PerformInnerJoinOperationWithNonPrimaryKeysScreen();
        screen.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
        PerformBatteryTestingScreen screen= new PerformBatteryTestingScreen();
        screen.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
        PerformRightJoinOperationScreen screen= new PerformRightJoinOperationScreen();
        screen.setVisible(true);
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GraphicalMainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GraphicalMainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GraphicalMainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GraphicalMainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GraphicalMainScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem About;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    // End of variables declaration//GEN-END:variables
}

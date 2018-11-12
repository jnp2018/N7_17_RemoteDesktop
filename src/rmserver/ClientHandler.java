/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmserver;

import static com.sun.glass.ui.Cursor.setVisible;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class ClientHandler extends Thread{
     private JDesktopPane desktop = null;
    private Socket cSocket = null;
    private JInternalFrame interFrame = new JInternalFrame("Client Screen",
                                                            true, true, true);
    private JPanel cPanel = new JPanel();
    
    public ClientHandler(Socket cSocket, JDesktopPane desktop) {
        this.cSocket = cSocket;
        this.desktop = desktop;
        start();
    }
    public void drawGUI(){
        interFrame.setLayout(new BorderLayout());
        interFrame.getContentPane().add(cPanel,BorderLayout.CENTER);
        interFrame.setSize(100,100);
        desktop.add(interFrame);
        try {
            interFrame.setMaximum(true);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }
        //this allows to handle KeyListener events
        cPanel.setFocusable(true);
        //cPanel.clipboard();
        
        interFrame.setVisible(true);
    }
    public void clipboard(){
      StringSelection data = new StringSelection
         ("This is copied to the clipboard");
      Clipboard cb = Toolkit.getDefaultToolkit()
         .getSystemClipboard();
      cb.setContents(data, data);


      // This represents the paste (Ctrl+V) operation

      try {
         Transferable t = cb.getContents(null);
         if (t.isDataFlavorSupported(DataFlavor.stringFlavor))
            System.out.println(t.getTransferData(DataFlavor
               .stringFlavor));
      } catch (UnsupportedFlavorException | IOException ex) {
          System.out.println("");
      }
   
    }

    public void run(){

        Rectangle clientScreenDim = null;
        ObjectInputStream ois = null;
        drawGUI();

        try{
            ois = new ObjectInputStream(cSocket.getInputStream());
            clientScreenDim =(Rectangle) ois.readObject();
        }catch(IOException ex){
            ex.printStackTrace();
        }catch(ClassNotFoundException ex){
            ex.printStackTrace();
        }
        // nhận ảnh chụp màn hình từ client
        new ClientScreen(ois,cPanel);
        // gửi event đến client
        new ClientCommands(cSocket,cPanel,clientScreenDim);
    }
}

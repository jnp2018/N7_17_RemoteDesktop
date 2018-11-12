/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmserver;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Admin
 */
public class RMServer {
    private JFrame frame = new JFrame();
    private JDesktopPane desktop = new JDesktopPane();

    public static void main(String args[]){
       // String port = JOptionPane.showInputDialog("Please enter listening port");
        new RMServer().initialize();
    }

    public void initialize(){

        try {
            ServerSocket sc = new ServerSocket(5000);
            drawGUI();
            while(true){
                Socket client = sc.accept();
                System.out.println("New client Connected to the server");
                new ClientHandler(client,desktop);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void drawGUI(){
            frame.add(desktop,BorderLayout.CENTER);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
    }
    
}

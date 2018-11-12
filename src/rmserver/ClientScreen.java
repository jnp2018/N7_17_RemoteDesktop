/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmserver;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class ClientScreen extends Thread{
    private ObjectInputStream cObjectInputStream = null;
    private JPanel cPanel = null;
    int i=0;
    public ClientScreen(ObjectInputStream ois, JPanel p) {
        cObjectInputStream = ois;
        cPanel = p;
        start();
    }

    public void run(){
        
            try {

                while(true){
                    ImageIcon imageIcon = (ImageIcon) cObjectInputStream.readObject();
                    Image image = imageIcon.getImage();
                    //BufferedImage img = (BufferedImage) image;
                    
                    
                    image = image.getScaledInstance(cPanel.getWidth(),cPanel.getHeight()
                                                        ,Image.SCALE_FAST);
                    BufferedImage bufferedImage = new BufferedImage(cPanel.getWidth(), cPanel.getHeight(),
        BufferedImage.TYPE_INT_RGB);
                    
                    Graphics g = bufferedImage.createGraphics();
                    g.drawImage(image, 0, 0, null);
                    g.dispose();
                    
                    // lưu ảnh vào thư mục ScreenClient ngoài màn hình Desktop
                    ImageIO.write( bufferedImage,"png",new File(System.getProperty("user.home")+"/Desktop/ScreenClient/"+i+".png"));
                            i++;
                    //hiện thị ảnh vừa nhận được
                    Graphics graphics = cPanel.getGraphics();
                    graphics.drawImage(image, 0, 0, cPanel.getWidth(),cPanel.getHeight(),cPanel);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
          } catch(ClassNotFoundException ex){
              ex.printStackTrace();
          }
     } 
}

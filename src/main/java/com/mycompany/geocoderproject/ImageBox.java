/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.geocoderproject;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author ROOT
 */
public class ImageBox extends JPanel{
    
    private Image image;
    private final String DEFAULT_IMAGE_PATH = "images/map.jpg";

    
    public ImageBox(Image image) {
        this.image = image;
    }

    public ImageBox() {
        try {
            image = ImageIO.read(new File(DEFAULT_IMAGE_PATH));
        } catch (IOException ex) {
            Logger.getLogger(ImageBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
        repaint();
    }
    
    public void setImage(URL url) {
        try {
            setImage(ImageIO.read(url));
        } catch (IOException ex) {
            Logger.getLogger(ImageBox.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
    
    
    
}

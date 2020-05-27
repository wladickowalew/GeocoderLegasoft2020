/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.geocoderproject;

import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ROOT
 */
public class YandexMaps {
    
    private static final String SERVER = "https://static-maps.yandex.ru/1.x/";
    
    public static URL getImage(GeoObject object, String l){
        String url = SERVER + "?l=" + l +
                              "&ll=" + object.getLL() +
                              "&spn=" + object.getSPN();
        try { 
            return new URL(url);
        } catch (Exception ex) {
            Logger.getLogger(YandexMaps.class.getName()).log(Level.SEVERE, null, ex); 
        }
        return null;
    }
    
}

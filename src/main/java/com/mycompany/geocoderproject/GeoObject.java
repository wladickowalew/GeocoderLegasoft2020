/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.geocoderproject;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

public class GeoObject {
    
    private double longitude , latitude;
    private double lowerLong , lowerLat;
    private double upperLong , upperLat;
    private String name;
    
    public GeoObject(JSONObject object){
        try {
            name = object.getJSONObject("metaDataProperty")
                         .getJSONObject("GeocoderMetaData")
                         .getString("text");
            String pos = object.getJSONObject("Point").getString("pos");
            int index = pos.indexOf(" ");
            longitude = Double.parseDouble(pos.substring(0, index));
            latitude  = Double.parseDouble(pos.substring(index + 2));
            
            String lower = object.getJSONObject("boundedBy").getJSONObject("Envelope").getString("lowerCorner");
            index = pos.indexOf(" ");
            lowerLong = Double.parseDouble(lower.substring(0, index));
            lowerLat  = Double.parseDouble(lower.substring(index + 2));
            
            String upper = object.getJSONObject("boundedBy").getJSONObject("Envelope").getString("upperCorner");
            index = pos.indexOf(" ");
            upperLong = Double.parseDouble(upper.substring(0, index));
            upperLat  = Double.parseDouble(upper.substring(index + 2));
        } catch (JSONException ex) {
            Logger.getLogger(GeoObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String toString() {
        return  name + " ll: " + longitude + ", " + latitude ;
    }
    
    

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}

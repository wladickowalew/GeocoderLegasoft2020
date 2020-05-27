/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.geocoderproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ROOT
 */
public class Weather {
    
    private static final String SERVER = "http://api.weatherstack.com/current";
    
    public static HashMap<String, String> getWeather(String query){
        try {
            String url = SERVER + "?query=" + URLEncoder.encode(query, "UTF-8") +
                    "&access_key="+Secret.getWeatherKEY()+
                    "&units=m";
            System.out.println(url);
            JSONObject object = getJSON(new URL(url)).getJSONObject("current");
            String temp  = "" + object.getInt("temperature");
            String ws    = "" + object.getInt("wind_speed");
            String pres  = "" + object.getInt("pressure");
            String image = (String) object.getJSONArray("weather_icons").get(0);
            String descr = (String) object.getJSONArray("weather_descriptions").get(0);
            HashMap<String, String> ans = new HashMap<>();
            ans.put("temperature", temp);
            ans.put("wind_speed", ws);
            ans.put("pressure", pres);
            ans.put("icon", image);
            ans.put("description", descr);
            return ans;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(Weather.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static JSONObject getJSON(URL url) throws IOException, JSONException{
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                    con.getInputStream(), "UTF-8"));
        String tmp;
        StringBuilder builder = new StringBuilder();
        while((tmp = in.readLine()) != null)
            builder.append(tmp);
        in.close();
        
        JSONObject JSON = new JSONObject(builder.toString());
        System.out.println(JSON);
        return JSON;
    }
    
}

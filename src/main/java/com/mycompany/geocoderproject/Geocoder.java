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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ROOT
 */
public class Geocoder {
    
    private static GeoObject[] objects;
    
    public static GeoObject getGeoObject(int index){
        return objects[index];
    }
    
    public static String[] getObjects(String query){
        try {
            String URLstr = getURL(query);
            URL url = new URL(URLstr);
            System.out.println("URL: " + url);
            JSONArray jsonArray = getJSONArray(url);
            objects = jsonArray2GeoObjectArray(jsonArray);
            String[] ans = new String[objects.length];
            for (int i = 0; i < objects.length; i++)
                ans[i] = objects[i].toString();
            return ans;
        } catch (UnsupportedEncodingException ex) {
            System.out.println("Ошибка декодирования");
            Logger.getLogger(Geocoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            System.out.println("Ошибка Формирования URL");
            Logger.getLogger(Geocoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Ошибка запроса");
            Logger.getLogger(Geocoder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            System.out.println("Ошибка JSON");
            Logger.getLogger(Geocoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static String getURL(String query) throws UnsupportedEncodingException{
        String server = "https://geocode-maps.yandex.ru/1.x/";
        String params = "format=json" +
                        "&results=100" +
                        "&lang=en_RU" +
                        "&geocode=" + URLEncoder.encode(query, "UTF-8") +
                        "&apikey=" + Secret.getGeocoderKEY();
        return server + "?" + params;
    }
    
    private static JSONArray getJSONArray(URL url) throws IOException, JSONException{
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
        JSONArray ans = JSON.getJSONObject("response")
                            .getJSONObject("GeoObjectCollection")
                            .getJSONArray("featureMember");
        return ans;
    }
    
    private static GeoObject[] jsonArray2GeoObjectArray(JSONArray json) throws JSONException{
        int n = json.length();
        GeoObject[] ans = new GeoObject[n];
        for(int i = 0; i < n; i++){
            GeoObject obj = new GeoObject(json.getJSONObject(i).getJSONObject("GeoObject"));
            ans[i] = obj;
        }
        return ans;
    }
    
}

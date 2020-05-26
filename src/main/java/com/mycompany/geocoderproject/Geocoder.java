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
    
    public static String[] getObjects(String query){
        try {
            String URLstr = getURL(query);
            URL url = new URL(URLstr);
            System.out.println("URL: " + url);
            JSONArray jsonArray = getJSONArray(url);
            return jsonArray2StringArray(jsonArray);
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
                        "&geocode=" + URLEncoder.encode(query, "UTF-8") +
                        "&apikey=" + Secret.getKEY();
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
    
    private static String[] jsonArray2StringArray(JSONArray json) throws JSONException{
        int n = json.length();
        String[] ans = new String[n];
        for(int i = 0; i < n; i++){
            GeoObject obj = new GeoObject(json.getJSONObject(i).getJSONObject("GeoObject"));
            ans[i] = obj.toString();
        }
        return ans;
    }
    
}

package com.example.asus_pc.fantutorial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

// create Linlkit class for kit
public class LinkKit {

    public static String GET(String url) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader;
        StringBuilder stringBuilder;
        String line;
        String jsonString = "";
        try {
            URL u = new URL(url);
            httpURLConnection = (HttpURLConnection) u.openConnection();
            httpURLConnection.setRequestMethod("GET");
            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            jsonString = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert httpURLConnection != null;
            httpURLConnection.disconnect();
        }

        return jsonString;
    }

    public static String POST(String u, Map<String, String> p)
            throws IOException
    {
        String s = null;
        HttpURLConnection conn = null;
        String line;
        URL url;
        try
        {
            url = new URL(u);
        }
        catch (MalformedURLException e)
        {
            throw new IllegalArgumentException("invalid url: " + u);
        }

        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = p.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }

        String body = bodyBuilder.toString();
        byte[] bytes = body.getBytes();
        try {

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code " + status);
            }

            BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line).append('\n');
            }
            s = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert conn != null;
            conn.disconnect();
        }

        return s;
    }
}

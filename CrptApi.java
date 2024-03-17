package org.java;

import com.google.common.util.concurrent.RateLimiter;
import java.net.URL;
import java.net.URLConnection;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;

public class CrptApi {


    public void createDocument(){
        RateLimiter rateLimiter = RateLimiter.create(2.0); // ограничение в 2 запроса в секунду

        while (true) {
            if (rateLimiter.tryAcquire()) {
                try {
                    URL url = new URL("http://www.example.com");
                    URLConnection connection = url.openConnection();
                    connection.setDoOutput(true); // разрешаем вывод

                    String jsonBody = "{\"параметр1\":\"значение1\",\"параметр2\":\"значение2\"}";

                    DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                    wr.writeBytes(jsonBody);
                    wr.flush();
                    wr.close();

                    InputStream is = connection.getInputStream();
                    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        System.out.println(line);
                    }
                    rd.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


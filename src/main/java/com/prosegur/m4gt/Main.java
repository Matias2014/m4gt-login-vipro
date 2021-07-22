package com.prosegur.m4gt;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

/*
Clase Main
Tiene 2 métodos estáticos:

* main: Implementada para probar por línea de comandos. llama a ejecutarRequest.
* ejecutarRequest: Recibe los elemtentos del JSON a enviar como parámetros,
                   usa la función Peticion.devuelveJson para generar JSON y
                   lo envía a través la función ManejadorJson.
* ManejadorJson: Toma el JSON generaod y lo envía a través de un POST HTTP.

Lautaro Gaviola/Cristian Cantero
*/



public class Main {

    public final String USER_AGENT = "Mozilla/5.0";

    public static void main(
        String[] args) {

        ejecutarRequest(args[0],args[1],args[2]);

    }

    public static String ejecutarRequest(
            String url,
            String user,
            String pass
    ) {
        String Salida = "";
        String SalidaJson;
        String ResponseBody;
        Peticion m = new Peticion();
        Gson gson = new Gson();

        m.setUrl(url);
        //A partir de aca lo que es propio de cada registro
        m.setUser(user);
        m.setPass(pass);

        m.validateData();

        try {
            SalidaJson = m.devuelveJson();
            ResponseBody = ManejadorJson(m);

            //TODO
            DesarmaJson value = gson.fromJson(ResponseBody, DesarmaJson.class);
            String Token = value.accessToken;

            System.out.println("---------------JSON ENVIADO-------");
            System.out.println(SalidaJson);
            System.out.println("---------------RESPONSE BODY------");
            System.out.println(ResponseBody);
            System.out.println("---------------access token-------");
            System.out.println(Token);


            return Token;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "m4_error - see stacktrace 1:" + e.toString();
        }
    }



    public static String ManejadorJson(Peticion m) {
        //String informacion = "{pre-envio}";

        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost(m.getUrl());
            StringEntity params = new StringEntity(m.getJson(), ContentType.APPLICATION_JSON);
            request.setEntity(params);

            HttpResponse response = httpClient.execute(request);
            HttpEntity responseEntity = response.getEntity();

            //TODO
            String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            System.out.println("responseBody: "+responseBody);
            /*
            System.out.println(responseEntity.toString());
            System.out.println(response.toString());
            System.out.println(request.getRequestLine().toString());
            */
            EntityUtils.consume(responseEntity);
            ((Closeable) response).close();

            /* return "response=" + response.toString(); */
            return responseBody;

        } catch (Exception ex) {
            //informacion = "{error-envio post sin proxy:" + ex.getMessage() + "}";
            ex.printStackTrace();
            return "m4_error - see stacktrace 2";
        }
    }
}

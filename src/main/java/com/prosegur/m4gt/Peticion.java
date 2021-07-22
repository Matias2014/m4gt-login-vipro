package com.prosegur.m4gt;

import com.google.gson.*;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.Properties;


/*
Clase Peticion
Tiene 1 metodo publico: (+setters y getters)

* devuelveJson: Convierte los atributos de la clase en un JSON.

Lautaro Gaviola/Cristian Cantero
*/

public class Peticion {

    private String Json;

    private String user;
    private String pass;
    private String url;

    public void validateData() {
        String Faltas="";

        if (user.isEmpty() || user.equalsIgnoreCase("#")) {
            Faltas=Faltas+"user";
        }

        if (pass.isEmpty() || pass.equalsIgnoreCase("#")) {
            Faltas=Faltas+"pass";
        }

        if (Faltas.length() > 1) {
            throw new IllegalArgumentException("Faltan parametros: " + Faltas);
        }

    }

    public String devuelveJson() {

        JsonObject ObjetoPrincipal = new JsonObject();

        ObjetoPrincipal.addProperty("username", getUser());
        ObjetoPrincipal.addProperty("password", getPass());

        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        Json = gson.toJson(ObjetoPrincipal);

        return Json;
        
        
    }
    
    public String getJson() {
            return Json;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

class DesarmaJson {
    String responseBody;
    String username;
    String name;
    String lastName;
    String accessToken;
    String email;
    String countries;


}
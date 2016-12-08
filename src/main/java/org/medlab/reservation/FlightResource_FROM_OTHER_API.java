/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.medlab.reservation;

//import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

import static javax.ws.rs.core.HttpHeaders.USER_AGENT;

import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Mathias
 */
@Path("/allairlines")
public class FlightResource_FROM_OTHER_API {

    @Context
    private UriInfo context;

    public FlightResource_FROM_OTHER_API() {
        populateList();
    }

    List<String> urls = new ArrayList<String>();

    public void populateList() {
        urls.add("http://airline-plaul.rhcloud.com/api/flightinfo/");
        urls.add("http://localhost:8080/api/flights/");
//        urls.add("http://138.68.78.190:8080/");
    }

    @GET
    @Path("{from}/{to}/{date}/{tickets}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getFlightsFromTo(
            @PathParam("from") String from,
            @PathParam("to") String to,
            @PathParam("date") String date,
            @PathParam("tickets") int tickets
    ) throws MalformedURLException, ProtocolException, IOException {

        String result = "[";

        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            URL obj = new URL(url + from + "/" + to + "/" + date + "/" + tickets);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            String json = response.toString();

            if (json.startsWith("[") && json.endsWith("]")) {
                json = json.substring(1, json.length() - 1);
//                json.replaceAll("\\[", "").replaceAll("\\]", "");
            }
            if (i < urls.size() - 1) {
                result += json + ",";
            } else {
                result += json;
            }
//            result += json;
        }
        result += "]";
        System.out.println(result);
        return result;
    }

    @GET
    @Path("{from}/{date}/{tickets}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFlightsFrom(
            @PathParam("from") String from,
            @PathParam("date") String date,
            @PathParam("tickets") int tickets
    ) throws MalformedURLException, ProtocolException, IOException {

        String result = "";

        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            URL obj = new URL(url + from + "/" + date + "/" + tickets);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
//            if (i != urls.size() - 1) {
//                result += response.toString() + ",";
//            } else {
//                result += response.toString();
//            }
            result += response.toString();
        }
        System.out.println(result);
//        String url = "http://airline-plaul.rhcloud.com/api/flightinfo/" + from + "/" + to + "/" + date + "/" + tickets;
        //print result
//        System.out.println(response.toString());
//        Gson gson = new Gson();
        return result;
    }
}

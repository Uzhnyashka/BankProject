package com.bankproject.clients;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bobyk on 07/05/16.
 */
public class OrderClientPost {
    public static void main(String[] args) {
        try {

            Client client = Client.create();

            long val = 1346524199000l;
            Date date=new Date(val);
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
            String dateText = df2.format(date);
            System.out.println(dateText);

            WebResource webResource = client
                    .resource("http://localhost:8080/rest/orders/add");

            //String input = "{\"name\":\"Egor\",\"login\":\"EG0R\",\"password\":\"Egor\", \"role\":\"admin\", \"phone\":\"12312\"}";
            String input = "{" +
                    "\"userId\":"+4+"," +
                    "\"cashType\":\"USD\"," +
                    "\"operationType\":\"sell\"," +
                    "\"creationDate\":\""+val+"\","+
                    "\"amount\":"+100+"," +
                    "\"status\":\"publish\"}";


            System.out.println(input);

          /*  ClientResponse response = webResource.type("application/json")
                    .post(ClientResponse.class, input);

            if (response.getStatus() >= 400) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            System.out.println("Output from Server .... \n");
            String output = response.getEntity(String.class);
            System.out.println(output);*/

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}

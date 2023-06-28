package com.kicks.inventory.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kicks.inventory.Shoe;
import com.kicks.inventory.ShoeSale;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class KicksClientImpl implements KicksClient {
    private static final String BASE_URL = "http://localhost:8080"; // Update with your server's URL

    private static KicksClientImpl instance;

    private final CloseableHttpClient httpClient;

    private KicksClientImpl() {
        this.httpClient = HttpClients.createDefault();
    }

    public static KicksClientImpl getInstance() {
        if (instance == null) {
            instance = new KicksClientImpl();
        }
        return instance;
    }

    @Override
    public boolean ping() {
        try {
            HttpGet request = new HttpGet(BASE_URL + "/ping");
            HttpResponse response = httpClient.execute(request);

            // Handle the response
            if (response.getStatusLine().getStatusCode() == 200) {
                return true;
            }
        } catch (IOException e) {
            // Handle the exception
        }

        return false;
    }


    @Override
    public List<Shoe> getShoes() {
        try {
            HttpGet request = new HttpGet(BASE_URL + "/shoes");
            HttpResponse response = httpClient.execute(request);

            // Handle the response and parse the list of shoes
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);
                return  parseResponseToObject(responseBody, new TypeReference<List<Shoe>>() {});
            } else {
                // Handle the error response
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void addShoe(Shoe shoe) {
        try {
            HttpPost request = new HttpPost(BASE_URL + "/shoes");
            StringEntity requestEntity = new StringEntity(
                    objectToJsonString(shoe),
                    ContentType.APPLICATION_JSON
            );
            request.setEntity(requestEntity);

            HttpResponse response = httpClient.execute(request);

            // Handle the response
            if (response.getStatusLine().getStatusCode() == 201) {
                // Shoe added successfully
            } else {
                // Handle the error response
            }
        } catch (IOException e) {
            // Handle the exception
        }
    }

    @Override
    public void addShoeSale(ShoeSale sale) {

        try {
            HttpPost request = new HttpPost(BASE_URL + "/shoes/sell");
            StringEntity requestEntity = new StringEntity(
                    objectToJsonString(sale),
                    ContentType.APPLICATION_JSON
            );
            request.setEntity(requestEntity);

            HttpResponse response = httpClient.execute(request);

            // Handle the response
            if (response.getStatusLine().getStatusCode() == 201) {
                // Shoe added successfully
            } else {
                // Handle the error response
            }
        } catch (IOException e) {
            // Handle the exception
        }

    }


    @Override
    public void updateShoe(Shoe shoe) {
        try {
            HttpPut request = new HttpPut(BASE_URL + "/shoes/" + shoe.getSku());
            StringEntity requestEntity = new StringEntity(
                    objectToJsonString(shoe),
                    ContentType.APPLICATION_JSON
            );
            request.setEntity(requestEntity);

            HttpResponse response = httpClient.execute(request);

            // Handle the response
            if (response.getStatusLine().getStatusCode() == 200) {
                // Shoe updated successfully
            } else {
                // Handle the error response
            }
        } catch (IOException e) {
            // Handle the exception
        }
    }


    @Override
    public Shoe getShoe(String sku) {
        try {
            HttpGet request = new HttpGet(BASE_URL + "/shoes/" + sku);
            HttpResponse response = httpClient.execute(request);

            // Handle the response and parse the Shoe object
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);
                // Parse the responseBody to get the Shoe object
                // Return the Shoe object
            } else {
                // Handle the error response
            }
        } catch (IOException e) {
            // Handle the exception
        }

        return null;
    }

    private <T> T parseResponseToObject(String responseBody, TypeReference<T> typeReference) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(responseBody, typeReference);
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
        }
        return null;
    }

    private String objectToJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // Handle the exception
            e.printStackTrace();
        }
        return "";
    }

}

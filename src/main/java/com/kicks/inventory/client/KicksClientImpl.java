package com.kicks.inventory.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kicks.inventory.dto.Shoe;
import com.kicks.inventory.dto.ShoeSale;
import com.kicks.inventory.dto.Vendor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class KicksClientImpl implements KicksClient {

    private String baseUrl = "";

    private static KicksClientImpl instance;

    private final CloseableHttpClient httpClient;

    private KicksClientImpl() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .build();

        // Create HttpClient with the configured RequestConfig
        this.httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    public static KicksClientImpl getInstance() {
        if (instance == null) {
            instance = new KicksClientImpl();
        }
        return instance;
    }

    private String getBaseUrl(){
        if(baseUrl.isEmpty()) {
            Properties properties = new Properties();
            try (FileInputStream inputStream = new FileInputStream("src/main/resources/application.properties")) {
                properties.load(inputStream);

                String url = properties.getProperty("host.address");
                System.out.println("Base URL: " + url);

                baseUrl = "http://"+url+":8080";

                // Use the baseUrl in your application logic
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return baseUrl;
    }

    @Override
    public boolean ping() {
        try {
            HttpGet request = new HttpGet(getBaseUrl() + "/ping");
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
            HttpGet request = new HttpGet(getBaseUrl() + "/inventory/shoes");
            HttpResponse response = httpClient.execute(request);

            // Handle the response and parse the list of shoes
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);
                List<Shoe> shoes = parseResponseToObject(responseBody, new TypeReference<List<Shoe>>() {});
                EntityUtils.consume(entity);
                return shoes;
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
            HttpPost request = new HttpPost(getBaseUrl() + "/inventory/shoes");
            StringEntity requestEntity = new StringEntity(
                    objectToJsonString(shoe),
                    ContentType.APPLICATION_JSON
            );
            request.setEntity(requestEntity);

            HttpResponse response = httpClient.execute(request);

            // Handle the response
            if (response.getStatusLine().getStatusCode() == 201) {

                HttpEntity entity = response.getEntity();
                EntityUtils.consume(entity);

                checkOperation(shoe);
            } else {
                System.out.println(shoe.getStyleCode() + " was NOT added.");
            }
        } catch (IOException e) {
            // Handle the exception
        }
    }

    @Override
    public void addShoeSale(ShoeSale sale) {

        try {
            HttpPost request = new HttpPost(getBaseUrl() + "/inventory/shoes/sell");
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

            EntityUtils.consume(requestEntity);
        } catch (IOException e) {
            // Handle the exception
        }

    }


    @Override
    public void updateShoe(Shoe shoe) {
        try {
            HttpPut request = new HttpPut(getBaseUrl() + "/inventory/shoes");
            StringEntity requestEntity = new StringEntity(
                    objectToJsonString(shoe),
                    ContentType.APPLICATION_JSON
            );
            request.setEntity(requestEntity);

            HttpResponse response = httpClient.execute(request);

            // Handle the response
            if (response.getStatusLine().getStatusCode() == 200) {
                EntityUtils.consume(response.getEntity());
                checkOperation(shoe);
            } else {
                // Handle the error response
            }
        } catch (IOException e) {
            // Handle the exception
        }
    }

    @Override
    public List<ShoeSale> getShoeSales() {
        try {
            HttpGet request = new HttpGet(getBaseUrl() + "/inventory/shoes/sold");
            HttpResponse response = httpClient.execute(request);

            // Handle the response and parse the list of shoes
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);
                List<ShoeSale> shoes = parseResponseToObject(responseBody, new TypeReference<List<ShoeSale>>() {});
                EntityUtils.consume(entity);
                return shoes;
            } else {
                // Handle the error response
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Shoe getShoe(String sku) {
        try {
            HttpGet request = new HttpGet(getBaseUrl() + "/inventory/shoes/" + sku);
            HttpResponse response = httpClient.execute(request);

            // Handle the response and parse the Shoe object
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);

                if(!responseBody.isEmpty()) {

                    Shoe shoe = parseResponseToObject(responseBody, new TypeReference<Shoe>() {});
                    EntityUtils.consume(entity);
                    return shoe;
                }
            } else {
                // Handle the error response
            }
        } catch (IOException e) {
            // Handle the exception
        }

        return null;
    }

    @Override
    public List<Vendor> getVendors() {
        try {
            HttpGet request = new HttpGet(getBaseUrl() + "/inventory/shoes/vendors");
            HttpResponse response = httpClient.execute(request);

            // Handle the response and parse the Shoe object
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String responseBody = EntityUtils.toString(entity);

                if(!responseBody.isEmpty()) {
                    List<Vendor> vendors = parseResponseToObject(responseBody, new TypeReference<List<Vendor>>() {});
                    EntityUtils.consume(entity);
                    return vendors;
                }
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
            objectMapper.registerModule(new JavaTimeModule());
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
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            // Handle the exception
            e.printStackTrace();
        }
        return "";
    }

    private void checkOperation(Shoe shoe){
        System.out.println("Response came back as success for styleCode: " + shoe.getStyleCode() + " sku: " + shoe.getSku());
        System.out.println("Checking..");
        Shoe shoeInDb = getShoe(shoe.getSku());
        if(null == shoeInDb || !shoeInDb.equals(shoe)) {
            System.out.println("styleCode: " + shoe.getStyleCode() + " sku: " + shoe.getSku() + " was NOT changed in DB.");
        }
        else {
            System.out.println("styleCode: " + shoe.getStyleCode() + " sku: " + shoe.getSku() + " was SUCCESSFULLY changed in DB.");
        }
    }

}

package com.ncr.customerconform.controller;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SpringRestTemplate
{

    public static void main( String[] args )
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType( MediaType.APPLICATION_JSON );
        ObjectMapper mapper = new ObjectMapper();
        TransactionDetail requestJson = new TransactionDetail();

        // TransactionInfoReq response = null;
        String jsonRequest = null;
        String result = "";

        try
        {
            // URL = env.getProperty("payment.url");
            RestTemplate template = new RestTemplate();
            template.getMessageConverters().add( new StringHttpMessageConverter() );
            template.getMessageConverters().add( new MappingJackson2HttpMessageConverter() );
            jsonRequest = mapper.writeValueAsString( requestJson );
            HttpEntity<String> entity = new HttpEntity<String>( jsonRequest, headers );
            /*
             * result = template.postForObject(
             * "http://localhost:8084/CustomerMobileConformation/paymentResource/payNow", entity, String.class );
             * response = mapper.readValue( result, TransactionInfoReq.class ); System.out.println( "Result "
             * +response.getTransactionType() + " " + response.getAmount() );
             */
            ResponseEntity<TransactionDetail> response = null;
            boolean flag = false;
            while(!flag) {
            	response = template.postForEntity("http://localhost:8084/CustomerMobileConformation/paymentResource/payNow", entity,
                        TransactionDetail.class );
            	if( response != null && response.getStatusCode().is2xxSuccessful()){
            		flag = true;
            	}
                Thread.sleep(30000);
            }
//            	ResponseEntity<TransactionDetail> response = template.postForEntity(
//                    "http://localhost:8084/CustomerMobileConformation/paymentResource/payNow", entity,
//                    TransactionDetail.class );
//            	Thread.sleep(30000);
            System.out.println( "Result " + response.getStatusCode().is2xxSuccessful() + " "
                    + response.getBody().getCustomerResponse() + " " + response.getBody().getAmount() );
//            ResponseEntity<TransactionInfoReq> response2 = template.getForEntity(
//                    "http://localhost:8099/CustomerMobileConformation/paymentResource/available-transaction?name=upendra",
//                    TransactionInfoReq.class );

//            System.out.println( "Result " + response2.getStatusCode().is2xxSuccessful() + " "
//                    + response2.getBody().getCustomerResponse() + " " + response2.getBody().getAmount() );

        }
        catch ( Exception e )
        {
            System.out.println( "ERROR : " + e.getMessage() );
        }

    }
}

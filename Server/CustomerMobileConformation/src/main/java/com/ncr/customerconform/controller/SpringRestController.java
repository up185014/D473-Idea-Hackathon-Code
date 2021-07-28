package com.ncr.customerconform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//http://192.168.101.11:8084/CustomerMobileConformation/paymentResource/authorisation
@RestController
@RequestMapping( "/paymentResource" )
public class SpringRestController
{
	public class MonitorObject{
	}
	public class MyWaitNotify{
	  MonitorObject myMonitorObject = new MonitorObject();
	  public void doWait(){
	    synchronized(myMonitorObject){
	      try{
	        myMonitorObject.wait();
	      } catch(InterruptedException e){
	    	  e.printStackTrace();
	      }
	    }
	  }
	  public void doNotify(){
	    synchronized(myMonitorObject){
	      myMonitorObject.notify();
	    }
	  }
	}

	MyWaitNotify nihal_obj = new MyWaitNotify();
	
	TransactionDetail requestJson = null;
	TransactionDetail txnDetails = null;
	 
    @PostMapping( "/payNow" )
    public TransactionDetail payInstant( @RequestBody TransactionDetail transactionInfoReq ) throws InterruptedException 
    {  
    	if( txnDetails == null){
    		txnDetails = new TransactionDetail();
    	}
    	txnDetails.setTransactionID(transactionInfoReq.getTransactionID());
    	txnDetails.setTransactionType(transactionInfoReq.getTransactionType());
//    	txnDetails.setCustomerResponse(transactionInfoReq.getCustomerResponse());
    	txnDetails.setAmount(transactionInfoReq.getAmount());
    	nihal_obj.doWait();
        return requestJson;
    }

//    @PostMapping( "/authorisation" )
//    public AuthorisationResponse Authorisation( @RequestBody AuthroisationInfoReq authorisationInfoReq )
//    {
//    	System.out.println("nihal under authorisation response");
//    	if( requestJson == null){
//    		System.out.println("In a if loop of requestJson - Authorisation");
//    		requestJson = new TransactionDetail();
//    	}
//    	AuthorisationResponse responseJson = new AuthorisationResponse();
//    	System.out.println("nihal " + authorisationInfoReq.getAuthorised());
//    	if ( authorisationInfoReq.getAuthorised()){
//    		System.out.println("nihal ***" + authorisationInfoReq.getAuthorised());
//    		requestJson.setCustomerResponse("true");
//    		responseJson.setEverythingOK(true);
//    	}
//    	else{
//    		System.out.println("nihal ###" + authorisationInfoReq.getAuthorised());
//    		requestJson.setCustomerResponse("false");
//    		responseJson.setEverythingOK(false);
//    	}
//    	nihal_obj.doNotify();
//    	return responseJson;
//    }
    @GetMapping( "/authorisation" )
    public AuthorisationResponse Authorisation( @RequestParam( name = "response", required = false ) String name )
    {
    	System.out.println("nihal under authorisation response");
    	if( requestJson == null){
    		System.out.println("In a if loop of requestJson - Authorisation");
    		requestJson = new TransactionDetail();
    	}
    	AuthorisationResponse responseJson = new AuthorisationResponse();
    	System.out.println("nihal= " + name );
//    	if ( name.equals("true")){
//    		requestJson.setCustomerResponse("true");
    		responseJson.setEverythingOK(true);
//    	}
//    	else{
    		requestJson.setCustomerResponse(name);
    		responseJson.setEverythingOK(false);
//    	}
    	txnDetails = null;
    	nihal_obj.doNotify();
    	return responseJson;
    }
    
    @GetMapping( "/available-transaction" )
    public TransactionDetail availableTransaction( @RequestParam( name = "name", required = false ) String name )
    {
        System.out.println( "response code for customer consent *** name i" + name );
        if( txnDetails != null && txnDetails.getTransactionID() != null){
        	return txnDetails;
        }
        return txnDetails;
    }
    
}


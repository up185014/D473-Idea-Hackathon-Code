package com.ncr.customerconform.controller;

import java.io.Serializable;

public class TransactionDetail implements Serializable
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public String transactionType = "WITHDRAWL";
    public String customerResponse = "true";
    public Double amount = 600.00;
    public Integer transactionID = 4444;

    public Integer getTransactionID()
    {
    	return transactionID;
    }
    
    public void setTransactionID( Integer transactionID )
    {
        this.transactionID = transactionID;
    }
    
    public String getTransactionType()
    {
        return transactionType;
    }

    public void setTransactionType( String transactionType )
    {
        this.transactionType = transactionType;
    }

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount( Double amount )
    {
        this.amount = amount;
    }

    public String getCustomerResponse()
    {
        return customerResponse;
    }

    public void setCustomerResponse( String customerResponse )
    {
        this.customerResponse = customerResponse;
    }

}

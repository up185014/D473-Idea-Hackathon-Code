package com.ncr.customerconform.controller;

import java.io.Serializable;

public class AuthorisationResponse implements Serializable{
	private static final long serialVersionUID = 2L;
    public Boolean everythingOK = true;

    public Boolean getEverythingOK()
    {
    	return everythingOK;
    }
    
    public void setEverythingOK( Boolean everythingOK )
    {
        this.everythingOK = everythingOK;
    }
}

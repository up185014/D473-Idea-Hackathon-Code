package com.ncr.customerconform.controller;

import java.io.Serializable;

public class AuthroisationInfoReq implements Serializable{
	private static final long serialVersionUID = 2L;
    public Boolean auth = true;

    public Boolean getAuthorised()
    {
    	return auth;
    }
    
    public void setAuthorised( Boolean auth )
    {
        this.auth = auth;
    }
}


package com.cs157a.bankingapp;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccountRequest {
    @XmlElement
    public int account_id;
    @XmlElement
    public int customer_id;
    @XmlElement
    public int type;
    @XmlElement
    public double balance;
}
package com.cs157a.bankingapp;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DepositRequest {
    @XmlElement
    public int accountID;
    @XmlElement
    public double amount;
}

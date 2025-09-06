package com.cs157a.bankingapp;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransferRequest {
    @XmlElement
    public int from;
    @XmlElement
    public int to;
    @XmlElement
    public double amount;
}

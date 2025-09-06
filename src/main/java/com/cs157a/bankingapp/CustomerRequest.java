package com.cs157a.bankingapp;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerRequest {
    @XmlElement
    public int customer_id;
    @XmlElement
    public String name;
    @XmlElement
    public String dob;
    @XmlElement
    public String address;
    @XmlElement
    public String phone;
    @XmlElement
    public String email;
    @XmlElement
    public String password;
}
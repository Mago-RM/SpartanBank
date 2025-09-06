package com.cs157a.bankingapp;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PasswordResetRequest {
    @XmlElement
    public String email;
    @XmlElement
    public String oldPassword;
    @XmlElement
    public String newPassword;
}

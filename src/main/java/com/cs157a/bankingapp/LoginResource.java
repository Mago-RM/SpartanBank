package com.cs157a.bankingapp;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.Base64;

@Path("/login")
public class LoginResource {
    @POST
    public Response login(@HeaderParam("Authorization") String token) {
        // Base64 Encoded
        // name@email.com:<SHA256 Hashed Password>

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(token);

        String decodedString = new String(decodedBytes);
        String[] data = decodedString.split(":");

        String email = data[0];
        String password = data[1];

        String auth = DBUtil.authenticate(email, password);

        if (auth.equals("Authentication failed"))
            return Response.status(401).build();

        return Response.ok(Utils.toJSON(auth)).build();
    }
}
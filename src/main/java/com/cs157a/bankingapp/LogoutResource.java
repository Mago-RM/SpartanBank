package com.cs157a.bankingapp;

import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/logout")
public class LogoutResource {
    @POST
    public Response logout(@HeaderParam("Authorization") String token) {
        // invalidate the session token
        int customerID = Integer.parseInt(token.split("Z")[1]);

        DBUtil.invalidateTokens(customerID);

        return Response.ok("Logged out").build();
    }
}

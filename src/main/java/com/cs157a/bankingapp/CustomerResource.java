package com.cs157a.bankingapp;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.print.attribute.standard.Media;

@Path("/customers")
public class CustomerResource {
    @Path("/")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newCustomer(CustomerRequest customer) {
        int customerID = DBUtil.newCustomer(customer.name, customer.dob, customer.address, customer.phone, customer.email, customer.password);

        // open a new checking account with 0 balance when user is created
        DBUtil.newAccount(customerID, 0, 0);

        // automatically login the user
        return Response.ok(Utils.toJSON(DBUtil.authenticate(customer.email, customer.password))).build();
    }

    @Path("/")
    @GET
    public Response getCustomerDetails(@HeaderParam("Authorization") String authToken) {
        // pull the customer details for the logged-in user
        if (DBUtil.validateToken(authToken) == -1)
            return Response.status(401, "Not authenticated").build();

        int customerID = Utils.customerFromToken(authToken);

        return Response.ok(Utils.toJSON(DBUtil.getCustomer(customerID))).build();

    }

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editCustomer(@HeaderParam("Authorization") String authToken, CustomerRequest customer) {
        if (DBUtil.validateToken(authToken) == -1)
            return Response.status(401, "Not authenticated").build();

        int customerID = Utils.customerFromToken(authToken);

        int res = DBUtil.editCustomer(customerID, customer.name, customer.email);

        return Response.ok(Utils.toJSON(res)).build();
    }

    @Path("/reset_password")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response changePassword(PasswordResetRequest passwordResetRequest) {
        boolean res = DBUtil.changePassword(passwordResetRequest.email, passwordResetRequest.oldPassword, passwordResetRequest.newPassword);

        return Response.ok(Utils.toJSON(res)).build();
    }

    @Path("/delete")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAccount(@HeaderParam("Authorization") String authToken) {
        if (DBUtil.validateToken(authToken) == -1)
            return Response.status(401, "Not authenticated").build();

        int customerID = Utils.customerFromToken(authToken);

        DBUtil.deleteCustomer(customerID);

        return Response.ok().build();
    }
}

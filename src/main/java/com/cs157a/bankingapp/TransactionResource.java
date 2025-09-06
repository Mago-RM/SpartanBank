package com.cs157a.bankingapp;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashSet;

@Path("/transactions")
public class TransactionResource {
    @Path("/")
    @GET
    public Response listCustomerTransactions(@HeaderParam("Authorization") String authToken) {
        if (DBUtil.validateToken(authToken) == -1)
            return Response.status(401, "Not authenticated").build();

        int customerID = Utils.customerFromToken(authToken);

        HashSet<DBUtil.Transaction> transactions = DBUtil.getCustomerTransactions(customerID);
        return Response.ok(Utils.toJSON(transactions)).build();
    }
    @Path("/{id}")
    @GET
    public Response listAccountTransactions(@PathParam("id") int accountID, @HeaderParam("Authorization") String authToken) {
        if (DBUtil.validateToken(authToken) == -1)
            return Response.status(401, "Not authenticated").build();

        HashSet<DBUtil.Transaction> transactions = DBUtil.getTransactions(accountID);
        return Response.ok(Utils.toJSON(transactions)).build();
    }

    @Path("/deposit")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deposit(@HeaderParam("Authorization") String authToken, DepositRequest deposit) {
        if (DBUtil.validateToken(authToken) == -1)
            return Response.status(401, "Not authenticated").build();

        DBUtil.deposit(deposit.accountID, deposit.amount);

        return Response.ok().build();
    }

    @Path("/transfer")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response balanceTransfer(@HeaderParam("Authorization") String authToken, TransferRequest transferRequest) {
        if (DBUtil.validateToken(authToken) == -1)
            return Response.status(401, "Not authenticated").build();

        boolean transfer = DBUtil.balanceTransfer(transferRequest.from, transferRequest.to, transferRequest.amount);

        if (!transfer) return Response.status(418).build();

        return Response.ok().build();
    }
}

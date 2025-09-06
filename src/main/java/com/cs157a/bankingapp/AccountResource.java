package com.cs157a.bankingapp;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;

@Path("/accounts")
public class AccountResource {

    @Path("/{id}")
    @GET
    public Response getAccount(@PathParam("id") int accountID, @HeaderParam("Authorization") String authToken) {
        if (DBUtil.validateToken(authToken) == -1)
            return Response.status(401, "Not authenticated").build();

        DBUtil.Account account = DBUtil.getAccount(accountID);
        return Response.ok(Utils.toJSON(account)).build();
    }

    @Path("/{id}")
    @DELETE
    public Response deleteAccount(@PathParam("id") int accountID, @HeaderParam("Authorization") String authToken) {
        if (DBUtil.validateToken(authToken) == -1)
            return Response.status(401, "Not authenticated").build();

        return Response.ok(DBUtil.deleteAccount(accountID)).build();
    }

    @Path("/")
    @GET
    public Response listAccounts(@HeaderParam("Authorization") String authToken) {
        if (DBUtil.validateToken(authToken) == -1)
            return Response.status(401, "Not authenticated").build();

        int customerID = Integer.parseInt(authToken.split("Z")[1]);

        ArrayList<DBUtil.Account> accounts = DBUtil.listAccounts(customerID);
        return Response.ok(Utils.toJSON(accounts)).build();
    }

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newAccount(@HeaderParam("Authorization") String authToken, AccountRequest account) {
        if (DBUtil.validateToken(authToken) == -1)
            return Response.status(401, "Not authenticated").build();

        int customerID = Utils.customerFromToken(authToken);

        // get the checking account
        DBUtil.Account checkingAccount = DBUtil.listAccounts(customerID).get(0);

        // verify the checking account has enough balance
        double checkingBalance = checkingAccount.balance();
        double savingsDeposit = account.balance;

        if (checkingBalance < savingsDeposit) {
            return Response.status(418).build();
        }

        // create with 0 balance as it will be a transaction
        int accountID = DBUtil.newAccount(customerID, 1, 0);

        // transfer the deposit
        DBUtil.balanceTransfer(checkingAccount.accountID(), accountID, savingsDeposit);

        return Response.ok().build();
    }
}

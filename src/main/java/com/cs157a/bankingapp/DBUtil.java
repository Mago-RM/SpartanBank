package com.cs157a.bankingapp;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;

public class DBUtil {
    private static Connection createConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/postgresql");
            return ds.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int getMax(String table, String columnName) {
        String query = String.format("SELECT MAX(%s) FROM %s;", columnName, table);
        try {
            Connection conn = createConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int val = rs.getInt(1);
            rs.close();
            stmt.close();
            conn.close();

            return val;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String authenticate(String email, String password) {
        // given email and (hashed) password, get the customer id, create a new session, and return the token
        String query = String.format("SELECT customer_id FROM customers WHERE email = '%s' AND password = '%s';", email, password);
        int customerID;
        try {
            Connection conn = createConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            customerID = rs.getInt("customer_id");
            rs.close();

            // prevent multiple sessions for the same user
            invalidateTokens(customerID);

            String token = String.format("%s%d", Instant.now().toString(), customerID);
            Timestamp expiration = Timestamp.from(Instant.now().plus(7, ChronoUnit.DAYS));
            String newSessionQuery = String.format("INSERT INTO sessions (token, customer_id, expiration) VALUES ('%s', %d, '%s');", token, customerID, expiration);

            stmt.executeUpdate(newSessionQuery);

            stmt.close();
            conn.close();

            return token;
        } catch (Exception e) {
            e.printStackTrace();
            // Malformed email/password, give authentication failure
            return "Authentication failed";
        }


    }

    public static int validateToken(String token) {
        // validate whether a token is still valid; if it is, return the associated customerid
        String query = String.format("SELECT customer_id, expiration FROM sessions WHERE token = '%s';", token);
        try {
            Connection conn = createConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (!rs.next()) {
                rs.close();
                stmt.close();
                conn.close();
                return -1;
            }

            Timestamp time = rs.getTimestamp("expiration");
            int customerID = rs.getInt("customer_id");
            rs.close();
            stmt.close();

            // if the token is expired, invalidate it and return false
            if (time.before(Timestamp.from(Instant.now()))) {
                stmt = conn.createStatement();
                stmt.executeUpdate("DELETE FROM sessions WHERE token = '%s';");
                stmt.close();
                conn.close();
                System.out.printf("invalidating token %s", token);
                return -1;
            }

            stmt.close();
            conn.close();
            return customerID;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void invalidateTokens(int customerID) {
        // invalidate all tokens for a specified customerID (essentially logout)
        String query = String.format("DELETE FROM sessions WHERE customer_id = '%d';", customerID);
        try {
            Connection conn = createConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            // Malformed email/password, give authentication failure
        }
    }

    public record Account (int accountID, int customerID, int accountType, double balance) {}

    public static ArrayList<Account> listAccounts(int customerID) {
        ArrayList<Account> accounts = new ArrayList<>();
        String query = String.format("SELECT * FROM accounts WHERE customer_id = %d", customerID);
        try {
            Connection conn = createConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int accountID = rs.getInt("account_id");
                int accountType = rs.getInt("type");
                double balance = rs.getDouble("balance");

                Account thisAccount = new Account(accountID, customerID, accountType, balance);
                accounts.add(thisAccount);
            }
            rs.close();
            stmt.close();
            conn.close();
            return accounts;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Account getAccount(int accountID) {
        String query = String.format("SELECT * FROM accounts WHERE account_id = %d", accountID);
        try {
            Connection conn = createConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            int customerID = rs.getInt("customer_id");
            int accountType = rs.getInt("type");
            double balance = rs.getInt("balance");
            Account thisAccount = new Account(accountID, customerID, accountType, balance);

            rs.close();
            stmt.close();
            conn.close();
            return thisAccount;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int newAccount(int customerID, int accountType, double balance) {
        int accountID = getMax("accounts", "account_id") + 1;

        String query = String.format("INSERT INTO accounts (account_id, customer_id, type, balance) VALUES (%d, %d, %d, %f);", accountID, customerID, accountType, balance);
        try {
            Connection conn = createConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            conn.close();
            return accountID;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String deleteAccount(int accountID) {
        String query = String.format("DELETE FROM accounts WHERE account_id = %d;", accountID);
        System.out.println(query);
        try {
            Connection conn = createConnection();
            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);
            stmt.close();
            conn.close();
            if (res == 1) {
                return "Success";
            } else {
                return "Fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public static boolean balanceTransfer(int from, int to, double value) {
        Connection conn = createConnection();
        try {
            // don't commit until after the balance transfer to ensure the reciever isn't credited if the sender would go negative
            conn.setAutoCommit(false);
            Statement updateFrom = conn.createStatement();
            updateFrom.executeUpdate(String.format("UPDATE accounts SET balance = balance - %f WHERE account_id = %d", value, from));

            Statement updateTo = conn.createStatement();
            updateTo.executeUpdate(String.format("UPDATE accounts SET balance = balance + %f WHERE account_id = %d", value, to));

            int transactionID = getMax("transactions", "transaction_id") + 1;

            Date date = new Date(System.currentTimeMillis());

            Statement transaction = conn.createStatement();
            transaction.executeUpdate(String.format("INSERT INTO transactions (transaction_id, from_account, to_account, date, amount, type) VALUES (%d, %d, %d, '%s', %f, 0);", transactionID, from, to, date, value));

            conn.commit();
            transaction.close();
            updateTo.close();
            updateFrom.close();
            conn.close();
            return true;
        } catch (Exception e) {
            try {
                conn.rollback();
                conn.close();
            } catch (SQLException sqlException) {
                System.out.println("Attempting to roll back balance transfer");
                sqlException.printStackTrace();
            }
            e.printStackTrace();
            return false;
        }
    }

    public static int newCustomer(String name, String dob, String address, String phone, String email, String password) {
        int customerID = getMax("customers", "customer_id") + 1;

        Date date_of_birth = Utils.dateFromString(dob);


        String query = String.format("INSERT INTO customers (customer_id, name, dob, address, phone, email, password) VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s');", customerID, name, date_of_birth, address, phone, email, password);
        try {
            Connection conn = createConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            conn.close();
            return customerID;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public record Customer (int customerID, String name, Date dob, String address, String phone, String email) {}

    public static Customer getCustomer(int customerID) {
        String query = String.format("SELECT name, dob, address, phone, email FROM customers WHERE customer_id = %d", customerID);
        try {
            Connection conn = createConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String name = rs.getString("name");
            Date dob = rs.getDate("dob");
            String address = rs.getString("address");
            String phone = rs.getString("phone");
            String email = rs.getString("email");
            Customer thisCustomer = new Customer(customerID, name, dob, address, phone, email);

            rs.close();
            stmt.close();
            conn.close();
            return thisCustomer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int editCustomer(int customerID, String name, String email) {
        String query = String.format("UPDATE customers SET name = '%s', email = '%s' WHERE customer_id = %d;", name, email, customerID);
        try {
            Connection conn = createConnection();
            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);
            stmt.close();
            conn.close();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public record Transaction (int transactionID, int from_account, int to_account, Date date, double amount, int type) {}

    public static HashSet<Transaction> getTransactions(int accountID) {
        String query = String.format("SELECT * FROM transactions WHERE account_id = %d", accountID);
        try {
            Connection conn = createConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            HashSet<Transaction> transactions = new HashSet<>();

            while (rs.next()) {
                int transactionID = rs.getInt("transaction_id");
                Date date = rs.getDate("date");
                double amount = rs.getDouble("amount");
                int type = rs.getInt("type");
                int from_account = rs.getInt("from_account");
                int to_account = rs.getInt("to_account");
                Transaction thisTransaction = new Transaction(transactionID, from_account, to_account, date, amount, type);
                transactions.add(thisTransaction);
            }

            rs.close();
            stmt.close();
            conn.close();
            return transactions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static HashSet<Transaction> getCustomerTransactions(int customer_id) {
        // query the materialized view customer_transactions
        String query = String.format("SELECT * FROM customer_transactions WHERE customer_id = %d", customer_id);
        try {
            Connection conn = createConnection();
            Statement refresh = conn.createStatement();
            refresh.executeUpdate("REFRESH MATERIALIZED VIEW customer_transactions");
            refresh.close();

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            HashSet<Transaction> transactions = new HashSet<>();

            while (rs.next()) {
                int transactionID = rs.getInt("transaction_id");
                Date date = rs.getDate("date");
                double amount = rs.getDouble("amount");
                int type = rs.getInt("type");
                int from_account = rs.getInt("from_account");
                int to_account = rs.getInt("to_account");
                Transaction thisTransaction = new Transaction(transactionID, from_account, to_account, date, amount, type);
                transactions.add(thisTransaction);
            }

            rs.close();
            stmt.close();
            conn.close();
            return transactions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deposit(int accountID, double amount) {
        Connection conn = createConnection();
        try {
            conn.setAutoCommit(false);
            Statement deposit = conn.createStatement();
            deposit.executeUpdate(String.format("UPDATE accounts SET balance = balance + %f WHERE account_id = %d", amount, accountID));

            int transactionID = getMax("transactions", "transaction_id") + 1;
            Date date = new Date(System.currentTimeMillis());

            Statement transaction = conn.createStatement();
            transaction.executeUpdate(String.format("INSERT INTO transactions (transaction_id, from_account, to_account, date, amount, type) VALUES (%d, %d, %d, '%s', %f, 1)", transactionID, accountID, accountID, date, amount));

            conn.commit();
            transaction.close();
            deposit.close();
            conn.close();
        } catch (Exception e) {
            try {
                conn.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
        }
    }

    public static boolean changePassword (String email, String oldPassword, String newPassword) {
        Connection conn = createConnection();
        try {
            Statement checkPassword = conn.createStatement();
            ResultSet res = checkPassword.executeQuery(String.format("SELECT customer_id FROM customers WHERE email = '%s' AND password = '%s';", email, oldPassword));
            if (res.next()) {
                int customerID = res.getInt("customer_id");
                checkPassword.close();

                Statement updatePassword = conn.createStatement();
                int success = updatePassword.executeUpdate(String.format("UPDATE customers SET password = '%s' WHERE email = '%s';", newPassword, email));

                updatePassword.close();
                conn.close();

                // force a relog when new password is made
                invalidateTokens(customerID);

                return success > 0;
            } else {
                checkPassword.close();
                conn.close();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String deleteCustomer(int customerID) {
        // invalidate the tokens
        invalidateTokens(customerID);


        String query = String.format("DELETE FROM customers WHERE customer_id = %d;", customerID);
        System.out.println(query);
        try {
            Connection conn = createConnection();
            Statement stmt = conn.createStatement();
            int res = stmt.executeUpdate(query);
            stmt.close();
            conn.close();
            if (res == 1) {
                return "Success";
            } else {
                return "Fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }
}

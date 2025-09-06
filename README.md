# Project Overview
Spartan Bank is a mock-banking app for CS 157A by Jonathan Etiz and Margarita Rincon. The app includes a variety of features, including:
- User & request authentication
- Balance transfers between personal accounts, or other users
- Mobile deposits
- Password change
- User information changes
- The option to open a savings account
- Customer registration

# Dependencies and Required Software

- The backend is a pseudo-RESTful API served by **Apache Tomcat 11.0.1**
- The frontend is a **Vue.js** app
    - Vue.js requires **node package manager** (npm)
- The database is **PostgreSQL 16.0**

# Installation and Setup Instructions

1. Ensure all dependencies above are installed.
2. Create a PostgreSQL user and database called 'bankingapp' 
3. Navigate to your Apache Tomcat installation directory, then `/conf`
    - Modify `context.xml` to have the following lines of code enclosed in the `<Context> ... </Context>` block:
        - Change `<POSTGRESQL SERVER ADDRESS>` to the IP address of your PostgresQL server
        - Modify the password to whatever you sent when creating the user
        - Alternatively, use the server address `direct.etiz.dev` with the password `cs157a` (this will be taken down after the assignment is graded)
    ```
    <Resource name="jdbc/postgresql" auth="Container" type="javax.sql.DataSource"
          username="bankingapp"
          password="<password>"
          driverClassName="org.postgresql.Driver"
          url="jdbc:postgresql://<POSTGRESQL SERVER ADDRESS>:5432/bankingapp"
          maxTotal="25"
          maxIdle="10"
          validationQuery="select 1" />
    ```
    - Modify `web.xml` to have the following lines of code enclosed in the `<web-app> </web-app>` block:
    ```
    <filter>
        <filter-name>CorsFilter</filter-name>
        <filter-class>org.apache.catalina.filters.CorsFilter</filter-class>
        <init-param>
            <param-name>cors.allowed.origins</param-name>
            <param-value>*</param-value>
        </init-param>
        <init-param>
            <param-name>cors.allowed.methods</param-name>
            <param-value>GET, POST, PUT, DELETE, OPTIONS</param-value>
        </init-param>
        <init-param>
            <param-name>cors.allowed.headers</param-name>
            <param-value>Content-Type, Authorization</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>CorsFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    ```
4. Create the database with the included `create_schema.sql`
5. Initialize data (if wanted) with the included `initialize_data.sql`
6. Run the apache tomcat server with the code contained herein
7. To run the frontend, `cd frontend` and `npm run dev`; this will launch the frontend.

## Test User
The database can be initialized with some dummy data to test functionality; below is user information for a user with transactions and accounts:

Email: john.doe@bank.com
Password: password

The password for all initialized users is `password`, but the above user is the only one guaranteed to have data.
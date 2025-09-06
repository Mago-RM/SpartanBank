--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: bankingapp; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE bankingapp WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';


ALTER DATABASE bankingapp OWNER TO postgres;

\connect bankingapp

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: accounts; Type: TABLE; Schema: public; Owner: bankingapp
--

CREATE TABLE public.accounts (
    account_id integer NOT NULL,
    customer_id integer NOT NULL,
    type integer NOT NULL,
    balance real NOT NULL,
    CONSTRAINT test_balance_positive CHECK ((balance >= (0)::double precision))
);


ALTER TABLE public.accounts OWNER TO bankingapp;

--
-- Name: accounts_id_seq; Type: SEQUENCE; Schema: public; Owner: bankingapp
--

CREATE SEQUENCE public.accounts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.accounts_id_seq OWNER TO bankingapp;

--
-- Name: accounts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: bankingapp
--

ALTER SEQUENCE public.accounts_id_seq OWNED BY public.accounts.account_id;


--
-- Name: transactions; Type: TABLE; Schema: public; Owner: bankingapp
--

CREATE TABLE public.transactions (
    transaction_id integer NOT NULL,
    from_account integer NOT NULL,
    date date NOT NULL,
    amount real NOT NULL,
    type integer NOT NULL,
    to_account integer NOT NULL
);


ALTER TABLE public.transactions OWNER TO bankingapp;

--
-- Name: customer_transactions; Type: MATERIALIZED VIEW; Schema: public; Owner: bankingapp
--

CREATE MATERIALIZED VIEW public.customer_transactions AS
 SELECT a.customer_id,
    t.transaction_id,
    t.from_account,
    t.to_account,
    t.date,
    t.amount,
    t.type
   FROM (public.transactions t
     JOIN public.accounts a ON (((t.from_account = a.account_id) OR (t.to_account = a.account_id))))
  WITH NO DATA;


ALTER MATERIALIZED VIEW public.customer_transactions OWNER TO bankingapp;

--
-- Name: customers; Type: TABLE; Schema: public; Owner: bankingapp
--

CREATE TABLE public.customers (
    customer_id integer NOT NULL,
    name text NOT NULL,
    dob date NOT NULL,
    address text NOT NULL,
    phone text NOT NULL,
    email text NOT NULL,
    password text NOT NULL
);


ALTER TABLE public.customers OWNER TO bankingapp;

--
-- Name: sessions; Type: TABLE; Schema: public; Owner: bankingapp
--

CREATE TABLE public.sessions (
    token text NOT NULL,
    customer_id integer NOT NULL,
    expiration timestamp without time zone NOT NULL
);


ALTER TABLE public.sessions OWNER TO bankingapp;


--
-- Name: accounts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bankingapp
--

SELECT pg_catalog.setval('public.accounts_id_seq', 1, true);


--
-- Name: accounts Accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: bankingapp
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT "Accounts_pkey" PRIMARY KEY (account_id);


--
-- Name: customers Customers_pkey; Type: CONSTRAINT; Schema: public; Owner: bankingapp
--

ALTER TABLE ONLY public.customers
    ADD CONSTRAINT "Customers_pkey" PRIMARY KEY (customer_id);


--
-- Name: sessions Sessions_pkey; Type: CONSTRAINT; Schema: public; Owner: bankingapp
--

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT "Sessions_pkey" PRIMARY KEY (token);


--
-- Name: transactions Transactions_pkey; Type: CONSTRAINT; Schema: public; Owner: bankingapp
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT "Transactions_pkey" PRIMARY KEY (transaction_id);


--
-- Name: sessions CustomerID; Type: FK CONSTRAINT; Schema: public; Owner: bankingapp
--

ALTER TABLE ONLY public.sessions
    ADD CONSTRAINT "CustomerID" FOREIGN KEY (customer_id) REFERENCES public.customers(customer_id);


--
-- Name: accounts CustomerID; Type: FK CONSTRAINT; Schema: public; Owner: bankingapp
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT "CustomerID" FOREIGN KEY (customer_id) REFERENCES public.customers(customer_id) ON DELETE CASCADE;


--
-- Name: transactions from_account; Type: FK CONSTRAINT; Schema: public; Owner: bankingapp
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT from_account FOREIGN KEY (from_account) REFERENCES public.accounts(account_id);


--
-- Name: transactions to_account; Type: FK CONSTRAINT; Schema: public; Owner: bankingapp
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT to_account FOREIGN KEY (to_account) REFERENCES public.accounts(account_id);


--
-- Name: DATABASE bankingapp; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON DATABASE bankingapp TO bankingapp;


--
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: pg_database_owner
--

GRANT ALL ON SCHEMA public TO bankingapp;


--
-- Name: customer_transactions; Type: MATERIALIZED VIEW DATA; Schema: public; Owner: bankingapp
--

REFRESH MATERIALIZED VIEW public.customer_transactions;


--
-- PostgreSQL database dump complete
--


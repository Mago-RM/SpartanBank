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
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: bankingapp
--

INSERT INTO public.customers VALUES (16, 'Jonathan Etiz', '2002-11-04', '15745 Bev Barron Rd, Sonora, CA, US 95370', '6506702655', 'jonathan.etiz@sjsu.edu', '6cf615d5bcaac778352a8f1f3360d23f02f34ec182e259897fd6ce485d7870d4');
INSERT INTO public.customers VALUES (3, 'Jane Doe', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '4088709424', 'jane.doe@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO public.customers VALUES (1, 'John Doe', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '6506702655', 'john.doe@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO public.customers VALUES (2, 'Test Person', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '6506702655', 'test.person@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO public.customers VALUES (4, 'Steve Dowell', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '4088709424', 'steve.dowell@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO public.customers VALUES (5, 'James Powell', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '4088709424', 'james.powell@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO public.customers VALUES (6, 'Walter White', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '4088709424', 'walter.white@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO public.customers VALUES (7, 'Steven McDonald', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '4088709424', 'steven.mcdonald@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO public.customers VALUES (8, 'James Price', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '4088709424', 'james.price@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO public.customers VALUES (9, 'Liam Turner', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '4088709424', 'liam.turner@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO public.customers VALUES (10, 'Cynthia Williams', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '4088709424', 'cynthia.williams@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO public.customers VALUES (11, 'Clarissa Blanc', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '4088709424', 'clarissa.blanc@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO public.customers VALUES (12, 'Dominic Brown', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '4088709424', 'dominic.brown@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO public.customers VALUES (13, 'Rebecca Thompson', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '4088709424', 'rebecca.thompson@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO public.customers VALUES (14, 'Lily Rodriguez', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '4088709424', 'lily.rodriguez@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');
INSERT INTO public.customers VALUES (15, 'Matt Smith', '2024-12-09', '1 Washington Sq, San Jose, CA USA 95112', '4088709424', 'matt.smith@bank.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');


--
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: bankingapp
--

INSERT INTO public.accounts VALUES (1, 1, 0, 2900);
INSERT INTO public.accounts VALUES (17, 16, 0, 200);
INSERT INTO public.accounts VALUES (18, 16, 1, 800);
INSERT INTO public.accounts VALUES (3, 1, 1, 600);
INSERT INTO public.accounts VALUES (2, 2, 1, 1000);
INSERT INTO public.accounts VALUES (4, 3, 0, 500);
INSERT INTO public.accounts VALUES (5, 4, 0, 9204);
INSERT INTO public.accounts VALUES (6, 5, 0, 9128);
INSERT INTO public.accounts VALUES (7, 6, 0, 192);
INSERT INTO public.accounts VALUES (8, 7, 0, 591483);
INSERT INTO public.accounts VALUES (9, 8, 0, 919248);
INSERT INTO public.accounts VALUES (10, 9, 0, 5);
INSERT INTO public.accounts VALUES (11, 10, 0, 942);
INSERT INTO public.accounts VALUES (12, 11, 0, 94);
INSERT INTO public.accounts VALUES (13, 12, 0, 853);
INSERT INTO public.accounts VALUES (14, 13, 0, 402);
INSERT INTO public.accounts VALUES (15, 14, 0, 2);
INSERT INTO public.accounts VALUES (16, 15, 0, 0);

--
-- Data for Name: transactions; Type: TABLE DATA; Schema: public; Owner: bankingapp
--

INSERT INTO public.transactions VALUES (1, 1, '2024-12-09', 500, 0, 2);
INSERT INTO public.transactions VALUES (2, 1, '2024-12-09', 5000, 1, 1);
INSERT INTO public.transactions VALUES (5, 1, '2024-12-09', 100, 0, 3);
INSERT INTO public.transactions VALUES (6, 1, '2024-12-09', 500, 0, 2);
INSERT INTO public.transactions VALUES (7, 3, '2024-12-09', 20, 0, 1);
INSERT INTO public.transactions VALUES (8, 1, '2024-12-09', 90, 0, 5);
INSERT INTO public.transactions VALUES (9, 9, '2024-12-09', 9, 0, 1);
INSERT INTO public.transactions VALUES (10, 5, '2024-12-09', 4.25, 0, 1);
INSERT INTO public.transactions VALUES (11, 7, '2024-12-09', 5.5, 0, 3);
INSERT INTO public.transactions VALUES (12, 13, '2024-12-09', 99.99, 0, 3);
INSERT INTO public.transactions VALUES (13, 1, '2024-12-09', 5, -1, 1);
INSERT INTO public.transactions VALUES (14, 5, '2024-12-09', 9214, 0, 1);
INSERT INTO public.transactions VALUES (15, 3, '2024-12-09', 9, 0, 1);
INSERT INTO public.transactions VALUES (4, 1, '2024-12-09', 500, 1, 1);
INSERT INTO public.transactions VALUES (3, 7, '2024-12-09', 500, 0, 3);
INSERT INTO public.transactions VALUES (16, 17, '2024-12-09', 500, 1, 17);
INSERT INTO public.transactions VALUES (17, 17, '2024-12-09', 100, 0, 18);
INSERT INTO public.transactions VALUES (18, 1, '2024-12-09', 500, 0, 17);
INSERT INTO public.transactions VALUES (19, 17, '2024-12-09', 700, 0, 18);


--
-- Name: accounts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: bankingapp
--

SELECT pg_catalog.setval('public.accounts_id_seq', 1, true);


--
-- PostgreSQL database dump complete
--


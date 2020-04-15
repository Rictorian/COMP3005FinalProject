--
-- PostgreSQL database dump
--

-- Dumped from database version 12.2
-- Dumped by pg_dump version 12.2

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
-- Name: basket; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.basket (
    uid integer NOT NULL,
    isbn character(10) NOT NULL,
    quantity integer
);


ALTER TABLE public.basket OWNER TO postgres;

--
-- Name: book; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book (
    isbn character(10) NOT NULL,
    name character varying(30),
    quantity integer,
    author character varying(30),
    genre character varying(12),
    publisher character varying(30),
    page_count integer,
    rating integer,
    cost numeric(5,2),
    return numeric(5,2)
);


ALTER TABLE public.book OWNER TO postgres;

--
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    uid integer NOT NULL,
    name character varying(30),
    address character varying(40),
    phone_number character(10),
    credit_card_number numeric(16,0)
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- Name: orderbasket; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orderbasket (
    isbn character(10) NOT NULL,
    quantity integer,
    order_number integer NOT NULL
);


ALTER TABLE public.orderbasket OWNER TO postgres;

--
-- Name: orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.orders (
    order_number integer NOT NULL,
    uid integer,
    delivery_date integer,
    address character varying(40),
    credit_card_number numeric(16,0)
);


ALTER TABLE public.orders OWNER TO postgres;

--
-- Name: publisher; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.publisher (
    name character varying(30) NOT NULL,
    address character varying(40),
    email character varying(30),
    banking_account integer
);


ALTER TABLE public.publisher OWNER TO postgres;

--
-- Name: publisherphone; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.publisherphone (
    name character varying(30) NOT NULL,
    phone_number character(10) NOT NULL
);


ALTER TABLE public.publisherphone OWNER TO postgres;

--
-- Name: basket basket_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.basket
    ADD CONSTRAINT basket_pkey PRIMARY KEY (uid, isbn);


--
-- Name: book book_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (isbn);


--
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (uid);


--
-- Name: orderbasket orderbasket_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderbasket
    ADD CONSTRAINT orderbasket_pkey PRIMARY KEY (order_number, isbn);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (order_number);


--
-- Name: publisher publisher_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publisher
    ADD CONSTRAINT publisher_pkey PRIMARY KEY (name);


--
-- Name: publisherphone publisherphone_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publisherphone
    ADD CONSTRAINT publisherphone_pkey PRIMARY KEY (name, phone_number);


--
-- Name: basket basket_uid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.basket
    ADD CONSTRAINT basket_uid_fkey FOREIGN KEY (uid) REFERENCES public.customer(uid);


--
-- Name: book book_publisher_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_publisher_fkey FOREIGN KEY (publisher) REFERENCES public.publisher(name);


--
-- Name: orderbasket orderbasket_isbn_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderbasket
    ADD CONSTRAINT orderbasket_isbn_fkey FOREIGN KEY (isbn) REFERENCES public.book(isbn);


--
-- Name: orderbasket orderbasket_order_number_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orderbasket
    ADD CONSTRAINT orderbasket_order_number_fkey FOREIGN KEY (order_number) REFERENCES public.orders(order_number);


--
-- Name: orders orders_uid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_uid_fkey FOREIGN KEY (uid) REFERENCES public.customer(uid);


--
-- Name: publisherphone publisherphone_name_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.publisherphone
    ADD CONSTRAINT publisherphone_name_fkey FOREIGN KEY (name) REFERENCES public.publisher(name);


--
-- PostgreSQL database dump complete
--


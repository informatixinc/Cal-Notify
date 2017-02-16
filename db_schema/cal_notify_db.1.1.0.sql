--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.5
-- Dumped by pg_dump version 9.5.5

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: contact_request; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE contact_request (
    name text,
    email text,
    message text,
    id integer NOT NULL
);


--
-- Name: contact_request_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE contact_request_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: contact_request_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE contact_request_id_seq OWNED BY contact_request.id;


--
-- Name: user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "user" (
    id integer NOT NULL,
    email text,
    password bytea,
    last_login time with time zone,
    first_name text,
    last_name text,
    phone_number text,
    address_one text,
    address_two text,
    state_id integer,
    city text,
    zip_code text,
    signup_date time with time zone,
    salt bytea
);


--
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE user_id_seq OWNED BY "user".id;


--
-- Name: event; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE event (
    id integer DEFAULT nextval('user_id_seq'::regclass) NOT NULL,
    eventid text NOT NULL,
    infolink text NOT NULL,
    location point NOT NULL,
    expires timestamp with time zone NOT NULL,
    description text NOT NULL,
    type text NOT NULL
);


--
-- Name: notification; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE notification (
    send_time time with time zone,
    expire_time time with time zone,
    info_url text,
    notification_type integer,
    id integer NOT NULL
);


--
-- Name: notification_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE notification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: notification_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE notification_id_seq OWNED BY notification.id;


--
-- Name: notification_settings; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE notification_settings (
    user_id integer NOT NULL,
    sms boolean,
    email boolean,
    push_notification boolean
);


--
-- Name: notification_transmission; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE notification_transmission (
    id integer NOT NULL,
    user_id integer,
    notification_id integer,
    send_time time with time zone,
    transmission_type integer
);


--
-- Name: notification_type; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE notification_type (
    type text,
    icon text,
    id integer NOT NULL
);


--
-- Name: notification_type_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE notification_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: notification_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE notification_type_id_seq OWNED BY notification_type.id;


--
-- Name: sns_token; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE sns_token (
    user_id integer,
    sns_token text,
    sns_type_id integer,
    id integer NOT NULL
);


--
-- Name: sns_token_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sns_token_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: sns_token_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE sns_token_id_seq OWNED BY sns_token.id;


--
-- Name: sns_type; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE sns_type (
    type text,
    id integer NOT NULL
);


--
-- Name: sns_type_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE sns_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: sns_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE sns_type_id_seq OWNED BY sns_type.id;


--
-- Name: transmission_type; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE transmission_type (
    type text,
    id integer NOT NULL
);


--
-- Name: transmission_type_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE transmission_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: transmission_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE transmission_type_id_seq OWNED BY transmission_type.id;


--
-- Name: us_state; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE us_state (
    name text,
    id integer NOT NULL
);


--
-- Name: us_state_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE us_state_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: us_state_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE us_state_id_seq OWNED BY us_state.id;


--
-- Name: user_locations; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE user_locations (
    user_id integer,
    location point,
    id integer NOT NULL
);


--
-- Name: user_locations_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE user_locations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: user_locations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE user_locations_id_seq OWNED BY user_locations.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_request ALTER COLUMN id SET DEFAULT nextval('contact_request_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY notification ALTER COLUMN id SET DEFAULT nextval('notification_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY notification_type ALTER COLUMN id SET DEFAULT nextval('notification_type_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY sns_token ALTER COLUMN id SET DEFAULT nextval('sns_token_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY sns_type ALTER COLUMN id SET DEFAULT nextval('sns_type_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY transmission_type ALTER COLUMN id SET DEFAULT nextval('transmission_type_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY us_state ALTER COLUMN id SET DEFAULT nextval('us_state_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY "user" ALTER COLUMN id SET DEFAULT nextval('user_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_locations ALTER COLUMN id SET DEFAULT nextval('user_locations_id_seq'::regclass);


--
-- Name: pk_contact_request; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY contact_request
    ADD CONSTRAINT pk_contact_request PRIMARY KEY (id);


--
-- Name: pk_event; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY event
    ADD CONSTRAINT pk_event PRIMARY KEY (id);


--
-- Name: pk_notification; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT pk_notification PRIMARY KEY (id);


--
-- Name: pk_notification_settings; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY notification_settings
    ADD CONSTRAINT pk_notification_settings PRIMARY KEY (user_id);


--
-- Name: pk_notification_transmission; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY notification_transmission
    ADD CONSTRAINT pk_notification_transmission PRIMARY KEY (id);


--
-- Name: pk_notification_type; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY notification_type
    ADD CONSTRAINT pk_notification_type PRIMARY KEY (id);


--
-- Name: pk_sns_token; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sns_token
    ADD CONSTRAINT pk_sns_token PRIMARY KEY (id);


--
-- Name: pk_sns_type; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sns_type
    ADD CONSTRAINT pk_sns_type PRIMARY KEY (id);


--
-- Name: pk_transmission_type; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY transmission_type
    ADD CONSTRAINT pk_transmission_type PRIMARY KEY (id);


--
-- Name: pk_us_state; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY us_state
    ADD CONSTRAINT pk_us_state PRIMARY KEY (id);


--
-- Name: pk_user; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT pk_user PRIMARY KEY (id);


--
-- Name: pk_user_location; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_locations
    ADD CONSTRAINT pk_user_location PRIMARY KEY (id);


--
-- Name: uk_user_email; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT uk_user_email UNIQUE (email);


--
-- Name: fk_notification_settings_user; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY notification_settings
    ADD CONSTRAINT fk_notification_settings_user FOREIGN KEY (user_id) REFERENCES "user"(id);


--
-- Name: fk_notification_transmission_notification; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY notification_transmission
    ADD CONSTRAINT fk_notification_transmission_notification FOREIGN KEY (notification_id) REFERENCES notification(id);


--
-- Name: fk_notification_transmission_user; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY notification_transmission
    ADD CONSTRAINT fk_notification_transmission_user FOREIGN KEY (user_id) REFERENCES "user"(id);


--
-- Name: fk_sns_token_sns_type; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sns_token
    ADD CONSTRAINT fk_sns_token_sns_type FOREIGN KEY (sns_type_id) REFERENCES sns_type(id);


--
-- Name: fk_sns_token_user; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY sns_token
    ADD CONSTRAINT fk_sns_token_user FOREIGN KEY (user_id) REFERENCES "user"(id);


--
-- Name: fk_user_locations_user; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_locations
    ADD CONSTRAINT fk_user_locations_user FOREIGN KEY (user_id) REFERENCES "user"(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--


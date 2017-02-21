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
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

create extension cube;
create extension earthdistance;

--
-- Name: account_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE account_type (
    id integer NOT NULL,
    type text
);


ALTER TABLE account_type OWNER TO postgres;

--
-- Name: account_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE account_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE account_type_id_seq OWNER TO postgres;

--
-- Name: account_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE account_type_id_seq OWNED BY account_type.id;


--
-- Name: contact_request; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE contact_request (
    name text,
    email text,
    message text,
    id integer NOT NULL
);


ALTER TABLE contact_request OWNER TO postgres;

--
-- Name: contact_request_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE contact_request_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE contact_request_id_seq OWNER TO postgres;

--
-- Name: contact_request_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE contact_request_id_seq OWNED BY contact_request.id;


--
-- Name: notification; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE notification (
  id integer NOT NULL,
  type_id integer,
  classification_id integer,
  title text,
  image_url text,
  info_url text,
  notification_id text,
  send_time timestamp with time zone,
  expire_time timestamp with time zone,
  location point
);


ALTER TABLE notification OWNER TO postgres;

--
-- Name: notification_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE notification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE notification_id_seq OWNER TO postgres;

--
-- Name: notification_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE notification_id_seq OWNED BY notification.id;


--
-- Name: notification_settings; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE notification_settings (
    user_location_id integer NOT NULL,
    sms boolean,
    email boolean,
    push_notification boolean
);


ALTER TABLE notification_settings OWNER TO postgres;

--
-- Name: notification_transmission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE notification_transmission (
    id integer NOT NULL,
    user_id integer,
    notification_id integer,
    send_time time with time zone,
    transmission_type integer
);


ALTER TABLE notification_transmission OWNER TO postgres;

--
-- Name: notification_classification; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE notification_classification (
    type text,
    icon text,
    id integer NOT NULL
);


ALTER TABLE notification_classification OWNER TO postgres;

--
-- Name: notification_classification_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE notification_classification_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE notification_classification_id_seq OWNER TO postgres;

--
-- Name: notification_classification_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE notification_classification_id_seq OWNED BY notification_classification.id;


--
-- Name: sns_token; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE sns_token (
    user_id integer,
    sns_token text,
    sns_type_id integer,
    id integer NOT NULL
);


ALTER TABLE sns_token OWNER TO postgres;

--
-- Name: sns_token_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sns_token_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sns_token_id_seq OWNER TO postgres;

--
-- Name: sns_token_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE sns_token_id_seq OWNED BY sns_token.id;


--
-- Name: sns_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE sns_type (
    type text,
    id integer NOT NULL
);


ALTER TABLE sns_type OWNER TO postgres;

--
-- Name: sns_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE sns_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE sns_type_id_seq OWNER TO postgres;

--
-- Name: sns_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE sns_type_id_seq OWNED BY sns_type.id;


--
-- Name: transmission_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE transmission_type (
    type text,
    id integer NOT NULL
);


ALTER TABLE transmission_type OWNER TO postgres;

--
-- Name: transmission_type_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE transmission_type_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE transmission_type_id_seq OWNER TO postgres;

--
-- Name: transmission_type_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE transmission_type_id_seq OWNED BY transmission_type.id;


--
-- Name: us_state; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE us_state (
    name text,
    id integer NOT NULL
);


ALTER TABLE us_state OWNER TO postgres;

--
-- Name: us_state_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE us_state_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE us_state_id_seq OWNER TO postgres;

--
-- Name: us_state_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE us_state_id_seq OWNED BY us_state.id;


--
-- Name: user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "user" (
    id integer NOT NULL,
    email text,
    password bytea,
    last_login time with time zone,
    first_name text,
    last_name text,
    phone_number text,
    signup_date time with time zone,
    salt bytea,
    account_type integer
);


ALTER TABLE "user" OWNER TO postgres;

--
-- Name: user_address; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE user_address (
    id integer NOT NULL,
    user_id integer,
    address_one text,
    address_two text,
    state_id integer,
    zip_code text
);


ALTER TABLE user_address OWNER TO postgres;

--
-- Name: user_address_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_address_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_address_id_seq OWNER TO postgres;

--
-- Name: user_address_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_address_id_seq OWNED BY user_address.id;


--
-- Name: user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_id_seq OWNER TO postgres;

--
-- Name: user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_id_seq OWNED BY "user".id;


--
-- Name: user_location; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE user_location (
    user_id integer,
    location point,
    id integer NOT NULL,
    address_id integer
);


ALTER TABLE user_location OWNER TO postgres;

--
-- Name: user_locations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE user_locations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE user_locations_id_seq OWNER TO postgres;

--
-- Name: user_locations_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE user_locations_id_seq OWNED BY user_location.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY account_type ALTER COLUMN id SET DEFAULT nextval('account_type_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY contact_request ALTER COLUMN id SET DEFAULT nextval('contact_request_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notification ALTER COLUMN id SET DEFAULT nextval('notification_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notification_classification ALTER COLUMN id SET DEFAULT nextval('notification_classification_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sns_token ALTER COLUMN id SET DEFAULT nextval('sns_token_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sns_type ALTER COLUMN id SET DEFAULT nextval('sns_type_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transmission_type ALTER COLUMN id SET DEFAULT nextval('transmission_type_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY us_state ALTER COLUMN id SET DEFAULT nextval('us_state_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user" ALTER COLUMN id SET DEFAULT nextval('user_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_address ALTER COLUMN id SET DEFAULT nextval('user_address_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_location ALTER COLUMN id SET DEFAULT nextval('user_locations_id_seq'::regclass);


--
-- Name: pk_account_type; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY account_type
    ADD CONSTRAINT pk_account_type PRIMARY KEY (id);


--
-- Name: pk_contact_request; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY contact_request
    ADD CONSTRAINT pk_contact_request PRIMARY KEY (id);


--
-- Name: pk_notification; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT pk_notification PRIMARY KEY (id);


--
-- Name: pk_notification_settings; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notification_settings
    ADD CONSTRAINT pk_notification_settings PRIMARY KEY (user_location_id);


--
-- Name: pk_notification_transmission; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notification_transmission
    ADD CONSTRAINT pk_notification_transmission PRIMARY KEY (id);


--
-- Name: pk_notification_classification; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notification_classification
    ADD CONSTRAINT pk_notification_classification PRIMARY KEY (id);


--
-- Name: pk_sns_token; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sns_token
    ADD CONSTRAINT pk_sns_token PRIMARY KEY (id);


--
-- Name: pk_sns_type; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sns_type
    ADD CONSTRAINT pk_sns_type PRIMARY KEY (id);


--
-- Name: pk_transmission_type; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY transmission_type
    ADD CONSTRAINT pk_transmission_type PRIMARY KEY (id);


--
-- Name: pk_us_state; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY us_state
    ADD CONSTRAINT pk_us_state PRIMARY KEY (id);


--
-- Name: pk_user; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT pk_user PRIMARY KEY (id);


--
-- Name: pk_user_address; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_address
    ADD CONSTRAINT pk_user_address PRIMARY KEY (id);


--
-- Name: pk_user_location; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_location
    ADD CONSTRAINT pk_user_location PRIMARY KEY (id);


--
-- Name: uk_user_email; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT uk_user_email UNIQUE (email);


--
-- Name: fk_notification_notification_classification; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT fk_notification_notification_classification FOREIGN KEY (classification_id) REFERENCES notification_classification(id);


--
-- Name: fk_notification_settings_user_location; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notification_settings
    ADD CONSTRAINT fk_notification_settings_user_location FOREIGN KEY (user_location_id) REFERENCES user_location(id);


--
-- Name: fk_notification_transmission_notification; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notification_transmission
    ADD CONSTRAINT fk_notification_transmission_notification FOREIGN KEY (notification_id) REFERENCES notification(id);


--
-- Name: fk_notification_transmission_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notification_transmission
    ADD CONSTRAINT fk_notification_transmission_user FOREIGN KEY (user_id) REFERENCES "user"(id);


--
-- Name: fk_sns_token_sns_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sns_token
    ADD CONSTRAINT fk_sns_token_sns_type FOREIGN KEY (sns_type_id) REFERENCES sns_type(id);


--
-- Name: fk_sns_token_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sns_token
    ADD CONSTRAINT fk_sns_token_user FOREIGN KEY (user_id) REFERENCES "user"(id);


--
-- Name: fk_user_account_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT fk_user_account_type FOREIGN KEY (account_type) REFERENCES account_type(id);


--
-- Name: fk_user_address_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_address
    ADD CONSTRAINT fk_user_address_user FOREIGN KEY (user_id) REFERENCES "user"(id);


--
-- Name: fk_user_locations_user; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY user_location
    ADD CONSTRAINT fk_user_locations_user FOREIGN KEY (user_id) REFERENCES "user"(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--


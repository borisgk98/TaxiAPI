--
-- PostgreSQL database dump
--

-- Dumped from database version 10.5 (Debian 10.5-1.pgdg90+1)
-- Dumped by pg_dump version 11.5

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

SET default_with_oids = false;

--
-- Name: auth_service; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.auth_service (
    name character varying(20) NOT NULL,
    id integer NOT NULL
);


ALTER TABLE public.auth_service OWNER TO postgres;

--
-- Name: auth_service_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.auth_service_data (
    id integer NOT NULL,
    auth_service integer,
    social_id character varying(255),
    friends_hash bigint,
    auth_service_user_id character varying(255)
);


ALTER TABLE public.auth_service_data OWNER TO postgres;

--
-- Name: auth_service_data_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.auth_service_data_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_service_data_id_seq OWNER TO postgres;

--
-- Name: auth_service_data_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.auth_service_data_id_seq OWNED BY public.auth_service_data.id;


--
-- Name: auth_service_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.auth_service_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.auth_service_id_seq OWNER TO postgres;

--
-- Name: auth_service_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.auth_service_id_seq OWNED BY public.auth_service.id;


--
-- Name: message; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.message (
    id integer NOT NULL,
    data bytea,
    date timestamp without time zone,
    status integer,
    type integer,
    trip_id integer,
    user_id integer
);


ALTER TABLE public.message OWNER TO postgres;

--
-- Name: message_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.message_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.message_id_seq OWNER TO postgres;

--
-- Name: message_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.message_id_seq OWNED BY public.message.id;


--
-- Name: taxi_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.taxi_user (
    id integer NOT NULL,
    avatar_url character varying(255),
    first_name character varying(255),
    last_name character varying(255)
);


ALTER TABLE public.taxi_user OWNER TO postgres;

--
-- Name: taxi_user_auth_service_data; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.taxi_user_auth_service_data (
    user_id integer NOT NULL,
    auth_service_data_id integer NOT NULL
);


ALTER TABLE public.taxi_user_auth_service_data OWNER TO postgres;

--
-- Name: taxi_user_friends; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.taxi_user_friends (
    user_id integer NOT NULL,
    friend_id integer NOT NULL,
    auth_service integer
);


ALTER TABLE public.taxi_user_friends OWNER TO postgres;

--
-- Name: taxi_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.taxi_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.taxi_user_id_seq OWNER TO postgres;

--
-- Name: taxi_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.taxi_user_id_seq OWNED BY public.taxi_user.id;


--
-- Name: trip; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trip (
    id integer NOT NULL,
    address_from character varying(255),
    address_to character varying(255),
    date timestamp without time zone,
    lat_from double precision,
    lat_to double precision,
    long_from double precision,
    long_to double precision,
    status integer
);


ALTER TABLE public.trip OWNER TO postgres;

--
-- Name: trip_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.trip_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.trip_id_seq OWNER TO postgres;

--
-- Name: trip_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.trip_id_seq OWNED BY public.trip.id;


--
-- Name: trip_users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trip_users (
    trip_id integer NOT NULL,
    user_id integer NOT NULL
);


ALTER TABLE public.trip_users OWNER TO postgres;

--
-- Name: auth_service id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_service ALTER COLUMN id SET DEFAULT nextval('public.auth_service_id_seq'::regclass);


--
-- Name: auth_service_data id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_service_data ALTER COLUMN id SET DEFAULT nextval('public.auth_service_data_id_seq'::regclass);


--
-- Name: message id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.message ALTER COLUMN id SET DEFAULT nextval('public.message_id_seq'::regclass);


--
-- Name: taxi_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.taxi_user ALTER COLUMN id SET DEFAULT nextval('public.taxi_user_id_seq'::regclass);


--
-- Name: trip id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trip ALTER COLUMN id SET DEFAULT nextval('public.trip_id_seq'::regclass);


--
-- Data for Name: auth_service; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.auth_service (name, id) FROM stdin;
FB	2
VK	1
\.


--
-- Data for Name: auth_service_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.auth_service_data (id, auth_service, social_id, friends_hash, auth_service_user_id) FROM stdin;
1	1	100500	0	\N
6	1	Optional(153171206)	\N	\N
2	1	153171206	775690970926602448	\N
\.


--
-- Data for Name: message; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.message (id, data, date, status, type, trip_id, user_id) FROM stdin;
\.


--
-- Data for Name: taxi_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.taxi_user (id, avatar_url, first_name, last_name) FROM stdin;
1	\N	Admin	Adminius
11	https://sun9-23.userapi.com/c836723/v836723063/68018/s5ZhKqSenwE.jpg?ava=1	Александр	Скворцов
12	http://donate.petridish.pw/sites/default/files/stickers/9/4_380.png	Slow	Poke
13	https://cdn.wallpapersafari.com/76/23/t6ciow.png	Pepe	Pepeowich
\.


--
-- Data for Name: taxi_user_auth_service_data; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.taxi_user_auth_service_data (user_id, auth_service_data_id) FROM stdin;
1	1
11	2
\.


--
-- Data for Name: taxi_user_friends; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.taxi_user_friends (user_id, friend_id, auth_service) FROM stdin;
11	13	1
13	11	1
11	1	1
\.


--
-- Data for Name: trip; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.trip (id, address_from, address_to, date, lat_from, lat_to, long_from, long_to, status) FROM stdin;
2	From	To	2019-08-24 16:14:35.403	1	1	1	1	0
3	F1	T1	2019-08-24 16:15:59.479	2	2	2	2	0
5	Портовая улица 15	улица Габдуллы Тукая 62/1	1970-01-19 06:14:18.612	55.771942138671875	55.7785738842814212	49.0972271045045332	49.1136920351772304	2
4	Портовая улица 15	улица Габдуллы Тукая 62/1	1970-01-19 06:14:18.612	55.771942138671875	55.7785738842814212	49.0972271045045332	49.1136920351772304	2
7	Портовая улица 15	улица Габдуллы Тукая 62/1	1970-01-19 06:14:18.612	55.771942138671875	55.7785738842814212	49.0972271045045332	49.1136920351772304	2
6	Портовая улица 15	улица Габдуллы Тукая 62/1	1970-01-19 06:14:18.612	55.771942138671875	55.7785738842814212	49.0972271045045332	49.1136920351772304	2
8	Портовая улица 15	улица Нурсултана Назарбаева 27	1970-01-19 06:38:47.42	55.7718200706235194	55.7743510000000029	49.0971569059645105	49.1415160000000029	2
9	Портовая улица 15	улица Нурсултана Назарбаева 27	1970-01-19 06:38:47.42	55.7718200706235194	55.7743510000000029	49.0971569059645105	49.1415160000000029	2
10	Портовая улица 15	улица Татарстан 49А	1970-01-19 06:14:18.635	55.771881103515625	55.7769595720625802	49.0970265025359325	49.1089237191326617	2
11	Портовая улица 15	улица Татарстан 49А	1970-01-19 06:14:18.635	55.771881103515625	55.7769595720625802	49.0970265025359325	49.1089237191326617	2
12	Портовая улица 15	улица Дурова 18с2	1970-01-19 06:12:52.823	55.771942138671875	55.7785250000000019	49.0971648008647037	37.623700999999997	2
14	Портовая улица 15	улица Дурова 18с2	1970-01-19 06:12:52.823	55.771942138671875	55.7785250000000019	49.0971648008647037	37.623700999999997	2
13	Портовая улица 15	улица Дурова 18с2	1970-01-19 06:12:52.823	55.771942138671875	55.7785250000000019	49.0971648008647037	37.623700999999997	2
15	улица Дулат Али 10	улица Шигабутдина Марджани 16–22	1970-01-19 06:14:21.037	55.7699539999999985	55.7800965938238207	49.1041280000000029	49.1199260578844275	2
16	улица Марселя Салимжанова 3	Портовая улица 15	1970-01-19 03:31:00.012	55.78045654296875	55.7719039999999993	49.1255938434363415	49.0973230000000029	2
17	Петербургская улица 11	улица Островского 	1970-01-19 03:32:26.417	55.7857142406507052	55.7832735646849684	49.1264935579969162	49.1261123444468879	2
1	TestFrom	TestTo	2019-08-24 00:27:34.898	0	0	0	0	2
58	Портовая улица 15	улица Парижской Коммуны 9	1970-01-19 03:55:50.411	55.7718505859375	55.7837988603815376	49.0972406385547444	49.1161487025634926	2
43	Портовая улица 15	улица Карима Тинчурина 	1970-01-19 06:53:44.401	55.771881103515625	55.7778557024878978	49.0971400085269494	49.1044695437517333	2
44	Портовая улица 15	улица Карима Тинчурина 	1970-01-19 06:53:44.401	55.771881103515625	55.7778557024878978	49.0971400085269494	49.1044695437517333	2
45	Портовая улица 15	улица Карима Тинчурина 	1970-01-19 06:53:44.401	55.771881103515625	55.7778557024878978	49.0971400085269494	49.1044695437517333	2
46	Портовая улица 15	улица Девятаева 	1970-01-19 03:54:26.455	55.7718505859375	55.7734040727648761	49.0971569798077709	49.099069031224353	2
47	Портовая улица 15	улица Татарстан 70	1970-01-19 03:54:26.416	55.771881103515625	55.7775264754723921	49.0972178419366116	49.1066631919017524	2
48	Портовая улица 15	улица Карима Тинчурина 3	1970-01-19 04:08:46.81	55.771881103515625	55.7790505110010955	49.0972379414404401	49.1045674766651672	2
57	Портовая улица 15	улица Карима Тинчурина 	1970-01-19 06:53:44.401	55.771881103515625	55.7778557024878978	49.0971400085269494	49.1044695437517333	2
56	Портовая улица 15	улица Карима Тинчурина 	1970-01-19 06:53:44.401	55.771881103515625	55.7778557024878978	49.0971400085269494	49.1044695437517333	2
55	Портовая улица 15	улица Карима Тинчурина 	1970-01-19 06:53:44.401	55.771881103515625	55.7778557024878978	49.0971400085269494	49.1044695437517333	2
54	Портовая улица 15	улица Карима Тинчурина 	1970-01-19 06:53:44.401	55.771881103515625	55.7778557024878978	49.0971400085269494	49.1044695437517333	2
53	Портовая улица 15	улица Карима Тинчурина 	1970-01-19 06:53:44.401	55.771881103515625	55.7778557024878978	49.0971400085269494	49.1044695437517333	2
52	Портовая улица 15	улица Карима Тинчурина 	1970-01-19 06:53:44.401	55.771881103515625	55.7778557024878978	49.0971400085269494	49.1044695437517333	2
51	Портовая улица 15	улица Карима Тинчурина 	1970-01-19 06:53:44.401	55.771881103515625	55.7778557024878978	49.0971400085269494	49.1044695437517333	2
50	Портовая улица 15	улица Карима Тинчурина 	1970-01-19 06:53:44.401	55.771881103515625	55.7778557024878978	49.0971400085269494	49.1044695437517333	2
49	Портовая улица 15	улица Карима Тинчурина 	1970-01-19 03:53:44.401	55.771881103515625	55.7778557024878978	49.0971400085269494	49.1044695437517333	2
59	Портовая улица 15	улица Островского 22	1970-01-19 03:54:27.621	55.771881103515625	55.7873106933130742	49.0972067767181173	49.1166024459975787	0
\.


--
-- Data for Name: trip_users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.trip_users (trip_id, user_id) FROM stdin;
2	12
2	13
3	1
3	12
3	13
59	11
\.


--
-- Name: auth_service_data_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.auth_service_data_id_seq', 6, true);


--
-- Name: auth_service_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.auth_service_id_seq', 1, true);


--
-- Name: message_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.message_id_seq', 1, false);


--
-- Name: taxi_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.taxi_user_id_seq', 17, true);


--
-- Name: trip_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.trip_id_seq', 59, true);


--
-- Name: auth_service_data auth_service_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_service_data
    ADD CONSTRAINT auth_service_data_pkey PRIMARY KEY (id);


--
-- Name: auth_service auth_service_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_service
    ADD CONSTRAINT auth_service_pk PRIMARY KEY (id);


--
-- Name: message message_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT message_pkey PRIMARY KEY (id);


--
-- Name: taxi_user_auth_service_data taxi_user_auth_service_data_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.taxi_user_auth_service_data
    ADD CONSTRAINT taxi_user_auth_service_data_pkey PRIMARY KEY (user_id, auth_service_data_id);


--
-- Name: taxi_user_friends taxi_user_friends_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.taxi_user_friends
    ADD CONSTRAINT taxi_user_friends_pkey PRIMARY KEY (user_id, friend_id);


--
-- Name: taxi_user taxi_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.taxi_user
    ADD CONSTRAINT taxi_user_pkey PRIMARY KEY (id);


--
-- Name: trip trip_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trip
    ADD CONSTRAINT trip_pkey PRIMARY KEY (id);


--
-- Name: trip_users trip_users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trip_users
    ADD CONSTRAINT trip_users_pkey PRIMARY KEY (trip_id, user_id);


--
-- Name: taxi_user_auth_service_data uk_n40sacxmbqtbvq755gnhom80j; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.taxi_user_auth_service_data
    ADD CONSTRAINT uk_n40sacxmbqtbvq755gnhom80j UNIQUE (auth_service_data_id);


--
-- Name: auth_service_id_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX auth_service_id_uindex ON public.auth_service USING btree (id);


--
-- Name: auth_service_name_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX auth_service_name_uindex ON public.auth_service USING btree (name);


--
-- Name: auth_service_data auth_service_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.auth_service_data
    ADD CONSTRAINT auth_service_fk FOREIGN KEY (auth_service) REFERENCES public.auth_service_data(id);


--
-- Name: trip_users fk4sj79nmd5oa17afai1nvmwnp0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trip_users
    ADD CONSTRAINT fk4sj79nmd5oa17afai1nvmwnp0 FOREIGN KEY (trip_id) REFERENCES public.trip(id);


--
-- Name: taxi_user_friends fk7hku0t0w155btdjpgcvfkcacs; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.taxi_user_friends
    ADD CONSTRAINT fk7hku0t0w155btdjpgcvfkcacs FOREIGN KEY (friend_id) REFERENCES public.taxi_user(id);


--
-- Name: taxi_user_friends fkfnphyggnjybfibaeeedy00ro6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.taxi_user_friends
    ADD CONSTRAINT fkfnphyggnjybfibaeeedy00ro6 FOREIGN KEY (user_id) REFERENCES public.taxi_user(id);


--
-- Name: trip_users fkh9nsbujo4dnlybddswws9sy63; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trip_users
    ADD CONSTRAINT fkh9nsbujo4dnlybddswws9sy63 FOREIGN KEY (user_id) REFERENCES public.taxi_user(id);


--
-- Name: message fkhfdmttlu9kw0cr1ff2b7n3s73; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT fkhfdmttlu9kw0cr1ff2b7n3s73 FOREIGN KEY (trip_id) REFERENCES public.trip(id);


--
-- Name: message fkkb7e9ggyhak32p5hg1e65u4a3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT fkkb7e9ggyhak32p5hg1e65u4a3 FOREIGN KEY (user_id) REFERENCES public.taxi_user(id);


--
-- Name: taxi_user_auth_service_data fkl901l3ndkmgn5t6k6bjxh39kt; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.taxi_user_auth_service_data
    ADD CONSTRAINT fkl901l3ndkmgn5t6k6bjxh39kt FOREIGN KEY (auth_service_data_id) REFERENCES public.auth_service_data(id);


--
-- Name: taxi_user_auth_service_data fkntp0ksypdts3oihsn1u6gf5i1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.taxi_user_auth_service_data
    ADD CONSTRAINT fkntp0ksypdts3oihsn1u6gf5i1 FOREIGN KEY (user_id) REFERENCES public.taxi_user(id);


--
-- PostgreSQL database dump complete
--


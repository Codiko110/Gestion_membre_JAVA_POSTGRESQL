--
-- PostgreSQL database dump
--

-- Dumped from database version 16.9
-- Dumped by pg_dump version 16.9

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
-- Name: admin; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.admin (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password character varying(100) NOT NULL
);


ALTER TABLE public.admin OWNER TO postgres;

--
-- Name: admin_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.admin_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.admin_id_seq OWNER TO postgres;

--
-- Name: admin_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.admin_id_seq OWNED BY public.admin.id;


--
-- Name: cotisation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cotisation (
    id integer NOT NULL,
    membre_id integer,
    montant numeric(10,2) NOT NULL,
    date_paiement date DEFAULT CURRENT_DATE,
    periode character varying(20)
);


ALTER TABLE public.cotisation OWNER TO postgres;

--
-- Name: cotisation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cotisation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cotisation_id_seq OWNER TO postgres;

--
-- Name: cotisation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cotisation_id_seq OWNED BY public.cotisation.id;


--
-- Name: membre; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.membre (
    id integer NOT NULL,
    nom character varying(100) NOT NULL,
    prenom character varying(100),
    email character varying(100),
    role character varying(50),
    date_adhesion date DEFAULT CURRENT_DATE,
    statut_cotisation character varying(20) DEFAULT 'non payé'::character varying
);


ALTER TABLE public.membre OWNER TO postgres;

--
-- Name: membre_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.membre_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.membre_id_seq OWNER TO postgres;

--
-- Name: membre_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.membre_id_seq OWNED BY public.membre.id;


--
-- Name: participation; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.participation (
    id integer NOT NULL,
    membre_id integer,
    projet_id integer,
    role_dans_projet character varying(50)
);


ALTER TABLE public.participation OWNER TO postgres;

--
-- Name: participation_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.participation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.participation_id_seq OWNER TO postgres;

--
-- Name: participation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.participation_id_seq OWNED BY public.participation.id;


--
-- Name: projet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.projet (
    id integer NOT NULL,
    titre character varying(150) NOT NULL,
    description text,
    date_debut date,
    date_fin date,
    budget numeric(12,2),
    etat character varying(50) DEFAULT 'en cours'::character varying
);


ALTER TABLE public.projet OWNER TO postgres;

--
-- Name: projet_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.projet_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.projet_id_seq OWNER TO postgres;

--
-- Name: projet_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.projet_id_seq OWNED BY public.projet.id;


--
-- Name: admin id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admin ALTER COLUMN id SET DEFAULT nextval('public.admin_id_seq'::regclass);


--
-- Name: cotisation id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cotisation ALTER COLUMN id SET DEFAULT nextval('public.cotisation_id_seq'::regclass);


--
-- Name: membre id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.membre ALTER COLUMN id SET DEFAULT nextval('public.membre_id_seq'::regclass);


--
-- Name: participation id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation ALTER COLUMN id SET DEFAULT nextval('public.participation_id_seq'::regclass);


--
-- Name: projet id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projet ALTER COLUMN id SET DEFAULT nextval('public.projet_id_seq'::regclass);


--
-- Data for Name: admin; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.admin (id, username, password) FROM stdin;
1	vatosoa	sedera
\.


--
-- Data for Name: cotisation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cotisation (id, membre_id, montant, date_paiement, periode) FROM stdin;
1	1	50000.00	2024-01-10	Janvier 2024
2	2	50000.00	2024-02-15	Février 2024
4	4	50000.00	2024-04-10	Avril 2024
5	5	0.00	\N	Mai 2024
6	6	50000.00	2024-06-18	Juin 2024
7	1	50000.00	2024-09-05	Septembre 2024
8	2	50000.00	2024-09-05	Septembre 2024
9	3	22000.00	2025-11-10	
\.


--
-- Data for Name: membre; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.membre (id, nom, prenom, email, role, date_adhesion, statut_cotisation) FROM stdin;
1	Rakoto	Jean	jean.rakoto@example.com	Président	2023-01-10	payé
2	Rabe	Miora	miora.rabe@example.com	Trésorier	2023-02-15	payé
4	Andry	Hery	hery.andry@example.com	Membre actif	2024-04-10	payé
5	Rasoa	Fanja	fanja.rasoa@example.com	Membre actif	2024-05-05	non payé
6	Raherisoa	Tojo	tojo.raherisoa@example.com	Bénévole	2024-06-18	payé
3	Randria	Tiana	tiana.randria@example.com	Secrétaire	2023-03-20	payé
\.


--
-- Data for Name: participation; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.participation (id, membre_id, projet_id, role_dans_projet) FROM stdin;
1	1	1	Coordinateur
2	2	1	Trésorier
3	3	2	Secrétaire
4	4	2	Participant
5	5	3	Responsable logistique
6	6	4	Formateur
7	2	3	Trésorier
8	1	4	Superviseur
\.


--
-- Data for Name: projet; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.projet (id, titre, description, date_debut, date_fin, budget, etat) FROM stdin;
3	Projet Santé Communautaire	Campagne médicale gratuite pour les enfants.	2025-01-15	2025-05-30	3500000.00	en préparation
4	Projet Informatique Jeunes	Formation gratuite en programmation pour les jeunes.	2025-03-01	2025-08-30	2500000.00	en attente
2	Projet Eau Claire	Installation de puits d’eau dans les villages.	2024-07-01	2024-12-31	5000000.00	en cours
1	Projet Ecole Verte	Reboisement et sensibilisation dans les écoles rurales.	2024-02-01	2024-06-30	200000.00	terminé
\.


--
-- Name: admin_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.admin_id_seq', 1, true);


--
-- Name: cotisation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cotisation_id_seq', 9, true);


--
-- Name: membre_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.membre_id_seq', 6, true);


--
-- Name: participation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.participation_id_seq', 8, true);


--
-- Name: projet_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.projet_id_seq', 4, true);


--
-- Name: admin admin_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admin
    ADD CONSTRAINT admin_pkey PRIMARY KEY (id);


--
-- Name: admin admin_username_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.admin
    ADD CONSTRAINT admin_username_key UNIQUE (username);


--
-- Name: cotisation cotisation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cotisation
    ADD CONSTRAINT cotisation_pkey PRIMARY KEY (id);


--
-- Name: membre membre_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.membre
    ADD CONSTRAINT membre_email_key UNIQUE (email);


--
-- Name: membre membre_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.membre
    ADD CONSTRAINT membre_pkey PRIMARY KEY (id);


--
-- Name: participation participation_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT participation_pkey PRIMARY KEY (id);


--
-- Name: projet projet_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.projet
    ADD CONSTRAINT projet_pkey PRIMARY KEY (id);


--
-- Name: cotisation cotisation_membre_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cotisation
    ADD CONSTRAINT cotisation_membre_id_fkey FOREIGN KEY (membre_id) REFERENCES public.membre(id) ON DELETE CASCADE;


--
-- Name: participation participation_membre_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT participation_membre_id_fkey FOREIGN KEY (membre_id) REFERENCES public.membre(id) ON DELETE CASCADE;


--
-- Name: participation participation_projet_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.participation
    ADD CONSTRAINT participation_projet_id_fkey FOREIGN KEY (projet_id) REFERENCES public.projet(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--


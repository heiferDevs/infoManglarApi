--
-- TOC entry 39 (class 2615 OID 18310)
-- Name: suia_iii; Type: SCHEMA; Schema: -; Owner: app_user
--

CREATE SCHEMA suia_iii;


ALTER SCHEMA suia_iii OWNER TO app_user;


CREATE TABLE suia_iii.roles (
    role_id integer NOT NULL,
    role_description character varying(255),
    role_name character varying(255),
    role_unique boolean,
    role_status boolean,
    role_sistemas character varying(255),
    role_used_authority character varying(255),
    role_allow_administer boolean
);

CREATE SEQUENCE suia_iii.seq_role_id
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE suia_iii.seq_role_id OWNER TO app_user;

--
-- TOC entry 10050 (class 2606 OID 27559)
-- Name: roles pk_roles; Type: CONSTRAINT; Schema: suia_iii; Owner: app_user
--

ALTER TABLE ONLY suia_iii.roles
    ADD CONSTRAINT pk_roles PRIMARY KEY (role_id);
    

--
-- TOC entry 547 (class 1259 OID 20089)
-- Name: users; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    user_name character varying(255) NOT NULL,
    user_password character varying(255),
    user_docu_id character varying(255),
    user_temp_password character varying(255),
    user_token boolean,
    user_creation_user character varying(45),
    user_creation_date timestamp without time zone,
    user_user_update character varying(45),
    user_date_update timestamp without time zone,
    justification_access character varying(255),
    temp_password character varying(255),
    area_id integer,
    user_status boolean,
    user_subrogante boolean,
    user_creator_user character varying(255),
    user_data_complete boolean,
    user_date_expiration timestamp without time zone,
    user_functionary boolean,
    user_edif_id integer,
    user_observations character varying(255),
    user_pin character varying(255),
    peop_id integer,
    user_justification_access character varying(10000),
    user_central_functionary boolean,
    user_is_area_boss boolean,
    user_active_as_facilitator boolean DEFAULT true,
    user_immediate_superior integer,
    user_work_performance_ratio double precision,
    user_renew_user_date timestamp without time zone,
    user_renew_pass_user boolean,
    user_code_capcha character varying(255),
    user_status_capcha boolean,
    user_date_expiration_link timestamp without time zone
)
WITH (autovacuum_enabled='true');


ALTER TABLE public.users OWNER TO app_user;

--
-- TOC entry 17301 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_id IS 'Identificador de la Tablas Usuario';


--
-- TOC entry 17302 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_name; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_name IS 'Nombre del Usuario, coincide con la cedula, ruc o pasaporte';


--
-- TOC entry 17303 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_password; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_password IS 'Clave de ingreso al sistema, encriptada, se utiliza para autenticarse al sistema y en el business central.';


--
-- TOC entry 17304 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_docu_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_docu_id IS 'Identificador del documento cedula.';


--
-- TOC entry 17305 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_temp_password; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_temp_password IS 'Contraseña temporal del usuario cuando se registra.';


--
-- TOC entry 17306 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_token; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_token IS 'Caracteriza si posee un token para la firma digital.';


--
-- TOC entry 17307 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_creation_user; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_creation_user IS 'Usuario que creo el usuario. ';


--
-- TOC entry 17308 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_creation_date; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_creation_date IS 'Fecha de creación del usuario.';


--
-- TOC entry 17309 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_user_update; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_user_update IS 'Usuario que actualizo la base.';


--
-- TOC entry 17310 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_date_update; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_date_update IS 'Fecha en que se creo el usuario. ';


--
-- TOC entry 17311 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.justification_access; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.justification_access IS 'Descripción de porque el usuario necesita acceso a la base.';


--
-- TOC entry 17312 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.temp_password; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.temp_password IS 'Clave temporal para usuario una vez que se registra, (Texto Plano).';


--
-- TOC entry 17313 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.area_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.area_id IS 'Identificador del Area, donde se encuentra ubicado el usuario.';


--
-- TOC entry 17314 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_status; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_status IS 'Estado del usuario, puede estar activado o no,, se toma en cuenta para todas las tareas dentro del sistema.';


--
-- TOC entry 17315 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_subrogante; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_subrogante IS 'Indica si el usuario, esta o no subrigando a otro usuario.';


--
-- TOC entry 17316 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_creator_user; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_creator_user IS 'Usuario que crea el registro';


--
-- TOC entry 17317 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_data_complete; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_data_complete IS 'Referente a si los datos estan completos.';


--
-- TOC entry 17318 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_date_expiration; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_date_expiration IS 'Fecha de expiración de acceso al sistema.';


--
-- TOC entry 17319 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_functionary; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_functionary IS 'Indica si el usuario es funcionario o no.';


--
-- TOC entry 17320 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_observations; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_observations IS 'Observaciones acerca del usuario.';


--
-- TOC entry 17321 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_pin; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_pin IS 'Identificacion Personal del usuario. 
Puede ser el ruc, cedula o pasaporte segun el tipo de persona.';


--
-- TOC entry 17322 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.peop_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.peop_id IS 'Identificador de la persona del Usuario, para personas juridicas es el representante legal.';


--
-- TOC entry 17323 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_justification_access; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_justification_access IS 'Similar a justification access';


--
-- TOC entry 17324 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_central_functionary; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_central_functionary IS 'Significa si el usuario es técnico de planta central.';


--
-- TOC entry 17325 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_active_as_facilitator; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_active_as_facilitator IS 'Usuario configurado como facilitador. ';


--
-- TOC entry 17326 (class 0 OID 0)
-- Dependencies: 547
-- Name: COLUMN users.user_work_performance_ratio; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.users.user_work_performance_ratio IS 'Relación de rendimiento en el trabajo.';

--
-- TOC entry 10232 (class 2606 OID 26695)
-- Name: users uc_user; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uc_user UNIQUE (user_name, user_password);
   
--
-- TOC entry 10236 (class 2606 OID 26707)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);

--
-- TOC entry 10234 (class 2606 OID 26703)
-- Name: users uq_users_index1; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uq_users_index1 UNIQUE (user_name);

--
-- TOC entry 632 (class 1259 OID 20525)
-- Name: geographical_locations; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.geographical_locations (
    gelo_id integer NOT NULL,
    gelo_codification_inec character varying(255),
    gelo_name character varying(255),
    gelo_parent_id integer,
    zone_id integer,
    gelo_status boolean,
    pglo_id integer,
    gelo_region character varying(255),
    area_id integer,
    area_id_municipio integer,
    gelo_observations character varying(1024)
);


ALTER TABLE public.geographical_locations OWNER TO app_user;

--
-- TOC entry 17800 (class 0 OID 0)
-- Dependencies: 632
-- Name: TABLE geographical_locations; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON TABLE public.geographical_locations IS 'Tabla que almacena localidades geográficas del sistema';

--
-- TOC entry 17801 (class 0 OID 0)
-- Dependencies: 632
-- Name: COLUMN geographical_locations.gelo_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.geographical_locations.gelo_id IS 'ID de la localidad geográfica. Clave primaria de la tabla geographical_locations';


--
-- TOC entry 17802 (class 0 OID 0)
-- Dependencies: 632
-- Name: COLUMN geographical_locations.gelo_codification_inec; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.geographical_locations.gelo_codification_inec IS 'Codificación de la localidad geográfica proporcionada por el INCEC';


--
-- TOC entry 17803 (class 0 OID 0)
-- Dependencies: 632
-- Name: COLUMN geographical_locations.gelo_name; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.geographical_locations.gelo_name IS 'Nombre de la Localidad geográfica';


--
-- TOC entry 17804 (class 0 OID 0)
-- Dependencies: 632
-- Name: COLUMN geographical_locations.gelo_parent_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.geographical_locations.gelo_parent_id IS 'Padre de la localidad geográfica en caso de que exista. Clave foranea a la tabla general_catalogs';


--
-- TOC entry 17805 (class 0 OID 0)
-- Dependencies: 632
-- Name: COLUMN geographical_locations.zone_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.geographical_locations.zone_id IS 'Zona de la Localidad geográfica, clave foranea a la tabla zones';


--
-- TOC entry 17806 (class 0 OID 0)
-- Dependencies: 632
-- Name: COLUMN geographical_locations.gelo_status; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.geographical_locations.gelo_status IS 'Estado de la localidad geográfica';


--
-- TOC entry 17807 (class 0 OID 0)
-- Dependencies: 632
-- Name: COLUMN geographical_locations.pglo_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.geographical_locations.pglo_id IS 'Región geográfica Natural de la localidad geográfica, clave foranea a la tabla projects_general_location';


--
-- TOC entry 17808 (class 0 OID 0)
-- Dependencies: 632
-- Name: COLUMN geographical_locations.gelo_region; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.geographical_locations.gelo_region IS 'Región de la localidad geográfica';


--
-- TOC entry 17809 (class 0 OID 0)
-- Dependencies: 632
-- Name: COLUMN geographical_locations.area_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.geographical_locations.area_id IS 'Ente acreditado que atiende a esta parroquia, clave foranea a la tabla areas';

ALTER TABLE ONLY public.geographical_locations
    ADD CONSTRAINT geographical_locations_pkey PRIMARY KEY (gelo_id);
    
   
   
-- Missing Geographical locations data, where are?

--
-- TOC entry 735 (class 1259 OID 20858)
-- Name: treatments_types; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.treatments_types (
    trty_id integer NOT NULL,
    trty_name character varying(255),
    trty_status boolean
);


ALTER TABLE public.treatments_types OWNER TO app_user;

--
-- TOC entry 18020 (class 0 OID 0)
-- Dependencies: 735
-- Name: TABLE treatments_types; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON TABLE public.treatments_types IS 'Tabla que almacena la informacion de los tipos de tratamientos a las personas Sr, Sra....';


--
-- TOC entry 18021 (class 0 OID 0)
-- Dependencies: 735
-- Name: COLUMN treatments_types.trty_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.treatments_types.trty_id IS 'ID del tipo de tratamiento. Clave primaria de la tabla treatments_types';


--
-- TOC entry 18022 (class 0 OID 0)
-- Dependencies: 735
-- Name: COLUMN treatments_types.trty_name; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.treatments_types.trty_name IS 'Nombre del tipo de tratamiento de las personas';


--
-- TOC entry 18023 (class 0 OID 0)
-- Dependencies: 735
-- Name: COLUMN treatments_types.trty_status; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.treatments_types.trty_status IS 'Estado del registro del tipo de tratamiento';

-- TOC entry 10398 (class 2606 OID 26687)
-- Name: treatments_types pk_treatments_types; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.treatments_types
    ADD CONSTRAINT pk_treatments_types PRIMARY KEY (trty_id);


--
-- TOC entry 654 (class 1259 OID 20632)
-- Name: nationalities; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.nationalities (
    nati_id integer NOT NULL,
    nati_description character varying(255),
    nati_status boolean
);


ALTER TABLE public.nationalities OWNER TO app_user;

--
-- TOC entry 17879 (class 0 OID 0)
-- Dependencies: 654
-- Name: TABLE nationalities; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON TABLE public.nationalities IS 'Tabla que almacena nacionalidades';


--
-- TOC entry 17880 (class 0 OID 0)
-- Dependencies: 654
-- Name: COLUMN nationalities.nati_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.nationalities.nati_id IS 'ID de la nacionalidad. Clave primaria de la tabla nationalities';


--
-- TOC entry 17881 (class 0 OID 0)
-- Dependencies: 654
-- Name: COLUMN nationalities.nati_description; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.nationalities.nati_description IS 'Descripción de la nacionalidad';


--
-- TOC entry 17882 (class 0 OID 0)
-- Dependencies: 654
-- Name: COLUMN nationalities.nati_status; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.nationalities.nati_status IS 'Estado de la nacionalidad';

--
-- TOC entry 10363 (class 2606 OID 26653)
-- Name: nationalities nationalities_pkey; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.nationalities
    ADD CONSTRAINT nationalities_pkey PRIMARY KEY (nati_id);
   
--
-- TOC entry 545 (class 1259 OID 20077)
-- Name: people; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.people (
    peop_id integer NOT NULL,
    peop_name character varying(255),
    peop_genre character varying(10),
    peop_title character varying(255),
    trty_id integer,
    peop_status boolean,
    gelo_id integer,
    nati_id integer,
    peop_id_document character varying(255),
    peop_position character varying(255),
    peop_pin character varying(255) NOT NULL,
    user_id integer,
    peop_cambio character varying(20),
    peop_user_create character varying(255),
    peop_date_create timestamp without time zone,
    peop_user_update character varying(255),
    peop_date_update timestamp without time zone,
    peop_ip_create character varying(255),
    peop_ip_update character varying(255),
    peop_observations character varying(1024)
)
WITH (autovacuum_enabled='true');


ALTER TABLE public.people OWNER TO app_user;

--
-- TOC entry 17262 (class 0 OID 0)
-- Dependencies: 545
-- Name: TABLE people; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON TABLE public.people IS 'Tabla que almacena la informacion de personas';


--
-- TOC entry 17263 (class 0 OID 0)
-- Dependencies: 545
-- Name: COLUMN people.peop_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.people.peop_id IS 'ID de la persona. Clave primaria de la tabla people';


--
-- TOC entry 17264 (class 0 OID 0)
-- Dependencies: 545
-- Name: COLUMN people.peop_name; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.people.peop_name IS 'Nombre de la persona';


--
-- TOC entry 17265 (class 0 OID 0)
-- Dependencies: 545
-- Name: COLUMN people.peop_genre; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.people.peop_genre IS 'Género de la Persona';


--
-- TOC entry 17266 (class 0 OID 0)
-- Dependencies: 545
-- Name: COLUMN people.peop_title; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.people.peop_title IS 'Tiulo personal de la persona , puede ser biologo, ingeniero, o cualquiera.';


--
-- TOC entry 17267 (class 0 OID 0)
-- Dependencies: 545
-- Name: COLUMN people.trty_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.people.trty_id IS 'Tipo de tratamientos, Sr. Sra. ect ...';


--
-- TOC entry 17268 (class 0 OID 0)
-- Dependencies: 545
-- Name: COLUMN people.peop_status; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.people.peop_status IS 'Estado del registro de la persona';


--
-- TOC entry 17269 (class 0 OID 0)
-- Dependencies: 545
-- Name: COLUMN people.gelo_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.people.gelo_id IS 'Ubicación geográfica de la persona, clave foranea a la tabla geographical_locations';


--
-- TOC entry 17270 (class 0 OID 0)
-- Dependencies: 545
-- Name: COLUMN people.nati_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.people.nati_id IS 'Nacionalidad de la persona. Clave foranea a la tabla nationalities';


--
-- TOC entry 17271 (class 0 OID 0)
-- Dependencies: 545
-- Name: COLUMN people.peop_id_document; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.people.peop_id_document IS 'ID del documento de la persona';


--
-- TOC entry 17272 (class 0 OID 0)
-- Dependencies: 545
-- Name: COLUMN people.peop_position; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.people.peop_position IS 'Posición o Cargo de la persona.';


--
-- TOC entry 17273 (class 0 OID 0)
-- Dependencies: 545
-- Name: COLUMN people.peop_pin; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.people.peop_pin IS 'Número de Cedula/RUC/Pasaporte';


--
-- TOC entry 17274 (class 0 OID 0)
-- Dependencies: 545
-- Name: COLUMN people.user_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.people.user_id IS 'ID de usuario de la persona. Clave foranea a la tabla users';


--
-- TOC entry 17275 (class 0 OID 0)
-- Dependencies: 545
-- Name: COLUMN people.peop_observations; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.people.peop_observations IS 'Observaciones Persona';

--
-- TOC entry 10221 (class 2606 OID 26673)
-- Name: people pk_people; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.people
    ADD CONSTRAINT pk_people PRIMARY KEY (peop_id);

--
-- TOC entry 722 (class 1259 OID 20799)
-- Name: seq_user_id; Type: SEQUENCE; Schema: public; Owner: app_user
--

CREATE SEQUENCE public.seq_user_id
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_user_id OWNER TO app_user;


--
-- TOC entry 702 (class 1259 OID 20759)
-- Name: seq_peop_id; Type: SEQUENCE; Schema: public; Owner: app_user
--

CREATE SEQUENCE public.seq_peop_id
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_peop_id OWNER TO app_user;

--
-- TOC entry 625 (class 1259 OID 20499)
-- Name: contacts_forms; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.contacts_forms (
    cofo_id integer NOT NULL,
    cofo_name character varying(255),
    cofo_status boolean,
    cofo_order integer
);


ALTER TABLE public.contacts_forms OWNER TO app_user;

--
-- TOC entry 17787 (class 0 OID 0)
-- Dependencies: 625
-- Name: TABLE contacts_forms; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON TABLE public.contacts_forms IS 'Tabla que almacena las formas de contactarse';


--
-- TOC entry 17788 (class 0 OID 0)
-- Dependencies: 625
-- Name: COLUMN contacts_forms.cofo_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.contacts_forms.cofo_id IS 'ID de la forma de contacto. Clave primaria de la tabla contacts_forms';


--
-- TOC entry 17789 (class 0 OID 0)
-- Dependencies: 625
-- Name: COLUMN contacts_forms.cofo_name; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.contacts_forms.cofo_name IS 'Nombre de la forma de contacto';


--
-- TOC entry 17790 (class 0 OID 0)
-- Dependencies: 625
-- Name: COLUMN contacts_forms.cofo_status; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.contacts_forms.cofo_status IS 'Estado de la forma de contacto';


--
-- TOC entry 17791 (class 0 OID 0)
-- Dependencies: 625
-- Name: COLUMN contacts_forms.cofo_order; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.contacts_forms.cofo_order IS 'Orden de la forma de contacto';

--
-- TOC entry 10325 (class 2606 OID 26663)
-- Name: contacts_forms pk_contacts_forms; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.contacts_forms
    ADD CONSTRAINT pk_contacts_forms PRIMARY KEY (cofo_id);
    
--
-- TOC entry 681 (class 1259 OID 20717)
-- Name: seq_cont_id; Type: SEQUENCE; Schema: public; Owner: app_user
--

CREATE SEQUENCE public.seq_cont_id
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.seq_cont_id OWNER TO app_user;

--
-- TOC entry 550 (class 1259 OID 20106)
-- Name: contacts; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.contacts (
    cont_id bigint NOT NULL,
    cont_value character varying(255),
    cofo_id integer,
    pers_id bigint,
    orga_id bigint,
    cont_status boolean,
    hwpl_id integer,
    plant_id bigint,
    cont_user_create character varying(255),
    cont_date_create timestamp without time zone,
    cont_user_update character varying(255),
    cont_date_update timestamp without time zone,
    cont_ip_create character varying(255),
    cont_ip_update character varying(255)
)
WITH (autovacuum_enabled='true');


ALTER TABLE public.contacts OWNER TO app_user;

--
-- TOC entry 17330 (class 0 OID 0)
-- Dependencies: 550
-- Name: TABLE contacts; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON TABLE public.contacts IS 'Tabla que almacena contactos';


--
-- TOC entry 17331 (class 0 OID 0)
-- Dependencies: 550
-- Name: COLUMN contacts.cont_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.contacts.cont_id IS 'ID del contacto. Clave primaria de la tabla contacts';


--
-- TOC entry 17332 (class 0 OID 0)
-- Dependencies: 550
-- Name: COLUMN contacts.cont_value; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.contacts.cont_value IS 'El valor que tiene el contacto dependiendo de la forma de Contacto';


--
-- TOC entry 17333 (class 0 OID 0)
-- Dependencies: 550
-- Name: COLUMN contacts.cofo_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.contacts.cofo_id IS 'Forma de contacto, Clave foranea a la tabla contacts_forms';


--
-- TOC entry 17334 (class 0 OID 0)
-- Dependencies: 550
-- Name: COLUMN contacts.pers_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.contacts.pers_id IS 'Campo indica si el contacto es persona natural, clave foranea a la tabla people';


--
-- TOC entry 17335 (class 0 OID 0)
-- Dependencies: 550
-- Name: COLUMN contacts.orga_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.contacts.orga_id IS 'Campo indica si el contacto es persona juridica, clave foranea a la tabla organizations';


--
-- TOC entry 17336 (class 0 OID 0)
-- Dependencies: 550
-- Name: COLUMN contacts.cont_status; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.contacts.cont_status IS 'Estado del contacto';

--
-- TOC entry 10243 (class 2606 OID 26661)
-- Name: contacts pk_contacts; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.contacts
    ADD CONSTRAINT pk_contacts PRIMARY KEY (cont_id);
    
--
-- TOC entry 1706 (class 1259 OID 24708)
-- Name: seq_rous_id; Type: SEQUENCE; Schema: suia_iii; Owner: app_user
--

CREATE SEQUENCE suia_iii.seq_rous_id
    START WITH 0
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


ALTER TABLE suia_iii.seq_rous_id OWNER TO app_user;

--
-- TOC entry 552 (class 1259 OID 20117)
-- Name: roles_users; Type: TABLE; Schema: suia_iii; Owner: app_user
--

CREATE TABLE suia_iii.roles_users (
    rous_id integer NOT NULL,
    role_id integer,
    user_id integer,
    rous_status boolean,
    rous_description character varying
);


ALTER TABLE suia_iii.roles_users OWNER TO app_user;

--
-- TOC entry 17339 (class 0 OID 0)
-- Dependencies: 552
-- Name: COLUMN roles_users.rous_status; Type: COMMENT; Schema: suia_iii; Owner: app_user
--

COMMENT ON COLUMN suia_iii.roles_users.rous_status IS 'Estado';


--
-- TOC entry 616 (class 1259 OID 20454)
-- Name: areas; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.areas (
    area_id integer NOT NULL,
    area_abbreviation character varying(255),
    area_email character varying(255),
    area_name character varying(255),
    area_creation_user character varying(45),
    area_creation_date timestamp without time zone,
    area_user_update character varying(45),
    area_date_update timestamp without time zone,
    area_parent_id integer,
    gelo_id integer,
    area_status boolean,
    arty_id integer,
    area_creator_user character varying(255),
    area_ente_identification character varying(20),
    entity_type character varying(20),
    area_system character varying(100),
    area_locked boolean DEFAULT false,
    area_habilitar boolean
)
WITH (autovacuum_enabled='true');


ALTER TABLE public.areas OWNER TO app_user;

--
-- TOC entry 544 (class 1259 OID 20071)
-- Name: organizations; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.organizations (
    orga_id integer NOT NULL,
    gelo_id integer,
    tyor_id integer,
    peop_id integer,
    orga_status boolean,
    orga_charge_legal_representative character varying(255),
    orga_name_organization character varying(255),
    orga_state_participation character varying(255),
    orga_ruc character varying(255) NOT NULL,
    org_qualified boolean,
    org_name_comercial character varying(255),
    orga_activity character varying(255),
    orga_user_create character varying(255),
    orga_date_create timestamp without time zone,
    orga_user_update character varying(255),
    orga_date_update timestamp without time zone,
    orga_ip_create character varying(255),
    orga_ip_update character varying(255)
)
WITH (autovacuum_enabled='true');


ALTER TABLE public.organizations OWNER TO app_user;

--
-- TOC entry 17250 (class 0 OID 0)
-- Dependencies: 544
-- Name: TABLE organizations; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON TABLE public.organizations IS 'Tabla que almacena los tipos de organizaciones';


--
-- TOC entry 17251 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.orga_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.orga_id IS 'ID de la organización. Clave primaria de la tabla organizations';


--
-- TOC entry 17252 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.gelo_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.gelo_id IS 'Localidad geográfica de la organización. Clave foranea a la tabla geographical_locations';


--
-- TOC entry 17253 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.tyor_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.tyor_id IS 'Tipo de organización. Clave foranea a la tabla organizations_types';


--
-- TOC entry 17254 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.peop_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.peop_id IS 'Persona de la Organización. Clave foranea a la tabla people';


--
-- TOC entry 17255 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.orga_status; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.orga_status IS 'Estado de la organización';


--
-- TOC entry 17256 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.orga_charge_legal_representative; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.orga_charge_legal_representative IS 'Representante legal de la organización';


--
-- TOC entry 17257 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.orga_name_organization; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.orga_name_organization IS 'Nombre de la organización';


--
-- TOC entry 17258 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.orga_state_participation; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.orga_state_participation IS 'Estado de la organización';


--
-- TOC entry 17259 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.orga_ruc; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.orga_ruc IS 'Ruc de la organización';


--
-- TOC entry 17260 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.org_qualified; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.org_qualified IS 'validar consultor calificado';

--
-- TOC entry 544 (class 1259 OID 20071)
-- Name: organizations; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.organizations (
    orga_id integer NOT NULL,
    gelo_id integer,
    tyor_id integer,
    peop_id integer,
    orga_status boolean,
    orga_charge_legal_representative character varying(255),
    orga_name_organization character varying(255),
    orga_state_participation character varying(255),
    orga_ruc character varying(255) NOT NULL,
    org_qualified boolean,
    org_name_comercial character varying(255),
    orga_activity character varying(255),
    orga_user_create character varying(255),
    orga_date_create timestamp without time zone,
    orga_user_update character varying(255),
    orga_date_update timestamp without time zone,
    orga_ip_create character varying(255),
    orga_ip_update character varying(255)
)
WITH (autovacuum_enabled='true');


ALTER TABLE public.organizations OWNER TO app_user;

--
-- TOC entry 17250 (class 0 OID 0)
-- Dependencies: 544
-- Name: TABLE organizations; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON TABLE public.organizations IS 'Tabla que almacena los tipos de organizaciones';


--
-- TOC entry 17251 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.orga_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.orga_id IS 'ID de la organización. Clave primaria de la tabla organizations';


--
-- TOC entry 17252 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.gelo_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.gelo_id IS 'Localidad geográfica de la organización. Clave foranea a la tabla geographical_locations';


--
-- TOC entry 17253 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.tyor_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.tyor_id IS 'Tipo de organización. Clave foranea a la tabla organizations_types';


--
-- TOC entry 17254 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.peop_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.peop_id IS 'Persona de la Organización. Clave foranea a la tabla people';


--
-- TOC entry 17255 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.orga_status; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.orga_status IS 'Estado de la organización';


--
-- TOC entry 17256 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.orga_charge_legal_representative; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.orga_charge_legal_representative IS 'Representante legal de la organización';


--
-- TOC entry 17257 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.orga_name_organization; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.orga_name_organization IS 'Nombre de la organización';


--
-- TOC entry 17258 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.orga_state_participation; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.orga_state_participation IS 'Estado de la organización';


--
-- TOC entry 17259 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.orga_ruc; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.orga_ruc IS 'Ruc de la organización';


--
-- TOC entry 17260 (class 0 OID 0)
-- Dependencies: 544
-- Name: COLUMN organizations.org_qualified; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations.org_qualified IS 'validar consultor calificado';

--
-- TOC entry 657 (class 1259 OID 20644)
-- Name: organizations_types; Type: TABLE; Schema: public; Owner: app_user
--

CREATE TABLE public.organizations_types (
    orty_id integer NOT NULL,
    orty_name character varying(50),
    orty_status boolean,
    orty_description character varying,
    orty_parent_id integer
);


ALTER TABLE public.organizations_types OWNER TO app_user;

--
-- TOC entry 17886 (class 0 OID 0)
-- Dependencies: 657
-- Name: TABLE organizations_types; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON TABLE public.organizations_types IS 'Tabla que almacena los tipos de organizaciones';


--
-- TOC entry 17887 (class 0 OID 0)
-- Dependencies: 657
-- Name: COLUMN organizations_types.orty_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations_types.orty_id IS 'ID tipo de organización. Clave primaria de la tabla organizations_types';


--
-- TOC entry 17888 (class 0 OID 0)
-- Dependencies: 657
-- Name: COLUMN organizations_types.orty_name; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations_types.orty_name IS 'Nombre del tipo de organización';


--
-- TOC entry 17889 (class 0 OID 0)
-- Dependencies: 657
-- Name: COLUMN organizations_types.orty_status; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations_types.orty_status IS 'Estado del tipo de organización';


--
-- TOC entry 17890 (class 0 OID 0)
-- Dependencies: 657
-- Name: COLUMN organizations_types.orty_description; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations_types.orty_description IS 'Descripción del tipo de organización';


--
-- TOC entry 17891 (class 0 OID 0)
-- Dependencies: 657
-- Name: COLUMN organizations_types.orty_parent_id; Type: COMMENT; Schema: public; Owner: app_user
--

COMMENT ON COLUMN public.organizations_types.orty_parent_id IS 'Padre del tipo de Organización, clave foranea a la tabla organizations_types';

--
-- TOC entry 12091 (class 2606 OID 29338)
-- Name: organizations unique_peop_id; Type: FK CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.organizations
    ADD CONSTRAINT unique_peop_id FOREIGN KEY (peop_id) REFERENCES public.people(peop_id);
   
--
-- TOC entry 10365 (class 2606 OID 26669)
-- Name: organizations_types pk_organization_types; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.organizations_types
    ADD CONSTRAINT pk_organization_types PRIMARY KEY (orty_id);


--
-- TOC entry 10215 (class 2606 OID 26671)
-- Name: organizations pk_organizations; Type: CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.organizations
    ADD CONSTRAINT pk_organizations PRIMARY KEY (orga_id);
   
--
-- TOC entry 12151 (class 2606 OID 29268)
-- Name: organizations_types fk_organization_types_orga_id_organization_types_orga_parent_id; Type: FK CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.organizations_types
    ADD CONSTRAINT fk_organization_types_orga_id_organization_types_orga_parent_id FOREIGN KEY (orty_parent_id) REFERENCES public.organizations_types(orty_id);


--
-- TOC entry 12092 (class 2606 OID 29273)
-- Name: organizations fk_organizations_orga_id_organization_types_orga_id; Type: FK CONSTRAINT; Schema: public; Owner: app_user
--

ALTER TABLE ONLY public.organizations
    ADD CONSTRAINT fk_organizations_orga_id_organization_types_orga_id FOREIGN KEY (tyor_id) REFERENCES public.organizations_types(orty_id);

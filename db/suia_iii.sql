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
-- Name: roles pk_roles; Type: CONSTRAINT; Schema: suia_iii; Owner: postgres
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
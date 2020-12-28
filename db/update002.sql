CREATE TABLE info_manglar.organization_info_forms
(
    organization_info_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    cumple_regla_no_enajenar boolean,
    cumple_regla_no_ampliar boolean,
    cumple_regla_buena_vecindad boolean,
    pescadores_independientes boolean,
    pescadores_de_otras_orgs boolean,
    denuncio_pescadores_no_autorizados boolean,
    todos_recuros_bioacuaticos boolean,
    pesca_fuera_del_manglar boolean,
    actividades_de_cria boolean,
    cumple_normas_cria boolean,
    devuelve_individuos_decomisados boolean,
    actividades_turismo boolean,
    normativa_turistica boolean,
    cuenta_socio_manglar boolean,
    actividades_educacion_ambiental boolean,
    actividades_investigacion boolean,
    cuenta_con_permisos boolean,
    evidencio_muerte_arboles boolean,
    evidencio_muerte_bioacuaticos boolean,
    evidencio_muerte_flora boolean,
    evidencio_muerte_fauna boolean,
    cambios_en_agua boolean,
    cambios_suelo boolean,
    cambios_clima boolean,
    cambios_temperatura boolean,

    cumple_regla_no_enajenar_desc character varying(500),
    cumple_regla_no_ampliar_desc character varying(500),
    cumple_regla_buena_vecindad_desc character varying(500),
    pescadores_independientes_desc character varying(500),
    pescadores_de_otras_orgs_desc character varying(500),
    denuncio_pescadores_no_autorizados_desc character varying(500),
    todos_recuros_bioacuaticos_desc character varying(500),
    pesca_fuera_del_manglar_desc character varying(500),
    actividades_de_cria_desc character varying(500),
    cumple_normas_cria_desc character varying(500),
    devuelve_individuos_decomisados_desc character varying(500),
    actividades_turismo_desc character varying(500),
    normativa_turistica_desc character varying(500),
    cuenta_socio_manglar_desc character varying(500),
    actividades_educacion_ambiental_desc character varying(500),
    actividades_investigacion_desc character varying(500),
    cuenta_con_permisos_desc character varying(500),
    evidencio_muerte_arboles_desc character varying(500),
    evidencio_muerte_bioacuaticos_desc character varying(500),
    evidencio_muerte_flora_desc character varying(500),
    evidencio_muerte_fauna_desc character varying(500),
    cambios_en_agua_desc character varying(500),
    cambios_suelo_desc character varying(500),
    cambios_clima_desc character varying(500),
    cambios_temperatura_desc character varying(500),


    CONSTRAINT pk_organization_info_forms PRIMARY KEY (organization_info_form_id),
    CONSTRAINT fk_organization_info_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.organization_info_forms
    OWNER to app_user;

CREATE INDEX organization_info_form_id ON info_manglar.organization_info_forms USING btree (organization_info_form_id);

CREATE SEQUENCE info_manglar.seq_organization_info_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

ALTER TABLE info_manglar.files_forms ADD COLUMN organization_info_form_id integer;
ALTER TABLE info_manglar.files_forms ADD FOREIGN KEY (organization_info_form_id) REFERENCES info_manglar.organization_info_forms(organization_info_form_id);

ALTER TABLE info_manglar.orgs_mapping ADD COLUMN mapping_date character varying(255);

CREATE TABLE info_manglar.pdf_report_forms
(
    pdf_report_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    is_published boolean,

    start_date character varying(255),
    end_date character varying(255),
    published_date character varying(255),

    CONSTRAINT pk_pdf_report_forms PRIMARY KEY (pdf_report_form_id),
    CONSTRAINT fk_pdf_report_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.pdf_report_forms
    OWNER to app_user;

CREATE INDEX pdf_report_form_id ON info_manglar.pdf_report_forms USING btree (pdf_report_form_id);

CREATE SEQUENCE info_manglar.seq_pdf_report_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

ALTER TABLE info_manglar.files_forms ADD COLUMN pdf_report_form_id integer;
ALTER TABLE info_manglar.files_forms ADD FOREIGN KEY (pdf_report_form_id) REFERENCES info_manglar.pdf_report_forms(pdf_report_form_id);


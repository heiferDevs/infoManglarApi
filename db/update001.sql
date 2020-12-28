ALTER TABLE info_manglar.shell_collection_forms DROP COLUMN collected_minor_count;

ALTER TABLE info_manglar.shell_collection_forms DROP COLUMN returned_no_tall_count;

ALTER TABLE info_manglar.crab_collection_forms DROP COLUMN crab_marketing_count;

ALTER TABLE info_manglar.crab_collection_forms DROP COLUMN collected_minor_count;

ALTER TABLE info_manglar.crab_collection_forms DROP COLUMN returned_no_tall_count;

DROP TABLE info_manglar.sectors_collections_forms;

DROP TABLE info_manglar.sectors_forms;

ALTER TABLE info_manglar.crab_collection_forms ADD COLUMN sector_name character varying(255);
ALTER TABLE info_manglar.crab_collection_forms ADD COLUMN collector_name character varying(255);
ALTER TABLE info_manglar.crab_collection_forms ADD COLUMN collector_date character varying(255);

ALTER TABLE info_manglar.shell_collection_forms ADD COLUMN sector_name character varying(255);
ALTER TABLE info_manglar.shell_collection_forms ADD COLUMN collector_name character varying(255);
ALTER TABLE info_manglar.shell_collection_forms ADD COLUMN collector_date character varying(255);

ALTER TABLE info_manglar.crab_size_forms ADD COLUMN sector_name character varying(255);
ALTER TABLE info_manglar.info_veda_forms ADD COLUMN sector_name character varying(255);
ALTER TABLE info_manglar.shell_size_forms ADD COLUMN sector_name character varying(255);

CREATE TABLE info_manglar.semi_annual_reports_forms
(
    semi_annual_report_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    organization_name character varying(255),
    semi_Annual character varying(255),
    year_report integer,

    CONSTRAINT pk_semi_annual_reports_forms PRIMARY KEY (semi_annual_report_form_id),
    CONSTRAINT fk_semi_annual_reports_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);
ALTER TABLE info_manglar.semi_annual_reports_forms
    OWNER to app_user;
CREATE INDEX semi_annual_report_form_id ON info_manglar.semi_annual_reports_forms USING btree (semi_annual_report_form_id);
CREATE SEQUENCE info_manglar.seq_semi_annual_reports_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

ALTER TABLE info_manglar.files_forms ADD COLUMN semi_annual_report_form_id integer;
ALTER TABLE info_manglar.files_forms ADD FOREIGN KEY (semi_annual_report_form_id) REFERENCES info_manglar.semi_annual_reports_forms(semi_annual_report_form_id);


CREATE TABLE info_manglar.technical_reports_forms
(
    technical_report_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    CONSTRAINT pk_technical_reports_forms PRIMARY KEY (technical_report_form_id),
    CONSTRAINT fk_technical_reports_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);
ALTER TABLE info_manglar.technical_reports_forms
    OWNER to app_user;
CREATE INDEX technical_report_form_id ON info_manglar.technical_reports_forms USING btree (technical_report_form_id);
CREATE SEQUENCE info_manglar.seq_technical_reports_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

ALTER TABLE info_manglar.files_forms ADD COLUMN technical_report_form_id integer;
ALTER TABLE info_manglar.files_forms ADD FOREIGN KEY (technical_report_form_id) REFERENCES info_manglar.technical_reports_forms(technical_report_form_id);


CREATE TABLE info_manglar.fishing_efforts_forms
(
    fishing_effort_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    fishing_effort_date character varying(255),
    active_partners integer,

    CONSTRAINT pk_fishing_efforts_forms PRIMARY KEY (fishing_effort_form_id),
    CONSTRAINT fk_fishing_efforts_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);
ALTER TABLE info_manglar.fishing_efforts_forms
    OWNER to app_user;
CREATE INDEX fishing_effort_form_id ON info_manglar.fishing_efforts_forms USING btree (fishing_effort_form_id);
CREATE SEQUENCE info_manglar.seq_fishing_efforts_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE info_manglar.limits_forms
(
    limits_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    CONSTRAINT pk_limits_forms PRIMARY KEY (limits_form_id),
    CONSTRAINT fk_limits_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);
ALTER TABLE info_manglar.limits_forms
    OWNER to app_user;
CREATE INDEX limits_form_id ON info_manglar.limits_forms USING btree (limits_form_id);
CREATE SEQUENCE info_manglar.seq_limits_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

ALTER TABLE info_manglar.orgs_mapping ADD COLUMN limits_form_id integer;
ALTER TABLE info_manglar.orgs_mapping ADD FOREIGN KEY (limits_form_id) REFERENCES info_manglar.limits_forms(limits_form_id);


CREATE TABLE info_manglar.agreements
(
    agreement_id integer NOT NULL,
    official_docs_form_id integer,
    agreement_type character varying(255),
    start_agreement_date character varying(255),
    end_agreement_date character varying(255),
    CONSTRAINT pk_agreements PRIMARY KEY (agreement_id),
    CONSTRAINT fk_agreements FOREIGN KEY (official_docs_form_id) REFERENCES info_manglar.officials_docs_forms(official_docs_form_id)
);
ALTER TABLE info_manglar.agreements
    OWNER to app_user;
CREATE INDEX agreement_id ON info_manglar.agreements USING btree (agreement_id);
CREATE SEQUENCE info_manglar.seq_agreements
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

ALTER TABLE info_manglar.files_forms ADD COLUMN agreement_id integer;
ALTER TABLE info_manglar.files_forms ADD FOREIGN KEY (agreement_id) REFERENCES info_manglar.agreements(agreement_id);

ALTER TABLE info_manglar.plans_info ADD COLUMN type_info character varying(255);

CREATE TABLE info_manglar.config_forms
(
    config_form_id integer NOT NULL,
    version character varying(255)
);
ALTER TABLE info_manglar.config_forms
    OWNER to app_user;
CREATE INDEX config_form_id ON info_manglar.config_forms USING btree (config_form_id);
CREATE SEQUENCE info_manglar.seq_config_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

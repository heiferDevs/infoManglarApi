
CREATE SCHEMA info_manglar
    AUTHORIZATION postgres;


CREATE TABLE info_manglar.organizations_manglar
(
    organization_manglar_id integer NOT NULL,
    organization_manglar_status boolean,
    organization_manglar_name character varying(255),
    organization_manglar_complete_name character varying(255),
    organization_manglar_type character varying(255),
    CONSTRAINT pk_organizations_manglar PRIMARY KEY (organization_manglar_id)
);

ALTER TABLE info_manglar.organizations_manglar
    OWNER to postgres;

CREATE INDEX organization_manglar_id ON info_manglar.organizations_manglar USING btree (organization_manglar_id);

CREATE SEQUENCE info_manglar.seq_organization_manglar_id
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE info_manglar.organizations_users
(
    orus_id integer NOT NULL,
    orus_status boolean,
    organization_manglar_id integer NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT pk_organizations_users PRIMARY KEY (orus_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id),
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES public.users(user_id)
);

ALTER TABLE info_manglar.organizations_users
    OWNER to postgres;

CREATE INDEX orus_id ON info_manglar.organizations_users USING btree (orus_id);

CREATE SEQUENCE info_manglar.seq_orus_id
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


CREATE TABLE info_manglar.email_notifications
(
    email_notification_id integer NOT NULL,
    email_notification_status boolean,
    email_notification_name character varying(255),
    email_notification_email character varying(255),
    email_notification_provinces character varying(255),
    email_notification_anomalies_types character varying(255),
    CONSTRAINT pk_email_notifications PRIMARY KEY (email_notification_id)
);

ALTER TABLE info_manglar.email_notifications
    OWNER to postgres;

CREATE INDEX email_notification_id ON info_manglar.email_notifications USING btree (email_notification_id);

CREATE SEQUENCE info_manglar.seq_email_notification_id
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


CREATE TABLE info_manglar.allowed_users
(
    allowed_user_id integer NOT NULL,
    allowed_user_status boolean,
    organization_manglar_id integer NOT NULL,
    gelo_id integer NOT NULL,
    role_id integer NOT NULL,
    allowed_user_name character varying(255),
    allowed_user_pin character varying(255),
    CONSTRAINT pk_allowed_users PRIMARY KEY (allowed_user_id),
    CONSTRAINT organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id),
    CONSTRAINT gelo_id FOREIGN KEY (gelo_id) REFERENCES public.geographical_locations(gelo_id),
    CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES suia_iii.roles(role_id)
);

ALTER TABLE info_manglar.allowed_users
    OWNER to postgres;

CREATE INDEX allowed_user_id ON info_manglar.allowed_users USING btree (allowed_user_id);

CREATE SEQUENCE info_manglar.seq_allowed_user_id
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE info_manglar.history_changes
(
    history_change_id integer NOT NULL,
    history_change_status boolean,
    user_id integer NOT NULL,
    history_change_date timestamp without time zone NOT NULL,
    form_id integer NOT NULL,
    form_type character varying(255)  NOT NULL,
    type_change character varying(255),
    user_name character varying(255),
    reason character varying(355),

    CONSTRAINT pk_history_changes PRIMARY KEY (history_change_id)
);

ALTER TABLE info_manglar.history_changes
    OWNER to postgres;

CREATE INDEX history_change_id ON info_manglar.history_changes USING btree (history_change_id);

CREATE SEQUENCE info_manglar.seq_history_changes
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE info_manglar.reforestation_forms
(
    reforestation_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    reforested_area decimal,
    mangle_sembrado_count integer,
    planting_date character varying(255),
    planting_state character varying(255),
    average_state decimal,

    CONSTRAINT pk_reforestation_forms PRIMARY KEY (reforestation_form_id),
    CONSTRAINT fk_reforestation_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.reforestation_forms
    OWNER to postgres;

CREATE INDEX reforestation_form_id ON info_manglar.reforestation_forms USING btree (reforestation_form_id);

CREATE SEQUENCE info_manglar.seq_reforestation_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


CREATE TABLE info_manglar.reforestation_forms_vertices
(
    reforestation_form_id integer NOT NULL,
    vertices character varying(255),

    CONSTRAINT fk_reforestation_forms_vertices FOREIGN KEY (reforestation_form_id) REFERENCES info_manglar.reforestation_forms(reforestation_form_id)
);

ALTER TABLE info_manglar.reforestation_forms_vertices
    OWNER to postgres;


CREATE TABLE info_manglar.reforestation_forms_entities
(
    reforestation_form_id integer NOT NULL,
    entities character varying(255),

    CONSTRAINT fk_reforestation_forms_entities FOREIGN KEY (reforestation_form_id) REFERENCES info_manglar.reforestation_forms(reforestation_form_id)
);

ALTER TABLE info_manglar.reforestation_forms_entities
    OWNER to postgres;


CREATE TABLE info_manglar.management_plan_forms
(
    management_plan_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    CONSTRAINT pk_management_plan_forms PRIMARY KEY (management_plan_form_id),
    CONSTRAINT fk_management_plan_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.management_plan_forms
    OWNER to postgres;

CREATE INDEX management_plan_form_id ON info_manglar.management_plan_forms USING btree (management_plan_form_id);

CREATE SEQUENCE info_manglar.seq_management_plan_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE info_manglar.plans_info
(
    plan_info_id integer NOT NULL,

    info character varying(355),

    management_plan_form_id integer,
    plan_info_parent_id integer,

    CONSTRAINT pk_plans_info PRIMARY KEY (plan_info_id),
    CONSTRAINT fk_plans_info FOREIGN KEY (management_plan_form_id) REFERENCES info_manglar.management_plan_forms(management_plan_form_id)
);

ALTER TABLE info_manglar.plans_info
    OWNER to postgres;

CREATE INDEX plan_info_id ON info_manglar.plans_info USING btree (plan_info_id);

CREATE SEQUENCE info_manglar.seq_plans_info
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE info_manglar.shell_collection_forms
(
    shell_collection_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    shell_period character varying(255),
    hours_worked integer,
    tuberculosas_shells_count integer,
    similis_shells_count integer,
    collected_minor_count integer,
    collected_greater_count integer,
    returned_no_tall_count integer,


    CONSTRAINT pk_shell_collection_forms PRIMARY KEY (shell_collection_form_id),
    CONSTRAINT fk_shell_collection_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.shell_collection_forms
    OWNER to postgres;

CREATE INDEX shell_collection_form_id ON info_manglar.shell_collection_forms USING btree (shell_collection_form_id);

CREATE SEQUENCE info_manglar.seq_shell_collection_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;



CREATE TABLE info_manglar.crab_collection_forms
(
    crab_collection_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    crab_period character varying(255),
    hours_worked integer,
    crab_collected_count integer,
    crab_marketing_count integer,
    quedados_crabs integer,
    females_returned integer,
    collected_minor_count integer,
    collected_greater_count integer,
    returned_no_tall_count integer,

    CONSTRAINT pk_crab_collection_forms PRIMARY KEY (crab_collection_form_id),
    CONSTRAINT fk_crab_collection_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.crab_collection_forms
    OWNER to postgres;

CREATE INDEX crab_collection_form_id ON info_manglar.crab_collection_forms USING btree (crab_collection_form_id);

CREATE SEQUENCE info_manglar.seq_crab_collection_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;



CREATE TABLE info_manglar.control_forms
(
    control_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    responsible_patrullaje character varying(255),
    sector character varying(255),
    start_site character varying(255),
    end_site character varying(255),
    register_details character varying(255),
    route_duration integer,
    route_date character varying(255),
    internal_auditor character varying(255),

    event_exists boolean,
    type character varying(255),
    verification character varying(255),

    CONSTRAINT pk_control_forms PRIMARY KEY (control_form_id),
    CONSTRAINT fk_control_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.control_forms
    OWNER to postgres;

CREATE INDEX control_form_id ON info_manglar.control_forms USING btree (control_form_id);

CREATE SEQUENCE info_manglar.seq_control_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


CREATE TABLE info_manglar.social_indicators_forms
(
    social_indicators_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    count_family_members integer,
    count_manglar_dependents integer,
    income_percentage decimal,
    nivel character varying(255),
    preparation character varying(500),

    CONSTRAINT pk_social_indicators_forms PRIMARY KEY (social_indicators_form_id),
    CONSTRAINT fk_social_indicators_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.social_indicators_forms
    OWNER to postgres;

CREATE INDEX social_indicators_form_id ON info_manglar.social_indicators_forms USING btree (social_indicators_form_id);

CREATE SEQUENCE info_manglar.seq_social_indicators_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE info_manglar.economic_indicators_forms
(
    economic_indicators_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    principal_activity character varying(255),
    average_income character varying(255),
    alternative_activities character varying(255),
    averageincome_alternatives character varying(255),

    CONSTRAINT pk_economic_indicators_forms PRIMARY KEY (economic_indicators_form_id),
    CONSTRAINT fk_economic_indicators_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.economic_indicators_forms
    OWNER to postgres;

CREATE INDEX economic_indicators_form_id ON info_manglar.economic_indicators_forms USING btree (economic_indicators_form_id);

CREATE SEQUENCE info_manglar.seq_economic_indicators_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE info_manglar.deforestation_forms
(
    deforestation_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    probable_cause character varying(255),
    deforested_area character varying(255),
    custody_area character varying(255),
    protected_area character varying(255),
    location character varying(255),
    latlong character varying(255),
    address character varying(255),
    estuary character varying(255),
    community character varying(255),

    CONSTRAINT pk_deforestation_forms PRIMARY KEY (deforestation_form_id),
    CONSTRAINT fk_deforestation_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.deforestation_forms
    OWNER to postgres;

CREATE INDEX deforestation_form_id ON info_manglar.deforestation_forms USING btree (deforestation_form_id);

CREATE SEQUENCE info_manglar.seq_deforestation_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;



CREATE TABLE info_manglar.crab_size_forms
(
    crab_size_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    cangrejero_name character varying(255),
    sampling_date character varying(255),
    crab_count integer,

    CONSTRAINT pk_crab_size_forms PRIMARY KEY (crab_size_form_id),
    CONSTRAINT fk_crab_size_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.crab_size_forms
    OWNER to postgres;

CREATE INDEX crab_size_form_id ON info_manglar.crab_size_forms USING btree (crab_size_form_id);

CREATE SEQUENCE info_manglar.seq_crab_size_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;





CREATE TABLE info_manglar.shell_size_forms
(
    shell_size_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    conchero_name character varying(255),
    sampling_date character varying(255),
    shell_count integer,

    CONSTRAINT pk_shell_size_forms PRIMARY KEY (shell_size_form_id),
    CONSTRAINT fk_shell_size_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.shell_size_forms
    OWNER to postgres;

CREATE INDEX shell_size_form_id ON info_manglar.shell_size_forms USING btree (shell_size_form_id);

CREATE SEQUENCE info_manglar.seq_shell_size_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;







CREATE TABLE info_manglar.info_veda_forms
(
    info_veda_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    information_gathering character varying(255),
    event_type character varying(255),
    muda_features character varying(255),
    observations character varying(255),

    CONSTRAINT pk_info_veda_forms PRIMARY KEY (info_veda_form_id),
    CONSTRAINT fk_info_veda_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.info_veda_forms
    OWNER to postgres;

CREATE INDEX info_veda_form_id ON info_manglar.info_veda_forms USING btree (info_veda_form_id);

CREATE SEQUENCE info_manglar.seq_info_veda_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;



CREATE TABLE info_manglar.investments_orgs_forms
(
    investments_orgs_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    surveillance_control decimal,
    sustainable_use decimal,
    administratives_expenses decimal,
    social_activities decimal,
    monitoring decimal,
    capacitation decimal,
    reforestation decimal,
    infrastructure decimal,

    CONSTRAINT pk_investments_orgs_forms PRIMARY KEY (investments_orgs_form_id),
    CONSTRAINT fk_investments_orgs_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.investments_orgs_forms
    OWNER to postgres;

CREATE INDEX investments_orgs_form_id ON info_manglar.investments_orgs_forms USING btree (investments_orgs_form_id);

CREATE SEQUENCE info_manglar.seq_investments_orgs_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE info_manglar.desc_projects_forms
(
    desc_projects_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    project_type character varying(255),
    project_name character varying(255),
    project_objective character varying(255),
    institution_type character varying(255),
    institutions_name character varying(255),
    budget decimal,

    CONSTRAINT pk_desc_projects_forms PRIMARY KEY (desc_projects_form_id),
    CONSTRAINT fk_desc_projects_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.desc_projects_forms
    OWNER to postgres;

CREATE INDEX desc_projects_form_id ON info_manglar.desc_projects_forms USING btree (desc_projects_form_id);

CREATE SEQUENCE info_manglar.seq_desc_projects_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


CREATE TABLE info_manglar.documents_projects
(
    document_project_id integer NOT NULL,

    support_type character varying(255),

    desc_projects_form_id integer,

    CONSTRAINT pk_documents_projects PRIMARY KEY (document_project_id),
    CONSTRAINT fk_documents_projects FOREIGN KEY (desc_projects_form_id) REFERENCES info_manglar.desc_projects_forms(desc_projects_form_id)
);

ALTER TABLE info_manglar.documents_projects
    OWNER to postgres;

CREATE INDEX document_project_id ON info_manglar.documents_projects USING btree (document_project_id);

CREATE SEQUENCE info_manglar.seq_documents_projects
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


CREATE TABLE info_manglar.evidence_forms
(
    evidence_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    activity character varying(255),
    activity_date character varying(255),
    activity_desc character varying(255),
    activity_results character varying(255),

    CONSTRAINT pk_evidence_forms PRIMARY KEY (evidence_form_id),
    CONSTRAINT fk_evidence_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.evidence_forms
    OWNER to postgres;

CREATE INDEX evidence_form_id ON info_manglar.evidence_forms USING btree (evidence_form_id);

CREATE SEQUENCE info_manglar.seq_evidence_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE info_manglar.evidence_activities
(
    evidence_activity_id integer NOT NULL,

    evidence_type character varying(255),

    evidence_form_id integer,

    CONSTRAINT pk_evidence_activities PRIMARY KEY (evidence_activity_id),
    CONSTRAINT fk_evidence_formss FOREIGN KEY (evidence_form_id) REFERENCES info_manglar.evidence_forms(evidence_form_id)
);

ALTER TABLE info_manglar.evidence_activities
    OWNER to postgres;

CREATE INDEX evidence_activity_id ON info_manglar.evidence_activities USING btree (evidence_activity_id);

CREATE SEQUENCE info_manglar.seq_evidence_activities
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


CREATE TABLE info_manglar.plan_tracking_forms
(
    plan_tracking_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    CONSTRAINT pk_plan_tracking_forms PRIMARY KEY (plan_tracking_form_id),
    CONSTRAINT fk_plan_tracking_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.plan_tracking_forms
    OWNER to postgres;

CREATE INDEX plan_tracking_form_id ON info_manglar.plan_tracking_forms USING btree (plan_tracking_form_id);

CREATE SEQUENCE info_manglar.seq_plan_tracking_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE info_manglar.planned_activities
(
    planned_activity_id integer NOT NULL,

    activity_description character varying(255),
    average_done decimal,

    plan_tracking_form_id integer,

    CONSTRAINT pk_planned_activities PRIMARY KEY (planned_activity_id),
    CONSTRAINT fk_planned_activities FOREIGN KEY (plan_tracking_form_id) REFERENCES info_manglar.plan_tracking_forms(plan_tracking_form_id)
);

ALTER TABLE info_manglar.planned_activities
    OWNER to postgres;

CREATE INDEX planned_activity_id ON info_manglar.planned_activities USING btree (planned_activity_id);

CREATE SEQUENCE info_manglar.seq_planned_activities
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


CREATE TABLE info_manglar.prices_forms
(
    prices_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    name character varying(255),
    mobile character varying(255),
    address character varying(255),

    CONSTRAINT pk_prices_forms PRIMARY KEY (prices_form_id),
    CONSTRAINT fk_prices_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.prices_forms
    OWNER to postgres;

CREATE INDEX prices_form_id ON info_manglar.prices_forms USING btree (prices_form_id);

CREATE SEQUENCE info_manglar.seq_prices_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;



CREATE TABLE info_manglar.prices_dailies
(
    price_daily_id integer NOT NULL,
    product_type character varying(255),
    bio_aquatic_type character varying(255),
    shell_quality character varying(255),
    bio_aquatic_price decimal,
    other_products_price decimal,
    shell_count integer,
    sarts_count integer,
    miel_mangle_count integer,
    craft_count integer,
    plantas_mangle_count integer,
    shell_pulp_pounds decimal,
    crab_pulp_pounds decimal,
    crab_quality character varying(255),
    crab_observations character varying(255),
    other_products character varying(255),
    craft_name character varying(255),
    service_type character varying(255),
    bioemprendimiento_name character varying(255),

    prices_form_id integer,

    CONSTRAINT pk_prices_dailies PRIMARY KEY (price_daily_id),
    CONSTRAINT fk_prices_dailies FOREIGN KEY (prices_form_id) REFERENCES info_manglar.prices_forms(prices_form_id)
);

ALTER TABLE info_manglar.prices_dailies
    OWNER to postgres;

CREATE INDEX price_daily_id ON info_manglar.prices_dailies USING btree (price_daily_id);

CREATE SEQUENCE info_manglar.seq_prices_dailies
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


CREATE TABLE info_manglar.mapping_forms
(
    mapping_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    CONSTRAINT pk_mapping_forms PRIMARY KEY (mapping_form_id),
    CONSTRAINT fk_mapping_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.mapping_forms
    OWNER to postgres;

CREATE INDEX mapping_form_id ON info_manglar.mapping_forms USING btree (mapping_form_id);

CREATE SEQUENCE info_manglar.seq_mapping_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE info_manglar.orgs_mapping
(
    org_mapping_id integer NOT NULL,
    organization_id integer,
    organization_name character varying(255),
    mapping_form_id integer,

    CONSTRAINT pk_orgs_mapping PRIMARY KEY (org_mapping_id),
    CONSTRAINT fk_orgs_mapping FOREIGN KEY (mapping_form_id) REFERENCES info_manglar.mapping_forms(mapping_form_id)
);

ALTER TABLE info_manglar.orgs_mapping
    OWNER to postgres;

CREATE INDEX org_mapping_id ON info_manglar.orgs_mapping USING btree (org_mapping_id);

CREATE SEQUENCE info_manglar.seq_orgs_mapping
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE info_manglar.officials_docs_forms
(
    official_docs_form_id integer NOT NULL,
    user_id integer NOT NULL,
    organization_manglar_id integer NOT NULL,
    form_type character varying(255),
    form_status boolean,
    created_at timestamp without time zone,

    organization_name character varying(255),
    organization_location character varying(255),
    summary character varying(500),
    principal_activity character varying(255),
    organization_type character varying(255),
    custody_area decimal,
    year_creation integer,
    seps_register character varying(255),
    president_name character varying(255),
    pin character varying(255),
    phone character varying(255),
    email character varying(255),

    CONSTRAINT pk_officials_docs_forms PRIMARY KEY (official_docs_form_id),
    CONSTRAINT fk_officials_docs_forms FOREIGN KEY (user_id) REFERENCES public.users(user_id),
    CONSTRAINT fk_organization_manglar_id FOREIGN KEY (organization_manglar_id) REFERENCES info_manglar.organizations_manglar(organization_manglar_id)
);

ALTER TABLE info_manglar.officials_docs_forms
    OWNER to postgres;

CREATE INDEX official_docs_form_id ON info_manglar.officials_docs_forms USING btree (official_docs_form_id);

CREATE SEQUENCE info_manglar.seq_official_docs
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;



CREATE TABLE info_manglar.sectors_forms
(
    sector_form_id integer NOT NULL,
    sector_form_status boolean,

    sector_form_id_option character varying(255),
    sector_form_name character varying(255),
    sector_form_type character varying(255),
    sector_form_info character varying(255),

    info_veda_form_id integer,
    crab_size_form_id integer,
    shell_size_form_id integer,

    CONSTRAINT pk_sectors_forms PRIMARY KEY (sector_form_id),
    CONSTRAINT fk_info_veda_forms FOREIGN KEY (info_veda_form_id) REFERENCES info_manglar.info_veda_forms(info_veda_form_id),
    CONSTRAINT fk_crab_size_forms FOREIGN KEY (crab_size_form_id) REFERENCES info_manglar.crab_size_forms(crab_size_form_id),
    CONSTRAINT fk_shell_size_forms FOREIGN KEY (shell_size_form_id) REFERENCES info_manglar.shell_size_forms(shell_size_form_id)
);

ALTER TABLE info_manglar.sectors_forms
    OWNER to postgres;

CREATE INDEX sector_form_id ON info_manglar.sectors_forms USING btree (sector_form_id);

CREATE SEQUENCE info_manglar.seq_sector_form
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


CREATE TABLE info_manglar.sectors_collections_forms
(
    sector_collection_form_id integer NOT NULL,
    sector_collection_form_status boolean,

    sector_collection_form_id_option character varying(255),
    sector_collection_form_name character varying(255),
    sector_collection_form_type character varying(255),
    sector_collection_form_info character varying(255),
    collector_name character varying(255),
    collector_date character varying(255),

    shell_collection_form_id integer,
    crab_collection_form_id integer,

    CONSTRAINT pk_sectors_collections_forms PRIMARY KEY (sector_collection_form_id),
    CONSTRAINT fk_shell_collection_forms FOREIGN KEY (shell_collection_form_id) REFERENCES info_manglar.shell_collection_forms(shell_collection_form_id),
    CONSTRAINT fk_crab_collection_forms FOREIGN KEY (crab_collection_form_id) REFERENCES info_manglar.crab_collection_forms(crab_collection_form_id)
);

ALTER TABLE info_manglar.sectors_collections_forms
    OWNER to postgres;

CREATE INDEX sector_collection_form_id ON info_manglar.sectors_collections_forms USING btree (sector_collection_form_id);

CREATE SEQUENCE info_manglar.seq_sectors_collections_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;


CREATE TABLE info_manglar.size_forms
(
    size_form_id integer NOT NULL,
    size_form_status boolean,

    size_form_id_option character varying(255),
    size_form_type character varying(255),
    size_form_sex character varying(255),
    size_form_width integer,
    size_form_length integer,

    crab_size_form_id integer,
    shell_size_form_id integer,

    CONSTRAINT pk_size_forms PRIMARY KEY (size_form_id),
    CONSTRAINT fk_crab_size_forms FOREIGN KEY (crab_size_form_id) REFERENCES info_manglar.crab_size_forms(crab_size_form_id),
    CONSTRAINT fk_shell_size_forms FOREIGN KEY (shell_size_form_id) REFERENCES info_manglar.shell_size_forms(shell_size_form_id)
);

ALTER TABLE info_manglar.size_forms
    OWNER to postgres;

CREATE INDEX size_form_id ON info_manglar.size_forms USING btree (size_form_id);

CREATE SEQUENCE info_manglar.seq_size_forms
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

CREATE TABLE info_manglar.files_forms
(
    file_form_id integer NOT NULL,
    file_form_status boolean,

    file_form_id_option character varying(255),
    file_form_name_file character varying(255),
    file_form_type character varying(255),
    file_form_url character varying(255),
    alfreco_code character varying(255),

    official_docs_form_id integer,
    price_daily_id integer,
    document_project_id integer,
    planned_activity_id integer,
    control_form_id integer,
    evidence_activity_id integer,
    org_mapping_id integer,

    CONSTRAINT pk_files_forms PRIMARY KEY (file_form_id),
    CONSTRAINT fk_files_forms FOREIGN KEY (official_docs_form_id) REFERENCES info_manglar.officials_docs_forms(official_docs_form_id),
    CONSTRAINT fk_prices_dailies FOREIGN KEY (price_daily_id) REFERENCES info_manglar.prices_dailies(price_daily_id),
    CONSTRAINT fk_planned_activities FOREIGN KEY (planned_activity_id) REFERENCES info_manglar.planned_activities(planned_activity_id),
    CONSTRAINT fk_documents_projects FOREIGN KEY (document_project_id) REFERENCES info_manglar.documents_projects(document_project_id),
    CONSTRAINT fk_control_forms FOREIGN KEY (control_form_id) REFERENCES info_manglar.control_forms(control_form_id),
    CONSTRAINT fk_evidence_activities FOREIGN KEY (evidence_activity_id) REFERENCES info_manglar.evidence_activities(evidence_activity_id),
    CONSTRAINT fk_orgs_mapping FOREIGN KEY (org_mapping_id) REFERENCES info_manglar.orgs_mapping(org_mapping_id)
);

ALTER TABLE info_manglar.files_forms
    OWNER to postgres;

CREATE INDEX file_form_id ON info_manglar.files_forms USING btree (file_form_id);

CREATE SEQUENCE info_manglar.seq_file_form
    START WITH 1
    INCREMENT BY 1
    MINVALUE 0
    NO MAXVALUE
    CACHE 1;

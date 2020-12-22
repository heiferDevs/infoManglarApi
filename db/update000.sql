ALTER TABLE info_manglar.officials_docs_forms DROP COLUMN principal_activity;

ALTER TABLE info_manglar.officials_docs_forms ADD COLUMN partners_list_date character varying(255);

ALTER TABLE info_manglar.officials_docs_forms ADD COLUMN internal_regulation_date character varying(255);

ALTER TABLE info_manglar.officials_docs_forms ADD COLUMN directors_register_date character varying(255);

ALTER TABLE info_manglar.social_indicators_forms DROP COLUMN preparation;




ALTER TABLE info_manglar.files_forms DROP COLUMN planned_activity_id;

ALTER TABLE info_manglar.pdf_report_forms ADD COLUMN is_approved boolean;
ALTER TABLE info_manglar.pdf_report_forms ADD COLUMN is_with_observations boolean;
ALTER TABLE info_manglar.pdf_report_forms ADD COLUMN approved_date character varying(255);
ALTER TABLE info_manglar.pdf_report_forms ADD COLUMN observation_date character varying(255);
ALTER TABLE info_manglar.pdf_report_forms ADD COLUMN approved_id character varying(255);
ALTER TABLE info_manglar.pdf_report_forms ADD COLUMN observation_id character varying(255);

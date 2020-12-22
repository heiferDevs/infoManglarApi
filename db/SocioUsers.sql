
INSERT INTO info_manglar.organizations_manglar(
	organization_manglar_id, organization_manglar_status, organization_manglar_name, organization_manglar_complete_name, organization_manglar_type)
	VALUES 
		((SELECT nextval('info_manglar.seq_organization_manglar_id')), true, 'MAE', 'MAE', 'mae'),
		((SELECT nextval('info_manglar.seq_organization_manglar_id')), true, 'INP', 'INP', 'inp'),
		((SELECT nextval('info_manglar.seq_organization_manglar_id')), true, 'ADMIN', 'ADMIN', 'super-admin'),
		((SELECT nextval('info_manglar.seq_organization_manglar_id')), true, 'TestOrg', 'TestOrg', 'org')
	;


INSERT INTO info_manglar.allowed_users (
	allowed_user_id, allowed_user_status, organization_manglar_id, gelo_id, role_id, allowed_user_name, allowed_user_pin)
VALUES
	((SELECT nextval('info_manglar.seq_allowed_user_id')), true,
	(select organization_manglar_id from info_manglar.organizations_manglar where organization_manglar_name = 'TestOrg'), 1,
	(select role_id from suia_iii.roles where role_name = 'Socio-InfoManglar'), 'Usuario socio','0000000000'),

	((SELECT nextval('info_manglar.seq_allowed_user_id')), true,
	(select organization_manglar_id from info_manglar.organizations_manglar where organization_manglar_name = 'TestOrg'), 1,
	(select role_id from suia_iii.roles where role_name = 'Org-InfoManglar'), 'Usuario org','1111111111'),

	((SELECT nextval('info_manglar.seq_allowed_user_id')), true,
	(select organization_manglar_id from info_manglar.organizations_manglar where organization_manglar_name = 'MAE'), 1,
	(select role_id from suia_iii.roles where role_name = 'Mae-InfoManglar'), 'Usuario mae','2222222222'),

	((SELECT nextval('info_manglar.seq_allowed_user_id')), true,
	(select organization_manglar_id from info_manglar.organizations_manglar where organization_manglar_name = 'INP'), 1,
	(select role_id from suia_iii.roles where role_name = 'Inp-InfoManglar'), 'Usuario inp','3333333333'),

	((SELECT nextval('info_manglar.seq_allowed_user_id')), true,
	(select organization_manglar_id from info_manglar.organizations_manglar where organization_manglar_name = 'ADMIN'), 1,
	(select role_id from suia_iii.roles where role_name = 'SuperAdmin-InfoManglar'), 'Usuario super admin','4444444444')
	;

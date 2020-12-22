

INSERT INTO suia_iii.roles(
    role_id, role_description, role_name, role_unique, role_status, role_sistemas, role_used_authority, role_allow_administer)
    VALUES 
    ((SELECT nextval('suia_iii.seq_role_id')), 'Socio-InfoManglar', 'Socio-InfoManglar', true, true, 'Socio-InfoManglar', 'Socio-InfoManglar', true),
    ((SELECT nextval('suia_iii.seq_role_id')), 'Org-InfoManglar', 'Org-InfoManglar', true, true, 'Org-InfoManglar', 'Org-InfoManglar', true),
    ((SELECT nextval('suia_iii.seq_role_id')), 'Mae-InfoManglar', 'Mae-InfoManglar', true, true, 'Mae-InfoManglar', 'Mae-InfoManglar', true),
    ((SELECT nextval('suia_iii.seq_role_id')), 'Inp-InfoManglar', 'Inp-InfoManglar', true, true, 'Inp-InfoManglar', 'Inp-InfoManglar', true),
    ((SELECT nextval('suia_iii.seq_role_id')), 'SuperAdmin-InfoManglar', 'SuperAdmin-InfoManglar', true, true, 'SuperAdmin-InfoManglar', 'SuperAdmin-InfoManglar', true)
    ;



// DEPLOY BASED ON LAST DEPLOY ON LOCAL COMPUTER

// in local
cd /Users/scuenca/jboss-eap-6.1/standalone/ && zip -r /Users/scuenca/deployments.zip deployments && cd && scp deployments.zip ubuntu@23.20.94.236:. && rm deployments.zip

// in server ssh ubuntu@23.20.94.236
rm -rf /usr/share/jboss-eap-6.1/standalone/deployments && cd && mv deployments.zip /usr/share/jboss-eap-6.1/standalone && cd /usr/share/jboss-eap-6.1/standalone && unzip deployments.zip && rm deployments.zip && cd

// api most be running on
23.20.94.236/info-manglar


// DEPLOY DB
// ON LOCAL export db
	en pgAdmin4 -  select suia db - backup - nameFile infosuia1.sql - text plain
	scp ~/Downloads/infosuia1.sql ubuntu@23.20.94.236:.


//  ON SERVER SOLO SI VAS UPDATE DE DB stop service jboss my server
sudo service jbossas6 stop

Postgres ENTRAR:
sudo -i -u postgres
$ psql

CREAR ROLES:
\c postgres
create role read;
create role postgres;
create role suia_iii_escritura;

VER ROLES
\du

VER BASES DE DATOS
\l

CREAR DB SUIA
drop database suia;
create database suia;
\c suia
\i /home/ubuntu/infosuia1.sql


SALIR
\q
exit
sudo reboot


// GET USER INFO FROM CONSOLE
select user_id, user_name from public.users where user_name='0702925843';
select peop_id from public.people where peop_pin = '0702925843';
select * from public.contacts where pers_id = 'from the before query';
// 0989941823  FLORES VILELA PATRICIA ELIZABETH
update public.contacts set cont_value = 'patriciafloresv194@hotmail.com' where cont_id = 324;


// GET ORGS
SELECT * from info_manglar.organizations_manglar;

// GET USERS AND ORGS
SELECT orus_id, info_manglar.organizations_users.user_id, public.users.user_name, info_manglar.organizations_users.organization_manglar_id, info_manglar.organizations_manglar.organization_manglar_name FROM info_manglar.organizations_users INNER JOIN public.users ON public.users.user_id=info_manglar.organizations_users.user_id INNER JOIN info_manglar.organizations_manglar ON info_manglar.organizations_manglar.organization_manglar_id=info_manglar.organizations_users.organization_manglar_id;



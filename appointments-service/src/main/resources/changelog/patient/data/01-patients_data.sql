--liquibase formatted sql
--changeset f.tsumakov@gmail.com:01-patients_data.sql

insert into patients(id, first_name, last_name)
values ('019fdd5a-8c0e-75d4-987c-0cb0d08de9a3', 'Patient1', 'First');

insert into patients(id, first_name, last_name)
values ('019fdd5a-8c0e-75d4-987c-112a195a3d65', 'Patient2', 'Second');

insert into patients(id, first_name, last_name)
values ('019fdd5a-8c0e-75d4-987c-155dbb2eaafc', 'Patient3', 'Third');

insert into patients(id, first_name, last_name)
values ('019fdd5a-8c0e-75d4-987c-187df2788f9d', 'Patient4', 'Fourth');

insert into patients(id, first_name, last_name)
values ('019fdd5a-8c0e-75d4-987c-1c2f2fae4799', 'Patient5', 'Fifth');
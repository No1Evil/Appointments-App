--liquibase formatted sql
--changeset f.tsumakov@gmail.com:01-practitioners_data.sql

insert into practitioners(id, first_name, last_name, service_code)
values ('019fdd5a-8c0e-75d4-987c-2064ed5ce5df', 'Practitioner1', 'First', 'dental');

insert into practitioners(id, first_name, last_name, service_code)
values ('019fdd5a-8c0e-75d4-987c-2482a0b4b72d', 'Practitioner2', 'Second', 'gp');

insert into practitioners(id, first_name, last_name, service_code)
values ('019fdd5a-8c0e-75d4-987c-2a0e272c914d', 'Practitioner3', 'Third', 'mental-health');
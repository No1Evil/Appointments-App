--liquibase formatted sql
--changeset f.tsumakov@gmail.com:01-patients.sql runOnChange:true

--changeset create table patients
create table if not exists patients
(
    id uuid primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null
)
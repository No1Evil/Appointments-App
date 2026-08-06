--liquibase formatted sql
--changeset f.tsumakov@gmail.com:01-patients.sql runOnChange:true

-- creates the patients table
create table if not exists patients
(
    id uuid primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null
)
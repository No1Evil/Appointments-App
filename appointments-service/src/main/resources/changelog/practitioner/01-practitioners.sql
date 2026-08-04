--liquibase formatted sql
--changeset f.tsumakov@gmail.com:01-practitioners.sql runOnChange:true

--changeset create table practitioners
create table if not exists practitioners
(
    id uuid primary key,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    service_name varchar(30) not null
)
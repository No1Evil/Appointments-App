--liquibase formatted sql
--changeset f.tsumakov@gmail.com:01-appointments.sql runOnChange:true

--changeset create appointments table
create table if not exists appointments
(
    id uuid primary key,
    slot_id bigint not null references slots(id),
    patient_id uuid not null references patients(id),
    practitioner_id uuid not null references practitioners(id),
    service_name varchar(30) not null,
    start_time timestamptz not null,
    end_time timestamptz not null,
    comment text,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
)
--liquibase formatted sql
--changeset maksim.boiko@tsumakov.com:01-slots_gp_data.sql

--19.01.2026
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 09:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 10:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 10:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 10:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 10:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 11:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 11:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 11:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 11:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 12:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 12:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 12:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 12:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 13:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 13:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 13:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 13:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 14:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 14:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 14:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 14:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 15:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 15:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 15:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 15:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 16:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 16:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 16:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.19 16:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.19 17:00:00', 'YYYY.MM.DD HH24:MI:SS'));

--26.01.2026
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 09:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 10:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 10:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 10:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 10:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 11:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 11:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 11:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 11:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 12:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 12:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 12:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 12:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 13:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 13:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 13:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 13:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 14:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 14:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 14:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 14:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 15:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 15:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 15:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 15:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 16:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 16:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 16:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.01.26 16:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.01.26 17:00:00', 'YYYY.MM.DD HH24:MI:SS'));

--02.02.2026
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 09:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 10:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 10:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 10:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 10:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 11:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 11:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 11:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 11:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 12:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 12:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 12:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 12:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 13:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 13:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 13:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 13:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 14:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 14:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 14:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 14:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 15:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 15:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 15:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 15:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 16:00:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 16:00:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 16:30:00', 'YYYY.MM.DD HH24:MI:SS'));
INSERT INTO slots(status, service, start_time, end_time)
VALUES ('free', 'gp', TO_TIMESTAMP('2026.02.02 16:30:00', 'YYYY.MM.DD HH24:MI:SS'), TO_TIMESTAMP('2026.02.02 17:00:00', 'YYYY.MM.DD HH24:MI:SS'));

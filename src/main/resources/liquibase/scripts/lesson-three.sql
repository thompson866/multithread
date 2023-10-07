-- liquibase formatted sql

-- changeset arxipov:1
CREATE INDEX student_name_idx ON student (name);
CREATE INDEX  faculty_name_color_idx ON faculty (name, color);
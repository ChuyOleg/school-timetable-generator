-- DROP SCHEMA public CASCADE;
-- CREATE SCHEMA public;

CREATE TYPE shift_enum as ENUM ('FIRST', 'SECOND');

CREATE TABLE IF NOT EXISTS subject (
    id serial PRIMARY KEY ,
    name varchar(64) NOT NULL
--  UNIQUE (name, user)
);

CREATE TABLE IF NOT EXISTS room (
    id serial PRIMARY KEY ,
    name varchar(64) NOT NULL ,
    capacity int NOT NULL CHECK ( capacity >= 1 )
--  UNIQUE (name, user)
);

CREATE TABLE IF NOT EXISTS teacher (
    id serial PRIMARY KEY ,
    name varchar(64) NOT NULL ,
    max_hours_per_week int NOT NULL CHECK ( max_hours_per_week >= 1 AND max_hours_per_week <= 35)
--  UNIQUE (name, user)
);

CREATE TABLE IF NOT EXISTS teacher_subject (
    teacher_id int REFERENCES teacher(id) ,
    subject_id int REFERENCES subject(id) ,
    PRIMARY KEY (teacher_id, subject_id)
);

CREATE TABLE IF NOT EXISTS time_slot (
    id serial PRIMARY KEY ,
    week_type varchar(16) NOT NULL,
    day varchar(16) NOT NULL ,
    lesson_number varchar(16) NOT NULL ,

    UNIQUE (week_type, day, lesson_number) ,
    CHECK (week_type in ('ODD', 'EVEN', 'BOTH')) ,
    CHECK (day in ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY')) ,
    CHECK (lesson_number in ('FIRST', 'SECOND', 'THIRD', 'FOURTH', 'FIFTH', 'SIXTH', 'SEVENTH', 'EIGHTH'))
);

CREATE TABLE IF NOT EXISTS class_group (
   id serial PRIMARY KEY ,
   grade_number varchar(16) NOT NULL ,
   letter varchar(1) NOT NULL ,
   shift varchar(16) NOT NULL ,

--  UNIQUE (grade_number, letter, user)
    CHECK (shift in ('FIRST', 'SECOND', 'THIRD', 'FOURTH', 'FIFTH', 'SIXTH', 'SEVENTH', 'EIGHTH', 'NINTH', 'TENTH', 'ELEVENTH')) ,
    CHECK (shift in ('FIRST', 'SECOND'))
);

CREATE TABLE IF NOT EXISTS group_limits (
    id serial PRIMARY KEY ,
    class_group_id int NOT NULL REFERENCES class_group(id) ON DELETE CASCADE ,
    max_hours_per_week int NOT NULL ,
    combine_time_slot_id int REFERENCES time_slot(id)
);

CREATE TABLE IF NOT EXISTS subject_teacher_in_group_mapping (
    id serial PRIMARY KEY ,
    group_limits_id int NOT NULL REFERENCES group_limits(id) ON DELETE CASCADE ,
    subject_id int NOT NULL REFERENCES subject(id) ON DELETE CASCADE ,
    teacher_id int NOT NULL REFERENCES teacher(id) ON DELETE CASCADE ,
    UNIQUE (group_limits_id, subject_id, teacher_id)
);

CREATE TABLE IF NOT EXISTS subject_hours_in_group_mapping (
    id serial PRIMARY KEY ,
    group_limits_id int NOT NULL REFERENCES group_limits(id) ON DELETE CASCADE ,
    subject_id int NOT NULL REFERENCES subject(id) ON DELETE CASCADE ,
    hours double precision NOT NULL ,
    UNIQUE (group_limits_id, subject_id)
);

CREATE TABLE IF NOT EXISTS time_table (
    id serial PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS lesson (
    id serial PRIMARY KEY ,
    group_id int NOT NULL REFERENCES class_group(id) ON DELETE CASCADE ,
    teacher_id int NOT NULL REFERENCES teacher(id) ,
    subject_id int NOT NULL REFERENCES subject(id) ,
    time_slot_id int NOT NULL REFERENCES time_slot(id) ,
    room_id int REFERENCES room(id) ,
    time_table_id int REFERENCES time_table(id)
);

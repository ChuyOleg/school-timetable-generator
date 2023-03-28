-- DROP SCHEMA public CASCADE;
-- CREATE SCHEMA public;

CREATE TYPE day_of_week_enum AS ENUM ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY');
CREATE TYPE week_type_enum as ENUM ('ODD', 'EVEN', 'BOTH');
CREATE TYPE lesson_number_enum as ENUM ('FIRST', 'SECOND', 'THIRD', 'FOURTH', 'FIFTH', 'SIXTH', 'SEVENTH', 'EIGHTH');
CREATE TYPE grade_number_enum as ENUM ('FIRST', 'SECOND', 'THIRD', 'FOURTH', 'FIFTH', 'SIXTH', 'SEVENTH', 'EIGHTH', 'NINTH', 'TENTH', 'ELEVENTH');
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
    week_type week_type_enum NOT NULL ,
    day day_of_week_enum NOT NULL ,
    lesson_number lesson_number_enum NOT NULL ,
    UNIQUE (week_type, day, lesson_number)
);

CREATE TABLE IF NOT EXISTS class_group (
   id serial PRIMARY KEY ,
   grade_number grade_number_enum NOT NULL ,
   letter varchar(1) NOT NULL ,
   shift shift_enum NOT NULL
--  UNIQUE (grade_number, letter, user)
);

CREATE TABLE IF NOT EXISTS group_limits (
    id serial PRIMARY KEY ,
    class_group_id int NOT NULL REFERENCES class_group(id) ON DELETE CASCADE ,
    max_hours_per_week int NOT NULL ,
    combine_time_slot_id int REFERENCES time_slot(id)
);

CREATE TABLE IF NOT EXISTS subject_teacher_in_group_mapping (
    group_limits_id int REFERENCES group_limits(id) ON DELETE CASCADE ,
    subject_id int REFERENCES subject(id) ON DELETE CASCADE ,
    teacher_id int REFERENCES teacher(id) ON DELETE CASCADE ,
    PRIMARY KEY (group_limits_id, subject_id, teacher_id)
);

CREATE TABLE IF NOT EXISTS subject_hours_in_group_mapping (
    group_limits_id int REFERENCES group_limits(id) ON DELETE CASCADE ,
    subject_id int REFERENCES subject(id) ON DELETE CASCADE ,
    hours double precision NOT NULL ,
    PRIMARY KEY (group_limits_id, subject_id)
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

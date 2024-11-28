-- DROP SCHEMA public CASCADE;
-- CREATE SCHEMA public;

CREATE TABLE IF NOT EXISTS users (
    id serial PRIMARY KEY ,
    email varchar(128) NOT NULL UNIQUE ,
    password varchar(256) NOT NULL ,
    role varchar(16) NOT NULL
);

CREATE TABLE IF NOT EXISTS subject (
    id serial PRIMARY KEY ,
    name varchar(64) NOT NULL ,
    user_id int NOT NULL REFERENCES users(id) ,
    created_date timestamp NOT NULL ,
    modified_date timestamp ,

    UNIQUE (name, user_id)
);

CREATE TABLE IF NOT EXISTS room (
    id serial PRIMARY KEY ,
    name varchar(64) NOT NULL ,
    capacity int NOT NULL CHECK ( capacity >= 1 ) ,
    user_id int NOT NULL REFERENCES users(id) ,
    created_date timestamp NOT NULL ,
    modified_date timestamp ,

    UNIQUE (name, user_id)
);

CREATE TABLE IF NOT EXISTS room_limit (
    id serial PRIMARY KEY ,
    room_id int NOT NULL REFERENCES room(id) ON DELETE CASCADE ,
    day varchar(16) NOT NULL ,
    shift int NOT NULL ,
    lesson_number_from int NOT NULL ,
    lesson_number_to int NOT NULL ,

    CHECK (day in ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY')) ,
    CHECK (shift >= 1 AND shift <= 2) ,
    CHECK (lesson_number_from >= 1 AND lesson_number_from <= 8) ,
    CHECK (lesson_number_to >= 1 AND lesson_number_to <= 8) ,
    CHECK (lesson_number_from < lesson_number_to)
);

CREATE TABLE IF NOT EXISTS teacher (
    id serial PRIMARY KEY ,
    name varchar(64) NOT NULL ,
    max_hours_per_week int NOT NULL CHECK ( max_hours_per_week >= 1 AND max_hours_per_week <= 35) ,
    user_id int NOT NULL REFERENCES users(id) ,
    created_date timestamp NOT NULL ,
    modified_date timestamp ,

    UNIQUE (name, user_id)
);

CREATE TABLE IF NOT EXISTS teacher_limits (
    id serial PRIMARY KEY ,
    teacher_id int NOT NULL REFERENCES teacher(id) ON DELETE CASCADE ,

    UNIQUE (teacher_id)
);

CREATE TABLE IF NOT EXISTS free_day_limit (
    id serial PRIMARY KEY ,
    teacher_limits_id int NOT NULL REFERENCES teacher_limits(id) ON DELETE CASCADE ,
    day varchar(16) NOT NULL ,

    CHECK (day in ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY'))
);

CREATE TABLE IF NOT EXISTS lessons_order_limit (
    id serial PRIMARY KEY ,
    teacher_limits_id int NOT NULL REFERENCES teacher_limits(id) ON DELETE CASCADE ,
    importance_level varchar(16) NOT NULL ,

    UNIQUE (teacher_limits_id) ,
    CHECK (importance_level in ('HIGH', 'MEDIUM', 'LOW'))
);

CREATE TABLE IF NOT EXISTS max_lessons_limit (
    id serial PRIMARY KEY ,
    teacher_limits_id int NOT NULL REFERENCES teacher_limits(id) ON DELETE CASCADE ,
    count int NOT NULL ,

    UNIQUE (teacher_limits_id) ,
    CHECK (count >= 1 AND count <= 8)
);

CREATE TABLE IF NOT EXISTS desired_period_limit (
    id serial PRIMARY KEY ,
    teacher_limits_id int NOT NULL REFERENCES teacher_limits(id) ON DELETE CASCADE ,
    day varchar(16) NOT NULL ,
    shift int NOT NULL ,
    lesson_from int NOT NULL ,
    lesson_to int NOT NULL ,

    CHECK (day in ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY')) ,
    CHECK (shift >= 1 AND shift <= 2) ,
    CHECK (lesson_from >= 1 AND lesson_from <= 8) ,
    CHECK (lesson_to >= 1 AND lesson_to <= 8) ,
    CHECK (lesson_from < lesson_to)
);

CREATE TABLE IF NOT EXISTS teacher_subject (
    teacher_id int REFERENCES teacher(id) ON DELETE CASCADE ,
    subject_id int REFERENCES subject(id) ,
    PRIMARY KEY (teacher_id, subject_id)
);

CREATE TABLE IF NOT EXISTS time_slot (
    id serial PRIMARY KEY ,
    week_type varchar(16) NOT NULL,
    day varchar(16) NOT NULL ,
    shift int NOT NULL ,
    lesson_number int NOT NULL ,

    UNIQUE (week_type, day, shift, lesson_number) ,
    CHECK (week_type in ('ODD', 'EVEN', 'BOTH')) ,
    CHECK (day in ('MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY')) ,
    CHECK (shift >= 1 AND shift <= 2) ,
    CHECK (lesson_number >= 1 AND lesson_number <= 8)
);

CREATE TABLE IF NOT EXISTS shifts_intersection (
    id serial PRIMARY KEY ,
    shift_one_lesson_number int NOT NULL ,
    shift_two_lesson_number int NOT NULL ,
    user_id int NOT NULL REFERENCES users(id) ,

    UNIQUE (user_id, shift_one_lesson_number) ,
    UNIQUE (user_id, shift_two_lesson_number) ,
    CHECK (shift_one_lesson_number >= 1 AND shift_one_lesson_number <= 8) ,
    CHECK (shift_two_lesson_number >= 1 AND shift_two_lesson_number <= 8) ,
    CHECK (shift_one_lesson_number > shift_two_lesson_number)
);

CREATE TABLE IF NOT EXISTS class_group (
   id serial PRIMARY KEY ,
   grade_number int NOT NULL ,
   letter varchar(1) NOT NULL ,
   shift int NOT NULL ,
   teacher_id int NOT NULL REFERENCES teacher(id) ,
   user_id int NOT NULL REFERENCES users(id) ,
   created_date timestamp NOT NULL ,
   modified_date timestamp ,

    UNIQUE (grade_number, letter, user_id) ,
    UNIQUE (teacher_id, user_id) ,
    CHECK (grade_number >= 1 AND grade_number <= 11) ,
    CHECK (shift >= 1 AND shift <= 2)
);

CREATE TABLE IF NOT EXISTS group_limits (
    id serial PRIMARY KEY ,
    class_group_id int NOT NULL REFERENCES class_group(id) ON DELETE CASCADE ,
    max_hours_per_week int NOT NULL ,
    combine_time_slot_id int REFERENCES time_slot(id)
);

CREATE TABLE IF NOT EXISTS subject_limits (
    id serial PRIMARY KEY ,
    group_limits_id int NOT NULL REFERENCES group_limits(id) ON DELETE CASCADE ,
    subject_id int NOT NULL REFERENCES subject(id) ,
    hours double precision NOT NULL ,
    teacher_id int NOT NULL REFERENCES teacher(id) ,
    room_id int REFERENCES room(id)  ,
    teacher_2_id int REFERENCES teacher(id) ,
    room_2_id int REFERENCES room(id) ,
    UNIQUE (group_limits_id, subject_id)
);


CREATE TABLE IF NOT EXISTS time_table (
    id serial PRIMARY KEY ,
    user_id int NOT NULL REFERENCES users(id),
    created_date timestamp NOT NULL ,
    modified_date timestamp,
    UNIQUE (user_id)
);

CREATE TABLE IF NOT EXISTS lesson (
    id serial PRIMARY KEY ,
    group_id int NOT NULL REFERENCES class_group(id) ,
    teacher_id int NOT NULL REFERENCES teacher(id) ,
    subject_id int NOT NULL REFERENCES subject(id) ,
    time_slot_id int NOT NULL REFERENCES time_slot(id) ,
    room_id int REFERENCES room(id) ,
    time_table_id int REFERENCES time_table(id) ON DELETE CASCADE
);

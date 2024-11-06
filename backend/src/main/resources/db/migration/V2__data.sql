-- Insert all possible combinations into time_slot table
INSERT INTO time_slot(week_type, day, lesson_number)
SELECT week_types, days, lesson_numbers
FROM unnest(ARRAY['ODD', 'EVEN', 'BOTH']) week_types
         CROSS JOIN unnest(ARRAY['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY']) days
         CROSS JOIN unnest(ARRAY[1, 2, 3, 4, 5, 6, 7, 8]) lesson_numbers;


INSERT INTO users(email, password, role)
VALUES
    ('user@gmail.com', '$2a$10$MAwJpDfT9Nib2X3LahpD7eqsCHz6XUXfWThHSREmwRjE4oX9UDNY2', 'USER'),
    ('user_2@gmail.com', '$2a$10$EKauRMWhRGAZ94eeARfOfeRx58TlLxCRjLzkzmpK/e3qkoARmlEL2', 'USER');


INSERT INTO subject(name, user_id, created_date)
VALUES
    ('Українська мова (письмо)', 1, now()), ('Українська мова (читання, now())', 1, now()), ('Англійська мова', 1, now()),
    ('Математика', 1, now()), ('Я досліджую світ', 1, now()), ('Дизайн і технології', 1, now()),
    ('Образотворче мистецтво', 1, now()), ('Фізична культура', 1, now()), ('Інформатика', 1, now()),
    ('Українська мова', 1, now()), ('Українська література', 1, now()), ('Німецька мова', 1, now()),
    ('Зарубіжна література', 1, now()), ('Природознавство', 1, now()), ('Історія', 1, now()),
    ('Трудове навчання', 1, now()), ('Музика', 1, now()), ('Основи здоров`я', 1, now()),
    ('Географія', 1, now()), ('Фізика', 1, now()), ('Хімія', 1, now()),
    ('Історія України', 1, now()), ('Всесвітня історія', 1, now()), ('Біологія', 1, now()),
    ('Правознавство', 1, now()), ('Захист Вітчизни', 1, now()), ('Предмети 1-4 класів', 1, now()),
    ('Алгебра', 1, now()), ('Геометрія', 1, now()), ('Мистецтво', 1, now());


INSERT INTO room(name, capacity, user_id, created_date)
VALUES
    ('Спортивний зал №1', 3, 1, now()), ('Спортивний зал №2', 2, 1, now()),
    ('Хімічний кабінет', 1, 1, now()), ('Фізичний кабінет', 1, 1, now()),
    ('Комп`ютерний клас №1', 1, 1, now()), ('Комп`ютерний клас №2', 1, 1, now()),
    ('Трудовий клас (дівчата)', 2, 1, now()), ('Трудовий клас (хлопці)', 2, 1, now());


INSERT INTO room_limit(room_id, day, lesson_number_from, lesson_number_to)
VALUES
    (1, 'MONDAY', 6, 8),
    (1, 'WEDNESDAY', 5, 8),
    (1, 'FRIDAY', 7, 8),
    (3, 'THURSDAY', 1, 3);


INSERT INTO teacher(name, max_hours_per_week, user_id, created_date)
VALUES
('Чернихівська З.Д.', 16, 1, now()), ('Маньковська О.А', 16, 1, now()), ('Тищик Е.Р.', 16, 1, now()),
('Чижевська Я.Д.', 16, 1, now()), ('Бучко Х.Д.', 16, 1, now()), ('Ковч Т.О.', 16, 1, now()),
('Осадчук І.У.', 17, 1, now()), ('Мокрицька Ч.Л.', 17, 1, now()), ('Білан У.А.', 17, 1, now()),
('Сінчук Д.О.', 17, 1, now()), ('Маляр С.І.', 17, 1, now()), ('Гуцало А.Б.', 17, 1, now()),
('Демешко Д.В.', 15, 1, now()), ('Негода І.М.', 18, 1, now()), ('Політило Е.З.', 18, 1, now()),
('Сорока В.Р.', 18, 1, now()), ('Губ`як О.В.', 21, 1, now()), ('Падун О.Т.', 19, 1, now()),
('Зубач Т.З.', 19, 1, now()), ('Іваненко М.Ф.', 21, 1, now()), ('Карасик Г.Я.', 20, 1, now()),
('Босенко В.Л.', 22, 1, now()), ('Кобець У.Б.', 20, 1, now()), ('Денисюк І.Т.', 19, 1, now()),
('Шимко В.А.', 21, 1, now()), ('Толубко Ф.А.', 21, 1, now()), ('Вайда Г.В.', 20, 1, now()),
('Корнелюк І.М.', 21, 1, now()), ('Бондар А.К.', 20, 1, now()), ('Лавренюк О.П.', 16, 1, now()),
('Гайовий Ш.А.', 16, 1, now()), ('Бурлюк Р.О.', 21, 1, now()), ('Сохацька Я.А.', 20, 1, now()),
('Васько Т.В.', 19, 1, now()), ('Бутенко Н.Л.', 16, 1, now()), ('Коструба І.П.', 19, 1, now()),
('Стешенко О.І.', 19, 1, now()), ('Цибенко Ч.Д.', 19, 1, now()), ('Бурміцька Ш.В.', 19, 1, now()),
('Зуріцька Р.Я.', 18, 1, now()), ('Бевз О.М.', 18, 1, now()), ('Скорик Ф.П.', 18, 1, now()),
('Батенко Е.П.', 18, 1, now()), ('Кочерга В.М.', 20, 1, now()), ('Доманицька В.Р.', 18, 1, now()),
('Швець К.М.', 18, 1, now()), ('Романюк З.В.', 15, 1, now()), ('Плужник В.П.', 16, 1, now()),
('Гальчак К.Р.', 15, 1, now()), ('Мацкевич А.З.', 20, 1, now()), ('Якименко К.П.', 15, 1, now()),
('Безуглий Ц.А.', 20, 1, now()), ('Дашенко Д.М.', 20, 1, now()), ('Філь А.П.', 18, 1, now()),
('Костець К.І.', 18, 1, now()), ('Карпека Г.С.', 14, 1, now()), ('Колесса С.І.', 14, 1, now()),
('Дунець О.П.', 18, 1, now()), ('Огієнко Є.В.', 18, 1, now());


INSERT INTO teacher_subject(teacher_id, subject_id)
VALUES
(1, 27), (2, 27) ,(3, 27) ,(4, 27) ,(5, 27), (6, 27),
(7, 27), (8, 27), (9, 27), (10, 27), (11, 27), (12, 27),
(13, 3), (14, 3), (15, 8), (16, 8), (17 ,6), (17, 9),
(18, 10), (18, 11), (19 ,4), (19 ,28), (19 ,29), (20, 3),
(21, 12), (22, 13), (23, 22), (23, 23), (23, 15), (24, 9),
(25, 21), (25, 18), (26, 7), (26, 17), (26, 30), (27, 10), (27, 11), (28, 8), (29, 24),
(29, 14), (30, 16), (31, 16), (32, 3), (33, 12), (34, 9),
(35, 19), (36, 11), (36, 10), (37, 11), (37, 10), (38, 4),
(38, 28), (38, 29), (39, 4), (39, 28), (39, 29), (40, 3),
(41, 3), (42, 12), (43, 12), (44, 22), (44, 23), (44, 15),
(45, 8), (46, 20), (47, 25), (47, 26), (48, 20), (48, 26), (49, 10), (49, 11),
(50, 4), (50, 28), (50, 29), (51, 4), (51, 28), (51, 29), (52, 15),
(52, 22), (52, 23), (53, 18), (53, 21), (54, 8), (55, 14), (55, 24),
(56, 16), (57, 16), (58, 3), (59, 3);


INSERT INTO class_group(grade_number, letter, shift, teacher_id, user_id, created_date)
VALUES
(1, 'А', 1, 1, 1, now()), (1, 'Б', 1, 2, 1, now()), (1, 'В', 1, 3, 1, now()),
(2, 'А', 2, 4, 1, now()), (2, 'Б', 2, 5, 1, now()), (2, 'В', 2, 6, 1, now()),
(3, 'А', 1, 7, 1, now()), (3, 'Б', 1, 8, 1, now()), (3, 'В', 1, 9, 1, now()),
(4, 'А', 2, 10, 1, now()), (4, 'Б', 2, 11, 1, now()), (4, 'В', 2, 12, 1, now()),
(5, 'А', 1, 18, 1, now()), (5, 'Б', 1, 19, 1, now()), (5, 'В', 1, 20, 1, now()),
(6, 'А', 2, 21, 1, now()), (6, 'Б', 2, 22, 1, now()), (6, 'В', 2, 23, 1, now()),
(7, 'А', 1, 25, 1, now()), (7, 'Б', 1, 29, 1, now()), (7, 'В', 1, 35, 1, now()),
(8, 'А', 1, 36, 1, now()), (8, 'Б', 1, 38, 1, now()), (8, 'В', 1, 40, 1, now()),
(9, 'А', 1, 41, 1, now()), (9, 'Б', 1, 42, 1, now()), (9, 'В', 1, 43, 1, now()),
(10, 'А', 1, 44, 1, now()), (10, 'Б', 1, 46, 1, now()),
(11, 'А', 1, 47, 1, now()), (11, 'Б', 1, 34, 1, now());


INSERT INTO group_limits(class_group_id, max_hours_per_week, combine_time_slot_id)
VALUES
(1, 22, null), (2, 22, null), (3, 22, null),
(4, 24, null), (5, 24, null), (6, 24, null),
(7, 25, null), (8, 25, null), (9, 25, null),
(10, 25, null), (11, 25, null), (12, 25, null),
(13, 31, null), (14, 31, null), (15, 31, null),
(16, 32, null), (17, 32, null), (18, 32, null),
(19, 32, null), (20, 32, null), (21, 32, null),
(22, 34, null), (23, 34, null), (24, 34, null),
(25, 35, null), (26, 35, null), (27, 35, null),
(28, 33, 86), (29, 33, 86),
(30, 33, 87), (31, 33, 87);

INSERT INTO subject_limits(group_limits_id, subject_id, hours, teacher_id, room_id, teacher_2_id, room_2_id)
VALUES
(1, 1, 4, 1, null, null, null), (1, 2, 3, 1, null, null, null), (1, 3, 2, 13, null, null, null),
(1, 4, 4, 1, null, null, null), (1, 5, 3, 1, null, null, null), (1, 6, 1, 17, 5, null, null),
(1, 7, 2, 1, null, null, null), (1, 8, 3, 15, 1, null, null), /* 1-А */

(2, 1, 4, 2, null, null, null), (2, 2, 3, 2, null, null, null), (2, 3, 2, 13, null, null, null),
(2, 4, 4, 2, null, null, null), (2, 5, 3, 2, null, null, null), (2, 6, 1, 17, 5, null, null),
(2, 7, 2, 2, null, null, null), (2, 8, 3, 15, 1, null, null), /* 1-Б */

(3, 1, 4, 3, null, null, null), (3, 2, 3, 3, null, null, null), (3, 3, 2, 13, null, null, null),
(3, 4, 4, 3, null, null, null), (3, 5, 3, 3, null, null, null), (3, 6, 1, 17, 5, null, null),
(3, 7, 2, 3, null, null, null), (3, 8, 3, 15, 1, null, null), /* 1-В */

(4, 1, 4, 4, null, null, null), (4, 2, 3, 4, null, null, null), (4, 3, 3, 14, null, null, null),
(4, 4, 4, 4, null, null, null), (4, 5, 3, 4, null, null, null), (4, 6, 1, 17, 5, null, null),
(4, 7, 2, 4, null, null, null), (4, 8, 3, 16, 1, null, null), (4, 9, 1, 17, 5, null, null), /* 2-А */

(5, 1, 4, 5, null, null, null), (5, 2, 3, 5, null, null, null), (5, 3, 3, 14, null, null, null),
(5, 4, 4, 5, null, null, null), (5, 5, 3, 5, null, null, null), (5, 6, 1, 17, 5, null, null),
(5, 7, 2, 5, null, null, null), (5, 8, 3, 16, 1, null, null), (5, 9, 1, 17, 5, null, null), /* 2-Б */

(6, 1, 4, 6, null, null, null), (6, 2, 3, 6, null, null, null), (6, 3, 3, 14, null, null, null),
(6, 4, 4, 6, null, null, null), (6, 5, 3, 6, null, null, null), (6, 6, 1, 17, 5, null, null),
(6, 7, 2, 6, null, null, null), (6, 8, 3, 16, 1, null, null), (6, 9, 1, 17, 5, null, null), /* 2-В */

(7, 1, 4, 7, null, null, null), (7, 2, 3, 7, null, null, null), (7, 3, 3, 13, null, null, null),
(7, 4, 5, 7, null, null, null), (7, 5, 3, 7, null, null, null), (7, 6, 1, 17, 5, null, null),
(7, 7, 2, 7, null, null, null), (7, 8, 3, 15, 1, null, null), (7, 9, 1, 17, 5, null, null), /* 3-А */

(8, 1, 4, 8, null, null, null), (8, 2, 3, 8, null, null, null), (8, 3, 3, 13, null, null, null),
(8, 4, 5, 8, null, null, null), (8, 5, 3, 8, null, null, null), (8, 6, 1, 17, 5, null, null),
(8, 7, 2, 8, null, null, null), (8, 8, 3, 15, 1, null, null), (8, 9, 1, 17, 5, null, null), /* 3-Б */

(9, 1, 4, 9, null, null, null), (9, 2, 3, 9, null, null, null), (9, 3, 3, 13, null, null, null),
(9, 4, 5, 9, null, null, null), (9, 5, 3, 9, null, null, null), (9, 6, 1, 17, 5, null, null),
(9, 7, 2, 9, null, null, null), (9, 8, 3, 15, 1, null, null), (9, 9, 1, 17, 5, null, null), /* 3-В */

(10, 1, 4, 10, null, null, null), (10, 2, 3, 10, null, null, null), (10, 3, 3, 14, null, null, null),
(10, 4, 5, 10, null, null, null), (10, 5, 3, 10, null, null, null), (10, 6, 1, 17, 5, null, null),
(10, 7, 2, 10, null, null, null), (10, 8, 3, 16, 1, null, null), (10, 9, 1, 17, 5, null, null), /* 4-А */

(11, 1, 4, 11, null, null, null), (11, 2, 3, 11, null, null, null), (11, 3, 3, 14, null, null, null),
(11, 4, 5, 11, null, null, null), (11, 5, 3, 11, null, null, null), (11, 6, 1, 17, 5, null, null),
(11, 7, 2, 11, null, null, null), (11, 8, 3, 16, 1, null, null), (11, 9, 1, 17, 5, null, null), /* 4-Б */

(12, 1, 4, 12, null, null, null), (12, 2, 3, 12, null, null, null), (12, 3, 3, 14, null, null, null),
(12, 4, 5, 12, null, null, null), (12, 5, 3, 12, null, null, null), (12, 6, 1, 17, 5, null, null),
(12, 7, 2, 12, null, null, null), (12, 8, 3, 16, 1, null, null), (12, 9, 1, 17, 5, null, null), /* 4-В */

(13, 17, 1, 26, null, null, null), (13, 8, 3, 28, 1, null, null), (13, 15, 3, 23, null, null, null),
(13, 11, 2, 18, null, null, null), (13, 7, 1, 26, null, null, null), (13, 18, 1, 25, null, null, null),
(13, 13, 2, 22, null, null, null), (13, 9, 1, 24, 5, 34, 6), (13, 4, 5, 19, null, null, null),
(13, 10, 3, 18, null, null, null), (13, 14, 2, 29, null, null, null), (13, 3, 3, 20, null, 32, null),
(13, 12, 2, 21, null, 33, null), (13, 16, 2, 30, 7, 31, 8), /* 5-А */

(14, 17, 1, 26, null, null, null), (14, 8, 3, 28, 1, null, null), (14, 15, 3, 23, null, null, null),
(14, 11, 2, 18, null, null, null), (14, 7, 1, 26, null, null, null), (14, 18, 1, 25, null, null, null),
(14, 13, 2, 22, null, null, null), (14, 9, 1, 24, 5, 34, 6), (14, 4, 5, 19, null, null, null),
(14, 10, 3, 18, null, null, null), (14, 14, 2, 29, null, null, null), (14, 3, 3, 20, null, 32, null),
(14, 12, 2, 21, null, 33, null), (14, 16, 2, 30, 7, 31, 8), /* 5-Б */

(15, 17, 1, 26, null, null, null), (15, 8, 3, 28, 1, null, null), (15, 15, 3, 23, null, null, null),
(15, 11, 2, 18, null, null, null), (15, 7, 1, 26, null, null, null), (15, 18, 1, 25, null, null, null),
(15, 13, 2, 22, null, null, null), (15, 9, 1, 24, 5, 34, 6), (15, 4, 5, 19, null, null, null),
(15, 10, 3, 18, null, null, null), (15, 14, 2, 29, null, null, null), (15, 3, 3, 20, null, 32, null),
(15, 12, 2, 21, null, 33, null), (15, 16, 2, 30, 7, 31, 8), /* 5-В */

(16, 3, 3, 20, null, 32, null), (16, 4, 5, 38, null, null, null), (16, 7, 1, 26, null, null, null),
(16, 8, 3, 28, 2, null, null), (16, 9, 1, 24, 5, 34, 6), (16, 10, 3, 36, null, null, null),
(16, 11, 2, 36, null, null, null), (16, 12, 2, 21, null, 33, null), (16, 13, 1, 22, null, null, null),
(16, 16, 2, 30, 8, 31, 7), (16, 17, 1, 26, null, null, null), (16, 18, 1, 25, null, null, null),
(16, 19, 1, 35, null, null, null), (16, 22, 2, 23, null, null, null), (16, 23, 2, 23, null, null, null),
(16, 24, 2, 29, null, null, null), /* 6-А */

(17, 3, 3, 20, null, 32, null), (17, 4, 5, 38, null, null, null), (17, 7, 1, 26, null, null, null),
(17, 8, 3, 28, 2, null, null), (17, 9, 1, 24, 5, 34, 6), (17, 10, 3, 36, null, null, null),
(17, 11, 2, 36, null, null, null), (17, 12, 2, 21, null, 33, null), (17, 13, 1, 22, null, null, null),
(17, 16, 2, 30, 8, 31, 7), (17, 17, 1, 26, null, null, null), (17, 18, 1, 25, null, null, null),
(17, 19, 1, 35, null, null, null), (17, 22, 2, 23, null, null, null), (17, 23, 2, 23, null, null, null),
(17, 24, 2, 29, null, null, null), /* 6-Б */

(18, 3, 3, 20, null, 32, null), (18, 4, 5, 38, null, null, null), (18, 7, 1, 26, null, null, null),
(18, 8, 3, 28, 2, null, null), (18, 9, 1, 24, 5, 34, 6), (18, 10, 3, 36, null, null, null),
(18, 11, 2, 36, null, null, null), (18, 12, 2, 21, null, 33, null), (18, 13, 1, 22, null, null, null),
(18, 16, 2, 30, 8, 31, 7), (18, 17, 1, 26, null, null, null), (18, 18, 1, 25, null, null, null),
(18, 19, 1, 35, null, null, null), (18, 22, 2, 52, null, null, null), (18, 23, 2, 52, null, null, null),
(18, 24, 2, 29, null, null, null), /* 6-В */

(19, 3, 3, 40, null, 41, null), (19, 7, 0.5, 26, null, null, null), (19, 8, 3, 45, 2, null, null),
(19, 9, 1, 24, 5, 34, 6), (19, 10, 2, 18, null, null, null), (19, 11, 2, 18, null, null, null),
(19, 12, 2, 42, null, 43, null), (19, 13, 1, 22, null, null, null), (19, 16, 2, 56, 8, 57, 7),
(19, 17, 0.5, 26, null, null, null), (19, 18, 1, 25, null, null, null), (19, 19, 1, 35, null, null, null),
(19, 20, 2, 46, null, null, null), (19, 21, 2, 25, null, null, null), (19, 22, 2, 44, null, null, null),
(19, 23, 1, 44, null, null, null), (19, 24, 2, 55, null, null, null), (19, 28, 2, 19, null, null, null),
(19, 29, 2, 19, null, null, null), /* 7-А */

(20, 3, 3, 40, null, 41, null), (20, 7, 0.5, 26, null, null, null), (20, 8, 3, 45, 2, null, null),
(20, 9, 1, 24, 5, 34, 6), (20, 10, 2, 36, null, null, null), (20, 11, 2, 36, null, null, null),
(20, 12, 2, 42, null, 43, null), (20, 13, 1, 22, null, null, null), (20, 16, 2, 30, 8, 31, 7),
(20, 17, 0.5, 26, null, null, null), (20, 18, 1, 25, null, null, null), (20, 19, 1, 35, null, null, null),
(20, 20, 2, 46, null, null, null), (20, 21, 2, 25, null, null, null), (20, 22, 2, 44, null, null, null),
(20, 23, 1, 44, null, null, null), (20, 24, 2, 55, null, null, null), (20, 28, 2, 38, null, null, null),
(20, 29, 2, 38, null, null, null), /* 7-Б */

(21, 3, 3, 40, null, 41, null), (21, 7, 0.5, 26, null, null, null), (21, 8, 3, 45, 2, null, null),
(21, 9, 1, 24, 5, 34, 6), (21, 10, 2, 37, null, null, null), (21, 11, 2, 37, null, null, null),
(21, 12, 2, 42, null, 43, null), (21, 13, 1, 22, null, null, null), (21, 16, 2, 30, 8, 31, 7),
(21, 17, 0.5, 26, null, null, null), (21, 18, 1, 25, null, null, null), (21, 19, 1, 35, null, null, null),
(21, 20, 2, 46, null, null, null), (21, 21, 2, 25, null, null, null), (21, 22, 2, 44, null, null, null),
(21, 23, 1, 44, null, null, null), (21, 24, 2, 55, null, null, null), (21, 28, 2, 39, null, null, null),
(21, 29, 2, 39, null, null, null), /* 7-В */

(22, 3, 3, 40, null, 41, null), (22, 8, 3, 54, 2, null, null), (22, 9, 1, 24, 5, 34, 6),
(22, 10, 3, 37, null, null, null), (22, 11, 2, 37, null, null, null), (22, 12, 2, 42, null, 43, null),
(22, 13, 1, 22, null, null, null), (22, 16, 2, 56, 8, 57, 7), (22, 18, 1, 53, null, null, null),
(22, 19, 1, 35, null, null, null), (22, 20, 2, 46, null, null, null), (22, 21, 2, 53, null, null, null),
(22, 22, 2, 44, null, null, null), (22, 23, 1, 44, null, null, null), (22, 24, 2, 55, null, null, null),
(22, 28, 3, 39, null, null, null), (22, 29, 2, 39, null, null, null), (22, 30, 1, 26, null, null, null), /* 8-А */

(23, 3, 3, 40, null, 41, null), (23, 8, 3, 54, 2, null, null), (23, 9, 1, 24, 5, 34, 6),
(23, 10, 3, 37, null, null, null), (23, 11, 2, 37, null, null, null), (23, 12, 2, 42, null, 43, null),
(23, 13, 1, 22, null, null, null), (23, 16, 2, 56, 8, 57, 7), (23, 18, 1, 53, null, null, null),
(23, 19, 1, 35, null, null, null), (23, 20, 2, 46, null, null, null), (23, 21, 2, 53, null, null, null),
(23, 22, 2, 44, null, null, null), (23, 23, 1, 52, null, null, null), (23, 24, 2, 55, null, null, null),
(23, 28, 3, 39, null, null, null), (23, 29, 2, 39, null, null, null), (23, 30, 1, 26, null, null, null), /* 8-Б */

(24, 3, 3, 40, null, 41, null), (24, 8, 3, 54, 2, null, null), (24, 9, 1, 24, 5, 34, 6),
(24, 10, 3, 37, null, null, null), (24, 11, 2, 37, null, null, null), (24, 12, 2, 42, null, 43, null),
(24, 13, 1, 22, null, null, null), (24, 16, 2, 56, 8, 57, 7), (24, 18, 1, 53, null, null, null),
(24, 19, 1, 35, null, null, null), (24, 20, 2, 46, null, null, null), (24, 21, 2, 53, null, null, null),
(24, 22, 2, 52, null, null, null), (24, 23, 1, 52, null, null, null), (24, 24, 2, 55, null, null, null),
(24, 28, 3, 39, null, null, null), (24, 29, 2, 39, null, null, null), (24, 30, 1, 26, null, null, null), /* 8-В */

(25, 3, 3, 58, null, 59, null), (25, 8, 3, 54, 1, null, null), (25, 9, 1, 24, 5, 34, 6),
(25, 10, 3, 49, null, null, null), (25, 11, 2, 49, null, null, null), (25, 12, 2, 21, null, 33, null),
(25, 13, 1, 22, null, null, null), (25, 16, 2, 56, 8, 57, 7), (25, 18, 1, 53, null, null, null),
(25, 19, 1, 35, null, null, null), (25, 20, 2, 46, null, null, null), (25, 21, 2, 53, null, null, null),
(25, 22, 2, 52, null, null, null), (25, 23, 1, 52, null, null, null), (25, 24, 2, 55, null, null, null),
(25, 25, 1, 47, null, null, null), (25, 28, 3, 51, null, null, null), (25, 29, 2, 51, null, null, null),
(25, 30, 1, 26, null, null, null), /* 9-А */

(26, 3, 3, 58, null, 59, null), (26, 8, 3, 54, 1, null, null), (26, 9, 1, 24, 5, 34, 6),
(26, 10, 3, 49, null, null, null), (26, 11, 2, 49, null, null, null), (26, 12, 2, 21, null, 33, null),
(26, 13, 1, 22, null, null, null), (26, 16, 2, 56, 8, 57, 7), (26, 18, 1, 53, null, null, null),
(26, 19, 1, 35, null, null, null), (26, 20, 2, 46, null, null, null), (26, 21, 2, 53, null, null, null),
(26, 22, 2, 52, null, null, null), (26, 23, 1, 52, null, null, null), (26, 24, 2, 55, null, null, null),
(26, 25, 1, 47, null, null, null), (26, 28, 3, 51, null, null, null), (26, 29, 2, 51, null, null, null),
(26, 30, 1, 26, null, null, null), /* 9-Б */

(27, 3, 3, 58, null, 59, null), (27, 8, 3, 54, 1, null, null), (27, 9, 1, 24, 5, 34, 6),
(27, 10, 3, 49, null, null, null), (27, 11, 2, 49, null, null, null), (27, 12, 2, 21, null, 33, null),
(27, 13, 1, 22, null, null, null), (27, 16, 2, 56, 8, 57, 7), (27, 18, 1, 53, null, null, null),
(27, 19, 1, 35, null, null, null), (27, 20, 2, 46, null, null, null), (27, 21, 2, 53, null, null, null),
(27, 22, 2, 52, null, null, null), (27, 23, 1, 52, null, null, null), (27, 24, 2, 55, null, null, null),
(27, 25, 1, 47, null, null, null), (27, 28, 3, 51, null, null, null), (27, 29, 2, 51, null, null, null),
(27, 30, 1, 26, null, null, null), /* 9-В */

(28, 3, 3, 58, null, 59, null), (28, 8, 3, 45, 2, null, null), (28, 9, 1, 24, 5, 34, 6),
(28, 10, 3, 27, null, null, null), (28, 11, 2, 27, null, null, null), (28, 12, 2, 42, null, 43, null),
(28, 13, 1, 22, null, null, null), (28, 19, 1, 35, null, null, null), (28, 20, 2, 48, null, null, null),
(28, 21, 2, 25, null, null, null), (28, 22, 2, 44, null, null, null), (28, 23, 1, 44, null, null, null),
(28, 24, 2, 29, null, null, null), (28, 25, 1, 47, null, null, null), (28, 26, 2, 48, null, 47, null),
(28, 28, 3, 50, null, null, null), (28, 29, 2, 50, null, null, null), /* 10-А */

(29, 3, 3, 58, null, 59, null), (29, 8, 3, 45, 2, null, null), (29, 9, 1, 24, 5, 34, 6),
(29, 10, 3, 27, null, null, null), (29, 11, 2, 27, null, null, null), (29, 12, 2, 42, null, 43, null),
(29, 13, 1, 22, null, null, null), (29, 19, 1, 35, null, null, null), (29, 20, 2, 48, null, null, null),
(29, 21, 2, 25, null, null, null), (29, 22, 2, 44, null, null, null), (29, 23, 1, 44, null, null, null),
(29, 24, 2, 29, null, null, null), (29, 25, 1, 47, null, null, null), (29, 26, 2, 48, null, 47, null),
(29, 28, 3, 50, null, null, null), (29, 29, 2, 50, null, null, null), /* 10-Б */

(30, 3, 3, 20, null, 32, null), (30, 8, 3, 28, 2, null, null), (30, 9, 1, 24, 5, 34, 6),
(30, 10, 3, 27, null, null, null), (30, 11, 2, 27, null, null, null), (30, 12, 2, 21, null, 33, null),
(30, 13, 1, 22, null, null, null), (30, 19, 1, 35, null, null, null), (30, 20, 2, 48, null, null, null),
(30, 21, 2, 53, null, null, null), (30, 22, 2, 23, null, null, null), (30, 23, 1, 23, null, null, null),
(30, 24, 2, 29, null, null, null), (30, 25, 1, 47, null, null, null), (30, 26, 2, 48, null, 47, null),
(30, 28, 3, 50, null, null, null), (30, 29, 2, 50, null, null, null), /* 11-А */

(31, 3, 3, 58, null, 59, null), (31, 8, 3, 45, 2, null, null), (31, 9, 1, 24, 5, 34, 6),
(31, 10, 3, 27, null, null, null), (31, 11, 2, 27, null, null, null), (31, 12, 2, 42, null, 43, null),
(31, 13, 1, 22, null, null, null), (31, 19, 1, 35, null, null, null), (31, 20, 2, 48, null, null, null),
(31, 21, 2, 25, null, null, null), (31, 22, 2, 52, null, null, null), (31, 23, 1, 52, null, null, null),
(31, 24, 2, 29, null, null, null), (31, 25, 1, 47, null, null, null), (31, 26, 2, 48, null, 47, null),
(31, 28, 3, 50, null, null, null), (31, 29, 2, 50, null, null, null); /* 11-Б */

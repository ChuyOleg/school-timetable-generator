-- Insert all possible combinations into time_slot table
INSERT INTO time_slot(week_type, day, lesson_number)
SELECT week_types, days, lesson_numbers
FROM unnest(ARRAY['ODD', 'EVEN', 'BOTH']) week_types
         CROSS JOIN unnest(ARRAY['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY']) days
         CROSS JOIN unnest(ARRAY[1, 2, 3, 4, 5, 6, 7, 8]) lesson_numbers;


INSERT INTO subject(name)
VALUES
    ('Українська мова (письмо)'), ('Українська мова (читання)'), ('Англійська мова'),
    ('Математика'), ('Я досліджую світ'), ('Дизайн і технології'),
    ('Образотворче мистецтво'), ('Фізична культура'), ('Інформатика'),
    ('Українська мова'), ('Українська література'), ('Німецька мова'),
    ('Зарубіжна література'), ('Історія'), ('Основи здоров`я'),
    ('Географія'), ('Фізика');


INSERT INTO room(name, capacity)
VALUES
    ('Спортивний зал №1', 78), ('Спортивний зал №2', 60),
    ('Хімічний кабінет', 40), ('Фізичний кабінет', 38),
    ('Комп`ютерний клас №1', 36), ('Комп`ютерний клас №2', 37);


INSERT INTO teacher(name, max_hours_per_week)
VALUES
('Вірський Єгор', 20), ('Залужний Юрій', 22), ('Яросевич Едуард', 19),
('Гармаш Йосип', 23), ('Сенько Євген', 25), ('Пащук Федір', 20),
('Семашко Яр', 19), ('Кармалюк Орест', 22), ('Чубатенко Ян', 20),
('Мудениця Захар', 25), ('Ярчук Шерлок', 22), ('Балі Юхим', 21);


INSERT INTO teacher_subject(teacher_id, subject_id)
VALUES
(1, 1), (1, 2), (2, 3), (2, 12), (3, 17),
(4, 6), (4, 9), (5, 4), (5, 17), (6, 16),
(7, 4), (8, 8), (9, 5), (9, 7), (9, 15),
(10, 10), (10, 11), (11, 13), (12, 14);


INSERT INTO class_group(grade_number, letter, shift, teacher_id)
VALUES
(7, 'A', 2, 2), (7, 'B', 2, 3), (7, 'C', 2, 4),
(9, 'A', 1, 1), (9, 'B', 1, 5), (9, 'C', 1, 6), (9, 'D', 1, 7);

INSERT INTO group_limits(class_group_id, max_hours_per_week, combine_time_slot_id)
VALUES
(1, 35, 71);

INSERT INTO subject_limits(group_limits_id, subject_id, hours, teacher_id, room_id, teacher_2_id, room_2_id)
VALUES
(1, 4, 2, 5, null, null, null)

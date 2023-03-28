-- Insert all possible combinations into time_slot table
INSERT INTO time_slot(week_type, day, lesson_number)
SELECT week_types::week_type_enum, days::day_of_week_enum, lesson_numbers::lesson_number_enum
FROM unnest(ARRAY['ODD', 'EVEN', 'BOTH']) week_types
         CROSS JOIN unnest(ARRAY['MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY']) days
         CROSS JOIN unnest(ARRAY['FIRST', 'SECOND', 'THIRD', 'FOURTH', 'FIFTH', 'SIXTH', 'SEVENTH', 'EIGHTH']) lesson_numbers;


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



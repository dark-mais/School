-- scripts423.sql

-- Первый JOIN-запрос для получения информации обо всех студентах вместе с названиями факультетов
SELECT
    s.name AS student_name,
    s.age AS student_age,
    f.name AS faculty_name
FROM
    Student s
JOIN
    Faculty f ON s.faculty_id = f.id;

-- Второй JOIN-запрос для получения студентов, у которых есть аватарки
SELECT
    s.name AS student_name,
    s.age AS student_age
FROM
    Student s
JOIN
    Avatar a ON s.id = a.student_id;
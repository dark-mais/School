-- scripts421.sql

-- Ограничение на минимальный возраст студента
ALTER TABLE Student
ADD CONSTRAINT chk_age CHECK (age >= 16);

-- Ограничение на уникальные и ненулевые имена студентов
ALTER TABLE Student
ADD CONSTRAINT uq_name UNIQUE (name),
ADD CONSTRAINT nn_name CHECK (name IS NOT NULL);

-- Ограничение на уникальность пары "название факультета - цвет"
ALTER TABLE Faculty
ADD CONSTRAINT uq_name_color UNIQUE (name, color);

-- Триггер для установки возраста по умолчанию
CREATE OR REPLACE FUNCTION set_default_age()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.age IS NULL THEN
        NEW.age := 20;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER set_default_age_trigger
BEFORE INSERT ON Student
FOR EACH ROW
EXECUTE FUNCTION set_default_age();
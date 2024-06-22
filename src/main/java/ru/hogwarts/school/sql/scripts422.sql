-- scripts422.sql

-- Таблица людей
CREATE TABLE Person (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INT DEFAULT 20,
    has_license BOOLEAN NOT NULL
);

-- Таблица машин
CREATE TABLE Car (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Связующая таблица для людей и машин (многие ко многим)
CREATE TABLE Person_Car (
    person_id INT REFERENCES Person(id),
    car_id INT REFERENCES Car(id),
    PRIMARY KEY (person_id, car_id)
);
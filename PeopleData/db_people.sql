CREATE DATABASE IF NOT EXISTS db_people;
USE db_people;

CREATE TABLE IF NOT EXISTS person (
    id VARCHAR(50) PRIMARY KEY,
    nama VARCHAR(100) NOT NULL,
    thn_lahir INT NOT NULL,
    kategori VARCHAR(50) NOT NULL,
    gender VARCHAR(50) NOT NULL
    );

-- Data awal (dummy data)
INSERT INTO person (id, nama, thn_lahir, kategori, gender) VALUES
    ('P001', 'Budi Santoso', 1995, 'Mahasiswa', 'Laki-laki'),
    ('P002', 'Siti Aminah', 1990, 'Dosen', 'Perempuan');
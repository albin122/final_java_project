-- Create database (already created with utf8mb4)
USE library_db;

-- Books table
CREATE TABLE IF NOT EXISTS books (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(255) NOT NULL UNIQUE,
  available_count INT NOT NULL DEFAULT 0,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Students table
CREATE TABLE IF NOT EXISTS students (
  reg_no VARCHAR(64) PRIMARY KEY,
  name VARCHAR(255) NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Borrows table
CREATE TABLE IF NOT EXISTS borrows (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_reg_no VARCHAR(64) NOT NULL,
  book_id INT NOT NULL,
  borrowed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  returned_at TIMESTAMP NULL,
  CONSTRAINT fk_borrow_student FOREIGN KEY (student_reg_no) REFERENCES students(reg_no) ON DELETE CASCADE,
  CONSTRAINT fk_borrow_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE,
  CONSTRAINT uq_active_borrow UNIQUE (student_reg_no, book_id, returned_at)
);

-- Seed data (optional)
INSERT IGNORE INTO students (reg_no) VALUES
  ('STU001'), ('STU002'), ('STU003');

INSERT IGNORE INTO books (title, available_count) VALUES
  ('Clean Code', 3),
  ('Effective Java', 2),
  ('Design Patterns', 1);



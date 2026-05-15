CREATE TABLE IF NOT EXISTS todo (

   id INT AUTO_INCREMENT PRIMARY KEY,

    title VARCHAR(255),

    description VARCHAR(1000),

    status VARCHAR(50),

    priority VARCHAR(50),

    due_date DATE,

    created_at TIMESTAMP
    );
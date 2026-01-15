DROP DATABASE IF EXISTS elm_admin;
CREATE DATABASE elm_admin DEFAULT CHARACTER SET utf8mb4;
USE elm_admin;

CREATE TABLE admin (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    admin_name VARCHAR(50) NOT NULL UNIQUE,
    admin_password VARCHAR(64) NOT NULL
);

CREATE TABLE business (
    business_id INT PRIMARY KEY AUTO_INCREMENT,
    business_account VARCHAR(50) NOT NULL UNIQUE,
    business_password VARCHAR(64) NOT NULL,
    business_name VARCHAR(100) NOT NULL,
    business_phone VARCHAR(30),
    business_address VARCHAR(255),
    business_desc VARCHAR(255)
);

CREATE TABLE food (
    food_id INT PRIMARY KEY AUTO_INCREMENT,
    business_id INT NOT NULL,
    food_name VARCHAR(100) NOT NULL,
    food_price DECIMAL(10,2) NOT NULL,
    food_desc VARCHAR(255),
    food_status TINYINT NOT NULL DEFAULT 1,
    CONSTRAINT fk_food_business FOREIGN KEY (business_id)
        REFERENCES business (business_id)
        ON DELETE CASCADE
);

INSERT INTO admin (admin_name, admin_password) VALUES
('admin', 'admin123');

INSERT INTO business (business_account, business_password, business_name, business_phone, business_address, business_desc)
VALUES
('shop1', '123456', '示例商家', '13800000000', '一号路', '示例商家描述');

INSERT INTO food (business_id, food_name, food_price, food_desc, food_status)
VALUES
(1, '面条', 12.50, '清汤面', 1),
(1, '盖饭', 18.00, '肉类盖饭', 1);

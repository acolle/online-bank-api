# Online-Bank-API
API for an Online Banking System

## Access Database
CREATE USER 'bank'@'localhost' IDENTIFIED BY 'api';\n
CREATE SCHEMA bankapi;\n
GRANT ALL PRIVILEGES ON bankapi.* TO 'bank'@'localhost';

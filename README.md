# Dental Office Management System

A comprehensive web-based dental office management system developed with Java EE and MVC architecture.

## Features

- User management with roles (Doctors and Secretaries)
- Patient management and medical records
- Appointment scheduling system
- Doctor schedule management
- Responsive interface built with Bootstrap

## Technologies Used

- Java EE
- Jakarta Persistence (JPA)
- MySQL
- Bootstrap 5
- jQuery
- DataTables
- SweetAlert2

## Prerequisites

- JDK 11 or higher
- MySQL 8.0
- Maven
- Jakarta EE compatible application server (e.g., TomEE, GlassFish)

## Project Structure

src/
|
├── main/
|
│   ├── java/
|   |
│   │   └── gdbv/clinica/
|   |
│   │       ├── exceptions/    # Excepciones personalizadas
|   |       |
│   │       ├── models/        # Entidades y modelos
|   |       |
│   │       ├── persistence/   # Capa de persistencia
|   |       |
│   │       ├── services/      # Lógica de negocio
|   |       |
│   │       └── servlets/      # Controladores
|   |       
│   ├── resources/            # Archivos de configuración
|   |
│   └── webapp/              # Archivos web y JSPs

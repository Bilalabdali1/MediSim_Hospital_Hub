# Virtual Hospital Management System

## Overview
The Virtual Hospital Management System is an advanced object-oriented Java program designed to emulate real-world hospital operations. This system adeptly manages various entities such as Physicians, Administrators, Volunteer Workers, and Patients, providing a robust and efficient solution for simulating hospital operations. It extensively uses key Java principles like polymorphism, inheritance, encapsulation, and abstraction to ensure a modular, maintainable, and scalable architecture.

## General Information and Configuration
- **Team Composition**: The hospital team comprises Physicians, Administrators, and Volunteer Workers.
- **Patient Care**: The hospital admits patients and provides treatment.
- **Volunteer Management**: Volunteers are considered employees but are not paid. Each volunteer is recorded with details such as First Name, Last Name, Age, Gender, Employee ID, and Address.
- **Employee Information**: The system stores detailed information for each employee, physician, or administrator, including First Name, Last Name, Age, Gender, Employee ID, Address, and Salary.
- **Unique IDs**: Assigns unique employee IDs starting from 100 and patient IDs starting from 1000.
- **Patient-Physician Assignment**: Each patient is assigned a designated physician, and a physician can have multiple patients.
- **Volunteer Assignment**: Volunteers can be assigned to any physician.
- **Administrator Roles**: Includes two types: director (who manages all physician administrators) and physician administrator.
- **Physician Specialties**: The hospital features specialties like Immunology, Dermatology, and Neurology. Each physician administrator manages physicians within their specialty.
- **Duplicate Record Management**: Ensures no duplicate patient records are maintained.

## Features
- **Patient Management**: Handles patient admissions and discharges with unique IDs to prevent duplicate records.
- **Physician Allocation**: Implements a first-come-first-serve algorithm for assigning patients to physicians, supporting up to 70 physicians across various specialties.
- **Volunteer Coordination**: Manages volunteers and assigns them to appropriate physicians, avoiding duplication.
- **Information Tracking**: Maintains comprehensive records for stakeholders and offers sorted data access.
- **Specialty-Based Administration**: Organizes physicians into specialties, managed by designated administrators.
- **Exception Handling**: Employs Java's exception handling mechanisms for managing special cases and exceptional scenarios.
- **Polymorphism and Inheritance**: Utilizes these principles for flexible method implementation across different user types and actions.
- **Encapsulation and Abstraction**: Ensures data integrity and hides complexity from end-users.
## Installation
1. **Clone the repository**:
2.  git clone https://github.com/Bilalabdali1/MediSim_Hospital_Hub.git

## Usage
- **Interact with the system**: Use the interfaces provided to admit/discharge patients, manage volunteers, etc.

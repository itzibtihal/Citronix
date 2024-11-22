# Citronix

Citronix is a comprehensive farm management application tailored for lemon farms. It streamlines farm operations, including production, harvest tracking, sales management, and client communication, ensuring optimal productivity and seamless operations.

## Features

### Farm Management
- **CRUD Operations:** Manage farms with attributes like name, location, area, and creation date.
- **Search:** Multi-criteria search for farms using Criteria Builder.

### Field Management
- Associate fields with farms and manage their areas.
- Ensure that:
  - The total area of fields does not exceed the farm area.
  - Field area is between 0.1 hectare and 50% of the farm area.
- Limit farms to a maximum of 10 fields.

### Tree Management
- **Tree Tracking:** Manage tree attributes like planting date, age, and associated field.
- **Age Calculation:** Automatically calculate tree age.
- **Productivity Rules:**
  - Young Tree (< 3 years): 2.5 kg/season.
  - Mature Tree (3–10 years): 12 kg/season.
  - Old Tree (> 10 years): 20 kg/season.
- **Planting Constraints:** Only allow planting between March and May.

### Harvest Management
- Track seasonal harvests (winter, spring, summer, autumn).
- Enforce a single harvest per season.
- Record harvest details, including date and total quantity.
- Monitor harvest contributions per tree.

### Sales Management
- **Sales Tracking:** Record sales details such as date, unit price, client, and associated harvest.
- **Revenue Calculation:** Automate revenue calculation using `Revenue = Quantity × Unit Price`.

### Email Management
- Automatically send an English quotation ("devis en anglais") to clients after recording a sale.
- Integrate with SMTP servers for secure email delivery.
- Support dynamic email templates for personalized client communication.

### Constraints and Validation
- Maximum tree density: 100 trees per hectare.
- Trees become unproductive after 20 years.
- Fields cannot have multiple harvests per season.
- Trees can only contribute to one harvest per season.

## Technical Stack

### Backend
- **Framework:** Spring Boot for REST API development.
- **Architecture:** Layered (Controller, Service, Repository, Entity).
- **Data Validation:** Spring annotations for input validation.
- **Utilities:**
  - Lombok for simplifying boilerplate code.
  - MapStruct for DTO-to-entity mapping.
  - Centralized exception handling.
- **Testing:** JUnit and Mockito for unit testing.

### Database
- **Relational Database:** PostgreSQL (or similar SQL DBMS).
- **ORM Framework:** Hibernate for data persistence.

### Email Management
- **Library:** Spring Boot Mail for email communication.
- **Features:**
  - Dynamic templates for client emails.
  - Secure SMTP configuration.
  - Logs for email delivery tracking.

### Tools and Libraries
- **Build Tool:** Maven.
- **Version Control:** Git.
- **Documentation:** Swagger for API documentation.

## Setup Instructions

### Prerequisites
- Java 11+
- Maven
- PostgreSQL (or preferred SQL database)
- IDE (e.g., IntelliJ IDEA)

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/itzibtihal/Citronix

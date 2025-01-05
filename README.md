# Key Account Manager (KAM) Lead Management System

## Project Overview

The Key Account Manager (KAM) Lead Management System is designed for B2B e-commerce platforms to help Key Account Managers manage restaurant accounts efficiently. The system provides features such as:

* Managing leads and their contact details

* Tracking interactions with leads

* Call planning for regular follow-ups

* Performance tracking to identify high-performing and underperforming accounts

* The application is built using Java and Spring Boot, leveraging an H2 database for persistence.

## System Requirements

* Java: JDK 17 or higher

* Maven: 3.8 or higher

* Memory: At least 2GB RAM

* Disk Space: At least 500MB free space

* IDE: IntelliJ IDEA, Eclipse, or any Java-compatible IDE

## Installation Instructions

* Clone the Repository:

>git clone https://github.com/Akash-code-bit/udaam-kam.git
>
>cd kam-lead-mgmt``

* Build the Project:

>mvn clean install
>
>Run the Application:
>
>mvn spring-boot:run
>

## Running Instructions

1. Start the application:

>mvn spring-boot:run

2.  Access the application via:

* API Endpoints: http://localhost:8080

* H2 Database Console: http://localhost:8080/h2-console

* H2 Console Credentials:

  * URL: jdbc:h2:mem:testdb

  * Username: sa

  * Password: password

## Test Execution Guide

* Run the unit tests using the following command:

>mvn test

This will execute all JUnit and Mockito-based tests for the project.

## API Documentation

### Endpoints:

**Lead Management:**

* Create a Lead:

  * Method: POST

  * URL: /api/leads

  * Request Body:
    ```json
    {
    "restaurantName": "Example Restaurant",
    "status": "New", 
    "callFrequency": 7 
    }

  * Response:

    ```json
    {
    "id": 1,
    "restaurantName": "Example Restaurant",
    "status": "New",
    "callFrequency": 7
    }

* Get All Leads:

  * Method: GET

  * URL: /api/leads

  * Response:

    ````json
    [
    {
    "id": 1,
    "restaurantName": "Example Restaurant",
    "status": "New",
    "callFrequency": 7
    }
    ]

**Contact Management:**
* Add Contact to Lead:

  * Method: POST

  * URL: /api/lead/{leadId}/addcontact

  * Request Body:
    ````json
    {
    "name": "John Doe",
    "role": "Manager",
    "contactInfo": "johndoe@example.com"
    }

* Get Contacts for a Lead:

Method: GET

URL: /api/lead/{leadId}/contacts

**Interaction Management:**

* Add Interaction to Lead:

  * Method: POST

  * URL: /api/lead/{leadId}/interactions

  * Request Body:

    ```json
    {
    "details": "Initial Discussion"
    }

* Get Interactions for a Lead:

  * Method: GET

  * URL: /api/lead/{leadId}/interactions

**Performance and Call Planning:**

* Get High-Performing Leads:**

  * Method: GET

  * URL: /api/leads/high-performing?minOrders={value}

* **Get Underperforming Leads:**

  * Method: GET

  * URL: /api/leads/underperforming?minOrders={value}

* Get Leads Requiring Calls:

  * Method: GET

  * URL: /api/leads/requires-call

## Sample Usage Examples

### Create a Lead:

    curl -X POST http://localhost:8080/api/leads \
    -H "Content-Type: application/json" \
    -d '{
    "restaurantName": "Example Restaurant",
    "status": "New",
    "callFrequency": 7
    }'

### Get All Leads:

    curl -X GET http://localhost:8080/api/leads

## Add a Contact to a Lead:

    curl -X POST http://localhost:8080/api/contacts/lead/1 \
    -H "Content-Type: application/json" \
    -d '{
    "name": "John Doe",
    "role": "Manager",
    "contactInfo": "johndoe@example.com"
    }'

### Get High-Performing Leads:

    curl -X GET http://localhost:8080/api/leads/high-performing?minOrders=10


# EcoStruxure IT Rate Calculation Tool

This project is developed to dynamically calculate detailed regional or country day rates for EcoStruxure IT Software Services, which include installation, configuration, and customization services. The tool aims to replace the manual Excel-based rate calculation method with a more reliable and streamlined application.

# Functional Requirements
## Profiles
### Users can configure individual employee profiles with the following details:
- Annual salary.
- Overhead multiplier percentage.
- Configurable fixed annual amount .
- Geography or country.
- Annual effective working hours.
- Utilization percentage (0-100%).
- Cost classification (Overhead or Production Resource).

## Rate Calculation
### Based on the employee profiles, the application calculates day rates and hourly rates using the following parameters:
- Annual salary
- Overhead multiplier
- Configurable fixed annual amount
- Annual effective working hours
- Utilization percentage

## Scenarios:
### Users can create different scenarios based on project, where profiles can be added for management:
- Group profiles with different scenarios
- Customizable
- Editable

# Technical Requirements
- Implemented in Java as a JavaFX desktop application using IntelliJ IDE.
- Persistent data management using MSSQL Server.
- Proper documentation for each sprint including sprint-planning activities, product design issues, and important decisions.
- Utilization of design patterns in design and implementation, documented accordingly.
- At least one core class must be tested through automated JUnit testing.

## Getting Started
- Clone this repository.
- Set up VPN connected to school database.
- Open the project in IntelliJ IDE.
- Import the following libraries through maven: "org.junit.jupiter:junit-jupiter:5.10.2" and "microsoft.sqlserver:mssql-jdbc:12.6.0.jre11"
- Mark the "resources" folder as resources root
- Run the application and follow the user interface for functionalities.


## Authors
- [@Zorehan](https://github.com/Zorehan)
- [@Nicklas Kramer](https://github.com/NillasKA)
- [@RuneKrogh](https://github.com/RuneKrogh)

## Roadmap
- [Scrumwise Link](https://www.scrumwise.com/scrum/#/overview/project/snickers-electric_cs2023-dk-eksamen/id-36893-40414-143)

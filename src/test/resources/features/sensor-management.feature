Feature: Sensor Management
  As an administrator
  I want to manage IoT sensors
  So that I can monitor device data

  Scenario: List all sensors
    Given I am authenticated as an administrator for sensor management
    When I request the list of sensors
    Then the sensor endpoint should respond

  Scenario: Get sensor by ID
    Given I am authenticated as an administrator for sensor management
    And there is a sensor with ID 1
    When I request sensor details for ID 1
    Then the sensor endpoint should respond

  Scenario: Find sensors by type
    Given I am authenticated as an administrator for sensor management
    When I search for sensors with type "temperature"
    Then the sensor endpoint should respond


Feature: Energy Consumption Management
  As an administrator
  I want to manage energy consumption data
  So that I can analyze energy usage

  Scenario: List all consumption records
    Given I am authenticated as an administrator for consumption management
    When I request the list of consumption records
    Then the consumption endpoint should respond

  Scenario: Get consumption by ID
    Given I am authenticated as an administrator for consumption management
    And there is a consumption record with ID 1
    When I request consumption details for ID 1
    Then the consumption endpoint should respond

  Scenario: Find consumption by device
    Given I am authenticated as an administrator for consumption management
    And there is a device with ID 1 for consumption
    When I request consumption records for device ID 1
    Then the consumption endpoint should respond

  Scenario: Get non-existent consumption record
    Given I am authenticated as an administrator for consumption management
    And there is a consumption record with ID 999999
    When I request consumption details for ID 999999
    Then the consumption response status should be 404


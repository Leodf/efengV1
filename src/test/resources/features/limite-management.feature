Feature: Consumption Limit Management
  As an administrator
  I want to configure consumption limits
  So that I can control energy usage

  Scenario: List all consumption limits
    Given I am authenticated as an administrator for limit management
    When I request the list of consumption limits
    Then the limit endpoint should respond

  Scenario: Get limit by ID
    Given I am authenticated as an administrator for limit management
    And there is a limit with ID 1
    When I request limit details for ID 1
    Then the limit endpoint should respond

  Scenario: Find limits by location
    Given I am authenticated as an administrator for limit management
    When I search for limits at location "Building A"
    Then the limit endpoint should respond


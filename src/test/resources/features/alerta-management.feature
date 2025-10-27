Feature: Energy Alert Management
  As an administrator
  I want to manage energy alerts
  So that I can monitor consumption warnings

  Scenario: List all alerts
    Given I am authenticated as an administrator for alert management
    When I request the list of alerts
    Then the alert endpoint should respond

  Scenario: Get alert by ID
    Given I am authenticated as an administrator for alert management
    And there is an alert with ID 1
    When I request alert details for ID 1
    Then the alert endpoint should respond

  Scenario: Find alerts by status
    Given I am authenticated as an administrator for alert management
    When I search for alerts with status "PENDENTE"
    Then the alert endpoint should respond


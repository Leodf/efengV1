Feature: User Registration
  As a user
  I want to register a new account
  So that I can access the system

  Scenario: Register endpoint is accessible
    Given I am registering with name "John Doe", email "john@test.com" and password "password123"
    When I submit the registration
    Then the registration endpoint should respond

  Scenario: Register with invalid email format
    Given I am registering with name "John Doe", email "invalid-email" and password "password123"
    When I submit the registration
    Then the registration response status should be 400

  Scenario: Register with empty password
    Given I am registering with name "John Doe", email "john@test.com" and password ""
    When I submit the registration
    Then the registration response status should be 400

  Scenario: Register with duplicate email
    Given I am registering with name "John Doe", email "duplicate@test.com" and password "password123"
    When I submit the registration
    Then the registration response status should be 409


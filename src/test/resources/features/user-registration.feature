Feature: User Registration
  As a user
  I want to register a new account
  So that I can access the system

  Scenario: Register endpoint is accessible
    Given I am registering with name "John Doe", email "john@test.com" and password "password123"
    When I submit the registration
    Then the registration endpoint should respond


Feature: User Login
  As a registered user
  I want to login with my credentials
  So that I can access the system

  Scenario: Login endpoint is accessible
    Given I have login credentials email "test@email.com" and password "password123"
    When I attempt to login
    Then the login endpoint should respond

  Scenario: Login with wrong password
    Given I have login credentials email "valid@email.com" and password "wrongpassword"
    When I attempt to login
    Then the login response should fail with status 401

  Scenario: Login with non-existent user
    Given I have login credentials email "nonexistent@email.com" and password "password123"
    When I attempt to login
    Then the login response should fail with status 401

  Scenario: Login with invalid email format
    Given I have login credentials email "invalid-email" and password "password123"
    When I attempt to login
    Then the login response should fail with status 400


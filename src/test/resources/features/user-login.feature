Feature: User Login
  As a registered user
  I want to login with my credentials
  So that I can access the system

  Scenario: Login endpoint is accessible
    Given I have login credentials email "test@email.com" and password "password123"
    When I attempt to login
    Then the login endpoint should respond


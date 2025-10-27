Feature: User Management
  As an administrator
  I want to manage users
  So that I can control access to the system

  Scenario: List all users
    Given I am authenticated as an administrator for user management
    When I request the list of users
    Then I should receive a list of users

  Scenario: Get user by ID
    Given I am authenticated as an administrator for user management
    And there is a user with ID 1
    When I request user details for ID 1
    Then I should receive user details

  Scenario: Update user information
    Given I am authenticated as an administrator for user management
    And there is a user with ID 1
    When I update the user with new information
    Then the user should be updated

  Scenario: Delete a user
    Given I am authenticated as an administrator for user management
    And there is a user with ID 1
    When I delete the user with ID 1
    Then the user should be deleted

  Scenario: Get non-existent user
    Given I am authenticated as an administrator for user management
    And there is a user with ID 999999
    When I request user details for ID 999999
    Then the user response status should be 404

  Scenario: List users without authentication
    Given I am not authenticated for user management
    When I request the list of users without auth
    Then the response status should be 401


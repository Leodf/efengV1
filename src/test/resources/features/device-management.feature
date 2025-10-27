Feature: Device Management
  As a system administrator
  I want to manage IoT devices
  So that I can monitor energy consumption

  Scenario: Create a new device
    Given I am authenticated as an administrator for device management
    And I have device information for "Device 1" at location "Building A"
    When I create the device
    Then the device should be created successfully

  Scenario: Retrieve device information
    Given I am authenticated as an administrator for device management
    And there is a device with ID 1
    When I request device details for ID 1
    Then I should receive device information

  Scenario: List all devices
    Given I am authenticated as an administrator for device management
    When I request the list of devices
    Then I should receive a list of devices

  Scenario: Get non-existent device
    Given I am authenticated as an administrator for device management
    And there is a device with ID 999999
    When I request device details for ID 999999
    Then the device response status should be 404


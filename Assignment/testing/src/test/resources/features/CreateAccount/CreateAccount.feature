Feature: Create Account
  As a user
  I want to create a new account
  So that I can access the system

  Background:
    Given the user is on the login page
    When the user enters valid credentials
    And hits submit
    Then the user should be logged in successfully

  Scenario: User creates a new account
    Given the user is on the create account page
    When the user enters the following account details:
      | Name       | Anusha       |
      | Password   | Password123   |
      | Email      | anusha@example.com |
      | Address    | 123 Main St    |
    And hits create
    Then the user should see a success message
    And the new account should be listed on the accounts page

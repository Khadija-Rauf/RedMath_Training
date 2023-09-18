Feature: Update Account
  As a user
  I want to update an existing account
  So that I can modify account information

  Background:
    Given the user is on the login page
    When the user enters valid credentials
    And hits submit
    Then the user should be logged in successfully

  Scenario: User updates an account
    Given the user is on accounts page
    When the user selects the account with ID "3"
    When the user enters valid updated account details
      | Name       | Anusha       |
      | Password   | Password123   |
      | Email      | anusha@example.com |
      | Address    | 123 Main St    |
    And hits update
    Then the new updated_account should be listed on the accounts page

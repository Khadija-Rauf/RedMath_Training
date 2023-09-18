Feature: Create Transaction
  As a user
  I want to create a new transaction

  Background:
    Given the user is on the login page
    When the user enters valid credentials
    And hits submit
    Then the user should be logged in successfully

  Scenario: User creates a new transaction
    Given the user is on the view account page with ID "2"
    When the user enters the following transaction details:
      | Description       | Online Purchase |
      | Amount            | 1000|
      | Indicator         | 0 |
    And hits create
    Then the user should see a success message
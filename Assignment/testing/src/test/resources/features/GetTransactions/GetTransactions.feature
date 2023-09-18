Feature: Get Transactions
  As a user
  I want to get all transactions

  Background:
    Given the user is on the login page
    When the user enters valid credentials
    And hits submit
    Then the user should be logged in successfully

  Scenario: User is getting all transactions of an account
    Given the user is on transactions page with ID "1"
    Then the user should see a list of transactions
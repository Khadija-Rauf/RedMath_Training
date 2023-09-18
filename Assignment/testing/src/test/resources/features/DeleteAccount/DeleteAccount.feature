Feature: Delete Account
  As a user
  I want to delete an account
  So that I can remove an existing user account

  Background:
    Given the user is on the login page
    When the user enters valid credentials
    And hits submit
    Then the user should be logged in successfully

  Scenario: User deletes an account
    Given the user is on accounts page
    When the user deletes the account with ID "3"
    Then the account with ID "3" should be deleted
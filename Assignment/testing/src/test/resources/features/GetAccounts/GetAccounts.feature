Feature: GetAccounts
  As a user
  I want to be redirected to the page after logging in
  So that I can manage user accounts

  Background:
    Given the user is on the login page
    When the user enters valid credentials
    And hits submit
    Then the user should be logged in successfully

  Scenario: User is automatically redirected to Accounts page
    Given the user is on accounts page
    Then the user should see a list of accounts
@vodqa
Feature: Scenario for the "Vodqa" app

  Scenario: Put app in background
    Given I login to vodqa application using credentials
    When I navigate to Home screen button
    Then App should work in background
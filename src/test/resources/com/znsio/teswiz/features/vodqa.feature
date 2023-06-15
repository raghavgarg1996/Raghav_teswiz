@vodqa @android
Feature: Scenario for the "Vodqa" app

#  CONFIG= ./configs/vodqa_local_config.properties TAG=vodqaAppInBackground ./gradlew run
  @vodqaAppInBackground
  Scenario: Put app in background
    Given I login to vodqa application using credentials
    Then App should work in background for 5 sec
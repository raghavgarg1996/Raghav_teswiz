@amazon

Feature: Amazon tests

  #  CONFIG=./configs/amazon_local_config.properties PLATFORM=web TAG=setHideScrollBar ./gradlew run
  @web @setHideScrollBar
    Scenario: As a guest user perform scroll action
    Given I as a guest user perform scroll




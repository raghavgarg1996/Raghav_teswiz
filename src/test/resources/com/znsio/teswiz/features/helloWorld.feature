@helloWorld

Feature: HelloWorld test

# CONFIG=.configs/helloworld_local_ios_config.properties TAG="@helloWorld" PLATFORM=iOS ./gradlew run
  @iOS
  Scenario: Validate click action in IOS app
    Given I  press Click Me button
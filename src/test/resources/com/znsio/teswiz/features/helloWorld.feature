@helloWorld @prod

Feature: HelloWorld tests
#  CONFIG=./configs/helloworld_local_ios_config.properties TAG="@helloWorld" PLATFORM=iOS ./gradlew run
  @iOS
  Scenario: As a guest user, I should be able to generate random numbers and see the thumbs up icon
    Given I make the number random "1" times
    And I press Click Me
    Then I can see the Thumbs Up icon
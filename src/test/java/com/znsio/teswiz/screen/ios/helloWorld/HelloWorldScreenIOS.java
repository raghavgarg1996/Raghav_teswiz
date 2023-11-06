package com.znsio.teswiz.screen.ios.helloWorld;

import com.znsio.teswiz.runner.Driver;
import com.znsio.teswiz.runner.Visual;
import com.znsio.teswiz.screen.android.ScreenShotScreenAndroid;
import com.znsio.teswiz.screen.helloWorld.HelloWorldScreen;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class HelloWorldScreenIOS extends HelloWorldScreen {
    private final Driver driver;
    private final Visual visually;
    private final String SCREEN_NAME = ScreenShotScreenAndroid.class.getSimpleName();
    private final By byMakeRandomNumberCheckbox = AppiumBy.accessibilityId("MakeRandomNumberCheckbox");
    private final By bySimulateDifferenceCheckbox = AppiumBy.accessibilityId("SimulateDiffsCheckbox");


    public HelloWorldScreenIOS(Driver driver, Visual visually) {
        this.driver = driver;
        this.visually = visually;
    }

    @Override
    public HelloWorldScreen generateRandomNumber(int counter) {
        visually.checkWindow(SCREEN_NAME, "MakeRandomNumberCheckbox-beforeClick-" + counter);
        driver.findElement(byMakeRandomNumberCheckbox)
                .click();
        visually.checkWindow(SCREEN_NAME, "MakeRandomNumberCheckbox-afterClick-" + counter);
        return this;
    }

    @Override
    public HelloWorldScreen pressClickMeBtn() {
        driver.findElement(AppiumBy.accessibilityId(""))
        return this;
    }

    @Override
    public HelloWorldScreen simulateAdditionalDifference() {
        visually.checkWindow(SCREEN_NAME, "SimulateDiffsCheckbox-beforeClick-");
        driver.findElement(bySimulateDifferenceCheckbox).click();
        visually.checkWindow(SCREEN_NAME, "SimulateDiffsCheckbox-afterClick-");
        return this;
    }
}

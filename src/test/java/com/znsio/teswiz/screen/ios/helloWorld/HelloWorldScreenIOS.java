package com.znsio.teswiz.screen.ios.helloWorld;

import com.znsio.teswiz.runner.Driver;
import com.znsio.teswiz.runner.Visual;
import com.znsio.teswiz.screen.helloWorld.HelloWorldScreen;
import org.apache.log4j.Logger;

public class HelloWorldScreenIOS extends HelloWorldScreen {

    private static final String SCREEN_NAME = HelloWorldScreenIOS.class.getSimpleName();
    private static final Logger LOGGER = Logger.getLogger(SCREEN_NAME);
    private final Driver driver;
    private final Visual visually;

    public HelloWorldScreenIOS(Driver driver, Visual visually) {
        this.driver = driver;
        this.visually = visually;
    }

    @Override
    public HelloWorldScreen clickOnClickButton() {

        return this;
    }

    @Override
    public boolean validateButtonClicked() {

        return false;
    }
}

package com.znsio.teswiz.screen.android.vodqa;

import com.znsio.teswiz.runner.Driver;
import com.znsio.teswiz.runner.Visual;
import com.znsio.teswiz.screen.vodqa.AndroidHomeScreen;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AndroidHomeScreenAndroid extends AndroidHomeScreen {
    private final Driver driver;
    private final Visual visually;
    private final String SCREEN_NAME = AndroidHomeScreenAndroid.class.getSimpleName();

    public AndroidHomeScreenAndroid(Driver driver, Visual visually) {
        this.driver = driver;
        this.visually = visually;
    }


    @Override
    public AndroidHomeScreen validateAppWorkInBackground(int time) {
        driver.putAppInBackground(time);
        return this;
    }

    @Override
    public boolean validateHomeScreen() {
        boolean isHomeScreenOpened = false;
        WebElement appDrawerButton = driver.findElement(By.xpath(""));
        if (appDrawerButton.isDisplayed()) {
            isHomeScreenOpened = true;
        }
        return isHomeScreenOpened;
    }
}

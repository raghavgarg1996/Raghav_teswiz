package com.znsio.teswiz.screen.android.vodqa;

import com.znsio.teswiz.businessLayer.vodqa.AndroidHomeBL;
import com.znsio.teswiz.runner.Driver;
import com.znsio.teswiz.runner.Visual;
import com.znsio.teswiz.screen.vodqa.AndroidHomeScreen;
import com.znsio.teswiz.screen.vodqa.VodqaScreen;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
public class VodqaScreenAndroid extends VodqaScreen {
    public static final By byHomeScreenButtonXpath = AppiumBy.xpath("");
    private final Driver driver;
    private final Visual visually;
    private final String SCREEN_NAME = VodqaScreenAndroid.class.getSimpleName();
    private final By byLoginButton = AppiumBy.xpath("//android.view.ViewGroup[@content-desc='login']/android.widget.Button");


    public VodqaScreenAndroid(Driver driver, Visual visually) {
        this.driver = driver;
        this.visually = visually;
    }

    @Override
    public VodqaScreen login() {
        driver.waitTillElementIsPresent(byLoginButton);
        driver.findElement(byLoginButton).click();
        return this;
    }

    public AndroidHomeScreen clickOnHomeButton() {
        driver.waitTillElementIsPresent(byHomeScreenButtonXpath);
        driver.putAppInBackground(34);
        return AndroidHomeScreen.get();
    }


}

package com.znsio.teswiz.runner;

import com.google.common.collect.ImmutableMap;
import com.znsio.teswiz.entities.Platform;
import com.znsio.teswiz.exceptions.FileNotUploadedException;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.HidesKeyboard;
import io.appium.java_client.MobileBy;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.HasNotifications;
import io.appium.java_client.android.StartsActivity;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.SupportsContextSwitching;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.znsio.teswiz.tools.Wait.waitFor;
import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static java.util.Collections.singletonList;

public class Driver {
    public static final String WEB_DRIVER = "WebDriver";
    public static final String APPIUM_DRIVER = "AppiumDriver";
    private static final Logger LOGGER = Logger.getLogger(Driver.class.getName());
    private final String type;
    private final WebDriver driver;
    private final String userPersona;
    private final String appName;
    private final Platform driverForPlatform;
    private static final String DIMENSION = "dimension: ";
    private static final String FROM_HEIGHT_TO_HEIGHT = "width: %s, from height: %s, to height: %s";
    private final boolean isRunningInHeadlessMode;
    private static final String TO = "' to '";
    private Visual visually;

    Driver(String testName, Platform forPlatform, String userPersona, String appName,
           AppiumDriver appiumDriver) {
        this.driver = appiumDriver;
        this.type = APPIUM_DRIVER;
        this.userPersona = userPersona;
        this.appName = appName;
        this.driverForPlatform = forPlatform;
        this.isRunningInHeadlessMode = false;
        instantiateEyes(testName, appiumDriver);
    }

    Driver(String testName, Platform forPlatform, String userPersona, String appName,
           WebDriver webDriver, boolean isRunInHeadlessMode) {
        this.driver = webDriver;
        this.type = WEB_DRIVER;
        this.userPersona = userPersona;
        this.appName = appName;
        this.driverForPlatform = forPlatform;
        this.isRunningInHeadlessMode = isRunInHeadlessMode;
        instantiateEyes(testName, webDriver);
    }

    private void instantiateEyes(String testName, AppiumDriver innerDriver) {
        this.visually = new Visual(this.type, this.driverForPlatform, innerDriver, testName, userPersona, appName);
    }

    private void instantiateEyes(String testName, WebDriver innerDriver) {
        this.visually = new Visual(this.type, this.driverForPlatform, innerDriver, testName,
                                   userPersona, appName);
    }

    public WebElement waitForClickabilityOf(String elementId) {
        return waitForClickabilityOf(elementId, 10);
    }

    public WebElement waitForClickabilityOf(String elementId, int numberOfSecondsToWait) {
        return (new WebDriverWait(driver, Duration.ofSeconds(numberOfSecondsToWait))).until(ExpectedConditions.elementToBeClickable(findElementByAccessibilityId(elementId)));
    }

    public WebElement findElementByAccessibilityId(String locator) {
        return driver.findElement(AppiumBy.accessibilityId(locator));
    }

    public void waitForAlert() {
        waitForAlert(10);
    }

    public void waitForAlert(int numberOfSecondsToWait) {
        new WebDriverWait(driver, Duration.ofSeconds(numberOfSecondsToWait)).until(ExpectedConditions.alertIsPresent());
        driver.switchTo()
              .alert();
    }

    public WebElement findElement(By elementId) {
        return driver.findElement(elementId);
    }

    public void hideKeyboard() {
        ((HidesKeyboard) driver).hideKeyboard();
    }

    public List<WebElement> findElements(By element) {
        return this.driver.findElements(element);
    }

    public WebElement findElementById(String locator) {
        return driver.findElement(By.id(locator));
    }

    public WebElement findElementByXpath(String locator) {
        return driver.findElement(By.xpath(locator));
    }

    public void scroll(Point fromPoint, Point toPoint) {
        AppiumDriver appiumDriver = (AppiumDriver) this.driver;
        PointerInput touch = new PointerInput(PointerInput.Kind.TOUCH, "touch");
        Sequence scroller = new Sequence(touch, 1);
        scroller.addAction(touch.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), toPoint.getX(), toPoint.getY()));
        scroller.addAction(touch.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        scroller.addAction(touch.createPointerMove(Duration.ofSeconds(1), PointerInput.Origin.viewport(), fromPoint.getX(), fromPoint.getY()));
        scroller.addAction(touch.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        LOGGER.info(String.format("fromPoint width: %s, fromPoint height: %s", fromPoint.getX(), fromPoint.getY()));
        LOGGER.info(String.format("toPoint width: %s, toPoint height: %s", toPoint.getX(), toPoint.getY()));
        appiumDriver.perform(singletonList(scroller));
    }

    public WebElement scrollToAnElementByText(String text) {
        return driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector())" + ".scrollIntoView(new UiSelector().text(\"" + text + "\"));"));
    }

    public boolean isElementPresent(By locator) {
        return !driver.findElements(locator).isEmpty();
    }

    public boolean isElementPresentByAccessibilityId(String locator) {
        return driver.findElements(AppiumBy.accessibilityId(locator))
                                      .size() > 0;
    }

    public boolean isElementPresentWithin(WebElement parentElement, By locator) {
        return !parentElement.findElements(locator).isEmpty();
    }

    public void scrollDownByScreenSize() {
        AppiumDriver appiumDriver = (AppiumDriver) this.driver;
        Dimension windowSize = appiumDriver.manage().window().getSize();
        LOGGER.info(DIMENSION + windowSize.toString());
        int width = windowSize.width / 2;
        int fromHeight = (int) (windowSize.height * 0.2);
        int toHeight = (int) (windowSize.height * 0.8);
        LOGGER.info(String.format("width: %s, from height: %s, to height: %s", width, fromHeight, toHeight));
        Point from=new Point(width,fromHeight);
        Point to=new Point(width,toHeight);
        scroll(from,to);
    }

    public void scrollVertically(int fromPercentScreenHeight, int toPercentScreenHeight,
                                 int percentScreenWidth) {
        AppiumDriver appiumDriver = (AppiumDriver) this.driver;
        Dimension windowSize = appiumDriver.manage().window().getSize();
        LOGGER.info(DIMENSION + windowSize.toString());
        int width = (windowSize.width * percentScreenWidth) / 100;
        int fromHeight = (windowSize.height * fromPercentScreenHeight) / 100;
        int toHeight = (windowSize.height * toPercentScreenHeight) / 100;
        LOGGER.info(String.format("width: %s, from height: %s, to height: %s", width, fromHeight, toHeight));
        LOGGER.info(String.format("width: %s, from height: %s, to height: %s", width, fromHeight, toHeight));
        Point from=new Point(width,fromHeight);
        Point to=new Point(width,toHeight);
        scroll(from,to);
    }

    public void tapOnMiddleOfScreen() {
        if(this.type.equals(Driver.APPIUM_DRIVER)) {
            tapOnMiddleOfScreenOnDevice();
        } else {
            simulateMouseMovementOnBrowser();
        }
    }

    private void tapOnMiddleOfScreenOnDevice() {
        AppiumDriver appiumDriver = (AppiumDriver) this.driver;
        Dimension screenSize = appiumDriver.manage().window().getSize();
        int midHeight = screenSize.height / 2;
        int midWidth = screenSize.width / 2;
        LOGGER.info(String.format("tapOnMiddleOfScreen: Screen dimensions: '%s'. Tapping on coordinates: %d:%d%n", screenSize, midWidth, midHeight));
        PointerInput touch = new PointerInput(PointerInput.Kind.TOUCH, "touch");
        Sequence clickPosition = new Sequence(touch, 1);
        clickPosition.addAction(touch.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), midWidth,midHeight))
                .addAction(touch.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(touch.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        appiumDriver.perform(Arrays.asList(clickPosition));
        waitFor(1);
    }

    private void simulateMouseMovementOnBrowser() {
        Actions actions = new Actions(this.driver);
        Dimension screenSize = driver.manage().window().getSize();
        Point currentPosition = driver.manage().window().getPosition();

        int midHeight = screenSize.height / 2;
        int midWidth = screenSize.width / 2;
        int currentPositionX = currentPosition.getX();
        int currentPositionY = currentPosition.getY();
        LOGGER.info(
                String.format("Current position: '%d':'%d'", currentPositionX, currentPositionY));

        int offsetX = currentPositionX < midWidth ? 50 : -50;
        int offsetY = currentPositionY < midHeight ? 50 : -50;

        LOGGER.info(String.format("Using offset: '%d':'%d'", offsetX, offsetY));

        actions.moveByOffset(offsetX, offsetY).perform();
        waitFor(1);
    }

    private int getWindowHeight() {
        AppiumDriver appiumDriver = (AppiumDriver) this.driver;
        Dimension windowSize = appiumDriver.manage().window().getSize();
        LOGGER.info(DIMENSION + windowSize.toString());
        return windowSize.height;
    }

    private int getWindowWidth() {
        AppiumDriver appiumDriver = (AppiumDriver) this.driver;
        return appiumDriver.manage().window().getSize().width;
    }

    private void checkPercentagesAreValid(int... percentages) {
        boolean arePercentagesValid = Arrays.stream(percentages).allMatch(percentage -> percentage >= 0 && percentage <= 100);
        if (!arePercentagesValid) {
            throw new RuntimeException(String.format("Invalid percentage value - percentage value should be between 0 - 100. but are %s",
                    Arrays.toString(percentages)));
        }
    }

    public void swipeRight() {
        int height = getWindowHeight() / 2;
        int fromWidth = (int) (getWindowWidth() * 0.2);
        int toWidth = (int) (getWindowWidth() * 0.7);
        LOGGER.info(String.format("height: %s, from width: %s, to width: %s", height, fromWidth, toWidth));
        swipe(height, fromWidth, toWidth);
    }

    public void swipeLeft() {
        int height = getWindowHeight() / 2;
        int fromWidth = (int) (getWindowWidth() * 0.8);
        int toWidth = (int) (getWindowWidth() * 0.3);
        LOGGER.info(String.format("height: %s, from width: %s, to width: %s", height, fromWidth, toWidth));
        swipe(height, fromWidth, toWidth);
    }

    public void swipeByPassingPercentageAttributes(int percentScreenHeight, int fromPercentScreenWidth, int toPercentScreenWidth) {
        LOGGER.info(String.format("percent attributes passed to method are: percentScreenHeight: %s, fromPercentScreenWidth: %s, toPercentScreenWidth: %s",
                percentScreenHeight, fromPercentScreenWidth, toPercentScreenWidth));
        checkPercentagesAreValid(percentScreenHeight, fromPercentScreenWidth, toPercentScreenWidth);
        int height = getWindowHeight() * percentScreenHeight / 100;
        int fromWidth = getWindowWidth() * fromPercentScreenWidth / 100;
        int toWidth = getWindowWidth() * toPercentScreenWidth / 100;
        LOGGER.info(String.format("swipe gesture at height: %s, from width: %s, to width: %s", height, fromWidth, toWidth));
        swipe(height, fromWidth, toWidth);
    }

    private void swipe(int height, int fromWidth, int toWidth) {
        AppiumDriver appiumDriver = (AppiumDriver) this.driver;
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1);
        sequence.addAction(finger.createPointerMove(ofMillis(0), PointerInput.Origin.viewport(), fromWidth, height));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
        sequence.addAction(new Pause(finger, ofSeconds(1)));
        sequence.addAction(finger.createPointerMove(ofSeconds(1), PointerInput.Origin.viewport(), toWidth, height));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
        appiumDriver.perform(singletonList(sequence));
    }

    public void openNotifications() {
        LOGGER.info("Fetching the NOTIFICATIONS on the device: ");
        waitFor(3);
        ((HasNotifications) driver).openNotifications();
        waitFor(2);
    }

    public void selectNotificationFromNotificationDrawer(By selectNotificationLocator) {
        AppiumDriver appiumDriver = (AppiumDriver) this.driver;
        Dimension screenSize = appiumDriver.manage().window().getSize();
        PointerInput touch = new PointerInput(PointerInput.Kind.TOUCH, "touch");
        Sequence dragNotificationBar = new Sequence(touch, 1);
        dragNotificationBar.addAction(touch.createPointerMove(Duration.ofSeconds(0), PointerInput.Origin.viewport(), screenSize.width / 2, 0));
        dragNotificationBar.addAction(touch.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        dragNotificationBar.addAction(touch.createPointerMove(Duration.ofSeconds(1), PointerInput.Origin.viewport(), screenSize.width / 2, screenSize.height));
        dragNotificationBar.addAction(touch.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        appiumDriver.perform(singletonList(dragNotificationBar));
        appiumDriver.perform(singletonList(dragNotificationBar));
        waitFor(1);

        WebElement selectNotificationElement = driver.findElement(selectNotificationLocator);
        LOGGER.info("Notification found: " + selectNotificationElement.isDisplayed());
        selectNotificationElement.click();
    }

    public void putAppInBackground(int duration) {
        throw new NotImplementedException("To be migrated to appium 2.0");
        // todo - implement for appium2.0
//        ((AppiumDriver) driver).runAppInBackground(Duration.ofSeconds(duration));
    }

    public void bringAppInForeground() {
        ((StartsActivity) driver).currentActivity();
    }

    public void goToDeepLinkUrl(String url, String packageName) {
        LOGGER.info("Hitting a Deep Link URL: " + url);
        ((AppiumDriver) driver).executeScript("mobile:deepLink",
                                              ImmutableMap.of("url", url, "package", packageName));
    }

    public WebDriver getInnerDriver() {
        return driver;
    }

    public String getType() {
        return this.type;
    }

    public Visual getVisual() {
        return this.visually;
    }

    public void longPress(By elementId) {
        WebElement elementToBeLongTapped =
                new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(elementId));

        TouchAction action = new TouchAction((PerformsTouchActions) driver);
        action.longPress(LongPressOptions.longPressOptions()
                                         .withElement(ElementOption.element(elementToBeLongTapped)))
              .release().perform();
    }

    public void pushFileToDevice(String filePathToPush, String devicePath) {
        LOGGER.info("Pushing the file: '" + filePathToPush + TO + Runner.getPlatform()
                                                                        .name() + "' " + "device "
                    + "on path: '" + devicePath + "'");
        try {
            if(Runner.getPlatform().equals(Platform.android)) {
                ((AndroidDriver) driver).pushFile(devicePath, new File(filePathToPush));
            } else if(Runner.getPlatform().equals(Platform.iOS)) {
                ((IOSDriver) driver).pushFile(devicePath, new File(filePathToPush));
            }
        } catch(IOException e) {
            throw new FileNotUploadedException(
                    String.format("Error in pushing the file: '%s%s%s' device on path: '%s'",
                                  filePathToPush, TO, Runner.getPlatform().name(), devicePath), e);
        }
    }

    public void allowPermission(By element) {
        waitForClickabilityOf(element);
        if(Runner.getPlatform().equals(Platform.android)) {
            driver.findElement(element).click();
        }
    }

    public WebElement waitForClickabilityOf(By elementId) {
        return waitForClickabilityOf(elementId, 10);
    }

    public WebElement waitForClickabilityOf(By elementId, int numberOfSecondsToWait) {
        return (new WebDriverWait(driver, Duration.ofSeconds(numberOfSecondsToWait)).until(ExpectedConditions.elementToBeClickable(elementId)));
    }

    public List<WebElement> findElementsByAccessibilityId(String elementId) {
        return ((AppiumDriver) driver).findElements(AppiumBy.accessibilityId(elementId));
    }

    public WebElement waitTillElementIsPresent(By elementId) {
        return waitTillElementIsPresent(elementId, 10);
    }

    public WebElement waitTillElementIsVisible(By elementId) {
        return waitTillElementIsVisible(elementId, 10);
    }

    public WebElement waitTillElementIsPresent(By elementId, int numberOfSecondsToWait) {
        return (new WebDriverWait(driver, Duration.ofSeconds(numberOfSecondsToWait)).until(ExpectedConditions.presenceOfElementLocated(elementId)));
    }

    public WebElement waitTillElementIsVisible(By elementId, int numberOfSecondsToWait) {
        return (new WebDriverWait(driver, Duration.ofSeconds(numberOfSecondsToWait)).until(ExpectedConditions.visibilityOfElementLocated(elementId)));
    }

    public List<WebElement> waitTillVisibilityOfAllElements(By elementId) {
        return waitTillVisibilityOfAllElements(elementId, 10);
    }

    public List<WebElement> waitTillVisibilityOfAllElements(By elementId, int numberOfSecondsToWait){
        return (new WebDriverWait(driver, Duration.ofSeconds(numberOfSecondsToWait)).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(elementId)));
    }

    public WebElement waitTillElementIsVisible(String elementId) {
        return waitTillElementIsVisible(elementId, 10);
    }

    public WebElement waitTillElementIsVisible(String elementId, int numberOfSecondsToWait) {
        return (new WebDriverWait(driver, Duration.ofSeconds(numberOfSecondsToWait)).until(ExpectedConditions.visibilityOf(findElementByAccessibilityId(elementId))));
    }

    public List<WebElement> waitTillPresenceOfAllElements(By elementId) {
        return waitTillPresenceOfAllElements(elementId, 10);
    }

    public List<WebElement> waitTillPresenceOfAllElements(By elementId, int numberOfSecondsToWait) {
        return (new WebDriverWait(driver, Duration.ofSeconds(numberOfSecondsToWait)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(elementId)));
    }

    public void setWindowSize(int width, int height) {
        if(this.type.equals(Driver.WEB_DRIVER)) {
            driver.manage().window().setSize(new Dimension(width, height));
        }
    }

    public void moveToElement(By moveToElementLocator) {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(moveToElementLocator)).build().perform();
        waitFor(1);
    }

    public boolean isDriverRunningInHeadlessMode() {
        return this.isRunningInHeadlessMode;
    }

    public WebDriver setWebViewContext() {
// todo - to be fixed for appium 2.0
//        AppiumDriver appiumDriver = (AppiumDriver) driver;
//        Set<String> contextNames = appiumDriver.getContextHandles();
//        return appiumDriver.context((String) contextNames.toArray()[contextNames.size() - 1]);
        SupportsContextSwitching contextSwitchingDriver = (SupportsContextSwitching) driver;
        Set<String> contextHandles = contextSwitchingDriver.getContextHandles();
        return contextSwitchingDriver.context((String) contextHandles.toArray()[contextHandles.size() - 1]);
    }

    public WebDriver setNativeAppContext() {
        return setNativeAppContext("NATIVE_APP");
    }

    public WebDriver setNativeAppContext(String contextName) {
        // todo - to be fixed for appium 2.0
//        AppiumDriver<WebElement> appiumDriver = (AppiumDriver<WebElement>) driver;
//        return appiumDriver.context(contextName);
        SupportsContextSwitching contextSwitchingDriver = (SupportsContextSwitching) driver;
        return contextSwitchingDriver.context(contextName);
    }

    public WebDriver switchFrameToDefault() {
        return driver.switchTo().defaultContent();
    }

    public WebDriver switchToFrame(String id) {
        return driver.switchTo().frame(id);
    }

    public void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0, document.body.scrollHeight)");
    }

    public void scrollTillElementIntoView(By elementId) {
        WebElement element = driver.findElement(elementId);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void switchToNextTab() {
        Iterator<String> iterator = driver.getWindowHandles().iterator();
        try {
            iterator.next();
            driver.switchTo().window(iterator.next());
        } catch(NoSuchElementException e) {
            throw new NoSuchElementException("Unable to get next window handle.", e);
        }
    }

    public void switchToParentTab() {
        try {
            driver.switchTo().window(driver.getWindowHandles().iterator().next());
        } catch(NoSuchElementException e) {
            throw new NoSuchElementException("No previous tab found.", e);
        }
    }

    public void uploadFileInBrowser(String filePath, By locator) {
        try {
            LOGGER.info("Uploading file: " + filePath + " to the browser");
            driver.findElement(locator).sendKeys(filePath);
        } catch(Exception e) {
            throw new FileNotUploadedException(
                    String.format("Error in uploading the file: '%s%s%s", filePath, TO,
                                  Runner.getPlatform().name()), e);
        }
    }

    /**
     * This method injects the media to browserstack to perform,
     * image scanning eg: QRcode,barcode etc
     * Throws NotImplementedException if platform is NOT android, and cloudName is NOT browserstack
     *
     * @param uploadFileURL
     */
    public void injectMediaToBrowserstackDevice(String uploadFileURL) {
        String cloudName = Runner.getCloudName();
        if(Runner.getPlatform().equals(Platform.android) && cloudName.equalsIgnoreCase(
                "browserstack")) {
            String cloudUser = Runner.getCloudUser();
            String cloudKey = Runner.getCloudKey();
            BrowserStackImageInjection.injectMediaToDriver(uploadFileURL, ((AppiumDriver) driver),
                                                           cloudUser, cloudKey);
        } else {
            throw new NotImplementedException(
                    "injectMediaToBrowserstackDevice is not implemented for: " + cloudName);
        }
    }

    public void scrollInDynamicLayer(String direction) {
        Dimension dimension = driver.manage().window().getSize();
        int width = (int) (dimension.width * 0.5);
        int fromHeight = (int) (dimension.height * 0.7), toHeight = (int) (dimension.height * 0.6);
        int[] height = {fromHeight, toHeight};
        if (direction.equalsIgnoreCase("up")) {
            Arrays.sort(height);
        }

        TouchAction<?> touchAction = new TouchAction<>((PerformsTouchActions) driver);
        touchAction.press(PointOption.point(width, height[0]))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                .moveTo(PointOption.point(width, height[1])).release().perform();
    }
}
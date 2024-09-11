package ui.runner;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ui.common.ScreenManager;
import org.softserve.aqa.ui.pages.unauthorizeduser.homepage.HomePage;

import java.time.Duration;

import static org.softserve.aqa.utils.Constants.SCREENSHOT_FOLDER;
import static org.softserve.aqa.utils.Constants.SOURCE_HTML;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(RunnerExtension.class)
public class TestRunner extends ScreenManager {
    protected static WebDriver driver;
    private static JavascriptExecutor js;
    protected static boolean isSuccess = false;

    public TestRunner() {
        super(driver, SCREENSHOT_FOLDER, SOURCE_HTML);
    }

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    public void tearDownClass(TestInfo testInfo) {
        if (!isSuccess) {
            logTestFailure(testInfo);
            takeScreenshot(testInfo.getDisplayName());
            takePageSource(testInfo.getDisplayName());
            cleanUpBrowser();
        }
    }

//    @AfterAll
//    public static void cleanUp() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }

    private void logTestFailure(TestInfo testInfo) {
        log.error("Test '{}' failed", testInfo.getDisplayName());
        testInfo.getTestMethod().ifPresent(method -> log.error("Test method: {}", method.getName()));
    }

    private void cleanUpBrowser() {
        driver.manage().deleteAllCookies();
        removeItemFromLocalStorage("accessToken");
        removeItemFromLocalStorage("refreshToken");
        driver.navigate().refresh();
    }

    public HomePage openApp() {
        driver.get("https://www.greencity.cx.ua/#/greenCity");
        return new HomePage(driver);
    }

    public void removeItemFromLocalStorage(String item) {
        js.executeScript(String.format("window.localStorage.removeItem('%s');", item));
    }
}

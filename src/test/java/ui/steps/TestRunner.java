package ui.steps;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;


import org.softserve.aqa.ui.pages.homapage.HomePage;


import java.time.Duration;

public class TestRunner {
    protected static WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterAll
    public static void cleanUp() {
        if (driver != null) {
            driver.quit();
        }
    }

    public HomePage openApp() {
        driver.get("https://www.greencity.cx.ua/#/greenCity");
        return new HomePage(driver);
    }
}

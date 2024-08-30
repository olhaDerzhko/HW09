package ui.steps.mainpagesteps;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.softserve.aqa.ui.pages.mainpage.MainPage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainPageTest {

    private WebDriver driver;
    private MainPage mainPage;


    @BeforeEach
    public void setUp() throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.greencity.cx.ua/#/greenCity");
        driver.manage().window().setSize(new Dimension(1264, 798));

        mainPage = new MainPage(driver);
        mainPage.switchToEnLanguage();
        Thread.sleep(2000);
    }

    @DisplayName("Verify title for main page")
    @Order(1)
    @Test
    public void verifyTitle() {
        Assertions.assertEquals("GreenCity", mainPage.getTitle());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

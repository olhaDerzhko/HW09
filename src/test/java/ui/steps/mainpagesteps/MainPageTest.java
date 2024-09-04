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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
    }

    @DisplayName("Verify title for main page")
    @Order(1)
    @Test
    public void verifyTitle() {
        Assertions.assertEquals("GreenCity", mainPage.getTitle());
    }

    @DisplayName("Verify error message is displayed for invalid email after clicking 'Subscribe' button")
    @Order(2)
    @ParameterizedTest(name = "{index} => email={0}")
    @ValueSource(strings = {"sampletestgreencity.com"})
    public void verifyEmailSubscription(String email) {
        mainPage.fillEmailForSubscribe(email);
        mainPage.clickOnSubscribeButton();
        String actualMessage = mainPage.getSubscriptionErrorMessage();

        String expectedErrorMessage = "Invalid email";
        Assertions.assertEquals(expectedErrorMessage, actualMessage, "Subscription was not successful");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

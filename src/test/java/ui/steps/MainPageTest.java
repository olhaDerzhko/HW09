package ui.steps;

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
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.softserve.aqa.ui.pages.MainPage;

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

    @DisplayName("Verify title for 'Sign In' modal'")
    @Order(2)
    @Test
    public void verifySignUpTitle() throws InterruptedException {
        mainPage.clickSignInButton();
        Thread.sleep(2000);

        Assertions.assertEquals("Welcome back!", mainPage.getWelcomeText());
        Assertions.assertEquals("Please enter your details to sign in.", mainPage.getSignInDetailsText());
    }

    @DisplayName("Verify valid 'Sign In' flow")
    @Order(3)
    @ParameterizedTest
    @CsvSource({
            "samplestest@greencity.com, weyt3$Guew^"
    })
    public void signInValid(String email, String password) {
        mainPage.clickSignInButton();

        mainPage.enterEmail(email);
        Assertions.assertEquals(email, mainPage.getEmailText(), "The email was not entered correctly");

        mainPage.enterPassword(password);
        Assertions.assertEquals(password, mainPage.getPasswordText(), "The password was not entered correctly");
        mainPage.clickSubmitButton();
    }

    @DisplayName("Verify  error messages for email & password field")
    @Order(5)
    @ParameterizedTest
    @CsvSource({
            "samplestesgreencity.com, 4444444444444444444444444444444^," +
                    "Please check that your e-mail address is indicated correctly," +
                    "Password must be less than 20 characters long without spaces."
    })
    public void checkErrorMsgForInValidEmailAndPassword(String email, String password, String emailErrorMsg, String passwordErrorMsg) throws InterruptedException {
        mainPage.clickSignInButton();

        mainPage.enterEmail(email);
        mainPage.enterPassword(password);

        Thread.sleep(3000);
        mainPage.clickSubmitButton();

        Assertions.assertEquals(emailErrorMsg, mainPage.getErrorEmailText(), "Email error message does not match");
        Assertions.assertEquals(passwordErrorMsg, mainPage.getErrorPasswordText(), "Password error message does not match");
    }

    @DisplayName("Verify error message when email and password are empty")
    @Order(4)
    @ParameterizedTest
    @CsvSource({
            "This field is required, This field is required"
    })
    public void checkErrorMsgForEmptyEmailAndPassword(String emailErrorMsg, String passwordErrorMsg) {
        mainPage.clickSignInButton();

        mainPage.enterEmail("");
        mainPage.enterPassword("");
        mainPage.clickSubmitButton();

        Assertions.assertEquals(emailErrorMsg, mainPage.getErrorEmailText(), "Email error message does not match");
        Assertions.assertEquals(passwordErrorMsg, mainPage.getErrorPasswordText(), "Password error message does not match");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

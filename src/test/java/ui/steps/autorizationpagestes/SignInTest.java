package ui.steps.autorizationpagestes;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.softserve.aqa.ui.pages.autorizationpage.SignInPage;


import java.util.stream.Stream;

public class SignInTest {
    private WebDriver driver;
    private SignInPage signInPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://www.greencity.cx.ua/#/greenCity");
        driver.manage().window().setSize(new Dimension(1264, 798));

        signInPage = new SignInPage(driver);
        signInPage.switchToEnLanguage();
    }

    @DisplayName("Verify title for 'Sign In' modal'")
    @Order(1)
    @Test
    public void verifySignUpTitle() {
        signInPage.clickSignInButton();

        Assertions.assertEquals("Welcome back!", signInPage.getWelcomeText());
        Assertions.assertEquals("Please enter your details to sign in.", signInPage.getSignInDetailsText());
    }

    @DisplayName("Verify valid 'Sign In' flow")
    @Order(3)
    @ParameterizedTest
    @MethodSource("signInProvider")
    public void signInValid(String email, String password) {
        signInPage.clickSignInButton();

        signInPage.enterEmail(email);
        Assertions.assertEquals(email, signInPage.getEmailText(), "The email was not entered correctly");

        signInPage.enterPassword(password);
        Assertions.assertEquals(password, signInPage.getPasswordText(), "The password was not entered correctly");

        signInPage.clickSubmitButton();
    }

    private static Stream<Arguments> signInProvider() {
        String email = System.getenv().get("MY_IDEA_EMAIL");
        String password = System.getenv().get("MY_IDEA_PASSWORD");

        if (email == null || password == null) {
            throw new IllegalStateException("Environment variables MY_IDEA_EMAIL or MY_IDEA_PASSWORD are not set.");
        }

        return Stream.of(
                Arguments.of(email, password));
    }


    @DisplayName("Verify  error messages for email & password field")
    @Order(2)
    @ParameterizedTest(name = "{index} => email={0}, password={1},emailErrorMsg={2}, passwordErrorMsg={3} ")
    @CsvSource({
            "samplestesgreencity.com, 4444444444444444444444444444444^," +
                    "Please check that your e-mail address is indicated correctly," +
                    "Password must be less than 20 characters long without spaces."
    })
    public void checkErrorMsgForInValidEmailAndPassword(String email, String password, String emailErrorMsg,
                                                        String passwordErrorMsg) throws InterruptedException {
        signInPage.clickSignInButton();
        signInPage.enterEmail(email);
        signInPage.enterPassword(password);
        signInPage.clickSubmitButton();

        Assertions.assertEquals(emailErrorMsg, signInPage.getErrorEmailText(), "Email error message does not match");
        Assertions.assertEquals(passwordErrorMsg, signInPage.getErrorPasswordText(), "Password error message does not match");
    }

    @DisplayName("Verify error message when email and password are empty")
    @Order(3)
    @ParameterizedTest
    @CsvSource({
            "This field is required, This field is required"
    })
    public void checkErrorMsgForEmptyEmailAndPassword(String emailErrorMsg, String passwordErrorMsg) {
        signInPage.clickSignInButton();

        signInPage.enterEmail("");
        signInPage.enterPassword("");
        signInPage.clickSubmitButton();

        Assertions.assertEquals(emailErrorMsg, signInPage.getErrorEmailText(), "Email error message does not match");
        Assertions.assertEquals(passwordErrorMsg, signInPage.getErrorPasswordText(), "Password error message does not match");
    }

    //    Site don't have password validation which match this message
//    Password have from 8 to 20 characters long without spaces and contain at least one uppercase letter (A-Z), one lowercase letter (a-z), a digit (0-9), and a special character (~`!@#$%^&*()+=_-{}[]|:;”’?/<>,.)
    @DisplayName("Verify password validation fails for invalid passwords")
    @Order(4)
    @ParameterizedTest(name = "{index} => email={0}, password={1}, expectedError={2}")
    @CsvFileSource(resources = "/upload/invalid_passwords.csv", numLinesToSkip = 1)
    public void testInvalidPasswords(String email, String invalidPassword, String passwordErrorMsg) {
        signInPage.clickSignInButton();
        signInPage.enterEmail(email);  // Use the email from CSV file
        signInPage.enterPassword(invalidPassword);
        signInPage.clickSignInButton();

        String actualErrorMsg = signInPage.getErrorPasswordText();
        Assertions.assertEquals(passwordErrorMsg, actualErrorMsg,
                "Error message does not match for password: " + invalidPassword);
    }
}

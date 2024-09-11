package ui.steps.autorizationsteps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.softserve.aqa.data.User;
import org.junit.jupiter.params.provider.MethodSource;
import org.softserve.aqa.data.UserRepository;
import org.softserve.aqa.ui.pages.autorization.LoginPage;

import org.softserve.aqa.ui.pages.unauthorizeduser.homepage.HomePage;
import ui.runner.TestRunner;

import java.util.stream.Stream;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest extends TestRunner {

    @DisplayName("Verify title for 'Sign In' modal'")
    @Order(1)
    @Test
    public void verifySignUpTitle() {
        LoginPage loginPage = openApp()
                .switchToEnLanguage()
                .openLoginPage();

        Assertions.assertEquals("Welcome back!", loginPage.getWelcomeText());
        Assertions.assertEquals("Please enter your details to sign in.", loginPage.getSignInDetailsText());
    }

    @DisplayName("Verify valid 'Sign In' flow")
    @Order(3)
    @ParameterizedTest(name = "{index} user = {0}")
    @MethodSource("signInProvider")
    public void signinWithValid(User validUser) {
        HomePage homePage = openApp()
                .switchToEnLanguage()
                .openLoginPage()
                .successfullyLogin(validUser);

        Assertions.assertEquals(validUser.getName(), homePage.getUserName(), "User name doesn't match");
    }

    private static Stream<Arguments> signInProvider() {
        return Stream.of(
                Arguments.of(UserRepository.getValidUser())
        );
    }

    @DisplayName("Verify error messages for email & password fields")
    @Order(2)
    @ParameterizedTest(name = "{index} => user={0}, expectedError={1}, expectedError={2}")
    @MethodSource("invalidAccountProvider")
    public void signinWithInValid(User invalidUser, String emailErrorMsg, String passwordErrorMsg) {
        LoginPage loginPage = openApp()
                .switchToEnLanguage()
                .openLoginPage()
                .unsuccessfullyLogin(invalidUser);

        Assertions.assertEquals(emailErrorMsg, loginPage.getErrorEmailText(), "Please check that your e-mail address is indicated correctly");
        Assertions.assertEquals(passwordErrorMsg, loginPage.getErrorPasswordText(), "Password error message does not match");
    }

    private static Stream<Arguments> invalidAccountProvider() {
        return Stream.of(
                Arguments.of(UserRepository.getInvalidUser(),
                        "Please check that your e-mail address is indicated correctly",
                        "Password have from 8 to 20 characters long without spaces and contain at least one uppercase letter (A-Z), one lowercase letter (a-z), a digit (0-9), and a special character (~`!@#$%^&*()+=_-{}[]|:;”’?/<>,.)")
        );
    }

    @DisplayName("Verify error message when email and password are empty")
    @Order(3)
    @ParameterizedTest(name = "{index} => user={0}, expectedError={1}")
    @MethodSource("errorMessageProvider")
    public void errorMessageProvider(User invalidUser, String emailErrorMsg, String passwordErrorMsg) {
        LoginPage loginPage = openApp()
                .switchToEnLanguage()
                .openLoginPage()
                .unsuccessfullyLogin(invalidUser);

        Assertions.assertEquals(emailErrorMsg, loginPage.getErrorEmailText(), "Email error message does not match");
        Assertions.assertEquals(passwordErrorMsg, loginPage.getErrorPasswordText(), "Password error message does not match");
    }

    private static Stream<Arguments> errorMessageProvider() {
        return Stream.of(
                Arguments.of(new User("", ""),
                        "This field is required",
                        "This field is required")
        );
    }

    @DisplayName("Verify password validation fails for invalid passwords")
    @Order(4)
    @ParameterizedTest(name = "{index} => email={0}, password={1}, expectedError={2}")
    @CsvFileSource(resources = "/upload/invalid_passwords.csv", numLinesToSkip = 1)
    public void testInvalidPasswords(String email, String invalidPassword, String expectedErrorMsg) {
        User user = new User(email, invalidPassword);

        LoginPage loginPage = openApp()
                .switchToEnLanguage()
                .openLoginPage()
                .unsuccessfullyLogin(user);

        String actualErrorMsg = loginPage.getErrorPasswordText();
        Assertions.assertTrue(actualErrorMsg.contains("Password have from 8 to 20 characters long"),
                "Error message does not contain the expected length requirement.");
        Assertions.assertTrue(actualErrorMsg.contains("contain at least one uppercase letter (A-Z)"),
                "Error message does not contain the expected uppercase letter requirement.");
    }
}
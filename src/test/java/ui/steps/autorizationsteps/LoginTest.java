package ui.steps.autorizationsteps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.softserve.aqa.data.User;
import org.junit.jupiter.params.provider.MethodSource;
import org.softserve.aqa.ui.pages.autorizationpage.LoginPage;

import org.softserve.aqa.ui.pages.homapage.HomePage;
import org.testng.Assert;
import ui.steps.TestRunner;

import java.util.stream.Stream;

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
    @ParameterizedTest
    @MethodSource("signInProvider")
    public void signInValid(User validUser) {
        HomePage homePage = openApp()
                .switchToEnLanguage()
                .openLoginPage()
                .successfullyLogin(validUser);

        Assertions.assertEquals(validUser.getName(), homePage.getUserName());
    }

    private static Stream<Arguments> signInProvider() {
        String email = System.getenv().get("MY_IDEA_EMAIL");
        String password = System.getenv().get("MY_IDEA_PASSWORD");

        if (email == null || password == null) {
            throw new IllegalStateException("Environment variables MY_IDEA_EMAIL or MY_IDEA_PASSWORD are not set.");
        }
        return Stream.of(
                Arguments.of(email, password)
        );
    }

}

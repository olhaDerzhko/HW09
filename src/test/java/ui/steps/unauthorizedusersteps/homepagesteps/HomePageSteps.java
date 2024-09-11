package ui.steps.unauthorizedusersteps.homepagesteps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.softserve.aqa.ui.pages.unauthorizeduser.homepage.HomePage;
import ui.runner.TestRunner;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HomePageSteps extends TestRunner {

    @DisplayName("Verify title for home page")
    @Order(1)
    @Test
    public void verifyHomePageTitle() {

        HomePage homePage = openApp()
                .switchToEnLanguage();

        Assertions.assertEquals("GreenCity", homePage.getTitle());
    }

    @DisplayName("Verify error message is displayed for invalid email after clicking Subscribe button")
    @Order(2)
    @ParameterizedTest(name = "{index} => email={0}")
    @ValueSource(strings = {"com"})
    public void verifyEmailSubscription(String email) {

        HomePage homePage = openApp()
                .switchToEnLanguage()
                .fillEmailForSubscribe(email);

        String actualMessage = homePage.getSubscriptionErrorMessage();
        String expectedErrorMessage = "Invalid email";

        Assertions.assertEquals(expectedErrorMessage, actualMessage, "Subscription was not successful");
    }
}

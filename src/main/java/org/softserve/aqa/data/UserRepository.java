package org.softserve.aqa.data;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserRepository {
    private static final Faker faker = new Faker();

    public static User getValidUser() {
        return validUser();
    }

    public static User getInvalidUser() {
        return invalidUser();
    }

    private static User validUser() {
        String email = System.getenv().get("MY_IDEA_EMAIL");
        String password = System.getenv().get("MY_IDEA_PASSWORD");
        String name = "Olha";

        if (email == null || password == null) {
            log.error("Environment variables MY_IDEA_EMAIL or MY_IDEA_PASSWORD are not set.");
            throw new IllegalStateException("Environment variables MY_IDEA_EMAIL or MY_IDEA_PASSWORD are not set.");
        }
        return new User(email, password, name);
    }

    private static User invalidUser() {
        String invalidEmail = faker.internet().emailAddress().replace("@", "");
        String invalidPassword = faker.internet().password(1, 4);

        return new User(invalidEmail, invalidPassword);
    }
}

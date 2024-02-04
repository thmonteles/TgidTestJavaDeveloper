package br.com.tgid.TgidTestJavaDeveloper.models;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    @Test
    void createUserWithValidData() {
        User user = new TestUserBuilder()
                .id(1L)
                .name("Rick Sanchez")
                .email("ricksanchez@hbo.com")
                .phone("+5511444444444")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).isEmpty();
    }

    @Test
    void createUserWithBlankName() {
        User user = new TestUserBuilder()
                .id(2L)
                .name("")
                .email("ricksanchez@hbo.com")
                .phone("+5511444444444")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("This field name cannot be empty");
    }

    @Test
    void createUserWithInvalidEmail() {
        User user = new TestUserBuilder()
                .id(3L)
                .name("Alice Wonderland")
                .email("invalid-email")
                .phone("+5511444444444")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Please provide a valid email address");
    }

    @Test
    void createUserWithEmptyEmail() {
        User user = new TestUserBuilder()
                .id(3L)
                .name("Alice Wonderland")
                .email("")
                .phone("+5511444444444")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("This field email cannot be empty");
    }

    @Test
    void createUserWithInvalidPhone() {
        User user = new TestUserBuilder()
                .id(4L)
                .name("Bob Builder")
                .email("bob.builder@example.com")
                .phone("invalid-phone")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("The phone number must contain only valid digits and allowed special characters.");
    }

    private static class TestUserBuilder {
        private final User user = new Client();

        public TestUserBuilder id(Long id) {
            user.setId(id);
            return this;
        }

        public TestUserBuilder name(String name) {
            user.setName(name);
            return this;
        }

        public TestUserBuilder email(String email) {
            user.setEmail(email);
            return this;
        }

        public TestUserBuilder phone(String phone) {
            user.setPhone(phone);
            return this;
        }

        public User build() {
            return user;
        }
    }
}

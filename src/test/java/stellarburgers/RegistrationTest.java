package stellarburgers;

import org.junit.jupiter.api.DisplayName;
import stellarburgers.support.Browser;
import stellarburgers.support.CrossBrowserTest;
import stellarburgers.support.TestUser;
import stellarburgers.support.UiTest;

class RegistrationTest extends UiTest {
    @CrossBrowserTest
    @DisplayName("Регистрация с паролем из шести символов")
    void acceptsSixCharacterPassword(Browser browser) {
        openRegistration(browser);
        TestUser user = TestUser.unique(6);
        registration.fill(user);
        users.track(user);
        registration.submit();
        login.signIn(user);
        header.openAccount();
        profile.checkEmail(user.email);
    }

    @CrossBrowserTest
    @DisplayName("Ошибка при пароле из пяти символов")
    void rejectsFiveCharacterPassword(Browser browser) {
        openRegistration(browser);
        registration.fill(TestUser.unique(5));
        registration.checkPasswordError();
    }
}

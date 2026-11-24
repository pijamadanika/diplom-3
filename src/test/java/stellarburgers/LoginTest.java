package stellarburgers;

import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import stellarburgers.support.Browser;
import stellarburgers.support.CrossBrowserTest;
import stellarburgers.support.TestUser;
import stellarburgers.support.UiTest;

import java.io.IOException;

class LoginTest extends UiTest {
    @CrossBrowserTest
    @DisplayName("Вход по кнопке «Войти в аккаунт»")
    void fromHome(Browser browser) throws IOException, InterruptedException {
        TestUser user = registeredUser(browser);
        home.openLogin();
        signInAndCheckProfile(user);
    }

    @CrossBrowserTest
    @DisplayName("Вход через «Личный Кабинет»")
    void fromHeader(Browser browser) throws IOException, InterruptedException {
        TestUser user = registeredUser(browser);
        header.openAccount();
        signInAndCheckProfile(user);
    }

    @CrossBrowserTest
    @DisplayName("Вход из формы регистрации")
    void fromRegistration(Browser browser) throws IOException, InterruptedException {
        TestUser user = registeredUser(browser);
        home.openLogin();
        login.openRegistration();
        registration.openLogin();
        signInAndCheckProfile(user);
    }

    @CrossBrowserTest
    @DisplayName("Вход из формы восстановления пароля")
    void fromRecovery(Browser browser) throws IOException, InterruptedException {
        TestUser user = registeredUser(browser);
        home.openLogin();
        login.openRecovery();
        recovery.openLogin();
        signInAndCheckProfile(user);
    }

    @Step("Проверить вход по данным пользователя в личном кабинете")
    private void signInAndCheckProfile(TestUser user) {
        login.signIn(user);
        header.openAccount();
        profile.checkEmail(user.email);
    }
}

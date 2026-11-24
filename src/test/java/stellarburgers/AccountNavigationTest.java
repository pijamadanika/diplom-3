package stellarburgers;

import org.junit.jupiter.api.DisplayName;
import stellarburgers.support.Browser;
import stellarburgers.support.CrossBrowserTest;
import stellarburgers.support.TestUser;
import stellarburgers.support.UiTest;

import java.io.IOException;

class AccountNavigationTest extends UiTest {
    @CrossBrowserTest
    @DisplayName("Переход в личный кабинет")
    void opensOwnProfile(Browser browser) throws IOException, InterruptedException {
        TestUser user = signInFromHome(browser);
        header.openAccount();
        profile.checkEmail(user.email);
    }

    @CrossBrowserTest
    @DisplayName("Из профиля в конструктор по ссылке «Конструктор»")
    void returnsViaConstructorLink(Browser browser) throws IOException, InterruptedException {
        openSignedInProfile(browser);
        header.openConstructor();
        home.checkAuthenticatedConstructor();
    }

    @CrossBrowserTest
    @DisplayName("Из профиля в конструктор по логотипу")
    void returnsViaLogo(Browser browser) throws IOException, InterruptedException {
        openSignedInProfile(browser);
        header.clickLogo();
        home.checkAuthenticatedConstructor();
    }
}

package stellarburgers;

import org.junit.jupiter.api.DisplayName;
import stellarburgers.support.Browser;
import stellarburgers.support.CrossBrowserTest;
import stellarburgers.support.UiTest;

import java.io.IOException;

class LogoutTest extends UiTest {
    @CrossBrowserTest
    @DisplayName("После выхода личный кабинет снова требует входа")
    void logoutEndsSession(Browser browser) throws IOException, InterruptedException {
        openSignedInProfile(browser);
        profile.logout();
        login.checkOpen();
        header.openConstructor();
        home.checkGuestConstructor();
        header.openAccount();
        login.checkOpen();
    }
}

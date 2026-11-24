package stellarburgers.support;

import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.openqa.selenium.WebDriver;
import stellarburgers.pages.ConstructorPage;
import stellarburgers.pages.Header;
import stellarburgers.pages.LoginPage;
import stellarburgers.pages.PasswordRecoveryPage;
import stellarburgers.pages.ProfilePage;
import stellarburgers.pages.RegistrationPage;

import java.io.IOException;
import java.util.Locale;
import java.util.stream.Stream;

public abstract class UiTest {
    private final String baseUrl = System.getProperty("baseUrl", "https://qa-stellarburgers.education-services.ru");
    protected final UserApi users = new UserApi(baseUrl);
    private WebDriver driver;
    protected ConstructorPage home;
    protected Header header;
    protected LoginPage login;
    protected RegistrationPage registration;
    protected PasswordRecoveryPage recovery;
    protected ProfilePage profile;

    protected static Stream<Browser> browsers() {
        String browser = System.getProperty("browser");
        return browser == null ? Stream.of(Browser.values()) : Stream.of(Browser.valueOf(browser.toUpperCase(Locale.ROOT)));
    }

    @Step("Открыть Stellar Burgers в браузере {browser}")
    protected void openHome(Browser browser) {
        driver = browser.start();
        home = new ConstructorPage(driver);
        header = new Header(driver);
        login = new LoginPage(driver);
        registration = new RegistrationPage(driver);
        recovery = new PasswordRecoveryPage(driver);
        profile = new ProfilePage(driver);
        home.open(baseUrl);
    }

    @Step("Создать аккаунт для теста")
    protected TestUser registeredUser(Browser browser) throws IOException, InterruptedException {
        openHome(browser);
        TestUser user = TestUser.unique(12);
        users.create(user);
        return user;
    }

    @Step("Открыть конструктор под зарегистрированным пользователем")
    protected TestUser signInFromHome(Browser browser) throws IOException, InterruptedException {
        TestUser user = registeredUser(browser);
        home.openLogin();
        login.signIn(user);
        home.checkAuthenticatedConstructor();
        return user;
    }

    @Step("Открыть профиль авторизованного пользователя")
    protected void openSignedInProfile(Browser browser) throws IOException, InterruptedException {
        TestUser user = signInFromHome(browser);
        header.openAccount();
        profile.checkEmail(user.email);
    }

    @Step("Открыть регистрацию с главной страницы")
    protected void openRegistration(Browser browser) {
        openHome(browser);
        home.openLogin();
        login.openRegistration();
    }

    @AfterEach
    @Step("Очистить тестовые данные и закрыть браузер")
    protected void tearDown() throws IOException, InterruptedException {
        try {
            users.cleanUp();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}

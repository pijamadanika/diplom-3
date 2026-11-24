package stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import stellarburgers.support.TestUser;

public class LoginPage extends BasePage {
    private final By heading = By.xpath("//h2[normalize-space()='Вход']");
    private final By emailInput = field("Email");
    private final By passwordInput = field("Пароль");
    private final By loginButton = By.xpath("//button[normalize-space()='Войти']");
    private final By registerLink = By.cssSelector("a[href='/register']");
    private final By recoveryLink = By.cssSelector("a[href='/forgot-password']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Проверить, что открылась форма входа")
    public void checkOpen() {
        wait.until(ExpectedConditions.urlMatches("/login/?$"));
        visible(heading);
        visible(loginButton);
    }

    @Step("Войти под тестовым пользователем")
    public void signIn(TestUser user) {
        checkOpen();
        visible(emailInput).sendKeys(user.email);
        visible(passwordInput).sendKeys(user.password);
        click(loginButton);
        wait.until(ExpectedConditions.not(ExpectedConditions.urlMatches("/login/?$")));
    }

    @Step("Открыть форму регистрации")
    public void openRegistration() {
        click(registerLink);
    }

    @Step("Открыть форму восстановления пароля")
    public void openRecovery() {
        click(recoveryLink);
    }
}

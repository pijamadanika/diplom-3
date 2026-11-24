package stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PasswordRecoveryPage extends BasePage {
    private final By loginLink = By.cssSelector("a[href='/login']");

    public PasswordRecoveryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Нажать «Войти» в форме восстановления пароля")
    public void openLogin() {
        wait.until(ExpectedConditions.urlMatches("/forgot-password/?$"));
        click(loginLink);
    }
}

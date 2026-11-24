package stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import stellarburgers.support.TestUser;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegistrationPage extends BasePage {
    private final By nameInput = field("Имя");
    private final By emailInput = field("Email");
    private final By passwordInput = field("Пароль");
    private final By registerButton = By.xpath("//button[normalize-space()='Зарегистрироваться']");
    private final By passwordError = By.cssSelector("form .input__error");
    private final By loginLink = By.cssSelector("a[href='/login']");

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    @Step("Заполнить форму регистрации")
    public void fill(TestUser user) {
        visible(nameInput).sendKeys(user.name);
        visible(emailInput).sendKeys(user.email);
        visible(passwordInput).sendKeys(user.password, Keys.TAB);
    }

    @Step("Нажать «Зарегистрироваться»")
    public void submit() {
        click(registerButton);
    }

    @Step("Проверить сообщение об ошибке пароля")
    public void checkPasswordError() {
        assertEquals("Некорректный пароль", visible(passwordError).getText());
    }

    @Step("Нажать «Войти» в форме регистрации")
    public void openLogin() {
        wait.until(ExpectedConditions.urlMatches("/register/?$"));
        click(loginLink);
    }
}

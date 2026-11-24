package stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfilePage extends BasePage {
    private final By loginInput = field("Логин");
    private final By logoutButton = By.xpath("//button[normalize-space()='Выход' or normalize-space()='Выйти']");

    public ProfilePage(WebDriver driver) {
        super(driver);
    }

    @Step("Проверить, что в личном кабинете указан email тестового пользователя")
    public void checkEmail(String email) {
        visible(logoutButton);
        wait.until(ExpectedConditions.attributeToBe(loginInput, "value", email));
        assertEquals(email, visible(loginInput).getDomProperty("value"));
    }

    @Step("Выйти из личного кабинета")
    public void logout() {
        click(logoutButton);
    }
}

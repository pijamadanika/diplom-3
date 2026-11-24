package stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstructorPage extends BasePage {
    private final By loginButton = By.xpath("//button[normalize-space()='Войти в аккаунт']");
    private final By orderButton = By.xpath("//button[normalize-space()='Оформить заказ']");
    private final By constructorTitle = By.xpath("//h1[normalize-space()='Соберите бургер']");
    private final By activeTab = By.cssSelector("[class*='tab_type_current'] span");
    private final By ingredientList = By.cssSelector("[class*='ingredients__menuContainer']");

    public ConstructorPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть главную страницу")
    public void open(String baseUrl) {
        driver.get(baseUrl);
        visible(constructorTitle);
    }

    @Step("Проверить, что конструктор предлагает войти в аккаунт")
    public void checkGuestConstructor() {
        visible(constructorTitle);
        visible(loginButton);
    }

    @Step("Нажать «Войти в аккаунт»")
    public void openLogin() {
        click(loginButton);
    }

    @Step("Проверить, что открыт конструктор авторизованного пользователя")
    public void checkAuthenticatedConstructor() {
        visible(constructorTitle);
        visible(orderButton);
    }

    @Step("Перейти в раздел «{section}»")
    public void selectSection(String section) {
        click(By.xpath("//div[contains(@class,'tab_tab__')]/span[normalize-space()='" + section + "']"));
    }

    @Step("Проверить активную вкладку и видимость заголовка раздела «{section}» в списке")
    public void checkSection(String section) {
        By sectionHeading = By.xpath("//h2[normalize-space()='" + section + "']");
        WebElement list = visible(ingredientList);
        WebElement heading = visible(sectionHeading);
        wait.until(d -> section.equals(d.findElement(activeTab).getText())
                && Boolean.TRUE.equals(((JavascriptExecutor) d).executeScript(
                    "const list = arguments[0].getBoundingClientRect(), heading = arguments[1].getBoundingClientRect();"
                    + "return heading.top >= list.top - 1 && heading.bottom <= list.bottom + 1;", list, heading)));
        assertEquals(section, driver.findElement(activeTab).getText());
    }
}

package stellarburgers.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header extends BasePage {
    private final By accountLink = By.cssSelector("header a[href='/account']");
    private final By constructorLink = By.xpath("//header//a[normalize-space()='Конструктор']");
    private final By logoLink = By.cssSelector("header [class*='header__logo'] a[href='/']");

    public Header(WebDriver driver) {
        super(driver);
    }

    @Step("Нажать «Личный Кабинет» в шапке")
    public void openAccount() {
        click(accountLink);
    }

    @Step("Нажать «Конструктор» в шапке")
    public void openConstructor() {
        click(constructorLink);
    }

    @Step("Нажать логотип Stellar Burgers")
    public void clickLogo() {
        click(logoLink);
    }
}

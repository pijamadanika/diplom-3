package stellarburgers;

import org.junit.jupiter.api.DisplayName;
import stellarburgers.support.Browser;
import stellarburgers.support.CrossBrowserTest;
import stellarburgers.support.UiTest;

class ConstructorTest extends UiTest {
    @CrossBrowserTest
    @DisplayName("Переход к булкам из раздела соусов")
    void switchesToBuns(Browser browser) {
        openHome(browser);
        home.selectSection("Соусы");
        home.checkSection("Соусы");
        home.selectSection("Булки");
        home.checkSection("Булки");
    }

    @CrossBrowserTest
    @DisplayName("Переход к соусам")
    void switchesToSauces(Browser browser) {
        openHome(browser);
        home.checkSection("Булки");
        home.selectSection("Соусы");
        home.checkSection("Соусы");
    }

    @CrossBrowserTest
    @DisplayName("Переход к начинкам")
    void switchesToFillings(Browser browser) {
        openHome(browser);
        home.checkSection("Булки");
        home.selectSection("Начинки");
        home.checkSection("Начинки");
    }
}

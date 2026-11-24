package stellarburgers.support;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Stream;

public enum Browser {
    CHROME, YANDEX;

    @Step("Запустить браузер {this}")
    public WebDriver start() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1440,1000");
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new");
        }
        if (this == CHROME) {
            return new ChromeDriver(options);
        }

        Path binary = Path.of(System.getProperty("yandex.binary", defaultYandexBinary()));
        if (!Files.isRegularFile(binary) || !Files.isExecutable(binary)) {
            throw new IllegalStateException("Яндекс.Браузер не найден: " + binary + ". Укажите -Dyandex.binary");
        }
        options.setBinary(binary.toFile());
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(yandexDriver().toFile()).build();
        return new ChromeDriver(service, options);
    }

    private static Path yandexDriver() {
        String configured = System.getProperty("yandex.driver");
        if (configured != null) {
            Path driver = Path.of(configured);
            if (Files.isRegularFile(driver) && Files.isExecutable(driver)) {
                return driver;
            }
            throw new IllegalStateException("Проверьте путь и права на запуск YandexDriver: " + driver);
        }
        String name = isWindows() ? "yandexdriver.exe" : "yandexdriver";
        Path projectDriver = Path.of("drivers", name).toAbsolutePath();
        Stream<Path> pathDrivers = Arrays.stream(System.getenv().getOrDefault("PATH", "").split(File.pathSeparator))
                .filter(dir -> !dir.isEmpty())
                .map(dir -> Path.of(dir, name));
        return Stream.concat(Stream.of(projectDriver), pathDrivers)
                .filter(Files::isRegularFile)
                .filter(Files::isExecutable)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Поместите исполняемый YandexDriver в " + projectDriver
                        + " или укажите -Dyandex.driver=/путь/к/" + name));
    }

    private static String defaultYandexBinary() {
        if (System.getProperty("os.name").startsWith("Mac")) {
            String appBinary = "Applications/Yandex.app/Contents/MacOS/Yandex";
            Path systemApp = Path.of("/", appBinary);
            Path userApp = Path.of(System.getProperty("user.home"), appBinary);
            return Files.exists(systemApp) || !Files.exists(userApp) ? systemApp.toString() : userApp.toString();
        }
        if (isWindows()) {
            return System.getenv("LOCALAPPDATA") + "/Yandex/YandexBrowser/Application/browser.exe";
        }
        return "/usr/bin/yandex-browser";
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase(Locale.ROOT).startsWith("windows");
    }
}

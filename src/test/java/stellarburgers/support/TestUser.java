package stellarburgers.support;

import io.qameta.allure.Step;
import java.util.UUID;

public final class TestUser {
    public final String name;
    public final String email;
    public final String password;

    private TestUser(String id, String password) {
        this.name = "Tester-" + id.substring(0, 8);
        this.email = "burger-" + id + "@example.com";
        this.password = password;
    }

    @Step("Подготовить уникальные данные пользователя")
    public static TestUser unique(int passwordLength) {
        String id = UUID.randomUUID().toString();
        return new TestUser(id, id.substring(0, passwordLength));
    }

    @Override
    public String toString() {
        return email;
    }
}

package stellarburgers.support;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.qameta.allure.Step;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;

public final class UserApi {
    private final URI base;
    private final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();
    private final Gson gson = new Gson();
    private String token;
    private TestUser pending;

    public UserApi(String baseUrl) {
        this.base = URI.create(baseUrl);
    }

    public void track(TestUser user) {
        pending = user;
    }

    @Step("Создать тестового пользователя через API")
    public void create(TestUser user) throws IOException, InterruptedException {
        track(user);
        token = readSuccess(post("/api/auth/register", user), 200).get("accessToken").getAsString();
    }

    @Step("Удалить тестового пользователя через API")
    public void cleanUp() throws IOException, InterruptedException {
        if (pending == null) {
            return;
        }
        if (token == null) {
            HttpResponse<String> response = post("/api/auth/login",
                    Map.of("email", pending.email, "password", pending.password));
            if ((response.statusCode() == 401 || response.statusCode() == 403)
                    && response.body().contains("email or password are incorrect")) {
                return;
            }
            token = readSuccess(response, 200).get("accessToken").getAsString();
        }
        HttpRequest request = request("/api/auth/user").header("Authorization", token).DELETE().build();
        readSuccess(client.send(request, HttpResponse.BodyHandlers.ofString()), 202);
    }

    private HttpRequest.Builder request(String path) {
        return HttpRequest.newBuilder(base.resolve(path)).timeout(Duration.ofSeconds(15));
    }

    private HttpResponse<String> post(String path, Object body) throws IOException, InterruptedException {
        HttpRequest request = request(path).header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(body))).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private JsonObject readSuccess(HttpResponse<String> response, int status) {
        if (response.statusCode() != status) {
            throw new AssertionError(response.request().method() + " " + response.uri().getPath()
                    + ": ожидался HTTP " + status + ", получен " + response.statusCode());
        }
        JsonObject json = gson.fromJson(response.body(), JsonObject.class);
        if (!json.get("success").getAsBoolean()) {
            throw new AssertionError("API вернул success=false: " + response.uri().getPath());
        }
        return json;
    }
}

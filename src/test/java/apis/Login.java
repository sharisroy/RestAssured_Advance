package apis;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class Login {

    public static void main(String[] args) {

        // Base URI for the API
        RestAssured.baseURI = "https://sqa-apis.onrender.com/";

        // Define login payload as a JSON string
        String payload = """
                {
                    "email": "h@gmail.com",
                    "password": "haris123"
                }
                """;

        // Send POST request to login endpoint
        Response loginResponse = given()
                .header("Content-Type", "application/json")
                .body(payload)
                .when()
                .post("auth/login")
                .then()
                .assertThat()
                // ✅ Validate HTTP status
                .statusCode(200)

                // ✅ Validate main response fields
                .body("code", equalTo(200))
                .body("message", equalTo("Login successful."))
                .body("success", equalTo(true))

                // ✅ Validate user info
                .body("user.email", equalTo("h@gmail.com"))
                .body("user.name", equalTo("Haris Chandra Roy"))

                // ✅ Validate access token presence
                .body("auth.access_token", notNullValue())
                .body("auth.expires_at", notNullValue())

                // Extract full response
                .extract()
                .response();

        // Print response nicely
        System.out.println("✅ Login Response:");
        System.out.println(loginResponse.asPrettyString());

        // Optional: extract token for further use
        String token = loginResponse.path("auth.access_token");
        System.out.println("\n🔑 Access Token: " + token);
    }
}

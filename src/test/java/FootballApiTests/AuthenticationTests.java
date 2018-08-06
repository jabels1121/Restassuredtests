package FootballApiTests;

import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class AuthenticationTests {

    @BeforeClass
    public static void setUp() {
        RestAssured.proxy("localhost", 8888);
    }

    @Test
    public void basicPreemptiveAuthTest() { // При таком типе авторизации логин и пароль будут улетать в Headerах в каждом запросе.

        given()
                .auth().preemptive().basic("username", "password").
        when()
                .get("http://localhost:8080/someEndPoint");

    }

    @Test
    public void basicChallengedAuthTest() { // При таком типе атворизации, логин и пароль не будут отправляться в хедерах, пока сервер явно не запросит их. При запросе от сервера RestAssured отправит повторный запрос и положит логин пароль в request header.

        given()
                .auth().basic("username", "password").
        when()
                .get("http://localhost:8080/someEndPoint");

    }

    @Test
    public void oauth1Test() {

        given().
                auth().oauth("consumerKey", "consumerSecret", "consumerAccessToken", "secretToken").
        when()
                .get("http://localhost:8080/someEndPoint");
    }

    @Test
    public void oauth2Test() {
        given().
                auth().oauth2("accessToken").
        when()
                .get("http://localhost:8080/someEndPoint");
    }

    @Test
    public void relaxedHttpsTest() {

        given().
                relaxedHTTPSValidation().
        when().
                get("https://localhost:8080/someEndPoint");
    }

    @Test
    public void keyStoreTest() {
        given().
                keyStore("/pathToJKS", "password").
        when().
                get("https://localhost:8080/someEndPoint");
    }
}

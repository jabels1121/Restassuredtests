package VideoGameDBTests;

import VideoGameDBTestsConfig.TestConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import java.io.File;

import static VideoGameDBTestsConfig.VideoGamesEndPoint.ENDPOINT;
import static VideoGameDBTestsConfig.VideoGamesEndPoint.SINGLE_VIDEOGAME;
import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

public class GetTests extends TestConfig{

    @Test
    public void getAllVideoGames() {
        given()
                .spec(videoGameJsonRequestSpec).
        when()
                .get(ENDPOINT).
        then()
                .log().all()
                .assertThat().body("id", hasItem(1));
    }

    @Test
    public void getFirstVideoGame() {
        given()
                .spec(videoGameJsonRequestSpec).
        when()
                .get(ENDPOINT + "/1").
        then()
                .log().all()
                .assertThat().body("id", is(1));
    }

    @Test
    public void getSingleVideoGame() {
        given().
                spec(videoGameJsonRequestSpec).
                pathParam("videoGameId", 5).
        when().
                get(SINGLE_VIDEOGAME).
        then().
                log().all().
                assertThat().body("id", is(5));
    }

    @Test
    public void checkTheResponseBodyIsJson() {
        /*Response response =
                given().
                        spec(videoGameJsonRequestSpec).
                when().
                        get(ENDPOINT).
                then().
                        contentType(ContentType.JSON).
                        extract().response();
        */
        given().
                spec(videoGameJsonRequestSpec).
        when().
                get(ENDPOINT).
        then().
                assertThat().contentType(ContentType.JSON);

    }

    @Test
    public void testVideoGameSchemaXML() {
        File xsdSchema =
                new File("C:\\Users\\Jaybe\\IdeaProjects\\Restassuredtests\\src\\main\\java\\resources\\VideoGame.xsd");
        given().
                spec(videoGameXMLRequestSpec).
                pathParam("videoGameId", 5).
        when().
                get(SINGLE_VIDEOGAME).
        then().
                body(matchesXsdInClasspath("VideoGame.xsd"));
    }

    @Test
    public void testVideoGameSchemaJSON() {
        File jsonSchema =
                new File("C:\\Users\\Jaybe\\IdeaProjects\\Restassuredtests\\src\\main\\java\\resources\\VideoGameJsonSchema.json");
        given().
                spec(videoGameJsonRequestSpec).
                pathParam("videoGameId", 5).
        when().
                get(SINGLE_VIDEOGAME).
        then().
                body(matchesJsonSchemaInClasspath("VideoGameJsonSchema.json"));
    }
}

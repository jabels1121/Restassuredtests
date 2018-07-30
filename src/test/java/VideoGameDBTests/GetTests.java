package VideoGameDBTests;

import VideoGameDBTestsConfig.TestConfig;
import org.junit.Test;

import static VideoGameDBTestsConfig.VideoGamesEndPoint.ENDPOINT;
import static VideoGameDBTestsConfig.VideoGamesEndPoint.SINGLE_VIDEOGAME;
import static io.restassured.RestAssured.given;
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

}

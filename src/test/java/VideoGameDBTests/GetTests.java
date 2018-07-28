package VideoGameDBTests;

import VideoGameDBTestsConfig.TestConfig;
import VideoGameDBTestsConfig.VideoGamesEndPoint;
import org.junit.Test;

import static VideoGameDBTestsConfig.VideoGamesEndPoint.ENDPOINT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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
}

package FootballApiTests;

import VideoGameDBTestsConfig.TestConfig;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetTests extends TestConfig {

    @Test
    public void getAllCompetitionsOneSeason() {
        given().
                spec(footbalApiJsonRequestSpec).
                queryParam("season", 2016).
        when().
                get("competitions/").
        then().
                log().all().
                assertThat().header("X-Authenticated-Client", "Jabels").
                body("year", everyItem(is("2016")));
    }

}

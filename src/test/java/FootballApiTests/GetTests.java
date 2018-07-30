package FootballApiTests;

import VideoGameDBTestsConfig.TestConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;

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

    @Test
    public void checkTeamsCountByOneComp() {
        given().
                spec(footbalApiJsonRequestSpec).
        when()
                .get("competitions/426/teams").
        then()
                .log().all()
                .assertThat().body("count", is(20));
    }

    @Test
    public void checkTheFirstNameTeamByOneComp() {
        given().
                spec(footbalApiJsonRequestSpec).
        when()
                .get("competitions/426/teams").
        then()
                .log().all()
                .assertThat().body("teams.name[0]", is("Hull City FC"));
    }

    @Test
    public void getAllTeamData_DoCheckFirst() throws ParseException {
        Response response =
                given().
                        spec(footbalApiJsonRequestSpec).
                when().
                        get("competitions/426/teams").
                then().
                        contentType(ContentType.JSON).
                        extract().response();
        String jsonResponseAsString = response.asString();
        System.out.println(jsonResponseAsString);
    }

    @Test
    public void extractResponseHeaders() {
        Response response =
                given().
                        spec(footbalApiJsonRequestSpec).
                when().
                        get("competitions/426/teams").
                then().
                        contentType(ContentType.JSON).
                        extract().response();

        Headers headers = response.getHeaders();
        String contentType = response.getHeader("Content-Type");

        System.out.println(contentType);
    }
}

package FootballApiTests;

import VideoGameDBTestsConfig.TestConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;

public class GetTests extends TestConfig {

    @Test
    public void getAllCompetitionsOneSeason() {
        given().
                spec(footballApiJsonRequestSpec).
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
                spec(footballApiJsonRequestSpec).
        when()
                .get("competitions/426/teams").
        then()
                .log().all()
                .assertThat().body("count", is(20));
    }

    @Test
    public void checkTheFirstNameTeamByOneComp() {
        given().
                spec(footballApiJsonRequestSpec).
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
                        spec(footballApiJsonRequestSpec).
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
                        spec(footballApiJsonRequestSpec).
                when().
                        get("competitions/426/teams").
                then().
                        contentType(ContentType.JSON).
                        extract().response();

        Headers headers = response.getHeaders();
        String contentType = response.getHeader("Content-Type");

        System.out.println(contentType);
    }

    @Test
    public void extractFirstTeamName() {
        String firstTeamName =
                given().
                        spec(footballApiJsonRequestSpec).
                when().
                        get("competitions/426/teams").
                jsonPath().getString("teams.name[0]");

        System.out.println(firstTeamName);
    }

    @Test
    public void extractAllTeamsName() {
        Response response =
                given().
                        spec(footballApiJsonRequestSpec).
                when().
                        get("competitions/426/teams").
                then().
                        contentType(ContentType.JSON).
                        extract().response();

        List<String> allTeamsName = response.jsonPath().getList("teams.name");
        for (String s : allTeamsName) {
            System.out.println(s);
        }
    }
}

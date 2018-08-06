package FootballApiTests;

import VideoGameDBTestsConfig.TestConfig;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class GPathJSONTests extends TestConfig {

    @Test
    public void extractAllPlayerDataWithFind() {

        Response response = given().spec(footballApiJsonRequestSpec).when().get("teams/1/players");

        Map<String, ?> singlePlayerData = response.path("players.find { it.jerseyNumber == 39 }");

        System.out.println(singlePlayerData);
    }

    @Test
    public void extractAllPlayersWithFind() {


        JSONObject jsonObject = new JSONObject();


        Response response = given().spec(footballApiJsonRequestSpec).when().get("teams/1/players");

        List<Map<String, ?>> allPlayers = response.path(
                "players"
        );

        Map<String, ?> onePlayer = allPlayers.get(0);

        jsonObject.putAll(onePlayer);


        System.out.println(jsonObject);

    }

    @Test
    public void extractMapOfObjectWithFindAndFindAll() {

        String position = "Centre-Back";
        String nationality = "England";

        Response response = given().spec(footballApiJsonRequestSpec).when().get("teams/66/players");

        Map<String, ?> playerOfCertainPositionAndCountry = response.path(
                "players.findAll { it.position == '%s' }.find { it.nationality == '%s' }"
                , position, nationality
        );


        System.out.println(playerOfCertainPositionAndCountry);

    }

    @Test
    public void extractMultiplePlayers() {

        String nationality = "England";

        Response response = given().spec(footballApiJsonRequestSpec).when().get("teams/66/players");

        List<Map<String, Object>> allPlayersCertainNation = response.path(
                "players.findAll { it.nationality == '%s' }", nationality);

        for (Map<String, Object> k : allPlayersCertainNation) {
            System.out.println(k);
        }

    }

}

package VideoGameDBTests;

import VideoGameDBTestsConfig.TestConfig;
import VideoGameDBTestsConfig.VideoGame;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import static VideoGameDBTestsConfig.VideoGamesEndPoint.ENDPOINT;
import static io.restassured.RestAssured.given;

public class PostTests extends TestConfig {

    public Object getJsonObject() {
        String fileName = "C:\\Users\\Jabels\\IdeaProjects\\Restassuredtests\\src\\test\\resources\\VideoGameJson.json";
        JSONParser parser = new JSONParser();
        JSONObject videoGame = null;
        try{
            videoGame = (JSONObject) parser.parse(new FileReader(fileName));
        }catch (ParseException e) {
            e.printStackTrace();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return videoGame;

    }

    public void deleteCreatedVideoGameByJson() {
        JSONObject videoGame = (JSONObject) getJsonObject();
        String videoGameId = (String) videoGame.get("id");
        System.out.println(videoGameId);
        given()
                .spec(videoGameJsonRequestSpec).
        when()
                .delete(ENDPOINT + "/" +videoGameId).
        then()
                .log().all();
    }

    public void deleteVideoGameByGameId(String gameId) {
        given().
                spec(videoGameJsonRequestSpec).
        when().
                delete(ENDPOINT + "/" + gameId).
        then().
                log().all();
    }

    @Test
    public void createNewVideoGameByJSON() {

        given()
                .spec(videoGameJsonRequestSpec)
                .body(getJsonObject()).
        when()
                .post(ENDPOINT).
        then()
                .log().all();

        deleteCreatedVideoGameByJson();
    }

    @Test
    public void testVideoGameSerialisationByJSON() {

        VideoGame videoGame =
                new VideoGame("300", "RPG", "2000-01-01",
                        "World of Warcraft", "12+", "100");

        checkTheVideoGameAndDeletedBeforePost(videoGame.getName());

        given().
                spec(videoGameJsonRequestSpec).
                body(videoGame).
        when().
                post(ENDPOINT).
        then().
                log().all();

        String videoGameId = videoGame.getId();
        deleteVideoGameByGameId(videoGameId);
    }

    public void checkTheVideoGameAndDeletedBeforePost(String videoGameName) {

        Response response = given().spec(videoGameJsonRequestSpec).when().get(ENDPOINT);
        String videoGameId;

        Map<String, ?> allVideoGames = response.path(
                "find { it.name == '%s' }", videoGameName);

        if (allVideoGames != null) {
            videoGameId = allVideoGames.get("id").toString();
            deleteVideoGameByGameId(videoGameId);
        }



    }
}

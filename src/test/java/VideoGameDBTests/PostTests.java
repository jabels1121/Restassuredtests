package VideoGameDBTests;

import VideoGameDBTestsConfig.TestConfig;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;

import static VideoGameDBTestsConfig.VideoGamesEndPoint.ENDPOINT;
import static io.restassured.RestAssured.given;

public class PostTests extends TestConfig {

    public Object getJsonObject() {
        String fileName = "C:\\Users\\Jabels\\IdeaProjects\\Restassuredtests\\src\\test\\recources\\VideoGameJson.json";
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
}

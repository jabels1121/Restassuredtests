package FootballApiTests;

import VideoGameDBTestsConfig.TestConfig;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.element.Node;
import io.restassured.response.Response;
import org.junit.Test;
import java.util.List;

import static VideoGameDBTestsConfig.VideoGamesEndPoint.ENDPOINT;
import static io.restassured.RestAssured.given;

public class GPathXMLTests extends TestConfig {

    @Test
    public void getFirstGameInList() {

        Response response = given().spec(videoGameXMLRequestSpec).when().get(ENDPOINT);

        String name = response.path(
                "videoGames.videoGame.name[0]");

        System.out.println(name);

    }

    @Test
    public void getAttributeName() {

        Response response = given().spec(videoGameXMLRequestSpec).when().get(ENDPOINT);

        String category = response.path(
                "videoGames.videoGame[0].@category");

        System.out.println(category);

    }

    @Test
    public void getListOfXmlNodes() {

        String responseAsString = given().spec(videoGameXMLRequestSpec).when().get(ENDPOINT).asString();

        List<Node> allResults = XmlPath.from(responseAsString).get(
                "videoGames.videoGame.findAll { element -> return element }");

        System.out.println(allResults.get(0));
    }

    @Test
    public void getAllVideoGamesWithCertainCategory() {

        Response response = given().spec(videoGameXMLRequestSpec).when().get(ENDPOINT);

        List<Node> allResults = XmlPath.from(response.asString()).get(
                "videoGames.videoGame.findAll { it.@category == 'Shooter' }");

        // another variant of GPath expression - "videoGames.videoGame.findAll { videoGame -> def category = videoGame.@category; category == 'Driving'}"

        for (Node s : allResults) {
            System.out.println(s);
        }
    }

    @Test
    public void getSingleNode() {

        Response response = given().spec(videoGameXMLRequestSpec).when().get(ENDPOINT);

        Node videoGame = XmlPath.from(response.asString()).get(
                "videoGames.videoGame.find { videoGame -> def name = videoGame.name; name == 'Tetris'}");

        System.out.println(videoGame);
    }

    @Test
    public void getSingleElementDepthFirst() {

        Response response = given().spec(videoGameXMLRequestSpec).when().get(ENDPOINT);

        int reviewScore = XmlPath.from(response.asString()).getInt(
                "**.find { it.name == 'Gran Turismo 3' }.reviewScore"); // поиск по алгоритму DepthFirstSearch

        System.out.println(reviewScore);

    }

    @Test
    public void getAllNodesBasedOnACondition () {

        Response response = given().spec(videoGameXMLRequestSpec).when().get(ENDPOINT);

        int reviewScore = 90;

        List<Node> allVideoGameOverCertainScore = XmlPath.from(response.asString()).get(
                "videoGames.videoGame.findAll { it.reviewScore.toFloat() >= " + reviewScore + "}");

        System.out.println(allVideoGameOverCertainScore);

    }
}

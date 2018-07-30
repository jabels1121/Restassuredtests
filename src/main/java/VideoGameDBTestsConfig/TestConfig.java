package VideoGameDBTestsConfig;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;

public class TestConfig {

    public static RequestSpecification videoGameJsonRequestSpec;
    public static RequestSpecification videoGameXMLRequestSpec;
    public static RequestSpecification footbalApiJsonRequestSpec;
    public static ResponseSpecification responseSpec;

    @BeforeClass
    public static void setUp() {
        videoGameJsonRequestSpec = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(8080).
                setBasePath("/app/").
                setProxy(8888).
                addHeader("Accept", "application/json").
                addHeader("Content-type", "application/json").
                build();

        videoGameXMLRequestSpec  = new RequestSpecBuilder().
                setBaseUri("http://localhost").
                setPort(8080).
                setBasePath("/app/").
                setProxy(8888).
                addHeader("Accept", "application/xml").
                addHeader("Content-type", "application/xml").
                build();

        footbalApiJsonRequestSpec = new RequestSpecBuilder().
                setBaseUri("http://api.football-data.org").
                setBasePath("/v1/").
                addHeader("X-Auth-Token", "c97b760003f04ab19fdb234a6ea95ef9").
                setProxy(8888).
                addHeader("X-Response-Control", "minified").
                build();


        responseSpec = new ResponseSpecBuilder().
                expectStatusCode(200).
                build();

        RestAssured.responseSpecification = responseSpec;
    }

}

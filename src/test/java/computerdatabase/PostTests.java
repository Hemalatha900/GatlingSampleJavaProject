package computerdatabase;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class PostTests extends Simulation {
    //http protocol
    HttpProtocolBuilder httpProtocolBuilder = http.baseUrl("https://reqres.in");

    // scenario

    ChainBuilder createUser =
            exec(http("Create User").post("/api/users")
                    .header("Content-type", "application/json")
                    .header("Accept", "application/json")
                    .body(StringBody("{\n" +
                            "    \"name\": \"morpheus\",\n" +
                            "    \"job\": \"leader\"\n" +
                            "}")).check(status().is(201)));
    ScenarioBuilder postRequest = scenario("Create User").exec(createUser);

    {
        setUp(
                postRequest.injectOpen(rampUsers(10).during(10))
        ).protocols(httpProtocolBuilder);
    }


}

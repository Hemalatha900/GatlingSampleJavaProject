package computerdatabase;

//simulator , chainBuilder

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

public class getUser extends Simulation {

    //http protocal
    HttpProtocolBuilder httpProtocolBuilder = http.baseUrl("https://reqres.in");


    //Scenario
    ChainBuilder homePage =
            exec(http("Home").get("/")).pause(2);

    ChainBuilder getUsers =
            exec(http("Users").get("/api/users?page=2"));

    ChainBuilder listOfUsers =
            exec(http("List Of Users").get("/api/unknown")).pause(2);


    ScenarioBuilder normalUser = scenario("NormalUsers").exec(homePage, getUsers);
    ScenarioBuilder adminUsers = scenario("AdminUsers").exec(listOfUsers);

    // inject users

    {
        setUp(
                normalUser.injectOpen(rampUsers(10).during(10)),
                adminUsers.injectOpen(rampUsers(20).during(10))
        ).protocols(httpProtocolBuilder);
    }


}

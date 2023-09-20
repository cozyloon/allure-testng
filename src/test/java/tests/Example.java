package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import io.qameta.allure.Story;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.GraphQlQuery;
import model.QueryVariable;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Feature("Feature")
@Story("Story")
public class Example {

    private String graphQLQuery = "query ($id: String!, $price: Float!) {\n" +
            "  products(id: $id, price: $price) {\n" +
            "    name\n" +
            "    price\n" +
            "    category {\n" +
            "      name\n" +
            "    }\n" +
            "    vendor {\n" +
            "      name\n" +
            "      id\n" +
            "    }\n" +
            "  }\n" +
            "}";

    @Test
    public void testGraphQl() {
        GraphQlQuery query = new GraphQlQuery();
        query.setQuery(graphQLQuery);

        QueryVariable variable = new QueryVariable();
        variable.setId("7");
        variable.setPrice(7.3);

        query.setVariables(variable);

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://www.predic8.de/fruit-shop-graphql")
                .setContentType(ContentType.JSON)
                .setBody(query)
                .build();

        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectBody("data.products[0].name", equalTo("Cherrie"))
                .expectStatusCode(200)
                .build();

        given()
                .spec(requestSpecification)
                .post()
                .then()
                .spec(responseSpecification);
    }
}

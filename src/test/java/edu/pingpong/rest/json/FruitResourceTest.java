package edu.pingpong.rest.json;

import edu.pingpong.rest.json.domain.Fruit;
import edu.pingpong.rest.json.repository.FruitRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

@QuarkusTest
public class FruitResourceTest {

    @Inject
    FruitRepository repository;

    @BeforeEach
    public void initDB() {
        repository.initDB();
    }

    @Test
    public void fruitsDataEndpoint() {
        List<Map<String, Object>> fruits =
                given()
                        .contentType(ContentType.JSON)
                        .when().get("/fruits")
                        .as(new TypeRef<List<Map<String, Object>>>() {
                        });

        Assertions.assertThat(fruits).hasSize(2);

        Assertions.assertThat(fruits.get(0)).containsValue("Strawberry");
        Assertions.assertThat(fruits.get(0)).containsEntry("description", "Winter fruit");

        Assertions.assertThat(fruits.get(1)).containsValue("Orange");
        Assertions.assertThat(fruits.get(1)).containsEntry("description", "Summer fruit");
    }

    @Test
    public void fruitsDataTransactionTest() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/fruits")
                .then()
                .statusCode(200)
                .body("$.size()", is(2),
                        "name", containsInAnyOrder("Strawberry", "Orange"),
                        "description", containsInAnyOrder("Winter fruit", "Summer fruit"));
    }

    @Test
    public void addFruitTest() {
        Fruit fruit = new Fruit("test", "test");
        given()
                .body(fruit)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .post("/fruits")
                .then()
                .statusCode(202)
                .body("name", equalTo("test"));
    }

    @Test
    public void deleteFruitTest() {
        Fruit fruit = new Fruit("Orange", "Summer fruit");
        given()
                .body(fruit)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when()
                .delete("/fruits")
                .then()
                .statusCode(202)
                .body("name", equalTo("Orange"));
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/fruits")
                .then()
                .statusCode(200)
                .body("$.size()", is(1),
                        "name", Matchers.contains("Strawberry"),
                        "description", Matchers.contains("Winter fruit"));
    }

    @Test
    public void getFruitTest() {
        given()
                .pathParam("name", "Orange")
                .when()
                .get("/fruits/{name}")
                .then()
                .contentType(ContentType.JSON)
                .body("name", equalTo("Orange"));

        given()
                .pathParam("name", "Papaya")
                .when()
                .get("/fruits/{name}")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(404)
                .body(equalTo("The fruit with name Papaya doesn't exist."));
    }
}
package edu.pingpong.rest.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.assertj.core.api.Assertions;
import edu.pingpong.rest.json.domain.Fruit;
import edu.pingpong.rest.json.repository.FruitRepository;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

@QuarkusTest
public class FruitRepositoryTest {

    // ./mvnw -Dtest=FruitRepository test
    @Inject
    FruitRepository repository;

    @BeforeEach
    public void setupTest() {
        repository.initDB();
    }

    @Test
    public void checkSetupTest() {
        assertEquals(2, repository.getData().size());
    }

    @Test
    public void containsFruitTest() {
        assertTrue(repository.getData().stream().anyMatch(fruit -> fruit.getName().equalsIgnoreCase("Orange")));
    }

    @Test
    public void removeFruitTest() {
        repository.removeFruit("Orange");
        assertEquals(1, repository.getData().size());
        assertFalse(repository.getData().stream().anyMatch(fruit -> fruit.getName().equalsIgnoreCase("Orange")));
    }

    @Test
    public void addFruitTest() {
        repository.addFruit(new Fruit("Kiwi", "I am a f*****g kiwi"));
        assertEquals(3, repository.getData().size());
        assertTrue(repository.getData().stream().anyMatch(fruit -> fruit.getName().equalsIgnoreCase("kiwi")));
    }

    @Test
    public void getFruitTest() {
        Assertions.assertThat(repository.getFruit("Orange").get()).hasOnlyFields("name", "description").hasFieldOrPropertyWithValue("name", "Orange");
        Assertions.assertThat(repository.getFruit("IDontExist")).isEmpty();
    }
}

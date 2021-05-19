package edu.pingpong.rest.json;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Set;

// Single bean instance
@ApplicationScoped
public class FruitRepository {

    private Set<Fruit> fruitsData = Collections.newSetFromMap(Collections.synchronizedMap(new LinkedHashMap<>()));

    // Empty constructor for the CDI
    public FruitRepository() {
    }

    @PostConstruct
    public void initDB() {
        fruitsData.clear(); //Defensive clean of the set
        fruitsData.add(new Fruit("Strawberry", "Winter fruit"));
        fruitsData.add(new Fruit("Orange", "Summer fruit"));
    }

    public Set<Fruit> getData() {
        return this.fruitsData;
    }

    public void addFruit(Fruit fruit) {
        this.fruitsData.add(fruit);
    }

    public Optional<Fruit> getFruit(String name) {
        // Optional because it can't always exist, equalsIgnoreCase to check if the string is equals ignoring the case.
        return getData().stream().filter(fruit -> fruit.getName().equalsIgnoreCase(name)).findFirst();
    }

    public void removeFruit(String name) {
        getData().removeIf(fruit -> fruit.getName().equalsIgnoreCase(name));
    }
}

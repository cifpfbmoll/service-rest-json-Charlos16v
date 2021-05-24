package edu.pingpong.rest.json.service;

import edu.pingpong.rest.json.domain.Fruit;
import edu.pingpong.rest.json.repository.FruitRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;
import java.util.Set;

// Single bean instance
@ApplicationScoped
public class FruitService {

    // Dependency injection of the repository.
    @Inject
    FruitRepository repository;

    // Empty constructor for the CDI
    public FruitService() {
    }

    public Set<Fruit> getData() {
        return repository.getData();
    }

    public void addFruit(Fruit fruit) {
        repository.addFruit(fruit);
    }

    public Optional<Fruit> getFruit(String name) {
        return name.isBlank() ? Optional.empty() : repository.getFruit(name);
    }

    public void removeFruit(String name) {
        repository.removeFruit(name);
    }
}

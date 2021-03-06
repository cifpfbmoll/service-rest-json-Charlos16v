package edu.pingpong.rest.json.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@JsonPropertyOrder({"name", "description"})
public class Fruit {

    @NotBlank
    public String name;

    @NotEmpty
    public String description;

    // Required constructor by the JSON serialization layer
    public Fruit() {
    }

    public Fruit(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

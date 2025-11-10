package entities.TP3;

import jakarta.persistence.Entity;

@Entity
public class Author extends Person {
    public Author() {}
    public Author(String name) {
        super(name);
    }
}

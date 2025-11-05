package app;

import entities.*;
import jakarta.persistence.*;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Publisher publisher = new Publisher();
        publisher.setName("O'Reilly");

        Category c1 = new Category();
        c1.setName("Java");
        Category c2 = new Category();
        c2.setName("Backend");

        Book book = new Book();
        book.setTitle("Mastering JPA");
        book.setPrice(59.9);
        book.setPublisher(publisher);
        book.setCategories(List.of(c1, c2));

        Review r1 = new Review();
        r1.setComment("Excellent livre !");
        r1.setRating(5);
        r1.setBook(book);

        Review r2 = new Review();
        r2.setComment("Très complet.");
        r2.setRating(4);
        r2.setBook(book);

        book.setReviews(List.of(r1, r2));

        em.persist(publisher);
        em.persist(c1);
        em.persist(c2);
        em.persist(book);

        em.getTransaction().commit();

        System.out.println("Données insérées avec succès !");
        em.close();
        emf.close();
    }
}

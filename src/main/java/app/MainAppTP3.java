package app;

import entities.*;
import entities.TP3.*;
import jakarta.persistence.*;
import java.util.List;

public class MainAppTP3 {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookPU");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Publisher p1 = new Publisher("Mohamed");
        Author a1 = new Author("Nasser");
        Author a2 = new Author("Aymane");

        em.persist(p1);
        em.persist(a1);
        em.persist(a2);

        em.getTransaction().commit();

        System.out.println("Données TP3 insérées avec succès !");


        List<Person> persons = em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
        System.out.println("\n=== Liste des Personnes ===");
        for (Person p : persons) {
            System.out.println("👤 " + p.getName() + " (" + p.getClass().getSimpleName() + ")");
        }

        em.close();
        emf.close();
    }
}

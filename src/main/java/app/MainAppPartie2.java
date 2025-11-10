package app;

import entities.*;
import jakarta.persistence.*;
import java.util.List;

public class MainAppPartie2 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bookPU");
        EntityManager em = emf.createEntityManager();

        // ---  Récupérer tous les livres d'une catégorie donnée ---
        System.out.println("\n= Livres dans la catégorie 'Java' ===");
        TypedQuery<Book> q1 = em.createQuery(
                "SELECT b FROM Book b JOIN b.categories c WHERE c.name = :catName", Book.class);
        q1.setParameter("catName", "Java");
        List<Book> javaBooks = q1.getResultList();
        for (Book b : javaBooks) {
            System.out.println(" Livre : " + b.getTitle() + " | Prix : " + b.getPrice());
        }

        // ---  Livres publiés par un éditeur spécifique ---
        System.out.println("\n===  Livres publiés par 'O'Reilly' ===");
        TypedQuery<Book> q2 = em.createQuery(
                "SELECT b FROM Book b WHERE b.publisher.name = :pubName", Book.class);
        q2.setParameter("pubName", "O'Reilly");
        List<Book> booksByPublisher = q2.getResultList();
        for (Book b : booksByPublisher) {
            System.out.println(" " + b.getTitle() + " | Editeur : " + b.getPublisher().getName());
        }

        // ---  Supprimer un livre ---
        em.getTransaction().begin();
        System.out.println("\n===  Suppression d'un livre (id=1) ===");
        Book toDelete = em.find(Book.class, 3L);
        if (toDelete != null) {
            em.remove(toDelete);
            System.out.println("🗑 Livre supprimé : " + toDelete.getTitle());
        } else {
            System.out.println(" Aucun livre avec l'id=1 trouvé.");
        }
        em.getTransaction().commit();

        // --- Mettre à jour le prix d'un livre ---
        em.getTransaction().begin();
        System.out.println("\n===  Mise à jour du prix d'un livre ===");
        Book toUpdate = em.find(Book.class, 2L);
        if (toUpdate != null) {
            System.out.println("Ancien prix : " + toUpdate.getPrice());
            toUpdate.setPrice(49.99);
            System.out.println(" Nouveau prix : " + toUpdate.getPrice());
        } else {
            System.out.println(" Aucun livre avec l'id=2 trouvé.");
        }
        em.getTransaction().commit();

        // --- Tester EAGER vs LAZY ---
        System.out.println("\n===  Test du chargement (EAGER/LAZY) ===");
        Book b1 = em.find(Book.class, 3L);
        if (b1 != null) {
            System.out.println(" Livre trouvé : " + b1.getTitle());
            System.out.println("Chargement des avis...");
            List<Review> reviews = b1.getReviews(); // Si LAZY, cette ligne déclenche le chargement
            for (Review r : reviews) {
                System.out.println(" " + r.getComment() + " (" + r.getRating() + "/5)");
            }
        } else {
            System.out.println("Aucun livre avec l'id=3 trouvé.");
        }

        System.out.println("\n Partie 2 terminée avec succès !");
        em.close();
        emf.close();
    }
}

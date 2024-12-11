package org.example;

/**
 * Hello world!
 *
 */
import org.hibernate.Session;

public class App {
    public static void main(String[] args) {
        Session session1 = HibernateUtil.getSessionFactory().openSession();
        session1.beginTransaction();

        // Save a User
        User user = new User("John Doe", "john.doe@example.com");
        session1.save(user);
        session1.getTransaction().commit();

        // First-Level Cache Example
        System.out.println("First-Level Cache:");
        User cachedUser1 = session1.get(User.class, user.getId());
        User cachedUser2 = session1.get(User.class, user.getId()); // Fetches from first-level cache
        System.out.println(cachedUser1.getId());
        System.out.println(cachedUser2.getId());
        session1.close();

        // Second-Level Cache Example
        System.out.println("Second-Level Cache:");
        Session session2 = HibernateUtil.getSessionFactory().openSession();
        User secondLevelCachedUser = session2.get(User.class, user.getId());
        System.out.println(secondLevelCachedUser.getId()); // Fetches from second-level cache
        session2.close();
    }
}

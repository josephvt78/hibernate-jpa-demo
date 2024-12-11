package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.hibernate.Session;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        //return new TestSuite( AppTest.class );
        TestSuite suite = new TestSuite( AppTest.class );
        suite.addTest(new AppTest("saveUserSuccessfully"));
        suite.addTest(new AppTest("firstLevelCacheRetrievesSameInstance"));
        suite.addTest(new AppTest("secondLevelCacheRetrievesSameInstance"));
        return suite;
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void saveUserSuccessfully() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User user = new User("Jane Doe", "jane.doe@example.com");
        session.save(user);
        session.getTransaction().commit();
        User retrievedUser = session.get(User.class, user.getId());
        assertNotNull(retrievedUser);
        assertEquals("Jane Doe", retrievedUser.getName());
        assertEquals("jane.doe@example.com", retrievedUser.getEmail());
        session.close();
    }

    public void firstLevelCacheRetrievesSameInstance() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User user = new User("John Smith", "john.smith@example.com");
        session.save(user);
        session.getTransaction().commit();
        User cachedUser1 = session.get(User.class, user.getId());
        User cachedUser2 = session.get(User.class, user.getId());
        assertSame(cachedUser1, cachedUser2);
        session.close();
    }

    public void secondLevelCacheRetrievesSameInstance() {
        Session session1 = HibernateUtil.getSessionFactory().openSession();
        session1.beginTransaction();
        User user = new User("Alice Johnson", "alice.johnson@example.com");
        session1.save(user);
        session1.getTransaction().commit();
        session1.close();
        Session session2 = HibernateUtil.getSessionFactory().openSession();
        User secondLevelCachedUser = session2.get(User.class, user.getId());
        assertNotNull(secondLevelCachedUser);
        assertEquals(user.getId(), secondLevelCachedUser.getId());
        session2.close();
    }
}

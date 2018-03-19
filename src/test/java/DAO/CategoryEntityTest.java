package DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;

@TestInstance( TestInstance.Lifecycle.PER_CLASS )
class CategoryEntityTest{

    private SessionFactory sessionFactory;
    private Session        session;

    @BeforeAll
    void set(){
        sessionFactory = HibernateUtils.getSessionFactory();
    }

    @AfterAll
    void shutdown(){
        HibernateUtils.shutdown();
    }

    @BeforeEach
    void setUp(){
        session = sessionFactory.openSession();
    }

    @Test
    void list(){
        session.createQuery( "from CategoryEntity" , CategoryEntity.class )
               .list()
               .forEach( System.out::println );
    }

    @AfterEach
    void tearDown(){
        session.close();
    }
}
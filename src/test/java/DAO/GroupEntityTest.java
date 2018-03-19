package DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance( TestInstance.Lifecycle.PER_CLASS )
class GroupEntityTest{

    private SessionFactory sessionFactory;
    private Session        session;
    private Transaction    transaction;

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
        transaction = session.getTransaction();
    }

    @Test
    void save(){
        PersonEntity personEntity = new PersonEntity( "login" ,
                                                      "pass" ,
                                                      session.createQuery( "from RaceEntity" ,
                                                                           RaceEntity.class )
                                                             .setMaxResults( 1 )
                                                             .getSingleResult() );
        GroupEntity groupEntity = new GroupEntity( "group1" ,
                                                   session.createQuery( "from CategoryEntity" ,
                                                                        CategoryEntity.class )
                                                          .setMaxResults( 1 )
                                                          .getSingleResult() ,
                                                   personEntity );
        transaction.begin();
        session.save( personEntity );
        session.save( groupEntity );
        transaction.commit();
        assertTrue( session.createQuery( "from GroupEntity where name='group1'" ,
                                         GroupEntity.class ).uniqueResultOptional().isPresent() );
    }

    @AfterEach
    void tearDown(){
        transaction.rollback();
        session.close();
    }
}
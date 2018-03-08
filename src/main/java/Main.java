import DAO.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class Main{
    public static void main( final String[] args ){
        try( SessionFactory sessionFactory = HibernateUtils.getSessionFactory() ;
             Session session = sessionFactory.openSession() ){
            System.out.println( "querying all the managed entities..." );
            session.getSessionFactory().getMetamodel().getEntities().forEach( entityType -> {
                final String   entityName = entityType.getName();
                final Query<?> query      = session.createQuery( "from " + entityName );
                System.out.println( "executing: " + query.getQueryString() );
                query.list().forEach( o -> System.out.println( "  " + o ) );
            } );
        }
    }
}
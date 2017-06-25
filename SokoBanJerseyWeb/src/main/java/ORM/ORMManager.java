package ORM;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

public class ORMManager {
	private SessionFactory factory;

	public ORMManager() {
		Configuration configuration = new Configuration();
		configuration.configure();
		factory = configuration.buildSessionFactory();
	}

	public List<LevelSolution> getAllLevelSolutions() {
		Session session = factory.openSession();
		@SuppressWarnings("unchecked")
		Query<LevelSolution> query = session.createQuery("from LevelSolution");
		List<LevelSolution> list = query.list();
		return list;
	}
	

	public void addLevelSolution(LevelSolution levelSolution) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(levelSolution);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}
	

}

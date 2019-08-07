package tests;

import entities.Bug;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class InitializerBean {

    @PersistenceContext(name = "java.training")
    EntityManager entityManager;

    @PostConstruct
    public void onStartup() {
        Bug bug = new Bug();

        entityManager.persist(bug);
    }

}


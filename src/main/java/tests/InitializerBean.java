package tests;

import entities.Bug;
import entities.Right;
import entities.Role;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Singleton
@Startup
public class InitializerBean {

    @PersistenceContext(name = "java.training")
    EntityManager entityManager;

    @PostConstruct
    public void onStartup() {
//        addRights();
//        addRoles();
    }

    private void addRights() {
        List<Right> rights = new ArrayList<>();

        for(int i = 0; i < 5; i++)
            rights.add(new Right());

        rights.get(0).setRightName("PERMISSION_MANAGEMENT");
        rights.get(1).setRightName("USER_MANAGEMENT");
        rights.get(2).setRightName("BUG_MANAGEMENT");
        rights.get(3).setRightName("BUG_CLOSE");
        rights.get(4).setRightName("BUG_EXPORT_PDF");

        for(Right r : rights) {
            entityManager.persist(r);
        }
    }

    private void addRoles() {
        List<Role> roles = new ArrayList<>();

        for(int i = 0; i < 5; i++)
            roles.add(new Role());

        roles.get(0).setRoleName("ADMINISTRATOR");
        roles.get(1).setRoleName("PROJECT_MANAGER");
        roles.get(2).setRoleName("TEST_MANAGER");
        roles.get(3).setRoleName("DEVELOPER");
        roles.get(4).setRoleName("TESTER");

        for(Role r : roles) {
            entityManager.persist(r);
        }
    }
}


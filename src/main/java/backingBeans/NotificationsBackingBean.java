package backingBeans;

import entities.Notification;
import entities.User;
import lombok.Data;
import security.WebHelper;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ManagedBean(name = "notificationsBackingBean")
@ApplicationScoped
public class NotificationsBackingBean {

    private String description;
    private LocalDateTime creationDate;
    private String notificationType;

    private User user = (User) WebHelper.getSession().getAttribute("loggedInUser");

    private List<Notification> notificationList;

    @PostConstruct
    public void init() {
        notificationList = user.getNotifications();
    }

}

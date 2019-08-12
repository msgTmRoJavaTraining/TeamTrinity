package beans;

import lombok.Data;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@Data
@ManagedBean(name = "notificationsBackingBean")
@ApplicationScoped
public class NotificationsBackingBean {
}

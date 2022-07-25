package vaadin.crm.ui.view.login;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.Collections;

@Route("login")
@PageTitle("Login | Vaadin CRM")
@PermitAll
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private final LoginForm login = new LoginForm();

    public LoginView() {
        configureLoginView();

        configureLogin(login);

        add(
                createHeader(),
                login
        );
    }

    private void configureLoginView() {
        this.addClassName("login-view"); //to CSS control
        this.setSizeFull();
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setAlignItems(Alignment.CENTER);
    }

    private H1 createHeader() {
        H1 header = new H1();
        header.setText("Vaadin CRM");
        return header;
    }

    private void configureLogin(LoginForm login) {
        login.setAction("login");
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!event
                .getLocation()
                .getQueryParameters()
                .getParameters()
                .getOrDefault("error", Collections.emptyList())
                .isEmpty()
        ) {
            login.setError(true);
        }
    }
}

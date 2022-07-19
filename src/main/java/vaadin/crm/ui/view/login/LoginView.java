package vaadin.crm.ui.view.login;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;

import javax.annotation.security.PermitAll;
import java.util.Collections;

@Route("login")
@PageTitle("Login | Vaadin CRM")
@PermitAll
public class LoginView extends VerticalLayout implements BeforeEnterListener {

    private LoginForm login;
    private H1 header;

    public LoginView(){
        //base configuration
        this.addClassName("login-view"); //to CSS control
        this.setSizeFull();
        this.setJustifyContentMode(JustifyContentMode.CENTER);
        this.setAlignItems(Alignment.CENTER);

        //configure components
        configureHeader();
        configureLogin();

        //form
        add(
                header,
                login
        );
    }

    private void configureHeader() {
        header = new H1("Vaadin CRM");
    }

    private void configureLogin() {
        login = new LoginForm();
        login.setAction("login");
    }


    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(!event
                .getLocation()
                .getQueryParameters()
                .getParameters()
                .getOrDefault("error", Collections.emptyList())
                .isEmpty()
        ){
            login.setError(true);
        }
    }
}

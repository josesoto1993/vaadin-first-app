package vaadin.crm.it.elements;

import com.vaadin.flow.component.login.testbench.LoginFormElement;
import com.vaadin.flow.component.orderedlayout.testbench.VerticalLayoutElement;
import com.vaadin.testbench.annotations.Attribute;

@Attribute(name = "class", contains = "login-view")
public class LoginViewElement extends VerticalLayoutElement {

    public boolean login(String username, String password) {
        LoginFormElement loginForm = $(LoginFormElement.class).first();

        loginForm.getUsernameField().setValue(username);
        loginForm.getPasswordField().setValue(password);
        loginForm.getSubmitButton().click();

        return !($(LoginFormElement.class).exists());
    }
}

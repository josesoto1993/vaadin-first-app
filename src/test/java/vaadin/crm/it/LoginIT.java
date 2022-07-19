package vaadin.crm.it;

import com.vaadin.flow.component.login.testbench.LoginFormElement;
import org.junit.jupiter.api.Test;
import vaadin.crm.it.elements.LoginViewElement;

import static org.junit.jupiter.api.Assertions.*;

public class LoginIT extends AbstractTest{
    public LoginIT(){
        super("");
    }

    @Test
    public void loginAsValidUserSucceeds(){
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();

        assertTrue(loginView.login("user","userpass"));
    }

    @Test
    public void loginAsInvalidUserFail(){
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();

        assertFalse(loginView.login("asetes","userpass"));
    }

    @Test
    public void loginAsInvalidPassFail(){
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();

        assertFalse(loginView.login("user","ae453"));
    }

    @Test
    public void loginAsInvalidFail(){
        LoginViewElement loginView = $(LoginViewElement.class).onPage().first();

        assertFalse(loginView.login("asefg","aseg4"));
    }
}

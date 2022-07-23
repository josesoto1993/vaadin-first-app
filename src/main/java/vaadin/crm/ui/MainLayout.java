package vaadin.crm.ui;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import vaadin.crm.security.SecurityService;
import vaadin.crm.ui.view.dashboard.DashboardView;
import vaadin.crm.ui.view.list.ListView;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;

        //header
        addToNavbar(createHeader());

        //Drawer
        configureDrawer();
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout headerLayout = new HorizontalLayout();

        configureHeaderStyle(headerLayout);

        headerLayout.add(
                new DrawerToggle(),
                createLogo(),
                createLogoutButton()
        );

        return headerLayout;
    }

    private Button createLogoutButton() {
        Button logoutButton = new Button();
        logoutButton.setText("Log out");
        logoutButton.addClickListener(click -> securityService.logout());
        return logoutButton;
    }

    private H1 createLogo() {
        H1 headerLogo = new H1();
        headerLogo.setText("Vaadin CRM");
        headerLogo.addClassName("logo");
        return headerLogo;
    }

    private void configureHeaderStyle(HorizontalLayout headerLayout) {
        headerLayout.addClassName("header");
        headerLayout.setWidth("100%");
        headerLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    }

    private void configureDrawer() {
        VerticalLayout linksVL = new VerticalLayout();

        linksVL.add(
                createConfiguredRouterLink("List", ListView.class),
                createConfiguredRouterLink("Dashboard", DashboardView.class)
        );

        addToDrawer(linksVL);
    }

    private RouterLink createConfiguredRouterLink(String label, Class<? extends Component> viewClass) {
        final RouterLink routerLink = new RouterLink(label, viewClass);
        routerLink.setHighlightCondition(HighlightConditions.sameLocation());
        return routerLink;
    }
}

package vaadin.crm.ui;

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

import java.util.ArrayList;
import java.util.List;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    private final H1 headerLogo = new H1();
    private final DrawerToggle drawerToggle = new DrawerToggle();
    private final Button logoutButton = new Button();
    private final HorizontalLayout header = new HorizontalLayout();
    private final VerticalLayout linksVL = new VerticalLayout();
    private final SecurityService securityService;

    public MainLayout(SecurityService securityService) {
        this.securityService = securityService;
        configureHeader();
        addToNavbar(header);
        createDrawer();
    }

    private void configureHeader() {
        //base configurations
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        //components configurations
        configureHeaderLogo();
        configureLogoutButton();

        //add items to header
        header.add(drawerToggle);
        header.add(headerLogo);
        header.add(logoutButton);
    }

    private void configureHeaderLogo() {
        headerLogo.setText("Vaadin CRM");
        headerLogo.addClassName("logo");
    }

    private void configureLogoutButton() {
        logoutButton.setText("Log out");
        logoutButton.addClickListener(click -> securityService.logout());
    }

    private void createDrawer() {
        //create links
        List<RouterLink> listLink = new ArrayList<>();
        listLink.add(new RouterLink("List", ListView.class));
        listLink.add(new RouterLink("Dashboard", DashboardView.class));

        //configurations
        listLink.forEach(routerLink -> routerLink.setHighlightCondition(HighlightConditions.sameLocation()));

        //add links to vertical layout and the result VL to the drawer
        listLink.forEach(linksVL::add);
        addToDrawer(linksVL);
    }
}

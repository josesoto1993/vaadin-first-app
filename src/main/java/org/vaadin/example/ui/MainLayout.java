package org.vaadin.example.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import org.vaadin.example.ui.view.dashboard.DashboardView;
import org.vaadin.example.ui.view.list.ListView;

import java.util.ArrayList;
import java.util.List;

@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    H1 headerLogo = new H1();
    DrawerToggle drawerToggle = new DrawerToggle();
    HorizontalLayout header = new HorizontalLayout();
    VerticalLayout linksVL = new VerticalLayout();

    public MainLayout() {
        configureHeader();
        addToNavbar(header);
        createDrawer();
    }

    private void configureHeader() {
        //base configurations
        header.addClassName("header");
        header.setWidth("100%");
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        //logo: for now, just a plain text
        headerLogo.setText("Vaadin CRM");
        headerLogo.addClassName("logo");

        //add items to header
        header.add(drawerToggle);
        header.add(headerLogo);
    }

    private void createDrawer() {
        //create links
        List<RouterLink> listLink = new ArrayList<>();
        listLink.add(new RouterLink("List", ListView.class));
        listLink.add(new RouterLink("Dashboard", DashboardView.class));

        //configurations
        listLink.forEach(routerLink -> routerLink.setHighlightCondition(HighlightConditions.sameLocation()));

        //add links to vertical layout and the result VL to the drawer
        listLink.forEach(routerLink -> linksVL.add(routerLink));
        addToDrawer(linksVL);
    }
}

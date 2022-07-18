package org.vaadin.example.ui;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.vaadin.example.backend.entity.Company;
import org.vaadin.example.backend.entity.Contact;
import org.vaadin.example.service.ContactService;

@Route("")
public class MainView extends VerticalLayout {

    private final ContactService contactService;

    Grid<Contact> grid = new Grid<>(Contact.class);
    TextField filterText = new TextField();

    public MainView(ContactService contactService) {
        //Config data
        this.contactService = contactService;
        this.addClassName("list-view"); //to CSS control
        this.setSizeFull();

        //Load the grid
        configureGrid();
        updateContacts();

        //Load text filter
        configureFilter();

        //Add components
        this.add(filterText, grid);
    }

    private void configureFilter() {
        //to CSS control
        filterText.addClassName("contact-text-filter");

        //configuration
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);

        //actual controller
        filterText.addValueChangeListener(text -> updateContacts());

    }

    private void configureGrid() {
        //to CSS control
        grid.addClassName("contact-Grid");

        //to get all possible space
        grid.setSizeFull();

        //main data from table
        //grid.removeColumnByKey("company"); // company must be configured as its a class
        grid.setColumns("firstName", "lastName", "email", "status");

        //company configuration
        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            if (company == null) {
                return "-";
            }
            return company.getName();
        }).setHeader("Company");

        //allocating space in columns
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void updateContacts() {
        grid.setItems(contactService.findWithFilterName(filterText.getValue()));
    }

}

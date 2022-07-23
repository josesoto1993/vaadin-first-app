package vaadin.crm.ui.view.list;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import vaadin.crm.backend.entity.Company;
import vaadin.crm.backend.entity.Contact;
import vaadin.crm.service.CompanyService;
import vaadin.crm.service.ContactService;
import vaadin.crm.ui.MainLayout;

import javax.annotation.security.PermitAll;
import java.util.List;

@Component
@Scope("prototype")
@Route(value = "", layout = MainLayout.class)
@PageTitle("Contacts | Vaadin CRM")
@PermitAll
public class ListView extends VerticalLayout {
    //services
    private final ContactService contactService;
    private final CompanyService companyService;

    //main view components
    private final Grid<Contact> grid = new Grid<>(Contact.class);
    private final TextField filterText = new TextField();
    private final Button newContactBtn = new Button();
    private final HorizontalLayout toolbar = new HorizontalLayout();

    //edit form component
    private ContactForm contactForm;

    public ListView(ContactService contactService,
                    CompanyService companyService) {
        //base configuration
        this.contactService = contactService;
        this.companyService = companyService;
        this.addClassName("list-view"); //to CSS control
        this.setSizeFull();

        //Load the grid
        configureGrid();
        updateContacts();

        //Load toolbar (filter + new Contact)
        configureToolBar();

        //Load the contact form
        configureForm();

        //Add components
        Div content = new Div(grid, contactForm);
        content.addClassName("content");
        content.setSizeFull();

        this.add(toolbar, content);
        closeEditor();
    }

    public Grid<Contact> getGrid() {
        return grid;
    }

    public ContactForm getContactForm() {
        return contactForm;
    }

    private void configureToolBar() {
        //to CSS control
        toolbar.addClassName("toolbar");

        //Load text filter
        configureFilter();

        //Load button for new contact
        configureNewContactBtn();

        //put both in toolbar
        toolbar.add(filterText, newContactBtn);
    }

    private void configureNewContactBtn() {
        newContactBtn.setText("Add contact");
        newContactBtn.addClickListener(click -> addContact());
    }

    private void addContact() {
        grid.asSingleSelect().clear();
        editContact(new Contact());
    }

    private void configureForm() {

        //to CSS control
        //contactForm.addClassName("contact-form");//done in constructor, so all forms look's equals

        //create a new one if null:
        if (contactForm == null) {
            //create form
            contactForm = new ContactForm(companyService.findAll(), List.of(Contact.Status.values()));
            //add the listener's for the buttons
            contactForm.addListener(ContactForm.SaveEvent.class, this::saveContactForm);
            contactForm.addListener(ContactForm.DeleteEvent.class, this::deleteContactForm);
            contactForm.addListener(ContactForm.CloseEvent.class, this::closeContactForm);
        }

        //to get all possible space
        contactForm.setSizeFull();
    }

    private void saveContactForm(ContactForm.SaveEvent saveEvent) {
        contactService.save(saveEvent.getContact());
        updateContacts();
        closeEditor();
    }

    private void deleteContactForm(ContactForm.DeleteEvent deleteEvent) {
        contactService.delete(deleteEvent.getContact());
        updateContacts();
        closeEditor();
    }

    private void closeContactForm(ContactForm.CloseEvent closeEvent) {
        closeEditor();
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
        grid.addClassName("contact-grid");

        //to get all possible space
        grid.setSizeFull();

        //main data from table
        //grid.removeColumnByKey("company"); // company must be configured as it's a class
        grid.setColumns("firstName", "lastName", "email", "status");

        //company configuration
        grid.addColumn(contact -> {
            Company company = contact.getCompany();
            if (company == null) {
                return "-";
            }
            return company.getName();
        }).setHeader("Company").setSortable(true);

        //allocating space in columns
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        //selector to interact
        grid.asSingleSelect().addValueChangeListener(gridNewSelect -> editContact(gridNewSelect.getValue()));
    }

    private void editContact(Contact contact) {
        if (contact == null) {
            //deselected
            closeEditor();
        } else {
            openEditor(contact);
        }
    }

    private void openEditor(Contact contact) {
        contactForm.setVisible(true);
        contactForm.setContact(contact);
        addClassName("editing");
    }

    private void closeEditor() {
        contactForm.setContact(null);
        contactForm.setVisible(false);
        removeClassName("editing");
    }

    private void updateContacts() {
        grid.setItems(contactService.findWithFilterName(filterText.getValue()));
    }

}

package org.vaadin.example.ui;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import org.vaadin.example.backend.entity.Company;
import org.vaadin.example.backend.entity.Contact;

import java.util.List;

public class ContactForm extends FormLayout {
    //main fields
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    ComboBox<Contact.Status> status = new ComboBox<>("Status");
    ComboBox<Company> company = new ComboBox<>("Company");

    //buttons to interact
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    //binder to interact
    Binder<Contact> binder = new BeanValidationBinder<>(Contact.class);
    private Contact contact;

    /**
     * bean constructor so can be called from other places to create ui with vaadin
     */
    public ContactForm(List<Company> companies, List<Contact.Status> validStatus) {
        addClassName("contact-form");

        binder.bindInstanceFields(this);

        //config dropdowns
        status.setItems(validStatus);
        company.setItems(companies);
        company.setItemLabelGenerator(Company::getName);

        //add components
        add(firstName,
                lastName,
                email,
                company,
                status,
                createButtonsLayout());
    }

    public void setContact(Contact contact) {
        this.contact = contact;
        binder.setBean(contact);
    }

    /**
     * buttons configurations and settings
     *
     * @return the final layout with all the buttons
     */
    private HorizontalLayout createButtonsLayout() {
        //visuals
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        //shortcuts
        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        //events
        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        //Make more user friendly
        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        //return the compound
        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

    // Events
    public static abstract class ContactFormEvent extends ComponentEvent<ContactForm> {
        private final Contact contact;

        protected ContactFormEvent(ContactForm source, Contact contact) {
            super(source, false);
            this.contact = contact;
        }

        public Contact getContact() {
            return contact;
        }
    }

    public static class SaveEvent extends ContactFormEvent {
        SaveEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }
    }

    public static class DeleteEvent extends ContactFormEvent {
        DeleteEvent(ContactForm source, Contact contact) {
            super(source, contact);
        }
    }

    public static class CloseEvent extends ContactFormEvent {
        CloseEvent(ContactForm source) {
            super(source, null);
        }
    }
}
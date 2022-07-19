package vaadin.crm.ui.view.list;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import vaadin.crm.backend.entity.Company;
import vaadin.crm.backend.entity.Contact;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ContactFormTest {
    private List<Company> companies;
    private List<Contact.Status> statuses;
    private Contact marcUsher;
    private Company company1;
    private Company company2;
    private Contact.Status status1;
    private Contact.Status status2;

    @BeforeEach
    public void setupData() {
        companies = new ArrayList<>();
        company1 = new Company();
        company1.setName("Vaadin Ltd");
        company2 = new Company();
        company2.setName("IT Mill");
        companies.add(company1);
        companies.add(company2);

        statuses = new ArrayList<>();
        status1 = Contact.Status.Contacted;
        status2 = Contact.Status.NotContacted;
        statuses.add(status1);
        statuses.add(status2);

        marcUsher = new Contact();
        marcUsher.setFirstName("Marc");
        marcUsher.setLastName("Usher");
        marcUsher.setEmail("marc@usher.com");
        marcUsher.setStatus(status1);
        marcUsher.setCompany(company2);
    }

    @Test
    public void fromFieldsPopulated(){
        ContactForm contactForm = new ContactForm(companies, statuses);
        contactForm.setContact(marcUsher);

        assertEquals("Marc", contactForm.firstName.getValue());
        assertEquals("Usher", contactForm.lastName.getValue());
        assertEquals("marc@usher.com", contactForm.email.getValue());
        assertEquals(company2, contactForm.company.getValue());
        assertEquals(status1, contactForm.status.getValue());
    }

    @Test
    public void saveEventHasCorrectValues() {
        ContactForm form = new ContactForm(companies, statuses);
        Contact contact = new Contact();
        form.setContact(contact);
        form.firstName.setValue("John");
        form.lastName.setValue("Doe");
        form.company.setValue(company1);
        form.email.setValue("john@doe.com");
        form.status.setValue(status2);

        AtomicReference<Contact> savedContactRef = new AtomicReference<>(null);
        form.addListener(ContactForm.SaveEvent.class, e -> savedContactRef.set(e.getContact()));

        form.save.click();

        Contact savedContact = savedContactRef.get();

        assertEquals("John", savedContact.getFirstName());
        assertEquals("Doe", savedContact.getLastName());
        assertEquals("john@doe.com", savedContact.getEmail());
        assertEquals(company1, savedContact.getCompany());
        assertEquals(status2, savedContact.getStatus());
    }
}
package vaadin.crm.service;

import org.springframework.stereotype.Service;
import vaadin.crm.backend.entity.Company;
import vaadin.crm.backend.entity.Contact;
import vaadin.crm.backend.repository.CompanyRepository;
import vaadin.crm.backend.repository.ContactRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class Populator {

    private final ContactRepository contactRepository;

    private final CompanyRepository companyRepository;

    public Populator(ContactRepository contactRepository,
                     CompanyRepository companyRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
    }

    @PostConstruct
    public void populateTestData() {
        if (companyRepository.count() == 0) {
            companyRepository.saveAll(
                    Stream.of("Path-Way Electronics", "E-Tech Management", "Path-E-Tech Management")
                            .map(Company::new)
                            .collect(Collectors.toList()));
        }
        if (contactRepository.count() == 0) {
            Random r = new Random(0);
            List<Company> companies = companyRepository.findAll();
            contactRepository.saveAll(
                    Stream.of("Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
                                    "Koen Johansen", "Alejandro Macdonald", "Angel Karlsson",
                                    "Yahir Gustavsson", "Haiden Svensson", "Emily Stewart",
                                    "Corinne Davis", "Ryann Davis", "Yurem Jackson",
                                    "Kelly Gustavsson", "Eileen Walker", "Katelyn Martin",
                                    "Israel Carlsson", "Quinn Hansson", "Makena Smith",
                                    "Danielle Watson", "Leland Harris", "Gunner Karlsen",
                                    "Jamar Olsson", "Lara Martin", "Ann Andersson",
                                    "Remington Andersson", "Rene Carlsson", "Elvis Olsen",
                                    "Solomon Olsen", "Jaydan Jackson", "Bernard Nilsen")
                            .map(name -> {

                                String[] split = name.split(" ");

                                Contact contact = new Contact();

                                contact.setFirstName(split[0]);

                                contact.setLastName(split[1]);

                                contact.setCompany(companies.get(r.nextInt(companies.size())));

                                contact.setStatus(Contact.Status.values()[r.nextInt(Contact
                                        .Status.values().length)]);

                                String email = (contact.getFirstName()
                                        + "."
                                        + contact.getLastName()
                                        + "@"
                                        + contact.getCompany().getName().replaceAll("[\\s-]", "")
                                        + ".com")
                                        .toLowerCase();

                                contact.setEmail(email);

                                return contact;
                            }).collect(Collectors.toList()));
        }
    }
}



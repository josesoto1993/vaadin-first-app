package org.vaadin.example.service;

import org.springframework.stereotype.Service;
import org.vaadin.example.backend.entity.Contact;
import org.vaadin.example.backend.repository.ContactRepository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ContactService {

    private static final Logger LOGGER = Logger.getLogger(ContactService.class.getName());

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    public List<Contact> findWithFilterName(String filterText) {
        if(filterText == null || filterText.isEmpty()){
            return findAll();
        }else{
            return contactRepository.findByName(filterText);
        }
    }

    public long count() {
        return contactRepository.count();
    }

    public void delete(Contact contact) {
        contactRepository.delete(contact);
    }

    public void save(Contact contact) {
        if (contact == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact is null."
                            + " Are you sure you have connected"
                            + " your form to the application?");
            return;
        }
        contactRepository.save(contact);
    }
}
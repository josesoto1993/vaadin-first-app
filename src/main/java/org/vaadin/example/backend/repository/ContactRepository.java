package org.vaadin.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.vaadin.example.backend.entity.Contact;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("SELECT c FROM Contact c " +
            "WHERE lower(c.firstName) LIKE lower(concat('%', :filterText, '%')) " +
            "or lower(c.lastName) LIKE lower(concat('%', :filterText, '%'))")
    List<Contact> findByName(@Param("filterText") String filterText);
}

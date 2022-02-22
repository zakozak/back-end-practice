package com.project.diplom.repo;

import com.project.diplom.model.entity.Contact;
import com.project.diplom.model.entity.ContactId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, ContactId> {
    List<Contact> findAllByUserId(Long userId);

    List<Contact> findAllByDestId(Long destId);
}

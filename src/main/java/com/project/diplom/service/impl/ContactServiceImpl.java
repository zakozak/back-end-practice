package com.project.diplom.service.impl;

import com.project.diplom.model.dto.ContactViewDto;
import com.project.diplom.model.entity.Contact;
import com.project.diplom.model.entity.ContactId;
import com.project.diplom.repo.ContactRepository;
import com.project.diplom.repo.UserRepository;
import com.project.diplom.service.ContactService;
import com.project.diplom.shared.ContactMapper;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ContactServiceImpl(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Set<ContactViewDto> getContactViews(Long authId) {
        return Stream.concat(
                contactRepository.findAllByUserId(authId)
                        .stream()
                        .map(c -> ContactMapper.mapToContactViewDto(c.getDestUser())),
                contactRepository.findAllByDestId(authId)
                        .stream()
                        .map(c -> ContactMapper.mapToContactViewDto(c.getUser())))
                .collect(Collectors.toSet());
    }

    @Override
    public void createContact(Long authId, Long destId) {
        Contact contact = new Contact();
        contact.setUserId(authId);
        contact.setDestId(destId);
        contactRepository.save(contact);
    }

    @Override
    public void deleteContact(Long authId, Long destId) {
        contactRepository.deleteById(new ContactId(authId, destId));
    }
}

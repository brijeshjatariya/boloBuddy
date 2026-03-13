package com.bolobuddy.service;

import com.bolobuddy.model.Child;
import com.bolobuddy.repository.ChildRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChildService {

    private final ChildRepository childRepository;

    public ChildService(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    public Child create(Child child) {
        return childRepository.save(child);
    }

    public Child getById(Long id) {
        return childRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Child not found with id: " + id));
    }

    public List<Child> getAll() {
        return childRepository.findAll();
    }

    public Child update(Long id, Child updateRequest) {
        Child existing = getById(id);
        existing.setName(updateRequest.getName());
        existing.setAge(updateRequest.getAge());
        existing.setAvatarColor(updateRequest.getAvatarColor());
        if (updateRequest.getLevel() != null) {
            existing.setLevel(updateRequest.getLevel());
        }
        if (updateRequest.getTotalStars() != null) {
            existing.setTotalStars(updateRequest.getTotalStars());
        }
        return childRepository.save(existing);
    }
}

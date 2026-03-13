package com.bolobuddy.controller;

import com.bolobuddy.model.Child;
import com.bolobuddy.service.ChildService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/children")
public class ChildController {

    private final ChildService childService;

    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Child createChild(@Valid @RequestBody Child child) {
        return childService.create(child);
    }

    @GetMapping("/{id}")
    public Child getChildById(@PathVariable Long id) {
        return childService.getById(id);
    }

    @GetMapping
    public List<Child> getAllChildren() {
        return childService.getAll();
    }

    @PutMapping("/{id}")
    public Child updateChild(@PathVariable Long id, @Valid @RequestBody Child child) {
        return childService.update(id, child);
    }
}

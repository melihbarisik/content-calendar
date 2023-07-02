package com.danvega.contentcalendar.controller;

import com.danvega.contentcalendar.model.Content;
import com.danvega.contentcalendar.repository.ContentCollectionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/content")
@CrossOrigin
public class ContentController {

    private final ContentCollectionRepository contentRepository;

    @Autowired
    public ContentController(ContentCollectionRepository contentCollectionRepository) {
        contentRepository = contentCollectionRepository;
    }

    // make a request and find all the pieces of content in the system
    @GetMapping("")
    public List<Content> getAll(){
        return contentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Content getById(@PathVariable Integer id){
        return contentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found!"));
    }

    @GetMapping("status/{status}")
    public Stream<Content> filterByStatus(@PathVariable String status) {
        return contentRepository.filterByStatus(status);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void createContent(@Valid @RequestBody Content content) {
        contentRepository.save(content);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateContent(@Valid @RequestBody Content content, @PathVariable Integer id) {
       if (!contentRepository.existById(id)) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found!");
       }
       contentRepository.update(content);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteContent(@PathVariable Integer id) {
       if (!contentRepository.existById(id)) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found!");
       }
        contentRepository.delete(id);
    }

}

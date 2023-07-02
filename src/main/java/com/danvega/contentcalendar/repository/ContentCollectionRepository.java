package com.danvega.contentcalendar.repository;

import com.danvega.contentcalendar.model.Content;
import com.danvega.contentcalendar.model.Status;
import com.danvega.contentcalendar.model.Type;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class ContentCollectionRepository {

    private final List<Content> contentList = new ArrayList<>();

    public ContentCollectionRepository() {}

    public List<Content> findAll() {
        return contentList;
    }

    public Optional<Content> findById(Integer id) {
        return contentList.stream().filter(content -> content.id().equals(id)).findFirst();
    }

    public Stream<Content> filterByStatus(String status) {
        return contentList.stream().filter(content -> content.status().equals(status));
    }

    public void save(Content content) {
        boolean exist = contentList.stream().filter(c -> c.id().equals(content.id())).count() == 1;
        if (exist) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This id is already used!");
        }
        contentList.add(content);
    }

    public void update(Content content) {
        contentList.removeIf(c -> c.id().equals(content.id()));
        contentList.add(content);
    }

    public void delete(Integer id) {
        contentList.removeIf(c -> c.id().equals(id));
    }

    public boolean existById(Integer id) {
        return contentList.stream().filter(content -> content.id().equals(id)).count() == 1;
    }

    @PostConstruct
    private void init() {
        Content c = new Content(
                1,
                "My First Blog Code",
                "My First Blog Post",
                Status.IDEA,
                Type.ARTICLE,
                LocalDateTime.now(),
                null, "");

        contentList.add(c);
    }
}

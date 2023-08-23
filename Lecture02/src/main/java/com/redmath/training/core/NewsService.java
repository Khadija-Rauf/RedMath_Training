package com.redmath.training.core;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class NewsService {

    private final NewsRepository repository;

    public NewsService(NewsRepository repository) {

        this.repository = repository;
    }

    public List<News> findAll() {
        return repository.findAll();
    }

    public Optional<News> findById(Long id) {
        return repository.findById(id);
    }

    public News create(News news) {
        if (news.getId() != null && repository.existsById(news.getId())) {
            return null;
        }
        news.setId(System.currentTimeMillis());
        news.setReportedAt(LocalDateTime.now());
        return repository.save(news);
    }
    public Optional<Void> delete(News news){
        if (news.getId() != null && repository.existsById(news.getId())) {
            repository.delete(news);
            return Optional.empty();
        }

        return null;
    }
    public News updateNews(Long id, News updatedNews) {
        Optional<News> optionalNews = repository.findById(id);

        if (optionalNews.isPresent()) {
            News existingNews = optionalNews.get();
            existingNews.setTitle(updatedNews.getTitle());
            existingNews.setDetails(updatedNews.getDetails());
            existingNews.setTags(updatedNews.getTags());
            return repository.save(existingNews);
        } else {
            return null;
        }
    }
}
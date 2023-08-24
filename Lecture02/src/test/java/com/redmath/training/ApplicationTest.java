package com.redmath.training;

import com.redmath.training.core.News;
import com.redmath.training.core.NewsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    @Test
    public void testFindAll() throws Exception {
        List<News> newsList = generateSampleNewsList(5); // Change 5 to the desired number of articles

        Mockito.when(newsService.findAll()).thenReturn(newsList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(newsList.size()));

    }

    // Helper method to generate sample news articles
    private List<News> generateSampleNewsList(int count) {
        return IntStream.range(1, count + 1)
                .mapToObj(i -> {
                    News news = new News();
                    news.setId((long) i);
                    news.setTitle("News " + i);
                    news.setDetails("Details " + i);
                    news.setTags("Tags " + i);
                    news.setReportedAt(LocalDateTime.now());
//                    System.out.println("Created News instance: " + news.getTags()+news.getDetails());
                    return news;
                })
                .collect(Collectors.toList());
    }
    @Test
    public void testFindById() throws Exception {
        Long sampleId = 1L;
        News sampleNews = new News();
        sampleNews.setId(sampleId);
        sampleNews.setTitle("title 1");
        sampleNews.setDetails("details 1");
        sampleNews.setTags("tags 1");

        Mockito.when(newsService.findById(Mockito.anyLong())).thenReturn(Optional.of(sampleNews));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/news/{id}", sampleId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(sampleId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(sampleNews.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details").value(sampleNews.getDetails()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags").value(sampleNews.getTags()));
    }
    @Test
    public void testDelete() throws Exception {
        // Create a sample News object with a known ID
        Long sampleId = 1L;
        News sampleNews = new News();

        Mockito.when(newsService.findById(Mockito.eq(sampleId))).thenReturn(Optional.of(sampleNews));
        Mockito.when(newsService.delete(Mockito.any(News.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/news/{id}", sampleId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    @Test
    public void testUpdate() throws Exception {
        // Create a sample News object with a known ID
        Long sampleId = 1L;
        News sampleNews = new News();
        sampleNews.setId(sampleId);
        sampleNews.setTitle("Sample News");
        sampleNews.setDetails("Sample Details");

        Mockito.when(newsService.updateNews(Mockito.eq(sampleId), Mockito.any(News.class))).thenReturn(sampleNews);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/news/{id}", sampleId)
                        .contentType("application/json")
                        .content("{\"title\":\"Updated Title\",\"details\":\"Updated Details\",\"tags\":\"Updated Tags\"}"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(sampleId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(sampleNews.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details").value(sampleNews.getDetails()));
    }
    @Test
    public void testCreate() throws Exception {
        // Create a sample News object without an ID (indicating it's a new resource)
        News sampleNews = new News();
        sampleNews.setTitle("New Title");
        sampleNews.setDetails("New Details");
        sampleNews.setTags("New Tags");
        sampleNews.setReportedAt(LocalDateTime.now()); // Set the reportedAt field

        // Mock the behavior of the NewsService for the create operation
        Mockito.when(newsService.create(Mockito.any(News.class))).thenReturn(sampleNews);

        // Create a JSON request body with the reportedAt field
        String requestBody = "{\"title\":\"New Title\",\"details\":\"New Details\",\"tags\":\"New Tags\",\"reportedAt\":\"2023-08-30T10:00:00\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/news") // Use POST for create
                        .contentType("application/json")
                        .content(requestBody))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(sampleNews.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.details").value(sampleNews.getDetails()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tags").value(sampleNews.getTags()));
//                .andExpect(MockMvcResultMatchers.jsonPath("$.reportedAt").value("2023-08-30T10:00:00"));
    }


}
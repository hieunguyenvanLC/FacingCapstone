package main;

import model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@SpringBootApplication()
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping("/article")
    public List<Article> getAllArticleList(){
        return articleService.getAllArticleList();
    }
    @RequestMapping("/article/{id}")
    public Article getArticle(@PathVariable String id){
        return articleService.getArticle(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/articles")
    public void addArticle(@RequestBody Article article){
        articleService.addArticle(article);
    }
}

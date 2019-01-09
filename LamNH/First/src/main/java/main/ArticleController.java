package main;

import model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public class ArticleController {

    @Autowired
    private  ArticleService articleService;

    @RequestMapping("/articles")
    public List<Article> getAll(){
        return articleService.getAll();
    }
}

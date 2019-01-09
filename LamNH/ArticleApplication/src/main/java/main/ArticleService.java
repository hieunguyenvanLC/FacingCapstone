package main;

import model.Article;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ArticleService {

   List<Article> articleList=new ArrayList<Article>(Arrays.asList(
          new Article("1","Test1","01234"),
            new Article("2","Test2","1232145")
   ));
    public List<Article> getAllArticleList(){
       return articleList;
   }
    public Article getArticle(final String id){
        return articleList.stream().filter(t->t.getId().equals(id)).findFirst().get();
    }

    public void addArticle(Article article) {
       articleList.add(article);
    }
}

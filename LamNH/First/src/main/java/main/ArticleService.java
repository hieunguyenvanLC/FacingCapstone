package main;

import model.Article;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class ArticleService {
    List<Article> articleList = new ArrayList<Article>(Arrays.asList(
            new Article("1","Test01","It is Des01"),
            new Article("2","Test02","It is Des02"),
            new Article("3","Test03","It is Des03")
    ));

    public List<Article> getAll(){
        return articleList;
    }
}

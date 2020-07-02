package abhijit.osdm_wop.models;

import java.util.List;

public class BBC_News {
    String status;



    String source;
    String sortedBy;
    List<NewsArticles> articles;


    public BBC_News(){}

    public BBC_News(String status, String source, String sortedBy, List<NewsArticles> articles) {
        this.status = status;
        this.source = source;
        this.sortedBy = sortedBy;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSortedBy() {
        return sortedBy;
    }

    public void setSortedBy(String sortedBy) {
        this.sortedBy = sortedBy;
    }

    public List<NewsArticles> getArticles() {
        return articles;
    }

    public void setArticles(List<NewsArticles> articles) {
        this.articles = articles;
    }


}

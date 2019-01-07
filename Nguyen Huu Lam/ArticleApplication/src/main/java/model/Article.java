package model;

public class Article {
    private String  id;
    private String name, phone;

    public Article(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Article() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

package io.coderbii.jumia.models;

public class Country {

    private Integer id;
    private String name;
    private String regex;
    private String code;

    public Country(Integer id, String name, String regex, String code) {
        this.id = id;
        this.name = name;
        this.regex = regex;
        this.code = code;
    }

    public Country() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", regex='" + regex + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

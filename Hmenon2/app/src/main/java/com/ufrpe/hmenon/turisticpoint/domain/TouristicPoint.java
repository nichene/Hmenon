package com.ufrpe.hmenon.turisticpoint.domain;


public class TouristicPoint {
    private Long id;
    private String name;
    private History history;
    private String image;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public History getHistory() {
        return history;
    }
    public void setHistory(History history) {
        this.history = history;
    }
    public void setHistoryText(String text){
        this.history.setCompleteHistory(text);
    }
    public void setHistoryResume(String text){
        this.history.setResume(text);
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
}

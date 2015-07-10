package com.ufrpe.hmenon.touristicpoint.domain;

/**
 * Representação da estrutura de um ponto turístico.
 * <p>
 * Apenas implementa getters e setters.
 */
public class TouristicPoint {
    /**
     * Identificador único para registro no banco de dados.
     */
    private Long id;
    private String name;
    private History history;
    private String image;
    private String activityText;
    private String coordinates;
    private String map;
    private String address;
    private int checked;

    public TouristicPoint() {
        setHistory(new History());
    }
    public String getCoordinates() {
        return coordinates;
    }
    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
    public int getChecked() {
        return checked;
    }
    public void setChecked(int checked) {
        this.checked = checked;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getMap() {
        return map;
    }
    public void setMap(String map) {
        this.map = map;
    }

    public String getActivityText() {
        return activityText;
    }
    public void setActivityText(String activityText) {
        this.activityText = activityText;
    }

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

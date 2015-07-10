package com.ufrpe.hmenon.touristicpoint.domain;

/**
 * Representação da história do ponto turístico.
 * <p>
 * Apenas mplementa getters e setters.
 */
public class History {
    private String resume;
    private String completeHistory;

    public String getResume() {
        return resume;
    }
    public void setResume(String resume) {
        this.resume = resume;
    }
    public String getCompleteHistory() {
        return completeHistory;
    }
    public void setCompleteHistory(String completeHistory) {
        this.completeHistory = completeHistory;
    }

}

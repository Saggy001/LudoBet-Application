package com.Saggy.ludobet;

public class historyList {
    private String history;
    private String dateandtime;

    public String getHistory() {
        return history;
    }

    public historyList(){
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public historyList(String history,String dateandtime) {
        this.history = history;
        this.dateandtime = dateandtime;
    }
}

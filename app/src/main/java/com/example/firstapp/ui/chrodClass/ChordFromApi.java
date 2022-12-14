package com.example.firstapp.ui.chrodClass;

public class ChordFromApi {

    private String name;
    private String strings;
    private String fingers;

    public ChordFromApi(String dataFromApi) {
        String[] rawData = dataFromApi.substring(2,dataFromApi.length()-2).replace("\"","").split("[:,]");
        setName(rawData[5]);
        setStrings(rawData[1].replaceAll(" ",""));
        setFingers(rawData[3].replaceAll(" ",""));

    }

    public String getName() {
        return name;
    }

    public String getStrings() {
        return strings;
    }

    public String getFingers() {
        return fingers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStrings(String strings) {
        this.strings = strings;
    }

    public void setFingers(String fingers) {
        this.fingers = fingers;
    }

    @Override
    public String toString() {
        return "ChordFromApi{" +
                "name='" + name + '\'' +
                ", strings='" + strings + '\'' +
                ", fingers='" + fingers + '\'' +
                '}';
    }
}

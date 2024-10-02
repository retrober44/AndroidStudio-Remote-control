package com.example.roberko44.fernbedienung;

public class TvChannel {

    int frequenz;
    String channel;
    int quality;
    String program;
    String provider;


    TvChannel(int f, String ch, int q, String pro, String prov){
        frequenz = f;
        channel = ch;
        quality = q;
        program = pro;
        provider = prov;
    }

    public String getChannel(){
        return channel;
    }

    public int getFrequenz() {
        return frequenz;
    }

    public void setFrequenz(int frequenz) {
        this.frequenz = frequenz;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}

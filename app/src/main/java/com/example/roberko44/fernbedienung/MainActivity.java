package com.example.roberko44.fernbedienung;


import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.channels.Channel;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{

    HttpRequest hr = new HttpRequest("172.16.200.69","8080", 1000, true);

    ArrayList<TvChannel> kanaele;
    ArrayList<String> spinnerProgram;
    Spinner spinner;
    SeekBar volBar;
    int oldvolume;
    public void doSomething() throws IOException, JSONException {
        hr.sendHttp("channelMain=8b");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            doSomething();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            hr.execute("debug=1");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        kanaele = new ArrayList<TvChannel>();
        spinnerProgram = new ArrayList<>();


        spinner = (Spinner) findViewById(R.id.spinnerliste);
        ArrayAdapter<String> chan = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerProgram);
        spinner.setAdapter(chan);

        ImageButton on4zu3 = (ImageButton) findViewById(R.id.zoom4zu3);
        on4zu3.setOnClickListener(this);
        ImageButton on16zu9 = (ImageButton) findViewById(R.id.zoom16zu9);
        on16zu9.setOnClickListener(this);
        ImageButton on3zu1 = (ImageButton) findViewById(R.id.zoom3zu1);
        on3zu1.setOnClickListener(this);
        ImageButton onOff = (ImageButton) findViewById(R.id.power);
        onOff.setOnClickListener(this);
        ImageButton pause = (ImageButton) findViewById(R.id.pause);
        pause.setOnClickListener(this);
        ImageButton pip = (ImageButton) findViewById(R.id.pip);
        pip.setOnClickListener(this);
        ImageButton up = (ImageButton) findViewById(R.id.up);
        up.setOnClickListener(this);
        ImageButton down = (ImageButton) findViewById(R.id.down);
        down.setOnClickListener(this);
        ImageButton volUp = (ImageButton) findViewById(R.id.volUp);
        volUp.setOnClickListener(this);
        ImageButton volDown = (ImageButton) findViewById(R.id.volDown);
        volDown.setOnClickListener(this);
        ImageButton mute = (ImageButton) findViewById(R.id.mute);
        mute.setOnClickListener(this);
        ImageButton scan = (ImageButton) findViewById(R.id.scan);
        scan.setOnClickListener(this);

        volBar = (SeekBar) findViewById(R.id.volSeekBar);
       // volBar.setProgress(defaultVolume);
        volBar.setProgress(defaultVolume);
        volBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                try {
                    hr.execute("volume="+ Integer.toString(progress));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                defaultVolume = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("Listener","nichts");
                System.out.println("sind drin");
            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.i("Listener","drin");

                try {
                    hr.execute("channelMain=" + kanaele.get(position).getChannel() );
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });



    }

    int defaultVolume = 20;
    public Channel[] channels;
    JSONObject json = new JSONObject();
    String jsonString = json.toString();
    boolean isOn = false;
    boolean isPause = false;
    boolean isPip = false;
    boolean isMute = false;
    /*
    if spinner.text == kanaele.get(i).program
        execute("chaannel="+kanaele.get(i).getchannel());
    */


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.power:

                if(!isOn){
                    try {
                        System.out.println("geht rein");
                        hr.execute("standby=1");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    isOn = true;
                }else{
                    try {
                        hr.execute("standby=0");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    isOn = false;
                }

                break;
            case R.id.pip:
                if(!isPip) {

                    try {
                        hr.execute("showPip=0");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    isPip = true;
                }else{
                    try {
                        hr.execute("showPip=1");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    isPip = false;
                }
                break;

            case R.id.pause:
                if(!isPause) {

                    try {
                        hr.execute("timeShiftPause=");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    isPause = true;
                }else{
                    try {
                        hr.execute("timeShiftPlay=17");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    isPause = false;
                }
                break;

            case R.id.scan:
                try {
                    json = hr.sendHttp("scanChannels=");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(json.has("channels"))
                {
                    JSONArray channels = null;
                    try {
                        channels = json.getJSONArray("channels");

                        for(int i = 0; i < channels.length(); i++){
                            String chan, progr, prov;
                            int freq, qual;

                            freq = (int) channels.getJSONObject(i).get("frequency");
                            chan = (String) channels.getJSONObject(i).get("channel");
                            qual = (int) channels.getJSONObject(i).get("quality");
                            progr = (String) channels.getJSONObject(i).get("program");
                            prov = (String) channels.getJSONObject(i).get("provider");

                            kanaele.add(new TvChannel(freq,chan,qual,progr,prov));


                        }


                        for(int i = 0; i < kanaele.size(); i++){
                            spinnerProgram.add(kanaele.get(i).getProgram());
                        }

                        String status = json.getString("status");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for(int i= 0 ; i < channels.length();i++){
                        try {
                            JSONObject channel = channels.getJSONObject(i);
                            Channel[] channels1;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }

                break;
            case R.id.zoom4zu3:

                try {
                    hr.execute("zoomMain=1");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.zoom16zu9:

                try {
                    hr.execute("zoomMain=0");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.zoom3zu1:

                try {
                    hr.execute("zoomMain=1");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.volUp:
                defaultVolume++;
                volBar.setProgress(defaultVolume);
                try {
                    hr.execute("volume="+ Integer.toString(defaultVolume));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.volDown:
                defaultVolume--;
                volBar.setProgress(defaultVolume);
                try {
                    hr.execute("volume=" +Integer.toString(defaultVolume));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.mute:

            if(!isMute) {

                oldvolume = defaultVolume;
                volBar.setProgress(0);
                try {
                    hr.execute("volume=0");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                isMute = true;
            }else{
                defaultVolume = oldvolume;
                volBar.setProgress(defaultVolume);
                try {
                    hr.execute("volume=" +Integer.toString(defaultVolume));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                isMute = false;
            }
            break;

            case R.id.spinnerliste:

             Log.i("dd","du spinner");
             System.out.println("dadsa");

            break;
        }
    }
}

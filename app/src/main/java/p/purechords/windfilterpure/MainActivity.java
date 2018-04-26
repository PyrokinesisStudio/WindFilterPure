package p.purechords.windfilterpure;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ToggleButton;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.Select;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitVoice;
import com.jsyn.ports.UnitInputPort;
import com.rey.material.widget.Slider;
import com.softsynth.shared.time.TimeStamp;
import com.jsyn.unitgen.PinkNoise;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.WhiteNoise;
import com.jsyn.unitgen.FilterBandPass;
import com.jsyn.unitgen.Circuit;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Object declarations

    private LineOut lineOut;
    private Try2 try2;

    // Primitives variable declarations

    float filterCutData1 = 670.0f;
    float filterCutData2 = 610.0f;
    float filterCutData3 = 540.0f;
    float filterResData1 = 6.0f;
    float filterResData2 = 8.0f;
    float filterResData3 = 7.0f;
    int filterModData = 0;

    // Start the synth engine
    Synthesizer synth = JSyn.createSynthesizer(new JSynAndroidAudioDevice());


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Create bundle

        super.onCreate(savedInstanceState);

        // Force English language, no localization

        Resources res = getApplicationContext().getResources();

        Locale locale = new Locale("en");
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        res.updateConfiguration(config, res.getDisplayMetrics());

        // Set view

        setContentView(R.layout.activity_main);

        // Get last user settings

        SharedPreferences sharedPref= getSharedPreferences("WindFilterPurePref", 0);
        if (sharedPref.contains("filterCutData1")) {

            filterCutData1 = sharedPref.getFloat("filterCutData1", filterCutData1);
            filterCutData2 = sharedPref.getFloat("filterCutData2", filterCutData2);
            filterCutData3 = sharedPref.getFloat("filterCutData3", filterCutData3);
            filterResData1 = sharedPref.getFloat("filterResData1", filterResData1);
            filterResData2 = sharedPref.getFloat("filterResData2", filterResData2);
            filterResData3 = sharedPref.getFloat("filterResData3", filterResData3);
            filterModData = sharedPref.getInt("filterModData", filterModData);

        }

        // Connect and initialize the audio

        synth.add(lineOut = new LineOut());
        synth.add(try2 = new Try2());
        try2.output.connect(0, lineOut.input, 0);
        try2.output.connect(0, lineOut.input, 1);

        // Define GUI Elements and listener methods

        final Slider sliderFilterCutGet1 = findViewById(R.id.sliderFilterCut1);
        sliderFilterCutGet1.setValue(filterCutData1, true);
        try2.mBandPass1.frequency.set(filterCutData1);
        sliderFilterCutGet1.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                try2.mBandPass1.frequency.set(sliderFilterCutGet1.getValue());
                filterCutData1 = (sliderFilterCutGet1.getValue());
            }
        });

        final Slider sliderFilterQGet1 = findViewById(R.id.sliderFilterQ1);
        sliderFilterQGet1.setValue(filterResData1, true);
        try2.mBandPass1.Q.set(filterResData1);
        sliderFilterQGet1.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                try2.mBandPass1.Q.set(sliderFilterQGet1.getValue());
                filterResData1 = (sliderFilterQGet1.getValue());
            }
        });

        final Slider sliderFilterCutGet2 = findViewById(R.id.sliderFilterCut2);
        sliderFilterCutGet2.setValue(filterCutData2, true);
        try2.mBandPass2.frequency.set(filterCutData2);
        sliderFilterCutGet2.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                try2.mBandPass2.frequency.set(sliderFilterCutGet2.getValue());
                filterCutData2 = (sliderFilterCutGet2.getValue());
            }
        });

        final Slider sliderFilterQGet2 = findViewById(R.id.sliderFilterQ2);
        sliderFilterQGet2.setValue(filterResData2, true);
        try2.mBandPass2.Q.set(filterResData2);
        sliderFilterQGet2.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                try2.mBandPass2.Q.set(sliderFilterQGet2.getValue());
                filterResData2 = (sliderFilterQGet2.getValue());
            }
        });

        final Slider sliderFilterCutGet3 = findViewById(R.id.sliderFilterCut3);
        sliderFilterCutGet3.setValue(filterCutData3, true);
        try2.mBandPass3.frequency.set(filterCutData3);
        sliderFilterCutGet3.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                try2.mBandPass3.frequency.set(sliderFilterCutGet3.getValue());
                filterCutData3 = (sliderFilterCutGet3.getValue());
            }
        });

        final Slider sliderFilterQGet3 = findViewById(R.id.sliderFilterQ3);
        sliderFilterQGet3.setValue(filterResData3, true);
        try2.mBandPass3.Q.set(filterResData3);
        sliderFilterQGet3.setOnPositionChangeListener(new Slider.OnPositionChangeListener() {
            @Override
            public void onPositionChanged(Slider view, boolean fromUser, float oldPos, float newPos, int oldValue, int newValue) {
                try2.mBandPass3.Q.set(sliderFilterQGet3.getValue());
                filterResData3 = (sliderFilterQGet3.getValue());
            }
        });

        final ToggleButton toggleButtonModOnGet = findViewById(R.id.toggleButtonModOn);

        toggleButtonModOnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ToggleButton) v).isChecked()) {
                   try2.mSelectMod.select.set(1.0);
                   filterModData = 1;
                   toggleButtonModOnGet.setBackgroundColor(Color.parseColor("#FF3F51B5"));
                }
                else {
                    try2.mSelectMod.select.set(0.0);
                    filterModData = 0;
                    toggleButtonModOnGet.setBackgroundColor(Color.parseColor("#9d9494"));
                }
            }
        });

        if (filterModData == 1) {
            toggleButtonModOnGet.setChecked(true);
            try2.mSelectMod.select.set(1.0);
            toggleButtonModOnGet.setBackgroundColor(Color.parseColor("#FF3F51B5"));
        }
        if (filterModData == 0) {
            toggleButtonModOnGet.setChecked(false);
            try2.mSelectMod.select.set(0.0);
            toggleButtonModOnGet.setBackgroundColor(Color.parseColor("#9d9494"));
        }

        final ToggleButton toggleButtonSynthOnGet = findViewById(R.id.toggleButtonSynthOn);
        toggleButtonSynthOnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ToggleButton) v).isChecked()) {
                    synth.start();
                    lineOut.start();

                    // Mute phone interruptions
                    MuteAudio();

                    toggleButtonSynthOnGet.setBackgroundColor(Color.parseColor("#FF3F51B5"));
                }
                else {
                    lineOut.stop();
                    synth.stop();

                    // Unmute phone interruptions
                    UnMuteAudio();

                    toggleButtonSynthOnGet.setBackgroundColor(Color.parseColor("#9d9494"));
                }
            }
        });


    }

    // Save last user settings on exit

    @Override
    protected void onPause() {
        super.onPause(); // Always call the superclass method first

        SharedPreferences sharedPref= getSharedPreferences("WindFilterPurePref", 0);
        SharedPreferences.Editor editor= sharedPref.edit();

        editor.putFloat("filterCutData1", filterCutData1);
        editor.putFloat("filterCutData2", filterCutData2);
        editor.putFloat("filterCutData3", filterCutData3);
        editor.putFloat("filterResData1", filterResData1);
        editor.putFloat("filterResData2", filterResData2);
        editor.putFloat("filterResData3", filterResData3);
        editor.putInt("filterModData", filterModData);
        editor.apply();
    }


    // Stop the synth engine and unmute phone audio on exit

    @Override
    protected void onDestroy() {
        super.onDestroy(); // Always call the superclass method first
        lineOut.stop();
        synth.stop();
        UnMuteAudio();
    }

    // Define mute and unmute phone audio interruptions methods

    public void MuteAudio(){
        AudioManager mAlramMAnager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0);
        } else {
            mAlramMAnager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        }
    }

    public void UnMuteAudio(){
        AudioManager mAlramMAnager=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0);
        } else {
            mAlramMAnager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
        }
    }

    // Define audio synthesis class, exported from Syntona

    public class Try2 extends Circuit implements UnitVoice {
        // Declare units and ports.
        WhiteNoise mWhiteNoise;
        PinkNoise mRedNoise2;
        PinkNoise mPinkNoise;
        FilterBandPass mBandPass1;
        PassThrough mFrequencyPassThrough;
        public UnitInputPort frequency;
        PassThrough mAmplitudePassThrough;
        public UnitInputPort amplitude;
        PassThrough mOutputPassThrough;
        public UnitOutputPort output;
        FilterBandPass mBandPass2;
        FilterBandPass mBandPass3;
        Select mSelectNoiseType;
        Select mSelectOutput;
        Select mSelectMod;

        // Declare inner classes for any child circuits.

        public Try2() {
            // Create unit generators.
            add(mWhiteNoise = new WhiteNoise());
            add(mRedNoise2 = new PinkNoise());
            add(mPinkNoise = new PinkNoise());
            add(mBandPass1 = new FilterBandPass());
            add(mFrequencyPassThrough = new PassThrough());
            addPort(frequency = mFrequencyPassThrough.input, "frequency");
            add(mAmplitudePassThrough = new PassThrough());
            addPort(amplitude = mAmplitudePassThrough.input, "amplitude");
            add(mOutputPassThrough = new PassThrough());
            addPort( output = mOutputPassThrough.output, "output");
            add(mBandPass2 = new FilterBandPass());
            add(mBandPass3 = new FilterBandPass());
            add(mSelectNoiseType = new Select());
            add(mSelectOutput = new Select());
            add(mSelectMod = new Select());
            // Connect units and ports.
            mWhiteNoise.output.connect(mSelectNoiseType.inputA);
            mRedNoise2.output.connect(mSelectNoiseType.inputB);
            mPinkNoise.output.connect(mSelectMod.inputB);
            mBandPass1.output.connect(mBandPass2.input);
            mBandPass2.output.connect(mBandPass3.input);
            mBandPass3.output.connect(mSelectOutput.inputB);
            mSelectNoiseType.output.connect(mBandPass1.input);
            mSelectOutput.output.connect(mOutputPassThrough.input);
            mSelectMod.output.connect(mSelectNoiseType.select);
            // Setup
            mWhiteNoise.amplitude.set(1.0);
            mRedNoise2.amplitude.set(0.1);
            mPinkNoise.amplitude.set(0.7);
            mBandPass1.frequency.set(670.8463318561876);
            mBandPass1.amplitude.set(1.0);
            mBandPass1.Q.set(1.5498856900000002);
            frequency.setup(0.0, 0.0, 1.0);
            amplitude.setup(0.0, 0.0, 1.0);
            mBandPass2.frequency.set(610.9582822955276);
            mBandPass2.amplitude.set(1.0);
            mBandPass2.Q.set(1.36865035);
            mBandPass3.frequency.set(400.0);
            mBandPass3.amplitude.set(1.0);
            mBandPass3.Q.set(1.0);
            mSelectOutput.inputA.set(0.0);
            mSelectOutput.select.set(1.0);
            mSelectMod.inputA.set(0.0);
            mSelectMod.select.set(0.0);
        }

        public void noteOn(double frequency, double amplitude, TimeStamp timeStamp) {
            this.frequency.set(frequency, timeStamp);
            this.amplitude.set(amplitude, timeStamp);
        }

        public void noteOff(TimeStamp timeStamp) {
        }

        public UnitOutputPort getOutput() {
            return output;
        }
    }

}

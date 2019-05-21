package com.main;

import java.util.Locale;
import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class TextSpeaker {
    private Synthesizer synthesizer;

    public TextSpeaker() throws Exception{
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");

        synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
        synthesizer.allocate();
        synthesizer.resume();
    }

    public void speak(String text){
        synthesizer.speakPlainText(text, null);
        System.out.println(text);
    }
}

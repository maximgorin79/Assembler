package ru.assembler.zxspectrum.io.loader;

import ru.assembler.io.wav.WavInputStream;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Test {
    public static void main(String args[]) throws IOException, UnsupportedAudioFileException {
        Converter conv = new Converter(new WavInputStream(args[0])
                , new FileOutputStream(args[1]));
        conv.setSaveInTzx(true);
        conv.execute();
    }
}

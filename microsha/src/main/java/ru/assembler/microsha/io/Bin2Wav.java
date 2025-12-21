package ru.assembler.microsha.io;

import ru.assembler.core.util.IOUtils;
import ru.assembler.microsha.io.generator.SoundGenerator;
import ru.assembler.microsha.io.rkm.RkmData;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Bin2Wav {
    private Bin2Wav() {

    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Use Bin2Wav <input.bin> <output.wav>");
            return;
        }
        SoundGenerator generator = new SoundGenerator(new File(args[1]));
        try (InputStream is = new FileInputStream(args[0])){
            generator.generateWav(is.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Generation into WAVE format is OK");
    }
}

package ru.assembler.microsha.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ru.assembler.microsha.io.generator.SoundGenerator;
import ru.assembler.microsha.io.rkm.RkmData;

public class Rkm2Wav {
	private Rkm2Wav() {
		
	}

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Use Rkm2Wav <input.rkm> <output.wav>");
			return;
		}
		RkmData data = new RkmData();
		SoundGenerator generator = new SoundGenerator(new File(args[1]));
		try (InputStream is = new FileInputStream(args[0])){
			data.read(is);
			generator.generateWav(data);			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		System.out.println("Generation into WAVE format is OK");
	}

}

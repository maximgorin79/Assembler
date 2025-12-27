package ru.zxspectrum.io.trd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Scl2Trd {
    private byte [] emptyArray = new byte[1792];
    private InputStream iStream;
    private OutputStream oStream;
    private byte[] buff = new byte[256];
    private int freeTrack = 1;
    private int freeSec = 0;
    private int count;
    private boolean isFull = true;
    private int totalFreeSect = 2544;

    private void cleanBuffer() {
        Arrays.fill(buff, (byte)0);
    }

    private void validateScl() throws IOException {
        cleanBuffer();
        iStream.read(buff, 0, 8);
        if (!(new String(buff, 0, 8)).equals("SINCLAIR")) {
            System.err.println("Wrong file! Select only SCL files");
            throw new IOException("Wrong file! Select only SCL files");
        }
        writeCatalog();
    }

    private void writeCatalog() throws IOException {
        int i;
        totalFreeSect = 2544;
        freeTrack = 1;
        freeSec = 0;
        count = 0;
        count = iStream.read();
        if (count < 0) {
            System.err.println("Bad count size!");
            throw new IOException();
        }
        for (i = 0; i < count; i++) {
            iStream.read(buff, 0, 14);
            buff[14] = (byte) freeSec;
            buff[15] = (byte) freeTrack;
            freeSec += buff[0xd] & 0xFF;
            freeTrack += freeSec / 16;
            totalFreeSect -= buff[0xd] & 0xFF;
            freeSec = freeSec % 16;
            oStream.write(buff, 0, 16);
        }
        cleanBuffer();
        for (i = count; i < 128;i++) {
            oStream.write(buff, 0, 16);
        }
        System.out.println(" * Disk catalog written");
        writeDiskInfo();
    }

    private void writeDiskInfo() throws IOException {
        cleanBuffer();
        buff[0xe3] = 0x16; // IMPORTANT! 80 track double sided
        buff[0xe4] = (byte) count;
        buff[0xe1] = (byte) freeSec;
        buff[0xe2] = (byte) freeTrack;

        if (isFull) {
            buff[0xe6] = (byte) (totalFreeSect / 256);
            buff[0xe5] = (byte) (totalFreeSect & 255);
        }

        buff[0xe7] = 0x10;
        buff[0xf5] = 's';
        buff[0xf6] = 'c';
        buff[0xf7] = 'l';
        buff[0xf8] = '2';
        buff[0xf9] = 't';
        buff[0xfa] = 'r';
        buff[0xfb] = 'd';
        oStream.write(buff, 0, 256);
        oStream.write(emptyArray); // Any dirt is ok
        System.out.println(" * Disk information written");
        writeDiskData();
    }

    private void writeDiskData() throws IOException {
        int r = iStream.read(buff, 0, 256);
        if (r == -1) {
            System.err.println("Stream is empty!");
            throw new IOException("Stream is empty!");
        }
        while (r == 256) {
            oStream.write(buff, 0, r);
            r = iStream.read(buff, 0, 256);
        }

        if (isFull) {
            cleanBuffer();
            for (r = 0; r < totalFreeSect; r++) {
                oStream.write(buff, 0, 256);
            }
        }
    }
    private void run(String inputFileName, String outputFileName) throws IOException {
        try {
            iStream = new FileInputStream(inputFileName);
            oStream = new FileOutputStream(outputFileName);
            validateScl();
            System.out.printf(" TRD file %s is stored and ready to use!\n", outputFileName);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        finally {
            if (iStream != null) {
                try {
                    iStream.close();
                    iStream = null;
                } catch (Exception e1) {
                    e1.fillInStackTrace();
                }
            }
            if (oStream != null) {
                try {
                    oStream.close();
                    oStream = null;
                } catch (Exception e2) {
                    e2.fillInStackTrace();
                }
            }
        }
    }
    private static String createFileName(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("null or empty");
        }
        int index = fileName.lastIndexOf('.');
        if (index != -1)    {
            fileName = fileName.substring(0, index);
        }
        return fileName + ".trd";
    }

    private static void convertSingle(String []args) throws IOException {
        String outputFileName = null;
        if (args.length >= 2) {
            outputFileName = args[1];
        }
        if (outputFileName == null) {
            outputFileName = createFileName(args[0]);
        }
        File input = new File(args[0]);
        if (!input.exists()) {
            throw new IOException("Input file does not exist!");
        }
        File output = new File(outputFileName);
        if (output.exists()) {
            throw new IOException(outputFileName + " file is already exists!");
        }
        new Scl2Trd().run(args[0], outputFileName);
    }

    private static void convertBatch(String []args) throws IOException {
        String pattern = "glob:*.scl";
        Path dir = Paths.get("").toAbsolutePath();
        if (args.length >= 2) {
            pattern = "glob:" + args[1];
        }
        SearchingFileByWildcard searcher = new SearchingFileByWildcard();
        List<String> fileNames = searcher.search(dir, pattern);
        int count = 0;
        for (String fileName : fileNames) {
           String outputFileName = createFileName(fileName);
           try {
               convertSingle(Arrays.asList(fileName, outputFileName).toArray(new String[2]));
               count++;
           } catch (IOException e) {
               System.err.println(e.getMessage());
           }
        }

        if (count == 1) {
            System.out.printf("%d file is converted successfully\n", count);
        } else {
            System.out.printf("%d files are converted successfully\n", count);
        }
    }

    public static void main(String []args) throws IOException {
        if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println("Scl2Trd <scl file> [<trd file>] - to convert a scl file into a trd file");
            System.out.println("Scl2Trd -d [<file pattern>] - to convert all scl files in the current directory");
            return;
        }
        if (args[0].equalsIgnoreCase("-d")) {
            convertBatch(args);
        } else {
            convertSingle(args);
        }
    }

    static class SearchingFileByWildcard {

        private List<String> matchesList = new LinkedList<>();

        private List<String> search(Path rootDir, String pattern) throws IOException {
            matchesList.clear();
            final FileVisitor<Path> matcherVisitor = new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attr)  {
                    FileSystem fs = FileSystems.getDefault();
                    PathMatcher matcher = fs.getPathMatcher(pattern);
                    Path name = file.getFileName();
                    if (matcher.matches(name)) {
                        matchesList.add(name.toString());
                    }
                    return FileVisitResult.CONTINUE;
                }
            };
            Files.walkFileTree(rootDir, matcherVisitor);
            return matchesList;
        }
    }
}

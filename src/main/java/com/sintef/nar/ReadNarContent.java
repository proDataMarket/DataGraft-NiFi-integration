package com.sintef.nar;

import java.io.*;
import java.nio.file.*;

/**
 * Created by shahzadk on 11/02/16.
 */
public class ReadNarContent {

    public void mainJar(String jarPath, String narPath, String fileName) throws IOException {

        cleanJarFileFromNar("Auto_Generated_Nar.jar", narPath);

        Path myFilePath = Paths.get(jarPath);
        Path zipFilePath = Paths.get(narPath);
        try( FileSystem fs = FileSystems.newFileSystem(zipFilePath, null) ){
            Path fileInsideZipPath = fs.getPath(fileName);
            Files.copy(myFilePath, fileInsideZipPath);
        } catch (IOException e) {
            e.printStackTrace(); //prøv å kjøre rest nå.
        }
    }

    public void cleanJarFileFromNar(String nameOfJarFile, String narPath){

        Path zipFilePath = Paths.get(narPath);
        try( FileSystem fs = FileSystems.newFileSystem(zipFilePath, null) ){
            Path fileInsideZipPath = fs.getPath(nameOfJarFile);
            Files.deleteIfExists(fileInsideZipPath);
        } catch (IOException e) {
            e.printStackTrace(); //prøv å kjøre rest nå.
        }
    }
}

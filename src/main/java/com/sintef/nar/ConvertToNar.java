package com.sintef.nar;

import java.io.*;
import java.nio.charset.Charset;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by shahzadk on 10/02/16.
 */
public class ConvertToNar {

    public static void converFolderToNar(String narFolderName, String metaInfFolder) throws Exception {
        ConvertToNar convertToNar = new ConvertToNar();

       // FileOutputStream fos = new FileOutputStream("/Users/shahzadk/Documents/diverse_sw_filer/Auto_Generated_Nar.nar");
        FileOutputStream fos = new FileOutputStream(narFolderName);
        ZipOutputStream zos = new ZipOutputStream(fos, Charset.forName("US-ASCII"));
       // testNewZip.addDirToZipArchive(zos, new File("/Users/shahzadk/Documents/diverse_sw_filer/META-INF/"), null);
        convertToNar.addDirToNarArchive(zos, new File(metaInfFolder), null);
        zos.flush();
        fos.flush();
        zos.close();
        fos.close();
    }

    public static void addDirToNarArchive(ZipOutputStream zos, File fileToZip, String parrentDirectoryName) throws Exception {
        if (fileToZip == null || !fileToZip.exists()) {
            return;
        }

        String zipEntryName = fileToZip.getName();
        if (parrentDirectoryName!=null && !parrentDirectoryName.isEmpty()) {
            zipEntryName = parrentDirectoryName + "/" + fileToZip.getName();
        }

        if (fileToZip.isDirectory()) {
            for (File file : fileToZip.listFiles()) {
                addDirToNarArchive(zos, file, zipEntryName);
            }
        } else {
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(fileToZip);
            zos.putNextEntry(new ZipEntry(zipEntryName));
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
            fis.close();
        }
    }
    }


package com.github.eighty88.api.downloader;

import javax.xml.ws.WebServiceException;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Downloader {
    java.net.URL URL;

    public Downloader(String URL) throws MalformedURLException {
        this.URL = new URL(URL);
    }

    public void DownloadTo(String Path) throws IOException {
        System.out.println("Download from " + URL.toString());
        System.out.print("Connection opening...");
        HttpURLConnection Connection =
                (HttpURLConnection) URL.openConnection();
        Connection.setAllowUserInteraction(false);
        Connection.setInstanceFollowRedirects(true);
        Connection.setRequestMethod("GET");
        Connection.connect();
        System.out.println(" Success!");


        int ResponseCode = Connection.getResponseCode();

        System.out.print("Checking connection...");
        if(ResponseCode != HttpURLConnection.HTTP_OK){
            throw new WebServiceException("HTTP Connection error");
        }
        System.out.println(" Success!");

        DataInputStream InputStream = new DataInputStream(
                Connection.getInputStream());

        String FileName = Connection.getHeaderField("Content-Disposition");

        if(FileName == null || !FileName.contains("filename=\"")) {
            FileName = Paths.get(URL.getPath()).getFileName().toString();
        } else {
            FileName = FileName.substring(FileName.indexOf("filename=\"") + 10, FileName.length() - 1);
        }

        System.out.print("Downloading " + FileName + "...");

        File Download = new File(Path, FileName);

        if(Download.exists()) {
            System.out.println();
            System.out.println(FileName + " already exists");
            InputStream.close();
        } else {
            ReadableByteChannel ByteChannel = Channels.newChannel(InputStream);

            FileOutputStream OutputStream = new FileOutputStream(Download);

            try {
                OutputStream.getChannel().transferFrom(ByteChannel, 0, Long.MAX_VALUE);
            } finally {
                System.out.println("Success!");
                InputStream.close();
                OutputStream.close();
                ByteChannel.close();
            }
        }
    }

    public void DownloadTo(Path path) throws IOException {
        DownloadTo(path.toString());
    }
}

class OSProperty {

    private static final int osType;

    public static OSType getType() {
        switch (osType) {
            case 1:
                return OSType.WINDOWS;
            case 2:
                return OSType.MAC;
            case 3:
                return OSType.LINUX;
            case 4:
                return OSType.OTHERS;
        }
        return OSType.OTHERS;
    }

    static {
        String OS = System.getProperty("os.name");
        if (OS.startsWith("Win")) {
            osType = 1;
        } else if (OS.contains("OS X")) {
            osType = 2;
        } else if(OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0 ){
            osType = 3;
        } else {
            osType = 4;
        }
    }
}

enum OSType {
    WINDOWS,MAC,LINUX,OTHERS
}


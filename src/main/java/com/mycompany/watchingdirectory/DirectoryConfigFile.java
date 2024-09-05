package com.mycompany.watchingdirectory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DirectoryConfigFile {

    private final String SEPARADOR = File.separator;
    private final String CONFIG_FILE = System.getProperty("user.dir") + SEPARADOR + "config.conf";
    private FileWriter escribe;
    private List<File> directorysList = new ArrayList<>();
    private WatchingDirectory watchingDirectory = new WatchingDirectory();

    public DirectoryConfigFile() {
        if (checkDirectoryConfigFile()) {
            readDirectoryConfigFile();
        } else {
            System.out.println(MsgList.errorExistConfigFile);
        }
    }

    public boolean checkDirectoryConfigFile() {
        File configFile = new File(CONFIG_FILE);
        if (!configFile.exists()) {
            createDirectoryConfigFile(configFile);
            if (!configFile.exists())
                return false;
        }
        return true;
    }

    public void createDirectoryConfigFile(File configFile) {

        try {
            escribe = new FileWriter(configFile, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean addDirectoryConfigFile(String path, boolean delDir) {

        File dir = new File(path);

        if (!dir.exists()) {
            return false;
        }

        if (!checkExistDirOnConfigFile(dir) && !delDir) {
            return false;
        }

        try {
            escribe = new FileWriter(CONFIG_FILE, true);
            BufferedWriter guarda = new BufferedWriter(escribe);
            guarda.append(path);
            guarda.newLine();
            guarda.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        watchingDirectory.setInitialPath(path);
        return true;
    }

    public boolean checkExistDirOnConfigFile(File path) {
        for (File f : directorysList) {
            if (f.getName().equals(path.getName())) {
                return false;
            }
        }
        return true;
    }

    public void deleteDirectoryConfigFile() {
        File delFile = new File(CONFIG_FILE);

        if (delFile.exists()) {
            delFile.delete();
            if (!delFile.exists()) {
            }
        }
        createDirectoryConfigFile(new File(CONFIG_FILE));
    }

    public void resetConfiguFile() {
        deleteDirectoryConfigFile();
        checkDirectoryConfigFile();
    }

    public boolean writeDirectoryConfigFile() {
        resetConfiguFile();
        for (int i = 0; i < directorysList.size(); i++) {
            addDirectoryConfigFile(directorysList.get(i).getAbsolutePath(), true);
        }

        return true;
    }

    public void readDirectoryConfigFile() {
        checkDirectoryConfigFile();
        Scanner scan = null;
        try {
            scan = new Scanner(new File(CONFIG_FILE));
            while (scan.hasNextLine()) {
                directorysList.add(new File(scan.nextLine()));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        scan.close();
    }

    public boolean delDirectoryListFilePath(String path) {
        File file = new File(path);

        if (!file.exists()) {
            return false;
        }

        if (checkExistDirOnConfigFile(file)) {
            return false;
        }

        for (int i = 0; i < directorysList.size(); i++) {
            if (directorysList.get(i).getAbsolutePath().equals(file.getAbsolutePath())) {
                directorysList.remove(i);
            }
        }

        writeDirectoryConfigFile();
        return true;

    }

    public List<File> getDirectorysList() {
        directorysList = new ArrayList<>();
        readDirectoryConfigFile();
        return directorysList;
    }

    public void startWatchingDirectorysList() {
        
        for (File f : directorysList) {
            watchingDirectory.setInitialPath(f.getAbsolutePath());
        }
    }

    public void stotWatchingDirectorysList() {
        for (File f : directorysList) {
            WatchingDirectory.stopThreads(f.getAbsolutePath());
        }
    }

}

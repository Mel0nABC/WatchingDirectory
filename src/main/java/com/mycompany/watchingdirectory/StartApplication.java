package com.mycompany.watchingdirectory;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class StartApplication {

    private Scanner scan;
    private boolean iniciado = true;
    private int optionMainMenu;
    public static final int MIN_MENU_OPTION = 1;
    public static final int MAX_MENU_OPTION = 5;
    private DirectoryConfigFile directoryConfigFile;
    private List<File> directoryList;

    public static void main(String[] args) {
        StartApplication startApplication = new StartApplication();
        startApplication.startMainMenu();
    }

    public void startMainMenu() {
        directoryConfigFile = new DirectoryConfigFile();
        directoryList = directoryConfigFile.getDirectorysList();
        directoryConfigFile.startWatchingDirectorysList();

        scan = new Scanner(System.in);

        System.out.println(MsgList.welcomeMsg);

        while (iniciado) {
            System.out.println(MsgList.mainMenu);

            if (checkIntValue(MsgList.errorIntValue))
                menuOptionSelected(optionMainMenu);

            scan.nextLine();
        }
    }

    public boolean checkIntValue(String errorMsg) {
        if (!scan.hasNextInt()) {
            System.out.println(errorMsg);
            return false;
        }
        optionMainMenu = scan.nextInt();
        if (optionMainMenu < MIN_MENU_OPTION | optionMainMenu > MAX_MENU_OPTION) {
            System.out.println(errorMsg);
            return false;
        }
        return true;
    }

    public void menuOptionSelected(int optionMainMenu) {
        switch (optionMainMenu) {
            case 1:
                // Lista directorios añadidos
                listDirectoryFile();
                break;
            case 2:
                // añadir carpeta al control
                addLocalDirectory();
                break;
            case 3:
                // eliminar carpeta controlada
                listDirectoryFile();
                delLocalDirectory();
                break;
            case 4:
                // Visualizar log, últimas lineas y opciones de visualización
                System.out.println("NUMERO --> " + optionMainMenu);
                break;
            case 5:
                logout();
                break;

            default:
                break;
        }
    }

    public void listDirectoryFile() {

        if (directoryList.size() < 1) {
            System.out.println(MsgList.emptyConfigDirFile);
        } else {
            System.out.println("");
            for (File f : directoryList) {
                System.out.println("Carpeta -> " + f.getAbsolutePath());
            }
            System.out.println("");
        }

    }

    public void addLocalDirectory() {
        System.out.println(MsgList.requestNewPath);
        Scanner scan = new Scanner(System.in);
        File ruta = new File(scan.next());
        scan.close();
        if (!directoryConfigFile.addDirectoryConfigFile(ruta.getAbsolutePath(), false)) {
            System.out.println(MsgList.errorNewPath);
            return;
        }
        updateDirectoryListArray();
    }

    public void delLocalDirectory() {
        System.out.println(MsgList.requestDewPath);
        Scanner scan = new Scanner(System.in);
        File ruta = new File(scan.next());
        scan.close();
        if (!directoryConfigFile.delDirectoryListFilePath(ruta.getAbsolutePath())) {
            System.out.println(MsgList.errorDelPath);
            return;
        }
        updateDirectoryListArray();
    }

    public void updateDirectoryListArray() {
        directoryList = directoryConfigFile.getDirectorysList();
    }

    public void logout() {
        boolean responseOk = true;
        while (responseOk) {
            System.out.println(MsgList.logoutMsg);
            String response = scan.next();

            if (response.toLowerCase().equals("si") | response.toLowerCase().equals("s")) {
                System.out.println(MsgList.exitMsg);
                iniciado = false;
                responseOk = false;
                directoryConfigFile.stotWatchingDirectorysList();
                break;
            }

            if (response.toLowerCase().equals("no") | response.toLowerCase().equals("n")) {
                System.out.println(MsgList.returnMainMenu);
                responseOk = false;
                break;
            }

            if (!response.toLowerCase().equals("no") | !response.toLowerCase().equals("n")) {
                System.out.println(MsgList.errorLogoutOption);
            }
        }
    }

}

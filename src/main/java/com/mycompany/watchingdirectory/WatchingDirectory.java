package com.mycompany.watchingdirectory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Genera threads del tipo WatchingDirectoryThread para controlar la actividad
 * de una ruta local.
 */
public class WatchingDirectory {

    // Variable para el control de bucles de carpetas.
    private static Map<String, Thread> listThreads = new HashMap<String, Thread>();
    private static Map<String, WatchingDirectoryThread> listWatchingObjects = new HashMap<String, WatchingDirectoryThread>();

    public WatchingDirectory() {

    }

    public void setInitialPath(String initialPath) {

        File dir = new File(initialPath);
        if (!dir.exists()) {
            System.out.println(
                    "\n##################################################################################################");
            System.out.println("## EL DIRECTORIO NO EXISTE --> " + initialPath);
            System.out.println(
                    "##################################################################################################1");
            return;
        }
        System.out.println(
                "\n##################################################################################################");
        System.out.println("## INICIAMOS WATCHDIRECTORY EN --> " + initialPath);
        System.out.println(
                "##################################################################################################");
        listPath(new File(initialPath));
    }

    public static void listPath(File path) {
        File[] dirList = path.listFiles();

        for (File f : dirList) {
            if (f.isDirectory()) {
                listPath((f.getAbsoluteFile()));
            }
        }
        newthread(path.getAbsolutePath());
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void newthread(String path) {
        WatchingDirectoryThread wtr = new WatchingDirectoryThread();
        wtr.setPath(path);
        Thread tr = new Thread(wtr);
        tr.setName(path);
        tr.start();
        listThreads.put(path, tr);
        listWatchingObjects.put(path, wtr);
    }

    public static Map<String, Thread> getThreads() {
        return listThreads;
    }

    public static void stopThreads(String path) {

        File dir = new File(path);

        if(!dir.exists()){
            System.out.println("El directorio no existe --> "+dir.getAbsolutePath());
            return;
        }

        File[] listSubdir = dir.listFiles();

        for (File f : listSubdir) {
            if (!delThead(f.getAbsolutePath())) {
                System.out.println("Stop control directorio --> " + f.getAbsolutePath());
            } else {
                System.out.println("Error al detener el thread, quizá era un archivo --> " + f.getAbsolutePath());
            }
        }

        if (!delThead(path)) {
            System.out.println("Stop control directorio --> " + path);
        } else {
            System.out.println("Error al detener el thread, quizá era un archivo --> " + path);
        }
    }

    public static boolean delThead(String path) {
        WatchingDirectoryThread wtStop = listWatchingObjects.get(path);
        if (wtStop != null) {
            wtStop.setStop(false);
            Thread trStop = listThreads.get(path);
            trStop.interrupt();
            while (trStop.isAlive()) {
            }
            deleteObjectList(path);
            return trStop.isAlive();
        }
        return true;
    }

    public static void deleteObjectList(String path) {
        listWatchingObjects.remove(path);
        listThreads.remove(path);
    }

}

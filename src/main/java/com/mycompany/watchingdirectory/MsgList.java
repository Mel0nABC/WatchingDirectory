package com.mycompany.watchingdirectory;

public class MsgList {

    public static final String welcomeMsg = "########################################\n" +
            "BIENVENIDO AL CONTROLADOR DE DIRECTORIOS\n" +
            "########################################\n";

    public static final String mainMenu = "################\n" +
            "MENÚ PRINCIPAL\n" +
            "################\n" +
            "1) Lista de directorios añadidos.\n" +
            "2) Añadir directorio.\n" +
            "3) Eliminar directorio.\n" +
            "4) Visualizar log de eventos.\n" +
            "5) SALIR\n" +
            "Elija la opción deseada ...";

    public static final String errorIntValue = "##### Las opciones disponibles son del "
            + StartApplication.MIN_MENU_OPTION + " al "
            + StartApplication.MAX_MENU_OPTION + ". #####";

    public static final String logoutMsg = "Saliendo de la aplicación. ¿Desea continuar? [Si/No]";
    public static final String returnMainMenu = "Volvemos al menú principal ...\n";
    public static final String exitMsg = "\n¡Hasta la próxima!\n";
    public static final String errorLogoutOption = "\n##### Las opciones correctas son [Si/No]. #####\n";
    public static final String errorExistConfigFile = "Ha habido algún tipo de error con el fichero de configuración de directorios.";
    public static final String requestNewPath = "Indica la ruta absoluta que se quiere añadir. ";
    public static final String requestDewPath = "Indica la ruta absoluta que se quiere eliminar. ";
    public static final String errorNewPath = "La ruta especificada no ha sido posible añadirla, puede que ya exista o no sea correcta.";
    public static final String errorDelPath = "La ruta especificada no ha sido posible eliminarla, puede que ya no exista o no sea correcta.";
    public static final String emptyConfigDirFile = "\nNo hay ningún directorio añadido.\n";

}

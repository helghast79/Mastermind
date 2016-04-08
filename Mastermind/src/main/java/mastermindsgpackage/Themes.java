package mastermindsgpackage;

import java.io.File;

/**
 * Created by macha on 14/02/2016.
 */
public enum Themes {

    CLASSIC("Classic", "Classic/"),
    AVENGERS("Avengers", "Avengers/"),
    FUTURAMA("Futurama", "Futurama/"),
    SUPERMARIO("Super Mario", "SuperMario/"),
    CUSTOM("Custom Theme", "Custom/");

    private String displayName;
    private String addToFileName;

    Themes(String displayName, String addToFileName){
        this.displayName = displayName;
        this.addToFileName = addToFileName;
    }

    public static boolean CustomDirectoryExists(){
        String path = Config.fileResourcesPath + Themes.values()[Themes.values().length-1].getAddToFileName();
        File f = new File(path);
        if (f.exists() && f.isDirectory()) {
           return true;
        }
        return false;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getAddToFileName() {
        return addToFileName;
    }
}

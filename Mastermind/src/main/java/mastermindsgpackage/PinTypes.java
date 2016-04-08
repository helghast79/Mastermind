package mastermindsgpackage;

/**
 * Created by codecadet on 09/02/16.
 */

/**
 * Created by macha on 08/02/2016.
 */
public enum PinTypes {

    NONE("None.png"),
    RIGHT("Right.png"),
    WRONG("Wrong.png"),
    SWITCHED("Switched.png");


    private String path;

    PinTypes(String path){

        this.path = path;
    }


    public String getPath() {
        //return path;

        return (Config.fileResourcesPath + Themes.values()[Config.currentThemeIndex].getAddToFileName() + Config.pinAddToPath + path);

        //example: "Master Mind Simple Graphics/Resources/cla_pin_Right.png
    }


}

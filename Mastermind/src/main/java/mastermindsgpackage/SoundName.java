package mastermindsgpackage;

/**
 * Created by codecadet on 23/02/16.
 */
public enum SoundName {


    MUSIC1("sample14"),
    MUSIC2("sample26");


    private String path;

    SoundName(String path){

        this.path = path;
    }


    public String getPath() {
        return path;

        //return (Config.fileResourcesPath + Themes.values()[Config.currentThemeIndex].getAddToFileName() + Config.pinAddToPath + path);

        //example: "Master Mind Simple Graphics/Resources/cla_pin_Right.png
    }



}

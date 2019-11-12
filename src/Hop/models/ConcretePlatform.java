package Hop.models;

public class ConcretePlatform implements Platform{
    private int platformType;

    public int getPlatformType() {
        return platformType;
    }

    public char getAsChar(){
        if(platformType == PLATFORM_NONE){
            return NONE_CHAR;
        }

        else{
            return SOLID_CHAR;
        }
    }

    public ConcretePlatform(int platformType) {
        this.platformType = platformType;
    }
}

package sample.model;

import java.io.File;

public class FileWord implements IResource {
    @Override
    public File getFile(String type) {
            if(type.equals("cn")){
                return new File("src/sample/data/cn/word.csv");
            }else{
                return new File("src/sample/data/us/word.csv");
            }
    }
}

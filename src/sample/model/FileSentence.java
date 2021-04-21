package sample.model;

import java.io.File;

public class FileSentence implements IResource {
    @Override
    public File getFile(String type) {
        if(type.equals("cn")){
            return new File("src/sample/data/cn/sentence.csv");
        }else{
            return new File("src/sample/data/us/sentence.csv");
        }
    }
}

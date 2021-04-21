package sample.view.paragraph;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class paragraph {
    private static volatile Pane pane = null;
    private paragraph(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController("sample.view.paragraph.paragraph.java");
        try{
            pane = (Pane) fxmlLoader.load(getClass().getResource("paragraph.fxml"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static Pane getPane(){
        if(pane == null){
            synchronized (paragraph.class){
                if(pane == null){
                    paragraph paragraph = new paragraph();
                    return pane;
                }
            }
        }
        return pane;
    }

}

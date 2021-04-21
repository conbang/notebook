package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import sample.model.FileSentence;
import sample.model.FileWord;
import sample.model.Listword;
import sample.view.paragraph.paragraph;
import sample.view.sentence.Sentence;
import sample.view.video.Video;
import sample.view.word.NewWord;

import java.util.Scanner;

public class Controller {
    public static Listword listword = new Listword();
    public static Listword listsentence = new Listword();
    Scanner scanner;
    String lang = "cn";
    @FXML
    public Pane content;
    public MenuButton language;
    public MenuItem cn;
    public MenuItem us;


    public void initialize() {
        readFileWord();
        readFileSentece();
        showTableWord();
    }

    public void chooseLanguage() {
        cn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lang = "cn";
            }
        });
        us.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lang = "us";
                System.out.println(lang);
            }
        });
    }

    public void showTableWord() {
        content.getChildren().clear();
        content.getChildren().add(NewWord.getContent());
    }

    public void showTableSentence() {
        content.getChildren().clear();
        content.getChildren().add(Sentence.getContent());
    }

    public void playVideo(){
        content.getChildren().clear();
        content.getChildren().add(Video.getVideo());
    }
    public void showParagraph(){
        content.getChildren().clear();
        content.getChildren().add(paragraph.getPane());
    }
    public void readFileWord() {
        listword.setFile(new FileWord().getFile(lang));
        listword.readFile();
    }

    public void readFileSentece() {
        listsentence.setFile(new FileSentence().getFile(lang));
        listsentence.readFile();
    }
}

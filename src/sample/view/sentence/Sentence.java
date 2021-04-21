package sample.view.sentence;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sample.Controller;
import sample.model.Word;
import sample.view.Alert.Alert;

public class Sentence {
    private TextField sentence;
    private TextField pronounce;
    private TextField meaning;
    private static volatile ListView tableView;
    private static volatile Pane content_sentence;
    public static int currentPage = 0;
    private Button delete;

    private Sentence() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController("sample.view.paragraph.Sentence");
        try {
            content_sentence = (Pane) fxmlLoader.load(getClass().getResource("sentence.fxml"));
            tableView = (ListView) content_sentence.lookup("#table");
            initial();
            tableView.setStyle("-fx-font-size: 15;-fx-font-weight: bold;-fx-text-alignment: center");
            tableView.setFixedCellSize(31);
            delete = (Button) content_sentence.lookup("#delete");
            delete.setOnAction(event -> delete());
            nextPage();
            previousPage();
            add();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Pane getContent() {
        if (content_sentence == null) {
            synchronized (sample.view.word.NewWord.class) {
                if (content_sentence == null) {
                    Sentence newSentence = new Sentence();
                    return content_sentence;
                }
            }
        }
        return content_sentence;
    }

    public void initial() {
        tableView.getItems().clear();
        if (Controller.listsentence.getSize() < 3) {
            for (int j = 0; j < Controller.listsentence.getSize(); j++) {
                tableView.getItems().add(Controller.listsentence.getWord(j).getWord());
                tableView.getItems().add(Controller.listsentence.getWord(j).getPronounce());
                tableView.getItems().add(Controller.listsentence.getWord(j).getMeaning());
            }
        } else {
            for (int j = 0; j < 3; j++) {
                tableView.getItems().add(Controller.listsentence.getWord(j).getWord());
                tableView.getItems().add(Controller.listsentence.getWord(j).getPronounce());
                tableView.getItems().add(Controller.listsentence.getWord(j).getMeaning());
            }
        }
    }

    public void delete() {
        ObservableList<String> sentenceSelected;
        sentenceSelected = tableView.getSelectionModel().getSelectedItems();
        Controller.listsentence.delete(new Word(sentenceSelected.get(0), "", ""));
        initial();
    }

    public void add() {
        sentence = (TextField) content_sentence.lookup("#sentence");
        pronounce = (TextField) content_sentence.lookup("#pronounce");
        meaning = (TextField) content_sentence.lookup("#meaning");
        meaning.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (!sentence.getText().equals("") && !pronounce.getText().equals("") && !meaning.getText().equals("")) {
                        Controller.listsentence.updateWord(new Word(sentence.getText(), pronounce.getText(), meaning.getText()));
                        sentence.setText("");
                        pronounce.setText("");
                        meaning.setText("");
                        initial();
                    }
                }
            }
        });
    }

    public static void nextPage() {
        FontAwesomeIconView nextPage = (FontAwesomeIconView) content_sentence.lookup("#nextPage");
        nextPage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int size = Controller.listsentence.getSize();
                int page = size / 3;
                if (currentPage == page) {
                    Alert.getAlert("it's over!");
                } else {
                    currentPage += 1;
                    tableView.getItems().clear();
                    if (currentPage == page) {
                        for (int j = 0; j < size % 3; j++) {
                            tableView.getItems().add(Controller.listsentence.getWord(currentPage * 3 + j).getWord());
                            tableView.getItems().add(Controller.listsentence.getWord(currentPage * 3 + j).getPronounce());
                            tableView.getItems().add(Controller.listsentence.getWord(currentPage * 3 + j).getMeaning());
                        }
                    } else {
                        for (int j = 0; j < 3; j++) {
                            tableView.getItems().add(Controller.listsentence.getWord(currentPage * 3 + j).getWord());
                            tableView.getItems().add(Controller.listsentence.getWord(currentPage * 3 + j).getPronounce());
                            tableView.getItems().add(Controller.listsentence.getWord(currentPage * 3 + j).getMeaning());
                        }
                    }
                }
            }
        });
    }

    public static void previousPage() {
        FontAwesomeIconView previousPage = (FontAwesomeIconView) content_sentence.lookup("#previousPage");
        previousPage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int size = Controller.listsentence.getSize();
                if (currentPage <= 0) {
                    Alert.getAlert("it's over!");
                } else {
                    currentPage--;
                    tableView.getItems().clear();
                    for (int j = 0; j < 3; j++) {
                        tableView.getItems().add(Controller.listsentence.getWord(currentPage * 3 + j).getWord());
                        tableView.getItems().add(Controller.listsentence.getWord(currentPage * 3 + j).getPronounce());
                        tableView.getItems().add(Controller.listsentence.getWord(currentPage * 3 + j).getMeaning());
                    }
                }
            }
        });
    }
}

package sample.view.word;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import sample.Controller;
import sample.model.Word;
import sample.view.Alert.Alert;
import sample.view.sentence.Pronounce;

public class NewWord {
    private TextField word;
    public TextField pronounce;
    private TextField meaning;
    private static volatile TableView tableView;
    private static volatile Pane content_word;
    public static int currentPage = 0;
    private Button delete;

    private NewWord() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController("sample.view.word.NewWord");

        try {
            content_word = (Pane) fxmlLoader.load(getClass().getResource("word.fxml"));
            tableView = (TableView) content_word.lookup("#table");
            delete = (Button) content_word.lookup("#delete");
            TableColumn<Word, String> column1 = new TableColumn<>("word");
            column1.setCellValueFactory(new PropertyValueFactory<>("word"));
            column1.setPrefWidth(141);
            TableColumn<Word, String> column2 = new TableColumn<>("pronounce");
            column2.setCellValueFactory(new PropertyValueFactory<>("pronounce"));
            column2.setPrefWidth(141);
            TableColumn<Word, String> column3 = new TableColumn<>("meaning");
            column3.setCellValueFactory(new PropertyValueFactory<>("meaning"));
            column3.setPrefWidth(143);
            tableView.getColumns().addAll(column1, column2, column3);
            tableView.setFixedCellSize(31);
            tableView.setStyle("-fx-font-size: 15;-fx-font-weight: bold;-fx-text-alignment: center");
            delete.setOnAction(event -> delete());
            pronounce = (TextField) content_word.lookup("#pronounce");
            Pronounce type = new Pronounce(pronounce);
            type.pinyin();
            initial();
            nextPage();
            previousPage();
            add();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Pane getContent() {
        if (content_word == null) {
            synchronized (NewWord.class) {
                if (content_word == null) {
                    NewWord newWord = new NewWord();
                    return content_word;
                }
            }
        }
        return content_word;
    }

    public void initial() {
        tableView.getItems().clear();
        if (Controller.listword.getSize() < 10) {
            for (int j = 0; j < Controller.listword.getSize(); j++) {
                tableView.getItems().add(Controller.listword.getWord(j));
            }
        } else {
            for (int j = 0; j < 10; j++) {
                tableView.getItems().add(Controller.listword.getWord(j));
            }
        }
    }

    public void delete() {
        ObservableList<Word> wordSelected, listword;
        listword = tableView.getItems();
        wordSelected = tableView.getSelectionModel().getSelectedItems();
        Controller.listword.delete(wordSelected.get(0));
        wordSelected.forEach(listword::remove);
    }

    public void add() {
        word = (TextField) content_word.lookup("#word");
        pronounce = (TextField) content_word.lookup("#pronounce");
        meaning = (TextField) content_word.lookup("#meaning");
        meaning.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    if (!word.getText().equals("") && !pronounce.getText().equals("") && !meaning.getText().equals("")) {
                        Controller.listword.updateWord(new Word(word.getText(), pronounce.getText(), meaning.getText()));
                        word.setText("");
                        pronounce.setText("");
                        meaning.setText("");
                        initial();
                    }
                }
            }
        });
    }

    public void nextPage() {
        FontAwesomeIconView nextPage = (FontAwesomeIconView) content_word.lookup("#nextPage");
        nextPage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int size = Controller.listword.getSize();
                int page = size / 10;
                if (currentPage == page) {
                    Alert.getAlert("it's over!");
                } else {
                    currentPage += 1;
                    tableView.getItems().clear();
                    if (currentPage == page) {
                        for (int j = 0; j < size % 10; j++) {
                            tableView.getItems().add(Controller.listword.getWord(currentPage * 10 + j));
                        }
                    } else {
                        for (int j = 0; j < 10; j++) {
                            tableView.getItems().add(Controller.listword.getWord(currentPage * 10 + j));
                        }
                    }
                }
            }
        });
    }

    public void previousPage() {
        FontAwesomeIconView previousPage = (FontAwesomeIconView) content_word.lookup("#previousPage");
        previousPage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int size = Controller.listword.getSize();
                if (currentPage <= 0) {
                    Alert.getAlert("it's over!");
                } else {
                    currentPage--;
                    tableView.getItems().clear();
                    for (int j = 0; j < 10; j++) {
                        tableView.getItems().add(Controller.listword.getWord(currentPage * 10 + j));
                    }
                }
            }
        });
    }
}

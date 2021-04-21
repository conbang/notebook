package sample.view.word;

import javafx.scene.text.Text;
import sample.model.Word;

public interface DisplayWord {
    Text displayWord(Word word);
    Text displayPronounce(Word word);
    Text displayMeaning(Word word);
}

package sample.view.sentence;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

public class Pronounce implements Typing {
    Map<Character, Character[]> list;
    public TextField textField;

    public Pronounce(TextField textField) {
        this.textField = textField;
        list = new HashMap<>();
        list.put('a', new Character[]{'ā', 'á', 'ǎ', 'à'});
        list.put('e', new Character[]{'ē', 'é', 'ě', 'è'});
        list.put('o', new Character[]{'ō', 'ó', 'ǒ', 'ò'});
        list.put('i', new Character[]{'ī', 'í', 'ǐ', 'ì'});
        list.put('u', new Character[]{'ū', 'ú', 'ǔ', 'ù'});
    }

    @Override
    public void pinyin() {
        textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String str = textField.getText();
                String pinyin;
                Character[] character;
                char vowel;
                if (!str.equals("")) {
                    int lengthStr = str.length();
                    switch (str.charAt(lengthStr - 1)) {
                        case '1':
                            vowel = str.charAt(lengthStr - 2);
                            character = list.get(vowel);
                            if (character != null) {
                                pinyin = str.substring(0, lengthStr - 2);
                                pinyin += character[0];
                                textField.setText(pinyin);
                                textField.positionCaret(lengthStr - 1);
                            }
                            break;
                        case '2':
                            vowel = str.charAt(lengthStr - 2);
                            character = list.get(vowel);
                            if (character != null) {
                                pinyin = str.substring(0, lengthStr - 2);
                                pinyin += character[1];
                                textField.setText(pinyin);
                                textField.positionCaret(lengthStr - 1);
                            }
                            break;
                        case '3':
                            vowel = str.charAt(lengthStr - 2);
                            character = list.get(vowel);
                            if (character != null) {
                                pinyin = str.substring(0, lengthStr - 2);
                                pinyin += character[2];
                                textField.setText(pinyin);
                                textField.positionCaret(lengthStr - 1);
                            }
                            break;
                        case '4':
                            vowel = str.charAt(lengthStr - 2);
                            character = list.get(vowel);
                            if (character != null) {
                                pinyin = str.substring(0, lengthStr - 2);
                                pinyin += character[3];
                                textField.setText(pinyin);
                                textField.positionCaret(lengthStr - 1);
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }
}

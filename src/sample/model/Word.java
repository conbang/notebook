package sample.model;

public class Word {
    private String word;
    private String pronounce;
    private String meaning;

    public Word(String word, String pronounce, String meaning) {
        this.word = word;
        this.pronounce = pronounce;
        this.meaning = meaning;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    @Override
    public String toString() {
        return word + "," +
                pronounce + "," +
                meaning;
    }
}

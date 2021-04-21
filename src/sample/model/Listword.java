package sample.model;

import sample.view.Alert.Alert;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Listword {
    private List<Word> list;
    private File file;

    public Listword() {
        list = new ArrayList<>();
    }

    public void updateWord(Word word) {
        if (isExist(word) != -1) {
            Alert.getAlert("this word is existed");
        } else {
            list.add(word);
            saveFile();
        }
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Word getWord(int index) {
        return list.get(index);
    }

    public int getSize() {
        return list.size();
    }

    public void delete(Word word) {
        int index = isExist(word);
        if (index != -1) {
            list.remove(index);
            saveFile();
        }
    }

    public int isExist(Word word) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getWord().equals(word.getWord())) return i;
        }
        return -1;
    }

    public void saveFile() {
        BufferedWriter bufferedWriter = null;
        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            String str = "";
            for (int i = 0; i < list.size(); i++) {
                str += list.get(i).getWord() + "," + list.get(i).getPronounce() + "," + list.get(i).getMeaning() + "\n";
            }
            out.write(str);
            out.close();
        } catch (IOException e) {
            Alert.getAlert("loi luu file!");
        }
    }

    public void readFile() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new BufferedReader(new InputStreamReader(file.toURI().toURL().openStream(),"UTF-8")));
            scanner.useDelimiter(",");
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                String[] data = input.split(",");
                list.add(new Word(data[0], data[1], data[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            Alert.getAlert("loi doc file");
        }
    }
}

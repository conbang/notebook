package sample.view.video;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Video {
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private final String path = "src/sample/data/cn/video";
    private List<String> listVideo;
    private static Pane video = null;
    private FontAwesomeIconView play;
    private FontAwesomeIconView next;
    private FontAwesomeIconView back;
    private boolean isPlaying = true;
    private static int index = 0;
    private Slider slider,volume;

    private Video() {
        listVideo = new LinkedList<>();
        File folder = new File(path);
        for (File e : folder.listFiles()) {
            listVideo.add(e.getPath());
        }
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController("sample.view.video.Video");
        try {
            video = (Pane) fxmlLoader.load(getClass().getResource("video.fxml"));
            mediaPlayer = new MediaPlayer(new Media(new File(listVideo.get(index)).toURI().toString()));
            mediaPlayer.setAutoPlay(true);
            mediaView = (MediaView) video.lookup("#video");
            mediaView.setMediaPlayer(mediaPlayer);
            slider = (Slider) video.lookup("#skip");
            volume = (Slider) video.lookup("#volume");
            mediaPlayer.setOnReady(() -> {
                slider.setMin(0);
                slider.setMax(mediaPlayer.getMedia().getDuration().toMinutes());
                slider.setValue(0);
            });
            volume.setMax(1);
            volume.setMin(0);
            volume.valueProperty().addListener(new InvalidationListener() {
                @Override
                public void invalidated(Observable observable) {
                    mediaPlayer.setVolume(volume.getValue());
                }
            });
            mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    Duration d = mediaPlayer.getCurrentTime();
                    slider.setValue(d.toMinutes());
                }
            });
            slider.valueProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (slider.isPressed()) {
                        double val = slider.getValue();
                        mediaPlayer.seek(new Duration(val * 60 * 1000));
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        play();
        next();
        back();
    }

    public static Pane getVideo() {
        if (video == null) {
            synchronized (Video.class) {
                if (video == null) {
                    Video video1 = new Video();
                    return video;
                }
            }
        }
        return video;
    }

    public void next() {
        next = (FontAwesomeIconView) video.lookup("#next");
        next.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                index++;
                mediaView.getMediaPlayer().dispose();
                if (index < listVideo.size()) {
                    mediaPlayer = new MediaPlayer(new Media(new File(listVideo.get(index)).toURI().toString()));
                    mediaPlayer.setAutoPlay(true);
                    mediaView = (MediaView) video.lookup("#video");
                    mediaView.setMediaPlayer(mediaPlayer);
                } else {
                    index = 0;
                    mediaPlayer = new MediaPlayer(new Media(new File(listVideo.get(index)).toURI().toString()));
                    mediaPlayer.setAutoPlay(true);
                    mediaView = (MediaView) video.lookup("#video");
                    mediaView.setMediaPlayer(mediaPlayer);
                }
            }
        });
    }

    public void back() {
        back = (FontAwesomeIconView) video.lookup("#back");
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                index--;
                mediaView.getMediaPlayer().dispose();
                if (index >= 0) {
                    mediaPlayer = new MediaPlayer(new Media(new File(listVideo.get(index)).toURI().toString()));
                    mediaPlayer.setAutoPlay(true);
                    mediaView = (MediaView) video.lookup("#video");
                    mediaView.setMediaPlayer(mediaPlayer);
                } else {
                    index = listVideo.size() - 1;
                    mediaPlayer = new MediaPlayer(new Media(new File(listVideo.get(index)).toURI().toString()));
                    mediaPlayer.setAutoPlay(true);
                    mediaView = (MediaView) video.lookup("#video");
                    mediaView.setMediaPlayer(mediaPlayer);
                }
            }
        });
    }

    public void play() {
        play = (FontAwesomeIconView) video.lookup("#play");
        play.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isPlaying) {
                    play.setGlyphName("PLAY");
                    mediaPlayer.pause();
                    isPlaying = false;
                } else {
                    play.setGlyphName("PAUSE");
                    mediaPlayer.play();
                    isPlaying = true;
                }
            }
        });
    }
}

package sample.utils;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Luke Ewin
 * @create 2023-05-14 20:46
 */
public class Tip {

    // 消息提示
    public static void tip(String message, Stage stage) {
        // 创建一个Label用于显示提示信息
        Label label = new Label(message);
        label.setStyle("-fx-font-size: 16px;");
        label.setBackground(new Background(new BackgroundFill(Color.web("#E6F2FF"), new CornerRadii(5), null)));
        label.setPadding(new javafx.geometry.Insets(10));

        // 创建一个Popup对象，设置Popup的大小和内容
        Popup popup = new Popup();
        popup.getContent().add(label);
        popup.setAutoHide(true);

        // 显示Popup对象，并在3秒后自动隐藏
        popup.show(stage);
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> popup.hide());
        delay.play();
    }
}

package sample;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import sample.entity.Message;
import sample.entity.RequestBody;
import sample.entity.ResponseBody;
import sample.utils.SaveFile;
import sample.utils.Tip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

public class Main extends Application {

     private static String URL;
     private static String CHAT_URL;
     private static String API_KEY;

    @Override
    public void init() {
        Properties prop = new Properties();
        try {
            prop.load(new BufferedInputStream(new FileInputStream(new File("ChatGPT.properties"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        URL = prop.getProperty("URL");
        CHAT_URL = prop.getProperty("CHAT_URL");
        API_KEY = prop.getProperty("API_KEY");
    }

    private TextArea chatArea;
    private TextArea inputArea;
    private Button sendButton;
    private Button saveRecordButton;
    private Button clearScreenButton;
    private Button clearContextButton;
    private Stage stage;

    // 存放上下文语境
    private List<Message> messages = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {

        // 创建UI界面
        chatArea = new TextArea();
        inputArea = new TextArea();
        sendButton = new Button("发送");
        saveRecordButton = new Button("保存聊天记录");
        clearScreenButton = new Button("清屏");
        clearContextButton = new Button("清除上下文语境");

        sendButton.setStyle("-fx-font-size: 15;");
        saveRecordButton.setStyle("-fx-font-size: 15;");
        clearScreenButton.setStyle("-fx-font-size: 15;");
        clearContextButton.setStyle("-fx-font-size: 15;");

        sendButton.setOnAction(e -> {
            Task<Void> task = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    // 执行耗时操作，例如发送网络请求或执行计算密集型任务
                    sendMessage();
                    // 返回null
                    return null;
                }
            };

            // 在后台线程上执行Task
            new Thread(task).start();

            // 将操作提交到JavaFX应用程序线程队列中
            Platform.runLater(() -> {
                // 在此更新UI或执行其他需要在JavaFX应用程序线程上执行的操作
            });
        });
        saveRecordButton.setOnAction(e -> SaveFile.saveFile(chatArea.getText(), primaryStage));
        clearScreenButton.setOnAction(e -> clearScreen());
        clearContextButton.setOnAction(e -> clearContext());

        HBox buttonBox = new HBox();
        buttonBox.getChildren().addAll(sendButton, new Region(), saveRecordButton, new Region(), clearScreenButton, new Region(), clearContextButton);
        HBox.setHgrow(new Region(), Priority.ALWAYS);
        HBox.setMargin(saveRecordButton, new Insets(0, 0, 0, 10));
        HBox.setMargin(clearScreenButton, new Insets(0, 0, 0, 10));
        HBox.setMargin(clearContextButton, new Insets(0, 0, 0, 10));

        VBox root = new VBox();
        root.getChildren().addAll(chatArea, inputArea, buttonBox);

        // 设置控件的属性
        chatArea.setEditable(false);
        chatArea.setWrapText(true);
        chatArea.setPrefHeight(350.0);
        chatArea.setStyle("-fx-control-inner-background: #f4f4f4; -fx-font-size: 15;");
        inputArea.setWrapText(true);
        inputArea.setPrefHeight(150.0);
        inputArea.setStyle("-fx-font-size: 15;");

        // 创建场景并设置窗口大小
        Scene scene = new Scene(root, 600, 540);

        // 显示窗口
        primaryStage.setScene(scene);
        primaryStage.setTitle("聊天窗口");
        primaryStage.show();

        stage = primaryStage;

        messages.add(new Message("system", "你是一个助手"));
    }

    private void sendMessage() {
        // 获取用户输入的消息并将其添加到聊天区域
        String prompt = inputArea.getText();

        // 获取当前时间
        String nowTime = getNowTime();
        chatArea.appendText(nowTime + "\n");
        chatArea.appendText("我说：" + prompt + "\n\n");

        // 清空输入框
        inputArea.setText("");

        // 存储上下文语境
        messages.add(new Message("user", prompt));

        // 获取 ChatGPT 的回复
        // String reply = httpRequest(messages);

        // 创建新的线程去发送 ChatGPT 请求
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return httpRequest(messages);
            }
        });
        new Thread(task).start();
        try {
            String reply = task.get();

            // 机器人回复时间
            String replyTime = getNowTime();
            chatArea.appendText(replyTime + "\n");
            // 把内容显示到 UI 界面上
            chatArea.appendText("机器人说：" + reply + "\n\n");

            messages.add(new Message("assistant", reply));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String httpRequest(List<Message> messages) {
        RequestBody requestBody = new RequestBody();
        requestBody.setUrl(CHAT_URL);
        requestBody.setApiKey(API_KEY);
        requestBody.setMessages(messages);

        // 转换为 JSON 字符串
        String jsonData = JSON.toJSONString(requestBody);

        // 向接口发送 http 请求，并得到接口响应的 JSON 字符串数据
        String responseJSON = HttpRequest.post(URL)
                .header("Content-Type", "application/json")
                .body(jsonData)
                .execute()
                .body();

        // 把 JSON 字符串数据转为对象
        ResponseBody responseBody = JSON.parseObject(responseJSON, ResponseBody.class);

        // 从对象中获取需要的内容
        String content = responseBody.getData().getContent();

        return content;
    }

    // 获取当前时间
    public String getNowTime() {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        return formattedDateTime;
    }

    // 清屏
    public void clearScreen() {
        chatArea.clear();
        Tip.tip("清屏成功", stage);
    }

    // 清除上下文语境
    public void clearContext() {
        messages.clear();
        messages.add(new Message("system", "你是一个智能助手"));
        Tip.tip("成功清除上下文语境", stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

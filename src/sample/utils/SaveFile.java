package sample.utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Luke Ewin
 * @create 2023-05-14 21:10
 */
public class SaveFile {

    public static void saveFile(String content, Stage primaryStage) {
        if (content.isEmpty()) {
            Tip.tip("内容为空", primaryStage);
        } else {
            // 限制可选文件格式
            FileChooser.ExtensionFilter txt = new FileChooser.ExtensionFilter("Text Files", "*.txt");
            FileChooser.ExtensionFilter md = new FileChooser.ExtensionFilter("Markdown Files", "*.md");

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(txt, md);

            // 配置文件保存对话框
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                String fileName = file.getName();
                String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
                if (fileExtension.equals("txt") || fileExtension.equals("md")) {
                    FileWriter fw = null;
                    try {
                        fw = new FileWriter(file);
                        fw.write(content);
                        Tip.tip("保存成功", primaryStage);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Tip.tip("保存失败", primaryStage);
                    } finally {
                        if (fw != null) {
                            try {
                                fw.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
}

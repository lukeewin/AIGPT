package sample.entity;

import lombok.Data;

import java.util.List;

/**
 * @author Luke Ewin
 * @create 2023-03-11 18:04
 */
@Data
public class RequestBody {

    private String url;

    private String apiKey;

    private List<Message> messages;
}

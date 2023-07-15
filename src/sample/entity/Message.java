package sample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Luke Ewin
 * @create 2023-03-22 13:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private String role;

    private String content;
}

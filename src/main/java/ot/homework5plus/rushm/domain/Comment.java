package ot.homework5plus.rushm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="comment")
public class Comment {

    @Id
    private long id;

    private String text;

    public Comment(String text) {
        this.text = text;
    }
}
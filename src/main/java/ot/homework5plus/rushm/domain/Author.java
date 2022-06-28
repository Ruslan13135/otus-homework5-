package ot.homework5plus.rushm.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "author")
public class Author {

    @Id
    private Long id;

    private String name;

    @Transient
    public static final String SEQUENCE_NAME = "authors_sequence";

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
    }
}

package ot.homework5plus.rushm.models.entity;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "book")
@Entity
@Table(name = "COMMENTS")
public class Comment extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "POST_DATE", nullable = false)
    private String postDate;

    @Column(name = "AUTHOR_NAME", nullable = false)
    private String authorName;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "FAVORITE")
    private boolean favorite = false;

    @ManyToOne(targetEntity = Book.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "BOOK_ID")
    private Book book;

}

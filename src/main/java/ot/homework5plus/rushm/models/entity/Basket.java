package ot.homework5plus.rushm.models.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "BASKETS")
public class Basket extends AuditModel implements Serializable {

    @Id
    @Column(name = "USER_ID")
    private Long id;

    @OneToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "USER_ID")
    private User user;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Book.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "BASKET_BOOKS", joinColumns = @JoinColumn(name = "BASKET_ID"), inverseJoinColumns = @JoinColumn(name = "BOOK_ID"))
    private List<Book> books;
}

package ot.homework5plus.rushm.models.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ot.homework5plus.rushm.models.enums.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ORDERS")
public class Order extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDER_NUMBER", nullable = false)
    private String orderNumber;

    @Column(name = "ORDER_TIME", nullable = false)
    private LocalDateTime orderTime;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(targetEntity = User.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Book.class, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "BOOK_ORDERS", joinColumns = @JoinColumn(name = "ORDER_ID"), inverseJoinColumns = @JoinColumn(name = "BOOK_ID"))
    private List<Book> books;
}

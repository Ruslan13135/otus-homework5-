package ot.homework5plus.rushm.models.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "INSTANCES")
public class Instance extends AuditModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "INVENTORY_NUMBER", nullable = false, unique = true)
    private String inventoryNumber;

    @ManyToOne(targetEntity = Book.class, cascade = CascadeType.MERGE)
    @JoinColumn(name = "BOOK_ID")
    private Book book;
}

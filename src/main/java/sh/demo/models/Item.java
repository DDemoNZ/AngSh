package sh.demo.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private Long price;
    private String imageUrl;
}

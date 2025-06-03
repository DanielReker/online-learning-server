package io.github.danielreker.onlinelearningserver.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "text_blocks")
public class TextBlock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "block_order")
    private Integer blockOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "educational_material_id", nullable = false)
    private EducationalMaterial educationalMaterial;
}
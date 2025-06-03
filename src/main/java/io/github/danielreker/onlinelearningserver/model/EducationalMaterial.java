package io.github.danielreker.onlinelearningserver.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "educational_materials")
public class EducationalMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "topic", nullable = false, length = 100)
    private String topic;

    @Column(name = "reading_time_minutes")
    private Integer readingTimeMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @OneToMany(mappedBy = "educationalMaterial", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    @OrderBy("blockOrder ASC")
    private List<TextBlock> textBlocks = new ArrayList<>();

    @OneToOne(mappedBy = "educationalMaterial", cascade = CascadeType.ALL, orphanRemoval = true)
    private Test test;

    @Builder.Default
    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    @Builder.Default
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt = Instant.now();


    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }


    public boolean hasTest() {
        return this.test != null;
    }

    public void addTextBlock(TextBlock textBlock) {
        textBlocks.add(textBlock);
        textBlock.setEducationalMaterial(this);
    }

    public void removeTextBlock(TextBlock textBlock) {
        textBlocks.remove(textBlock);
        textBlock.setEducationalMaterial(null);
    }

    public void setTest(Test test) {
        if (test == null) {
            if (this.test != null) {
                this.test.setEducationalMaterial(null);
            }
        } else {
            test.setEducationalMaterial(this);
        }
        this.test = test;
    }
}
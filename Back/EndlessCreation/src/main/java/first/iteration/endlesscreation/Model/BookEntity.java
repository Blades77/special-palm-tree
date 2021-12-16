package first.iteration.endlesscreation.Model;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String bookTitle;
    private Integer pagesNumber;
    private Long ownerUser;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_tag",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<TagEntity> tags = new HashSet<>();

    @OneToMany(mappedBy = "bookEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<BookPageEntity> pages = new HashSet<>();

    @OneToMany(mappedBy = "bookEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<BookReviewEntity> reviews = new HashSet<>();


    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Integer getPagesNumber() {
        return pagesNumber;
    }

    public void setPagesNumber(Integer pagesNumber) {
        this.pagesNumber = pagesNumber;
    }

    public Long getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(Long ownerUser) {
        this.ownerUser = ownerUser;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<TagEntity> getTags() {
        return tags;
    }

    public void setTags(Set<TagEntity> tags) {
        this.tags = tags;
    }

    public void addTag(TagEntity tag) {
        this.tags.add(tag);
        tag.getBooks().add(this);
    }

    public void removeTag(TagEntity tag) {
        this.tags.remove(tag);
        tag.getBooks().remove(this);
    }

    public Set<BookPageEntity> getPages() {
        return pages;
    }

    public void setPages(Set<BookPageEntity> pages) {
        this.pages = pages;
    }

    public Set<BookReviewEntity> getReviews() {
        return reviews;
    }

    public void setReviews(Set<BookReviewEntity> reviews) {
        this.reviews = reviews;
    }
}

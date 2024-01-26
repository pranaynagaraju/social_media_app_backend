package com.techbypranay.socialmediaapp.entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;

@Entity
@Data
@Table(name = "save")
 public class Save {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String savePostId;
    private int postId;
    private String firebaseUserId;

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    private Instant createdOn;

   public String getSavePostId() {
      return savePostId;
   }

   public void setSavePostId(String savePostId) {
      this.savePostId = savePostId;
   }

   public int getPostId() {
      return postId;
   }

   public void setPostId(int postId) {
      this.postId = postId;
   }

   public String getFirebaseUserId() {
      return firebaseUserId;
   }

   public void setFirebaseUserId(String firebaseUserId) {
      this.firebaseUserId = firebaseUserId;
   }
}

package toyproject.blogPosting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toyproject.blogPosting.entity.primaryKey.FavoritePk;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@IdClass(FavoritePk.class)
public class Favorite {

    @Id
    private String userEmail;
    @Id
    private int boardNumber;
}

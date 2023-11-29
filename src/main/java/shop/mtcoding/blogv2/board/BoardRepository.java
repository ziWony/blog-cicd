package shop.mtcoding.blogv2.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    //@Query("select b from Board b join fetch b.user join fetch b.replies r where b.id=:id")
    @Query("select b from Board b join fetch b.user left join fetch b.replies r where b.id=:id")
    Optional<Board> findByIdFetchUserAndReplies(@Param("id") int id);
}

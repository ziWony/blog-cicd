package shop.mtcoding.blogv2.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blogv2._core.error.ex.Exception403;
import shop.mtcoding.blogv2._core.error.ex.Exception404;
import shop.mtcoding.blogv2._core.error.ex.Exception500;
import shop.mtcoding.blogv2.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public void 게시글쓰기(BoardRequest.SaveDTO requestDTO, User sessionUser) {
        try {
            boardRepository.save(requestDTO.toEntity(sessionUser));
        } catch (Exception e) {
            log.error("DB error : " + e.getMessage());
            throw new Exception500("게시글쓰기 오류");
        }
    }

    public List<BoardResponse.DTO> 게시글목록보기() {
        List<Board> boardListPS = boardRepository.findAll();
        return boardListPS.stream().map(board -> new BoardResponse.DTO(board)).collect(Collectors.toList());
    }

    public BoardResponse.DetailDTO 게시글상세보기(Integer id, User sessionUser) {
        Board boardPS = boardRepository.findByIdFetchUserAndReplies(id).orElseThrow(
                () -> new Exception404("해당 id의 게시글을 찾을 수 없어요 : " + id)
        );
        return new BoardResponse.DetailDTO(boardPS, sessionUser);
    }

    public BoardResponse.UpdateFormDTO 게시글수정폼보기(Integer id, User sessionUser) {
        Board boardPS = boardRepository.findById(id).orElseThrow(
                () -> new Exception404("해당 id의 게시글을 찾을 수 없어요 : " + id)
        );

        if (sessionUser.getId() != boardPS.getUser().getId()) {
            throw new Exception403("당신은 해당 게시글을 수정할 권한이 없어요");
        }
        return new BoardResponse.UpdateFormDTO(boardPS);
    }

    @Transactional
    public void 게시글수정하기(Integer id, BoardRequest.UpdateDTO requestDTO, User sessionUser) {
        Board boardPS = boardRepository.findById(id).orElseThrow(
                () -> new Exception404("해당 id의 게시글을 찾을 수 없어요 : " + id)
        );
        if (sessionUser.getId() != boardPS.getUser().getId()) {
            throw new Exception403("당신은 해당 게시글을 수정할 권한이 없어요");
        }
        boardPS.update(requestDTO.getTitle(), requestDTO.getContent());
    } // 더티 채킹

    @Transactional
    public void 게시글삭제하기(Integer id, User sessionUser) {
        Board boardPS = boardRepository.findById(id).orElseThrow(
                () -> new Exception404("해당 id의 게시글을 찾을 수 없어요 : " + id)
        );
        if (sessionUser.getId() != boardPS.getUser().getId()) {
            throw new Exception403("당신은 해당 게시글을 삭제할 권한이 없어요");
        }
        boardRepository.deleteById(boardPS.getId());
    }
}

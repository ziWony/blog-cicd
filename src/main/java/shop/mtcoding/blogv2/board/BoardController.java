package shop.mtcoding.blogv2.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shop.mtcoding.blogv2._core.error.ex.Exception401;
import shop.mtcoding.blogv2._core.error.ex.Exception403;
import shop.mtcoding.blogv2.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    @PostMapping("/board/save")
    public String save(@Valid BoardRequest.SaveDTO requestDTO, Errors errors) {
        // 수정
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("인증이 필요해요", false);
        }
        boardService.게시글쓰기(requestDTO, sessionUser);
        return "redirect:/";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("인증이 필요해요", false);
        }
        boardService.게시글삭제하기(id, sessionUser);
        return "redirect:/";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id, @Valid BoardRequest.UpdateDTO requestDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("인증이 필요해요", false);
        }
        boardService.게시글수정하기(id, requestDTO, sessionUser);
        return "redirect:/board/" + id;
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("인증이 필요해요", false);
        }

        BoardResponse.UpdateFormDTO responseDTO = boardService.게시글수정폼보기(id, sessionUser);
        request.setAttribute("board", responseDTO);
        return "board/updateForm";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        BoardResponse.DetailDTO responseDTO = boardService.게시글상세보기(id, sessionUser);
        request.setAttribute("board", responseDTO);
        return "board/detail";
    }

    @GetMapping({"", "/", "/board"})
    public String index(HttpServletRequest request) {
        List<BoardResponse.DTO> requestDTO = boardService.게시글목록보기();
        request.setAttribute("boards", requestDTO);
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("인증이 필요해요", false);
        }
        return "board/saveForm";
    }
}

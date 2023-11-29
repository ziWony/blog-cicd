package shop.mtcoding.blogv2.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    // HttpSession은 너무 자주 사용해서 IoC 컨테이너에 등록되어 있음
    private final HttpSession session;

    @PostMapping("/login")
    public String login(@Valid UserRequest.LoginDTO requestDTO, Errors errors) {
        User sessionUser = userService.로그인(requestDTO);
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/";
    }

    @PostMapping("/join")
    public String join(@Valid UserRequest.JoinDTO requestDTO, Errors errors) {
        userService.회원가입(requestDTO);
        return "redirect:/loginForm";
    }

    @PostMapping("/user/update")
    public String update(@Valid UserRequest.PasswordUpdateDTO requestDTO, Errors errors) {
        // 1. 세션 정보를 가져온다.
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 2. 회원수정 (핵심로직)
        userService.회원수정(requestDTO, sessionUser.getId());

        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout() {
        session.invalidate(); // 세션에 저장된 모든 값을 삭제합니다.
        return "redirect:/";
    }

    @GetMapping("/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm(HttpServletRequest request) {
        // 1. 세션 정보를 가져온다.
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 2. 인증된 사용자가 확인되면 회원정보를 조회한다.
        UserResponse.DTO responseDTO = userService.회원정보보기(sessionUser.getId());

        // 3. 화면에 필요한 데이터만 request 가방에 담는다.
        request.setAttribute("user", responseDTO);

        // 4. redirect가 아닌, requestDispatcher를 통해 내부 재요청을 한다.
        return "user/updateForm"; //
    }
}

package shop.mtcoding.blogv2.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blogv2._core.error.ex.Exception400;
import shop.mtcoding.blogv2._core.error.ex.Exception401;
import shop.mtcoding.blogv2._core.error.ex.Exception404;
import shop.mtcoding.blogv2._core.error.ex.Exception500;

import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@RequiredArgsConstructor
@Service // IoC 등록 (컴퍼넌트 스캔)
public class UserService {

    // DI
    private final UserRepository userRepository;

    @Transactional // 트랜잭션 관리 (insert, delete, update)
    public void 회원가입(UserRequest.JoinDTO requestDTO){
        Optional<User> userOP = userRepository.findByUsername(requestDTO.getUsername());
        if(userOP.isPresent()){
            throw new Exception400("동일한 유저네임이 존재합니다.");
        }

        try {
            userRepository.save(requestDTO.toEntity());
        }catch (Exception e){
            log.error("DB error : "+e.getMessage());
            throw new Exception500("server error");
        }
    }

    public User 로그인(UserRequest.LoginDTO requestDTO){
        User userPS = userRepository.findByUsernameAndPassword(requestDTO.getUsername(), requestDTO.getPassword())
                .orElseThrow(() -> new Exception401("아이디 혹은 패스워드가 틀렸습니다", true));
        return userPS;
    }

    public UserResponse.DTO 회원정보보기(int sessionUserId){
        // Optional
        User userPS = userRepository.findById(sessionUserId)
                .orElseThrow(() -> new Exception404("회원 정보를 찾을 수 없습니다"));

        // response dto -> 화면에 전달해야 할 데이터만 만든다
        return new UserResponse.DTO(userPS);
    }

    @Transactional
    public void 회원수정(UserRequest.PasswordUpdateDTO requestDTO, int sessionUserId) {
        User userPS = userRepository.findById(sessionUserId)
                .orElseThrow(() -> new Exception404("회원 정보를 찾을 수 없습니다"));
        userPS.updatePassword(requestDTO.getPassword()); // 더티체킹
    }
}

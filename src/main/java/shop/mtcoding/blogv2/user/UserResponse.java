package shop.mtcoding.blogv2.user;

import lombok.Data;

public class UserResponse {

    @Data
    public static class DTO{
        int id;
        String username;
        String email;

        public DTO(User user) {
            this.id = user.getId(); // id는 무조건 전달 (화면에 보이지 않아도)
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }
}

package shop.mtcoding.blogv2.board;

import lombok.Data;
import shop.mtcoding.blogv2.user.User;

import javax.validation.constraints.NotEmpty;

public class BoardRequest {

    @Data
    public static class UpdateDTO {
        @NotEmpty
        private String title;
        @NotEmpty
        private String content;
    }

    @Data
    public static class SaveDTO {
        @NotEmpty
        private String title;
        @NotEmpty
        private String content;

        public Board toEntity(User sessionUser) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(sessionUser)
                    .build();
        }
    }
}

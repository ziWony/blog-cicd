package shop.mtcoding.blogv2.board;

import lombok.Data;
import shop.mtcoding.blogv2.reply.Reply;
import shop.mtcoding.blogv2.reply.ReplyResponse;
import shop.mtcoding.blogv2.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BoardResponse {

    @Data
    public static class DTO {
        private int id;
        private String title;

        public DTO(Board board){
            this.id = board.getId();
            this.title = board.getTitle();
        }
    }

    @Data
    public static class DetailDTO {
        private int id;
        private String title;
        private String content;
        private int userId;
        private String username;
        private boolean isOwner;
        private List<ReplyResponse.DTO> replies;

        public DetailDTO(Board board, User sessionUser){
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userId = board.getUser().getId();
            this.username = board.getUser().getUsername();
            if(sessionUser == null){
                this.isOwner = false;
            }else{
                this.isOwner = sessionUser.getId() == board.getUser().getId();
            }
            this.replies = board.getReplies().stream().map(reply -> new ReplyResponse.DTO(reply, sessionUser)).collect(Collectors.toList());
        }
    }

    @Data
    public static class UpdateFormDTO {
        private int id;
        private String title;
        private String content;

        public UpdateFormDTO(Board board){
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
        }
    }

}

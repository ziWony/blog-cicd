package shop.mtcoding.blogv2.reply;

import lombok.Data;
import shop.mtcoding.blogv2.user.User;

public class ReplyResponse {
    @Data
    public static class DTO {
        private int id;
        private String comment;
        private int boardId;
        private int userId;
        private String username;
        private boolean isOwner; // 댓글의 주인 여부

        public DTO(Reply reply, User sessionUser){
            this.id = reply.getId();
            this.comment = reply.getComment();
            this.boardId = reply.getBoard().getId();
            this.userId = reply.getUser().getId();
            this.username = reply.getUser().getUsername();
            if(sessionUser == null){
                this.isOwner = false;
            }else{
                this.isOwner = sessionUser.getId() == reply.getUser().getId();
            }
        }
    }
}

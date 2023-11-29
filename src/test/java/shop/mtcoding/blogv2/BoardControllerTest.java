package shop.mtcoding.blogv2;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import shop.mtcoding.blogv2.board.BoardResponse;
import shop.mtcoding.blogv2.user.User;

import java.time.LocalDateTime;

@ActiveProfiles("dev")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BoardControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void detail_test() throws Exception {
        // given
        int boardId = 1;

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.get("/board/"+boardId)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        BoardResponse.DetailDTO responseDTO =  (BoardResponse.DetailDTO) resultActions.andReturn().getRequest().getAttribute("board");
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        Assertions.assertThat(responseDTO.getTitle()).isEqualTo("title1");
        Assertions.assertThat(responseDTO.getContent()).isEqualTo("content1");
        Assertions.assertThat(responseDTO.getReplies().get(0).getComment()).isEqualTo("reply1");
    }

    @Test
    public void save_test() throws Exception {
        // given
        User sessionUser = User.builder()
                .id(1)
                .username("ssar")
                .password("1234")
                .email("ssar@nate.com")
                .createdAt(LocalDateTime.now())
                .build();

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("sessionUser", sessionUser);

        String requestBody = "title=title4&content=content4";

        // when
        ResultActions resultActions = mvc.perform(
                MockMvcRequestBuilders.post("/board/save")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content(requestBody)
                        .session(session)
        );
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println(responseBody);

        // then
        resultActions.andExpect(MockMvcResultMatchers.status().is3xxRedirection());
    }

}

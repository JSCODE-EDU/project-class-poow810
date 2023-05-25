package com.example.jscodestudy.dto;


import com.example.jscodestudy.domain.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;

    // 내용은 1글자 이상 1000글자 이하여야 한다.
    @NotBlank(message = "내용을 입력하세요.")
    @Size(min = 1, max = 1000, message = "내용은 1글자 이상 1000글자 이하여야 한다.")
    private String writer;

    // 제목은 1글자 이상 15글자 이하여야 한다.
    // 제목은 공백으로만 이루어질 수는 없다.
    @NotBlank(message = "제목을 입력하세요.")
    @Size(min = 1, max = 15, message = "제목은 1글자 이상 15글자 이하여야 한다.")
    private String title;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    public Board toEntity() {
        Board build = Board.builder()
                .id(id)
                .writer(writer)
                .title(title)
                .content(content)
                .build();
        return build;
    }

    @Builder
    public BoardDto(Long id, String title, String content, String writer, LocalDateTime createDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
    }
}


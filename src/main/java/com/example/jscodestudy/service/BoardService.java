package com.example.jscodestudy.service;

import com.example.jscodestudy.domain.Board;
import com.example.jscodestudy.dto.BoardDto;
import com.example.jscodestudy.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class BoardService {

    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Page<BoardDto> getList(Pageable pageable) {
        Page<Board> boards = boardRepository.findAll(pageable);
        return boards.map(this::convertEntityToDto);
    }

    @Transactional
    public Page<BoardDto> searchPosts(String keyword, Pageable pageable) {
        String trimmedKeyword = keyword.trim().replaceAll("\\s+", "");
        if (trimmedKeyword.length() <= 1) {
            throw new IllegalArgumentException("Keyword should be more than 1 letter excluding spaces");
        }
        return boardRepository.findByTitleContaining(trimmedKeyword, pageable).map(this::convertEntityToDto);
    }

    @Transactional
    private BoardDto convertEntityToDto(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .createDate(board.getCreatedDate())
                .build();
    }

    @Transactional
    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }


    @Transactional
    public BoardDto getPost(Long id) {
        Optional<Board> boardWrapper = boardRepository.findById(id);

        Board board = boardWrapper.get();


        BoardDto boardDto = BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .createDate(board.getCreatedDate())
                .build();

    //Board board = boardRepository.findById(id)
    //            .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
    //
    //    return convertEntityToDto(board);
    //}

        return boardDto;
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void deletePostById(Long id) {
        Optional<Board> boardOptional = boardRepository.findById(id);
        if (boardOptional.isPresent()) {
            boardRepository.delete(boardOptional.get());
        } else {
            throw new IllegalArgumentException("Post not found with ID: " + id);
        }
    }

}

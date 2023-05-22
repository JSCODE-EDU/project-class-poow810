package com.example.jscodestudy.service;

import com.example.jscodestudy.domain.Board;
import com.example.jscodestudy.dto.BoardDto;
import com.example.jscodestudy.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }


    @Transactional
    public List<BoardDto> searchPosts(String keyword) {
        List<Board> boards = boardRepository.findByTitleContaining(keyword);
        List<BoardDto> boardDtoList = new ArrayList<>();

        if(boards.isEmpty()) return boardDtoList;

        for(Board board : boards) {
            boardDtoList.add(this.convertEntityToDto(board));
        }
        return boardDtoList;
    }

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



    private static final int Block_Page_Count = 5;
    private static final int Page_Post_Count = 5;
    @Transactional
    public List<BoardDto> getBoardlist(Integer pageNum) {
        Page<Board> page = boardRepository
                .findAll(PageRequest
                        .of(pageNum-1, Page_Post_Count, Sort.by(Sort.Direction.ASC, "createdDate")));

        // List<Board> boards = boardRepository.findAll();
        List<Board> boards = page.getContent();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board board : boards) {
            boardDtoList.add(this.convertEntityToDto(board));
        }
        return boardDtoList;
    }
    public Integer[] getPageList(Integer curPageNum){
        Integer[] pageList = new Integer[Block_Page_Count];

        // 총 게시글 개수
        Double postTotalCount = Double.valueOf(this.getBoardCount());

        // 총 게시글 개수를 기준으로 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int)(Math.ceil((postTotalCount/Page_Post_Count)));

        // 현재 페이지를 기준으로 블럭 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + Block_Page_Count)
                ? curPageNum + Block_Page_Count
                : totalLastPageNum;

        // 페이지 시작 번호 조정
        curPageNum = (curPageNum<=3) ? 1 : curPageNum-2;

        // 페이지 번호 할당
        for(int val=curPageNum, i=0;val<=blockLastPageNum;val++, i++){
            pageList[i] = val;
        }
        return pageList;
    }

    private Long getBoardCount() {
        return boardRepository.count();
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

        return boardDto;
    }

    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }

}

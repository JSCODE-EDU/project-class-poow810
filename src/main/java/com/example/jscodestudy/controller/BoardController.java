package com.example.jscodestudy.controller;

import com.example.jscodestudy.dto.BoardDto;
import com.example.jscodestudy.service.BoardService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/")
    public String list(@Valid @PageableDefault(size = 100, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<BoardDto> boardDtoPage = boardService.getList(pageable);
        model.addAttribute("boardList", boardDtoPage);

        return "board/list.html";
    }

    @GetMapping("/board/search")
    public String search(@RequestParam(value = "keyword") String keyword, @PageableDefault(size = 100, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        Page<BoardDto> boardDtoPage = boardService.searchPosts(keyword, pageable);

        if (boardDtoPage.isEmpty()){
            model.addAttribute("errorMessage", "No results found for the search query.");
            return "error-page";
        }
        model.addAttribute("boardList", boardDtoPage.getContent());

        return "board/list.html";
    }

    @GetMapping("/post")
    public String write() {
        return "board/write.html";
    }

    @PostMapping("/post")
    public String write(@Valid BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/";
    }

    // @GetMapping("/post/{no}")
    @RequestMapping(value = "/post/{no}", method = {RequestMethod.GET}) //@GetMapping 오류
    public String detail(@PathVariable("no") Long id, Model model) {
        BoardDto boardDto = boardService.getPost(id);

        model.addAttribute("boardDto", boardDto);
        return "board/detail.html";
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long id, Model model) {
        BoardDto boardDto = boardService.getPost(id);

        model.addAttribute("boardDto", boardDto);
        return "board/update.html";
    }

    @PutMapping("/post/edit/{no}")
    public String update(@Valid BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:/";
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "byId", defaultValue = "false") boolean byId) {
        if (byId) {
            boardService.deletePostById(id);
        } else {
            boardService.deletePost(id);
        }
        return "redirect:/";
    }
}


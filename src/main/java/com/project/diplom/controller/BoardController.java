package com.project.diplom.controller;

import com.project.diplom.model.dto.BoardDto;
import com.project.diplom.model.dto.BoardViewDto;
import com.project.diplom.service.BoardService;
import com.project.diplom.shared.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<BoardViewDto> getBoard(@PathVariable("id") Long id, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(boardService.getBoard(id, customUserDetails.getId()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<BoardViewDto>> getBoards(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(boardService.getBoards(customUserDetails.getId()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Object> createBoard(@Valid @RequestBody BoardDto boardDetails,
                                              Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        boardService.createBoard(boardDetails, customUserDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Object> updateBoard(@PathVariable("id") Long boardId,
                                              @Valid @RequestBody BoardDto boardDetails,
                                              Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        boardService.updateBoard(boardDetails, boardId, customUserDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Object> deleteBoard(@PathVariable("id") Long id, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        boardService.deleteBoard(id, customUserDetails.getId());
        return ResponseEntity.noContent().build();
    }
}

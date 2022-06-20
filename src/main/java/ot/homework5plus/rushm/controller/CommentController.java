package ot.homework5plus.rushm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ot.homework5plus.rushm.domain.Book;
import ot.homework5plus.rushm.domain.Comment;
import ot.homework5plus.rushm.service.CommentService;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/delete/{comment}")
    public String deleteComment(@PathVariable Comment comment) {
        long id = comment.getBook().getId();
        commentService.deleteComment(comment);
        return "redirect:/view/" + id;
    }

    @GetMapping(value = "/addComment", params = "bookId")
    public String addComment(@RequestParam("bookId") Book book,
                             Model model) {
        model.addAttribute("comment", new Comment("", book));
        return "commentEdit";
    }

    @PostMapping("/addComment")
    public String addBook(@ModelAttribute Comment comment) {
        commentService.addOrSaveComment(comment);
        return "redirect:/view/" + comment.getBook().getId();
    }

    @GetMapping("/edit/{comment}")
    public String editComment(@PathVariable Comment comment, Model model) {
        model.addAttribute("comment", comment);
        return "commentEdit";
    }
}

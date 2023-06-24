package org.insat.helpDesk.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.insat.helpDesk.Model.Reply;
import org.insat.helpDesk.Repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/replies/")
public class ReplyController {
    @Autowired
    private ReplyRepository replyRepository;
    @GetMapping("all")
    public List<Reply> getAllReplies() {
        return replyRepository.findAll();  
    }
    @GetMapping("{id}")
    public List<Reply> getReplyByTicketId(@PathVariable Long id) {
        return replyRepository.findByTicketId(id);  
    }

    @PostMapping("add")
    public Reply addReply(@RequestBody Reply reply) {
        return replyRepository.save(reply);
    }

    @DeleteMapping("delete/{id}")
    public void deleteReply(@PathVariable Long id) {
        replyRepository.deleteById(id);
    }
}

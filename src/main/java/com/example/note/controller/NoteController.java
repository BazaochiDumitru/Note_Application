package com.example.note.controller;

import com.example.note.model.Note;
import com.example.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class NoteController {

    @Autowired
    private NoteRepository noteRepository;

    @GetMapping("/note")
    public String noteMain(Model model) {
        Iterable<Note> notes = noteRepository.findAll();
        model.addAttribute("notes", notes);
        return "note-main";
    }

    @GetMapping("/note/add")
    public String noteAdd(Model model) {
        return "note-add";
    }

    @PostMapping("/note/add")
    public String notePostAdd(@RequestParam String text, Model model){
        Note note = new Note(text);
        noteRepository.save(note);
        return "redirect:/note";
    }

    @GetMapping("/note/{id}")
    public String noteDetails(@PathVariable(value = "id") Integer id, Model model) {
        if(!noteRepository.existsById(id)){
            return "redirect:/note";
        }
        Optional<Note> note = noteRepository.findById(id);
        ArrayList<Note> res = new ArrayList<>();
        note.ifPresent(res::add);
        model.addAttribute("note", res);
        return "note-details";
    }

    @PostMapping("/note/{id}/remove")
    public String noteDelete(@PathVariable(value = "id") Integer id, Model model){
        Note note = noteRepository.findById(id).orElseThrow();
        noteRepository.delete(note);
        return "redirect:/note";
    }
}

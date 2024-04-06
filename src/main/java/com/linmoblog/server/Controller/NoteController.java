package com.linmoblog.server.Controller;

import com.linmoblog.server.Entity.Note;
import com.linmoblog.server.Entity.Result;
import com.linmoblog.server.Service.NoteService;
import com.linmoblog.server.aspect.ApiOperationLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.List;

@RequestMapping(value = "/api")
@RestController
@Tag(name = "文章接口")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @ApiOperationLog(description = "添加文章接口")
    @Operation(summary = "添加文章")
    @PostMapping("/protected/notes")
    public Result<Null> addNote(@RequestBody Note note) {
        return noteService.addNote(note);
    }

    @ApiOperationLog(description = "获取所有文章")
    @Operation(summary = "获取所有文章")
    @GetMapping("/public/notes")
    public Result<List<Note>> getNoteList() {
        return noteService.getNoteList();
    }

    @ApiOperationLog(description = "获取加精文章")
    @Operation(summary = "获取加精文章")
    @GetMapping("/public/topnotes")
    public Result<List<Note>> getTopNoteList() {
        return noteService.getTopNoteList();
    }

    @ApiOperationLog(description = "根据id获取文章")
    @Operation(summary = "根据id获取文章")
    @GetMapping("/public/notes/{id}")
    public Result<Note> getNoteById(@PathVariable Integer id) {
        return noteService.getNoteById(id);
    }

    @ApiOperationLog(description = "分页获取文章")
    @Operation(summary = "分页获取文章")
    @GetMapping("/public/notes/page")
    public Result<List<Note>> getNotePages(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "4") Integer pageSize) {
        return noteService.getNotePages(page, pageSize);
    }

    @ApiOperationLog(description = "删除文章")
    @Operation(summary = "删除文章")
    @DeleteMapping("/protected/notes")
    public Result<Null> deleteNote(@RequestBody List<Integer> notes) {
        return noteService.deleteNote(notes);
    }

    @ApiOperationLog(description = "修改文章")
    @Operation(summary = "修改文章")
    @PostMapping("/protected/notes/{id}")
    public Result<Null> updateNote(@PathVariable Integer id,@RequestBody Note note) {
        return noteService.updateNote(id,note);
    }

    @ApiOperationLog(description = "搜索文章")
    @Operation(summary = "搜索文章")
    @PostMapping("/public/notes/search")
    public Result<List<Note>> searchNote(@RequestParam(required = false) String title,
                                         @RequestParam(required = false) String categories,
                                         @RequestParam(required = false) String tagsLab,
                                         @RequestParam(required = false, defaultValue = "-1") int top,
                                         @RequestParam(required = false) Data time,
                                         @RequestParam(required = false) String status) {
        return noteService.searchNote(title, categories, tagsLab, top, time,status);
    }

}

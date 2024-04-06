package com.linmoblog.server.Service;

import com.github.pagehelper.PageHelper;
import com.linmoblog.server.Dao.NoteDao;
import com.linmoblog.server.Entity.Note;
import com.linmoblog.server.Entity.Result;
import com.linmoblog.server.enums.ResultCode;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteDao noteDao;
    public Result<Null> addNote(Note note) {
        noteDao.addNote(note);
        return new Result<>(ResultCode.SUCCESS);
    }


    public Result<List<Note>> getNoteList() {
        List<Note> notes = noteDao.getNoteList();
        return new Result<>(ResultCode.SUCCESS,notes);
    }

    public Result<Null> deleteNote(List<Integer> notes) {
        noteDao.deleteNote(notes);
        return new Result<>(ResultCode.SUCCESS);
    }

    public Result<Null> updateNote(Integer id, Note note) {
        noteDao.updateNote(id,note);
        return new Result<>(ResultCode.SUCCESS);
    }

    public Result<Note> getNoteById(Integer id) {
        Note note = noteDao.getNoteById(id);
        return new Result<>(ResultCode.SUCCESS,note);
    }

    public Result<List<Note>> getNotePages(Integer page, Integer pageSize) {
        Integer start = (page - 1) * pageSize;
        List<Note> noteList = noteDao.getNotePages(start,pageSize);
        return new Result<>(ResultCode.SUCCESS,noteList);
    }

    public Result<List<Note>> searchNote(String title, String categories, String tagsLab, int top, Data time,String status) {
        List<Note> noteList = noteDao.searchNote(title,categories,tagsLab,top,time,status);
        return new Result<>(ResultCode.SUCCESS,noteList);
    }

    public Result<List<Note>> getTopNoteList() {
        List<Note> noteList = noteDao.getTopNoteList();
        return new Result<>(ResultCode.SUCCESS,noteList);
    }
}

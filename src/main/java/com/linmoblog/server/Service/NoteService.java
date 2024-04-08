package com.linmoblog.server.Service;

import com.linmoblog.server.Dao.NoteDao;
import com.linmoblog.server.Entity.Note;
import com.linmoblog.server.Entity.Result;
import com.linmoblog.server.Entity.bo.NoteSearchBO;
import com.linmoblog.server.Entity.vo.NoteVO;
import com.linmoblog.server.Entity.vo.Pair;
import com.linmoblog.server.Mapper.CategoryMapper;
import com.linmoblog.server.Mapper.NoteMapper;
import com.linmoblog.server.enums.ResultCode;
import jakarta.annotation.Resource;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {
    @Autowired
    private NoteDao noteDao;
    @Resource
    private NoteMapper noteMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Transactional
    public void addNote(Note note) {
        noteMapper.addNote(note);

    }


    public List<Note> getNoteList() {
        //1. 查询
        List<Note> noteList = noteMapper.getNoteList();
        return noteList;
    }

    public Result<Null> deleteNote(List<Integer> notes) {
        noteDao.deleteNote(notes);
        return new Result<>(ResultCode.SUCCESS);
    }

    public Result<Null> updateNote(Integer id, Note note) {
        noteMapper.updateNote(id,note);
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

    public List<NoteVO> searchNote(NoteSearchBO bo) {
        List<NoteVO> noteList = noteMapper.searchNote(bo);
        // TODO ，联表查询
        return noteList;
    }

    public Result<List<Note>> getTopNoteList() {
        List<Note> noteList = noteDao.getTopNoteList();
        return new Result<>(ResultCode.SUCCESS,noteList);
    }

    public List<Pair<Integer, Integer>> getNoteCountByCategoryKey(List<Integer> categoryKeyList) {
        if (categoryKeyList.isEmpty()) {
            return Collections.emptyList();
        }
        return noteMapper.getNoteCountByCategoryKey(categoryKeyList);
    }
}

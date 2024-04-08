package com.linmoblog.server.Mapper;

import com.linmoblog.server.Entity.Note;
import com.linmoblog.server.Entity.bo.NoteSearchBO;
import com.linmoblog.server.Entity.vo.NoteVO;
import com.linmoblog.server.Entity.vo.Pair;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.crypto.Data;
import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("insert notes (note_title,note_content,cover,description,note_category,note_tags,is_top,status,create_time,update_time) " +
            "values (#{noteTitle},#{noteContent},#{cover},#{description},#{noteCategory},#{noteTags},#{isTop},#{status},#{createTime},#{updateTime})")
    void addNote(Note note);

    @Select("select * from notes")
    List<Note> getNoteList();


    void deleteNote(List<Integer> notesList);

    void updateNote(Integer id, Note note);

    @Select("select * from notes where note_key = #{id}")
    Note getNoteById(Integer id);

    @Select("SELECT * FROM notes WHERE is_top = 0 ORDER BY update_time DESC LIMIT #{start}, #{pageSize}")
    List<Note> getNotePages(Integer start, Integer pageSize);

    List<NoteVO> searchNote(@Param("bo") NoteSearchBO bo);

    @Select("select * from notes where is_top = 1")
    List<Note> getTopNoteList();

    List<Pair<Integer, Integer>> getNoteCountByCategoryKey(@Param("categoryKeyList") List<Integer> categoryKeyList);
}

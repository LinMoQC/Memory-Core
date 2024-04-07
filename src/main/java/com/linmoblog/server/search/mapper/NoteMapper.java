package com.linmoblog.server.search.mapper;

import com.linmoblog.server.Entity.Note;
import com.linmoblog.server.search.repository.impl.MeiliSearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Poison02
 * @date 2024/4/7
 */
@Repository
public class NoteMapper extends MeiliSearchRepository<Note> {
}

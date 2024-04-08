package com.linmoblog.server.Entity.bo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 笔记搜索业务对象
 */
@Data
public class NoteSearchBO {
    private String title;
    private String categories;
    private String tagsLab;
    private int top;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    /**
     * 状态
     */
    private String status;

}

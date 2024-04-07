package com.linmoblog.server.Mapper;

import com.linmoblog.server.Entity.Image;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ImageMapper {

    @Insert("insert into images (image_url) value (#{imgUrl})")
    void upload(String imageUrl);

    void delete(@Param("imageUrlList") List<String> imageUrlList);

    @Select("select * from images")
    List<Image> getImages();
}

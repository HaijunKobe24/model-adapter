package cn.unipus.modeladapter.api.service;

import cn.unipus.modeladapter.api.dto.UnitStatusInfo;
import cn.unipus.modeladapter.api.dto.CopyCourseDataDTO;
import cn.unipus.modeladapter.api.dto.CreateUnitDTO;
import cn.unipus.modeladapter.remote.starter.http.course.dto.CourseStructDTO;

import java.util.List;

/**
 * @description: 课程服务接口
 * @author:henry
 * @create: 2025-07-02 18:23
 **/
public interface ICourseService {

    List<UnitStatusInfo> queryUnitStatus(String bookId);

    CopyCourseDataDTO copyCourse(String bookId, String openId);

    CreateUnitDTO createUnit(String bookId, String name);

    /**
     * 发布课程
     * @param bookId 课程ID
     */
    void publishCourse(String bookId);

    /**
     * 获取自定义课程结构
     * @param bookId 教材ID
     * @return 课程结构列表
     */
    List<CourseStructDTO> customCourseStruct(String bookId);

    /**
     * 修改单元名称
     * @param bookId 教材ID
     * @param unitId 单元ID
     * @param name 新单元名称
     */
    void updateUnitName(String bookId, String unitId, String name);

    /**
     * 删除单元
     * @param bookId 教材ID
     * @param unitId 单元ID
     */
    void deleteUnit(String bookId, String unitId);
}

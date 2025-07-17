package cn.unipus.modeladapter.api.service.impl;

import cn.unipus.modeladapter.api.dto.CopyCourseDataDTO;
import cn.unipus.modeladapter.api.dto.CreateUnitDTO;
import cn.unipus.modeladapter.api.dto.UnitStatusInfo;
import cn.unipus.modeladapter.api.service.ICourseService;
import cn.unipus.modeladapter.api.service.IPublishBookService;
import cn.unipus.modeladapter.base.db.entity.Book;
import cn.unipus.modeladapter.base.db.entity.BookUnit;
import cn.unipus.modeladapter.base.db.repository.BookRepository;
import cn.unipus.modeladapter.base.db.repository.BookUnitRepository;
import cn.unipus.modeladapter.base.common.constant.UnitStatus;
import cn.unipus.modeladapter.base.service.BookService;
import cn.unipus.modeladapter.remote.starter.http.course.dto.CourseStructDTO;
import cn.unipus.modeladapter.remote.starter.http.course.dto.UnitStructDTO;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements ICourseService {

    @Resource
    private IPublishBookService iPublishBookService;

    @Resource
    private BookService bookService;

    @Resource
    private BookRepository bookRepository;

    @Resource
    private BookUnitRepository bookUnitRepository;
    /**
     * 复制课程
     * @param bookId 源课程ID
     * @param openId 操作人openID
     * @return 复制结果数据
     */
    @Override
    public CopyCourseDataDTO copyCourse(String bookId, String openId) {
        return iPublishBookService.copyBook(bookId, openId);
    }

    /**
     * 创建单元
     * @param bookId 教材ID
     * @param name 单元名称
     * @return 包含bookId和newUnitId的对象
     */
    @Override
    public CreateUnitDTO createUnit(String bookId, String name) {
        CreateUnitDTO result = new CreateUnitDTO();
        result.setBookId(bookId);
        result.setNewUnitId(iPublishBookService.addBookNode(bookId, name));
        return result;
    }

    /**
     * 发布课程
     * @param bookId 课程ID
     */
    public void publishCourse(String bookId) {
        iPublishBookService.publishBook(bookId);
    }

    /**
     * 根据书籍ID查询书籍单元状态列表
     *
     * @param bookId 书籍ID
     * @return 单元状态信息列表
     */
    @Override
    public List<UnitStatusInfo> queryUnitStatus(String bookId) {
        List<UnitStructDTO> unitList = bookService.courseBookNodes(bookId);
        List<BookUnit> unitCustomList = bookUnitRepository.findByBookId(bookId);

        Set<String> customUnitIds = unitCustomList.stream()
                .map(BookUnit::getId)
                .collect(Collectors.toSet());

        List<UnitStatusInfo> result = Lists.newArrayList();

        // 只有 unitList 不为空且不为 null 时才添加 notInCustomList
        if (unitList != null && !unitList.isEmpty()) {
            List<UnitStatusInfo> notInCustomList = unitList.stream()
                    .filter(unit -> !customUnitIds.contains(unit.getId()))
                    .map(unit -> {
                        UnitStatusInfo info = new UnitStatusInfo();
                        info.setUnitId(unit.getId());
                        info.setStatus(UnitStatus.PUBLISHED.getCode());
                        return info;
                    })
                    .collect(Collectors.toList());
            result.addAll(notInCustomList);
        }

        // inCustomList 逻辑不变
        List<UnitStatusInfo> inCustomList = unitCustomList.stream()
                .map(unit -> {
                    UnitStatusInfo info = new UnitStatusInfo();
                    info.setUnitId(unit.getId());
                    info.setStatus(unit.getStatus() == null ? null : unit.getStatus().toString());
                    return info;
                })
                .collect(Collectors.toList());
        result.addAll(inCustomList);

        return result;
    }

    /**
     * 获取自定义课程结构
     * @param bookId 教材ID
     * @return 课程结构列表
     */
    @Override
    public List<CourseStructDTO> customCourseStruct(String bookId) {
        List<Book> bookList = bookRepository.findByRefId(bookId);
        List<CourseStructDTO> result = Lists.newArrayList();
        if (bookList != null) {
            for (Book book : bookList) {
                List<UnitStructDTO> unitList = bookService.courseBookNodes(book.getId());
                CourseStructDTO courseStructDTO = new CourseStructDTO();
                courseStructDTO.setBookId(book.getId());
                courseStructDTO.setUnitStruct(JSON.toJSONString(unitList));
                result.add(courseStructDTO);
            }
        }
        return result;
    }

    @Override
    public void updateUnitName(String bookId, String unitId, String name) {
        iPublishBookService.updateBookNode(bookId, unitId, name);
    }

    @Override
    public void deleteUnit(String bookId, String unitId) {
        iPublishBookService.deleteBookNode(bookId, unitId);
    }
}
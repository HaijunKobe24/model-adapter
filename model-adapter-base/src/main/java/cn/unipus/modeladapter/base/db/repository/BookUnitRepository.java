package cn.unipus.modeladapter.base.db.repository;

import cn.unipus.modeladapter.base.db.entity.BookUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 教材单元DAO
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Repository
public interface BookUnitRepository extends JpaRepository<BookUnit, String> {

    /**
     * 根据教材ID查询单元列表
     *
     * @param bookId 教材ID
     * @return 单元列表
     */
    @Query(value = "SELECT * FROM tab_book_unit WHERE book_id = :bookId ORDER BY sort",
            nativeQuery = true)
    List<BookUnit> findByBookId(@NonNull @Param("bookId") String bookId);

    /**
     * 查询指定教材下最大的sort值
     *
     * @param bookId 教材ID
     * @return 最大sort（如无数据返回null）
     */
    @Query(value = "SELECT MAX(sort) FROM tab_book_unit WHERE book_id = :bookId",
            nativeQuery = true)
    Optional<Integer> findMaxSortByBookId(@NonNull @Param("bookId") String bookId);

    /**
     * 根据bookId修改status
     *
     * @param bookId 教材ID
     * @param status 新状态
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE tab_book_unit SET status = :status, modified = now() WHERE book_id = :bookId",
            nativeQuery = true)
    void updateStatusByBookId(@NonNull @Param("bookId") String bookId,
            @NonNull @Param("status") Byte status);
}
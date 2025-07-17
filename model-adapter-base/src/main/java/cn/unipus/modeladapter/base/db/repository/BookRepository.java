package cn.unipus.modeladapter.base.db.repository;

import cn.unipus.modeladapter.base.db.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 教材信息DAO
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    /**
     * 根据引用教材ID查询教材
     *
     * @param refId 引用的教材ID
     * @return 教材列表
     */
    @Query(value = "SELECT * FROM tab_book WHERE ref_id = :refId", nativeQuery = true)
    List<Book> findByRefId(@NonNull @Param("refId") String refId);
}
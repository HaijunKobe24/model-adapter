package cn.unipus.modeladapter.base.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 教材单元实体
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tab_book_unit")
public class BookUnit {

    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 教材ID
     */
    private String bookId;

    /**
     * 状态：0、草稿，1、待发布，2、已发布
     */
    private Byte status;

    /**
     * 序号
     */
    private Short sort;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 最后修改人
     */
    private String modifier;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;
}
package cn.unipus.modeladapter.base.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 教材信息实体
 *
 * @author haijun.gao
 * @date 2025/7/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tab_book")
public class Book {

    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 引用的教材ID，即官方教材ID
     */
    private String refId;

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
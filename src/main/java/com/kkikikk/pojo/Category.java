package com.kkikikk.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;

@Data // 使用Lombok注解，自动生成getter/setter/toString/equals/hashCode等方法
public class Category {
    @NotNull(groups = updateCategory.class)
    private Integer id; // 主键ID

    @NotEmpty
    private String categoryName; // 分类名称

    @NotEmpty
    private String categoryAlias; // 分类别名

    private Integer createUser; // 创建人ID，创建人信息，非空

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 时间格式化
    private LocalDateTime createTime; // 创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 时间格式化
    private LocalDateTime updateTime; // 更新时间

    //如果说某个校验没有指定分组,默认属于Default分组
    //分组之间可以继承,A extends B,B extends C,A属于C

    // 用于新增分类时的校验
    public interface addCategory extends Default {
    }

    // 用于更新分类时的校验
    public interface updateCategory extends Default {
    }

    // 用于删除分类时的校验
    public interface deleteCategory extends addCategory{
    }
}

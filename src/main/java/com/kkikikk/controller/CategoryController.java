package com.kkikikk.controller;

import com.kkikikk.pojo.Category;
import com.kkikikk.pojo.Result;
import com.kkikikk.service.CategoryService;
import com.kkikikk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 使用POST请求添加新的类别
     *
     * @param category 通过请求体传递的类别对象，包含新类别的详细信息
     * @return 返回一个表示操作结果的对象，这里总是返回表示成功的Result对象
     * <p>
     * 说明：此方法接收一个Category对象，通过categoryService.addCategory方法将其添加到数据存储中
     * 不需要额外的业务逻辑处理，直接调用服务层方法进行添加操作
     */
    @PostMapping
    public Result addCategory(@RequestBody @Validated(Category.addCategory.class) Category category) {
        categoryService.addCategory(category);
        return Result.success();
    }

    /**
     * 查询所有分类
     * <p>
     * 通过GET请求访问此方法，用于获取所有分类的列表
     * 该方法调用了categoryService的list方法来获取分类数据，并将这些数据封装到Result对象中返回
     *
     * @return 包含所有分类的Result对象，如果操作成功，Result对象中的data字段将包含分类列表
     */
    @GetMapping
    public Result<List<Category>> list() {
        List<Category> categories = categoryService.list();
        return Result.success(categories);
    }

    /**
     * 查询分类详情
     * <p>
     * 通过GET请求访问"/detail"路径，接收一个分类ID参数，返回对应的分类信息
     * 此方法解释了为什么需要这个方法（查询分类详情），以及它是如何工作的（通过ID查询分类信息并返回）
     *
     * @param id 分类的唯一标识符通过URL传入，用于查询特定的分类详情
     * @return 返回一个Result对象，包含查询到的分类信息，如果查询成功，Result的成功标志为true，并携带分类数据
     */
    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        Category category = categoryService.findById(id);
        return Result.success(category);
    }

    /**
     * 更新分类信息
     * <p>
     * 通过@PutMapping注解指定这是一个处理HTTP PUT请求的方法
     * 使用@Validated注解进行参数校验，确保传入的分类信息符合预定义的校验规则
     *
     * @param category 待更新的分类信息，通过@RequestBody注解接收HTTP请求体中的数据
     *                 参数校验失败时，会返回包含错误信息的HTTP响应
     * @return Result 成功更新后，返回表示操作成功的Result对象
     * <p>
     * 该方法委托给categoryService进行具体的更新操作，不直接处理HTTP层面的逻辑
     */
    @PutMapping
    public Result updateCategory(@RequestBody @Validated(Category.updateCategory.class) Category category) {
        categoryService.updateCategory(category);
        return Result.success();
    }

    /**
     * 删除文章
     * <p>
     * 通过DELETE请求访问此方法，用于删除指定ID的文章
     *
     * @param id 要删除的文章ID，通过查询参数传递
     * @return 返回一个表示操作结果的Result对象，如果删除成功，Result对象中的code字段为0
     */
    @DeleteMapping
    public Result deleteCategory(@RequestParam @Validated(Category.deleteCategory.class) Integer id) {
        categoryService.deleteCategory(id);
        return Result.success();
    }

}

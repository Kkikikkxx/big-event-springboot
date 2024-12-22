package com.kkikikk.controller;

import com.kkikikk.pojo.Article;
import com.kkikikk.pojo.PageBean;
import com.kkikikk.pojo.Result;
import com.kkikikk.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 添加新的文章。
     *
     * @param article 包含文章详细信息的对象
     * @return 操作结果
     */
    @PostMapping
    public Result addArticle(@RequestBody @Validated Article article) {
        articleService.addArticle(article);
        return Result.success();
    }

    /**
     * 查询文章列表
     * 通过分页和条件查询，获取文章列表这个功能是为了在前端展示文章列表时，可以根据不同的分类、状态等条件进行筛选，
     * 同时支持分页显示，提高用户体验
     *
     * @param pageNum    当前页码，用于分页查询
     * @param pageSize   每页显示的数量，用于分页查询
     * @param categoryId 文章分类ID，用于按分类筛选文章，可选参数
     * @param state      文章状态，用于按状态筛选文章，可选参数
     * @return 返回一个Result对象，包含查询到的文章分页列表
     */
    @GetMapping
    public Result<PageBean<Article>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String state
    ) {
        PageBean<Article> PageBean = articleService.list(pageNum, pageSize, categoryId, state);
        return Result.success(PageBean);
    }


    /**
     * 根据ID获取发布的文章详情
     *
     * @param id 文章的唯一标识符
     * @return 包含文章详情的Result对象
     */
    @GetMapping("/detail")
    public Result<Article> detail(Integer id) {
        Article article = articleService.findById(id);
        return Result.success(article);
    }

    /**
     * 更新文章信息。
     *
     * <p>
     * 通过@RequestBody将请求体中的数据绑定到Article对象，并通过@Validated进行参数校验，
     * 确保传入的文章信息符合预期的格式和规则。
     * </p>
     *
     * @param article 需要更新的文章对象
     * @return 成功响应结果
     */
    @PutMapping
    public Result updateArticle(@RequestBody @Validated Article article) {
        articleService.updateArticle(article);
        return Result.success();
    }

    /**
     * 删除文章
     *
     * @param id 文章的ID
     * @return 操作结果
     */
    @DeleteMapping
    public Result deleteArticle(Integer id) {
        // 调用文章服务删除指定ID的文章
        articleService.deleteArticle(id);
        // 返回删除成功的结果
        return Result.success();
    }

}

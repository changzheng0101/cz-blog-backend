package com.weixiao.controller;

import com.weixiao.common.result.DataResponse;
import com.weixiao.common.result.PageBean;
import com.weixiao.dto.ArticleDTO;
import com.weixiao.service.ArticleService;
import com.weixiao.vo.ArticleVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文章管理控制器
 * 
 * @author weixiao
 * @version 1.0
 * @since 2026-01-21
 */
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 创建文章
     *
     * @param articleDTO 文章信息
     * @return 创建的文章信息
     */
    @PostMapping
    public DataResponse<ArticleVO> createArticle(@RequestBody @Valid ArticleDTO articleDTO) {
        ArticleVO articleVO = articleService.createArticle(articleDTO);
        return DataResponse.success(articleVO);
    }

    /**
     * 根据ID获取文章
     *
     * @param id 文章ID
     * @return 文章信息
     */
    @GetMapping("/{id}")
    public DataResponse<ArticleVO> getArticleById(@PathVariable Long id) {
        ArticleVO articleVO = articleService.getArticleById(id);
        return DataResponse.success(articleVO);
    }

    /**
     * 分页获取文章列表
     *
     * @param pageNum    页码
     * @param pageSize   每页大小
     * @param title      标题（可选）
     * @param status     状态（可选）
     * @param categoryId 分类ID（可选）
     * @return 文章分页列表
     */
    @GetMapping
    public DataResponse<PageBean<ArticleVO>> getArticleList(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long categoryId) {
        PageBean<ArticleVO> pageBean = articleService.getArticleList(pageNum, pageSize, title, status, categoryId);
        return DataResponse.success(pageBean);
    }

    /**
     * 更新文章
     *
     * @param id 文章ID
     * @param articleDTO 更新的文章信息
     * @return 更新后的文章信息
     */
    @PutMapping("/{id}")
    public DataResponse<ArticleVO> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO articleDTO) {
        ArticleVO articleVO = articleService.updateArticle(id, articleDTO);
        return DataResponse.success(articleVO);
    }

    /**
     * 删除文章
     *
     * @param id 文章ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public DataResponse<Boolean> deleteArticle(@PathVariable Long id) {
        boolean result = articleService.deleteArticle(id);
        return DataResponse.success(result);
    }

    /**
     * 获取已发布的文章列表（此接口可能不再需要，可直接用分页接口 status=PUBLISH）
     * 但为了兼容性，可以保留或标记为废弃
     *
     * @return 已发布的文章列表
     */
    @GetMapping("/published")
    public DataResponse<List<ArticleVO>> getPublishedArticles() {
        // 复用 getArticleList 接口逻辑，这里简单起见，可以暂时不改动 Service 的 getArticlesByStatus 实现，
        // 或者在 Service 中删除旧方法并在 Controller 层重定向。
        // 由于 Service 接口已删除 getArticlesByStatus，这里需要调整调用
        PageBean<ArticleVO> pageBean = articleService.getArticleList(1, 100, null, "PUBLISH", null);
        return DataResponse.success(pageBean.getData());
    }

    /**
     * 根据分类获取文章列表
     *
     * @param categoryId 分类ID
     * @return 文章列表
     */
    @GetMapping("/category/{categoryId}")
    public DataResponse<List<ArticleVO>> getArticlesByCategory(@PathVariable Long categoryId) {
        List<ArticleVO> articles = articleService.getArticlesByCategory(categoryId);
        return DataResponse.success(articles);
    }

    /**
     * 增加文章阅读量
     *
     * @param id 文章ID
     * @return 更新结果
     */
    @PostMapping("/{id}/read")
    public DataResponse<Boolean> increaseReadCount(@PathVariable Long id) {
        articleService.increaseReadCount(id);
        return DataResponse.success(true);
    }
}
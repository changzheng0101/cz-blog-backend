package com.weixiao.controller;

import com.weixiao.common.exception.GlobalException;
import com.weixiao.common.result.DataResponse;
import com.weixiao.common.result.DataResponseCode;
import com.weixiao.common.result.PageBean;
import com.weixiao.dto.ArticleDTO;
import com.weixiao.dto.CategoryDTO;
import com.weixiao.service.ArticleService;
import com.weixiao.service.CategoryService;
import com.weixiao.vo.ArticleVO;
import com.weixiao.vo.CategoryVO;
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

    @Autowired
    private CategoryService categoryService;

    /**
     * 创建文章
     * 分类以 categoryId 为准；若仅传 categoryName 则按名称查找并转为 id，不存在则创建分类；
     * 若同时传入 categoryId 与 categoryName，则校验二者是否匹配，不匹配则抛异常。
     *
     * @param articleDTO 文章信息（可含 categoryId 和/或 categoryName）
     * @return 创建的文章信息
     */
    @PostMapping
    public DataResponse<ArticleVO> createArticle(@RequestBody @Valid ArticleDTO articleDTO) {
        resolveCategoryId(articleDTO);
        ArticleVO articleVO = articleService.createArticle(articleDTO);
        return DataResponse.success(articleVO);
    }

    /**
     * 根据 categoryId、categoryName 解析并设置 articleDTO 的 categoryId。
     * 以 categoryId 为准；仅 categoryName 时按名称查找或创建；二者都有时校验匹配。
     */
    private void resolveCategoryId(ArticleDTO articleDTO) {
        Long categoryId = articleDTO.getCategoryId();
        String categoryName = articleDTO.getCategoryName();
        boolean hasId = categoryId != null;
        boolean hasName = categoryName != null && !categoryName.isBlank();

        if (hasId && hasName) {
            CategoryVO category = categoryService.getCategoryById(categoryId);
            if (!categoryName.trim().equals(category.getName())) {
                throw new GlobalException(DataResponseCode.PARAM_ERROR, "分类ID与分类名称不匹配");
            }
            return;
        }
        if (hasId) {
            return;
        }
        if (hasName) {
            CategoryVO byName = categoryService.getCategoryByName(categoryName.trim());
            if (byName != null) {
                articleDTO.setCategoryId(byName.getId());
            } else {
                CategoryDTO dto = new CategoryDTO();
                dto.setName(categoryName.trim());
                CategoryVO created = categoryService.createCategory(dto);
                articleDTO.setCategoryId(created.getId());
            }
        }
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
package com.weixiao.service;

import com.weixiao.dto.ArticleDTO;
import com.weixiao.vo.ArticleVO;

import java.util.List;

/**
 * 文章服务接口
 */
public interface ArticleService {

    /**
     * 创建文章
     *
     * @param articleDTO 文章DTO
     * @return 创建的文章VO
     */
    ArticleVO createArticle(ArticleDTO articleDTO);

    /**
     * 根据ID获取文章
     *
     * @param id 文章ID
     * @return 文章VO
     */
    ArticleVO getArticleById(Long id);

    /**
     * 分页查询文章列表
     *
     * @param pageNum  页码
     * @param pageSize 每页条数
     * @param title    标题（可选）
     * @param status   状态（可选）
     * @param categoryId 分类ID（可选）
     * @return 文章VO分页结果
     */
    com.weixiao.common.result.PageBean<ArticleVO> getArticleList(int pageNum, int pageSize, String title, String status, Long categoryId);

    /**
     * 更新文章
     *
     * @param id         文章ID
     * @param articleDTO 文章DTO
     * @return 更新后的文章VO
     */
    ArticleVO updateArticle(Long id, ArticleDTO articleDTO);

    /**
     * 删除文章
     *
     * @param id 文章ID
     * @return 是否删除成功
     */
    boolean deleteArticle(Long id);

    /**
     * 获取置顶文章列表
     *
     * @return 文章VO列表
     */
    List<ArticleVO> getTopArticles();

    /**
     * 根据分类ID获取文章列表
     *
     * @param categoryId 分类ID
     * @return 文章VO列表
     */
    List<ArticleVO> getArticlesByCategory(Long categoryId);

    /**
     * 增加文章阅读量
     *
     * @param id 文章ID
     * @return 是否增加成功
     */
    boolean increaseReadCount(Long id);
}
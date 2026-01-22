package com.weixiao.mapper;

import com.weixiao.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 文章Mapper接口
 * 
 * @author weixiao
 * @version 1.0
 * @since 2026-01-21
 */
@Mapper
public interface ArticleMapper {
    
    /**
     * 插入文章
     * 
     * @param article 文章实体
     * @return 影响的行数
     */
    int insert(Article article);
    
    /**
     * 根据ID查询文章
     * 
     * @param id 文章ID
     * @return 文章实体
     */
    Article selectById(Long id);
    
    /**
     * 查询所有文章列表（按创建时间倒序）
     * 
     * @return 文章列表
     */
    List<Article> selectAll();
    
    /**
     * 更新文章
     * 
     * @param article 文章实体
     * @return 影响的行数
     */
    int update(Article article);
    
    /**
     * 删除文章
     * 
     * @param id 文章ID
     * @return 影响的行数
     */
    int deleteById(Long id);
    
    /**
     * 根据状态查询文章列表
     * 
     * @param status 文章状态
     * @return 文章列表
     */
    List<Article> selectByStatus(@Param("status") String status);
    
    /**
     * 查询置顶文章列表
     *
     * @return 置顶文章列表
     */
    List<Article> selectTopArticles();
    
    /**
     * 根据分类ID查询文章列表
     *
     * @param categoryId 分类ID
     * @return 文章列表
     */
    List<Article> selectByCategoryId(@Param("categoryId") Long categoryId);
    
    /**
     * 增加文章阅读量
     *
     * @param id 文章ID
     * @return 影响的行数
     */
    int increaseReadCount(@Param("id") Long id);

    /**
     * 查询文章列表
     *
     * @param title      标题
     * @param status     状态
     * @param categoryId 分类ID
     * @return 文章列表
     */
    List<Article> selectArticleList(@Param("title") String title, @Param("status") String status, @Param("categoryId") Long categoryId);
}
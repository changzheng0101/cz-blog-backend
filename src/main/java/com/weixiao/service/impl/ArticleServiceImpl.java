package com.weixiao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.weixiao.common.exception.GlobalException;
import com.weixiao.common.result.DataResponseCode;
import com.weixiao.common.result.PageBean;
import com.weixiao.common.utils.SecurityUtils;
import com.weixiao.dto.ArticleDTO;
import com.weixiao.entity.Article;
import com.weixiao.mapper.ArticleMapper;
import com.weixiao.mapper.CategoryMapper;
import com.weixiao.service.ArticleService;
import com.weixiao.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文章服务实现类
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional
    public ArticleVO createArticle(ArticleDTO articleDTO) {
        Article article = convertToEntity(articleDTO);
        article.setAuthorId(SecurityUtils.getUserId());
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        article.setCreatedBy(SecurityUtils.getUserId());
        article.setUpdatedBy(SecurityUtils.getUserId());
        articleMapper.insert(article);
        return convertToVO(article);
    }

    @Override
    public ArticleVO getArticleById(Long id) {
        // 增加阅读量
        articleMapper.increaseReadCount(id);
        Article article = articleMapper.selectById(id);
        return article != null ? convertToVO(article) : null;
    }

    @Override
    public PageBean<ArticleVO> getArticleList(int pageNum, int pageSize, String title, String status, Long categoryId) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.selectArticleList(title, status, categoryId);
        Page<Article> page = (Page<Article>) articles;
        List<ArticleVO> articleVOS = articles.stream().map(this::convertToVO).collect(Collectors.toList());
        return new PageBean<>(page.getTotal(), articleVOS);
    }

    @Override
    @Transactional
    public ArticleVO updateArticle(Long id, ArticleDTO articleDTO) {
        Article existingArticle = articleMapper.selectById(id);
        if (existingArticle == null) {
            return null;
        }

        // 校验权限：确保当前用户是文章作者
        Long currentUserId = SecurityUtils.getUserId();
        if (!existingArticle.getAuthorId().equals(currentUserId)) {
            throw new GlobalException(DataResponseCode.FORBIDDEN,"无权修改此文章");
        }

        // 构建更新对象，只更新非空字段
        Article articleToUpdate = new Article();
        articleToUpdate.setId(id);
        articleToUpdate.setTitle(articleDTO.getTitle());
        articleToUpdate.setContent(articleDTO.getContent());
        articleToUpdate.setAbstractContent(articleDTO.getAbstractContent());
        articleToUpdate.setCategoryId(articleDTO.getCategoryId());
        articleToUpdate.setLabels(articleDTO.getLabels());
        articleToUpdate.setStatus(articleDTO.getStatus());
        articleToUpdate.setIsTop(articleDTO.getIsTop());
        articleToUpdate.setCover(articleDTO.getCover());
        articleToUpdate.setUpdatedBy(SecurityUtils.getUserId());
        // updateTime 在 Mapper 中使用 NOW() 更新，这里不需要设置

        articleMapper.update(articleToUpdate);

        // 返回更新后的文章详情
        return getArticleById(id);
    }

    @Override
    public boolean deleteArticle(Long id) {
        // 逻辑删除：更新状态为 DELETE
        return articleMapper.deleteById(id) > 0;
    }

    @Override
    public List<ArticleVO> getTopArticles() {
        List<Article> articles = articleMapper.selectTopArticles();
        return articles.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<ArticleVO> getArticlesByCategory(Long categoryId) {
        List<Article> articles = articleMapper.selectByCategoryId(categoryId);
        return articles.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    /**
     * 将DTO转换为实体
     */
    private Article convertToEntity(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setAbstractContent(articleDTO.getAbstractContent());
        article.setCategoryId(articleDTO.getCategoryId());
        article.setLabels(articleDTO.getLabels());
        article.setStatus(articleDTO.getStatus());
        article.setIsTop(articleDTO.getIsTop());
        article.setCover(articleDTO.getCover());
        return article;
    }

    /**
     * 将实体转换为VO
     */
    private ArticleVO convertToVO(Article article) {
        ArticleVO vo = new ArticleVO();
        vo.setId(article.getId());
        vo.setTitle(article.getTitle());
        vo.setContent(article.getContent());
        vo.setAbstractContent(article.getAbstractContent());
        vo.setCategoryId(article.getCategoryId());
        vo.setCategoryName(categoryMapper.selectById(article.getCategoryId()).getName());
        vo.setLabels(article.getLabels());
        vo.setStatus(article.getStatus());
        vo.setIsTop(article.getIsTop());
        vo.setCover(article.getCover());
        vo.setCreateTime(article.getCreateTime());
        vo.setUpdateTime(article.getUpdateTime());
        vo.setCreatedBy(article.getCreatedBy());
        vo.setUpdatedBy(article.getUpdatedBy());
        return vo;
    }


    @Override
    public boolean increaseReadCount(Long id) {
        return articleMapper.increaseReadCount(id) > 0;
    }
}
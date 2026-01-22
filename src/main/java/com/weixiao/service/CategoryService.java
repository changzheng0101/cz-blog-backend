package com.weixiao.service;

import com.weixiao.dto.CategoryDTO;
import com.weixiao.vo.CategoryVO;

import java.util.List;

/**
 * @author weixiao
 * @description 文章分类Service接口
 */
public interface CategoryService {
    /**
     * 新增分类
     *
     * @param categoryDTO 分类DTO
     * @return 分类VO
     */
    CategoryVO createCategory(CategoryDTO categoryDTO);

    /**
     * 删除分类
     *
     * @param id 分类ID
     */
    void deleteCategory(Long id);

    /**
     * 更新分类
     *
     * @param id 分类ID
     * @param categoryDTO 分类DTO
     */
    void updateCategory(Long id, CategoryDTO categoryDTO);

    /**
     * 根据ID查询分类
     *
     * @param id 分类ID
     * @return 分类VO
     */
    CategoryVO getCategoryById(Long id);

    /**
     * 查询所有分类
     *
     * @return 分类VO列表
     */
    List<CategoryVO> getAllCategories();
}
package com.weixiao.service.impl;

import com.weixiao.common.exception.GlobalException;
import com.weixiao.common.result.DataResponseCode;
import com.weixiao.common.utils.SecurityUtils;
import com.weixiao.dto.CategoryDTO;
import com.weixiao.entity.Category;
import com.weixiao.mapper.CategoryMapper;
import com.weixiao.service.CategoryService;
import com.weixiao.vo.CategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author weixiao
 * @description 文章分类Service实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreatedBy(SecurityUtils.getUserId());
        category.setUpdatedBy(SecurityUtils.getUserId());
        categoryMapper.insert(category);
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category, categoryVO);
        return categoryVO;
    }

    @Override
    public void deleteCategory(Long id) {
        if (categoryMapper.selectById(id) == null) {
            throw new GlobalException(DataResponseCode.BUSINESS_ERROR, "分类不存在");
        }
        categoryMapper.deleteById(id);
    }

    @Override
    public void updateCategory(Long id, CategoryDTO categoryDTO) {
        if (categoryMapper.selectById(id) == null) {
            throw new GlobalException(DataResponseCode.BUSINESS_ERROR, "分类不存在");
        }
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setId(id);
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdatedBy(SecurityUtils.getUserId());
        categoryMapper.update(category);
    }

    @Override
    public CategoryVO getCategoryById(Long id) {
        Category category = categoryMapper.selectById(id);
        if (category == null) {
            throw new GlobalException(DataResponseCode.BUSINESS_ERROR, "分类不存在");
        }
        CategoryVO categoryVO = new CategoryVO();
        BeanUtils.copyProperties(category, categoryVO);
        return categoryVO;
    }

    @Override
    public List<CategoryVO> getAllCategories() {
        List<Category> categories = categoryMapper.selectAll();
        return categories.stream().map(category -> {
            CategoryVO categoryVO = new CategoryVO();
            BeanUtils.copyProperties(category, categoryVO);
            return categoryVO;
        }).collect(Collectors.toList());
    }
}
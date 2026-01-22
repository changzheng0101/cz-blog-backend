package com.weixiao.controller;

import com.weixiao.common.result.DataResponse;
import com.weixiao.dto.CategoryDTO;
import com.weixiao.service.CategoryService;
import com.weixiao.vo.CategoryVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理控制器
 *
 * @author weixiao
 * @version 1.0
 * @since 2026-01-22
 */
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 创建分类
     *
     * @param categoryDTO 分类信息
     * @return 创建的分类信息
     */
    @PostMapping
    public DataResponse<CategoryVO> createCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        CategoryVO categoryVO = categoryService.createCategory(categoryDTO);
        return DataResponse.success(categoryVO);
    }

    /**
     * 删除分类
     *
     * @param id 分类ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public DataResponse<Boolean> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return DataResponse.success(true);
    }

    /**
     * 更新分类
     *
     * @param id          分类ID
     * @param categoryDTO 更新的分类信息
     * @return 更新结果
     */
    @PutMapping("/{id}")
    public DataResponse<Boolean> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDTO categoryDTO) {
        categoryService.updateCategory(id, categoryDTO);
        return DataResponse.success(true);
    }

    /**
     * 根据ID获取分类
     *
     * @param id 分类ID
     * @return 分类信息
     */
    @GetMapping("/{id}")
    public DataResponse<CategoryVO> getCategoryById(@PathVariable Long id) {
        CategoryVO categoryVO = categoryService.getCategoryById(id);
        return DataResponse.success(categoryVO);
    }

    /**
     * 获取所有分类
     *
     * @return 分类列表
     */
    @GetMapping
    public DataResponse<List<CategoryVO>> getAllCategories() {
        List<CategoryVO> categories = categoryService.getAllCategories();
        return DataResponse.success(categories);
    }
}
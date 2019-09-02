package com.leyou.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.mapper.BrandMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;


import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;


    public PageResult<Brand> pageQuery(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //select * from tb_brand where limit 1,5  name like "%华为%" orderby id desc
        //select count(*) from tb_brand where  name like "%华为%"
        //开启分页查询
        PageHelper.startPage(page,rows); // limit 1,5
        //通过什么查询
        Example example = new Example(Brand.class);
        if (StringUtils.isNoneBlank(key)){
            //创建一个查询条件的构造对象
            Example.Criteria criteria = example.createCriteria();
            // name like "%华为%"
            criteria.andLike("name","%key%");

        }

        if(StringUtils.isNoneBlank(sortBy)){
            // order by id desc
            example.setOrderByClause(sortBy+(desc?" desc":" asc"));//要空格不然iddesc会拼接在一起

        }
        Page<Brand> brandPage = (Page<Brand>) brandMapper.selectByExample(example);
        return new PageResult<Brand>(brandPage.getTotal(),new Long(brandPage.getPages()),brandPage);
    }

    @Transactional
    public void addBrand(Brand brand, List<Long> cids) {
        brandMapper.insertSelective(brand);
        //3,120

        cids.forEach(cid->{
            brandMapper.insertBrandCategory(brand.getId(),cid);
        });
    }

    public Brand queryBrandById(Long brandId) {

        return brandMapper.selectByPrimaryKey(brandId);
    }

    public List<Brand> queryBrandByCategory(Long cid) {
        return brandMapper.queryBrandByCategory(cid);
    }
}

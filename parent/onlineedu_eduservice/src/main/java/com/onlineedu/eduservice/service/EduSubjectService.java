package com.onlineedu.eduservice.service;

import com.onlineedu.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.onlineedu.eduservice.vo.OneLevelSubjectDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Hzhero
 * @since 2019-09-17
 */
public interface EduSubjectService extends IService<EduSubject> {

    List<String> importData(MultipartFile file);
    //按照要求 返回所有一级分类
    List<OneLevelSubjectDTO> getAllSubject();

    boolean deleteSubjectById(String id);

    boolean addOneLevelSubject(EduSubject eduSubject);

    boolean addTwoLevelSubject(EduSubject eduSubject);
}

package com.lyuan.admin.comment.entity;

import com.lyuan.common.entity.BaseListDTO;
import lombok.Data;

/**
 * @Description:
 * @Author: Zhuo Linbo
 * @Date: 2020/3/24 13:29
 */
@Data
public class CommentListDTO extends BaseListDTO {

    private Integer basecourseId;

    /**
     * 0：全部 1：好评 2：中评 3：差评
     */
    private Integer grade;
}

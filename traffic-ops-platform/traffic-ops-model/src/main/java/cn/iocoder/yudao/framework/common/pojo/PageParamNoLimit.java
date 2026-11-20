package cn.iocoder.yudao.framework.common.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Schema(description="分页参数-不限制页数")
public class PageParamNoLimit extends PageParam {

    private static final Integer HD_PAGE_SIZE = 10;

    @Schema(description = "每页条数", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为 1")
    private Integer pageSize = HD_PAGE_SIZE;

    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    @Override
    public PageParam setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
}

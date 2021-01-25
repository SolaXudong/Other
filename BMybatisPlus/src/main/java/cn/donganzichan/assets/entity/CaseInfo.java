package cn.donganzichan.assets.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 案件表
 * </p>
 *
 * @author XuDong
 * @since 2021-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CaseInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "case_id", type = IdType.AUTO)
    private Integer caseId;

    /**
     * 案件编号
     */
    private String caseNo;

    /**
     * 产品ID
     */
    private Integer productId;

    /**
     * 入库批次ID
     */
    private Integer batchId;

    /**
     * 债权方ID
     */
    private Integer creditorId;

    /**
     * 案人ID
     */
    private Integer caseUserId;

    /**
     * 案人姓名
     */
    private String userName;

    private String userPhone;

    /**
     * 身份证号
     */
    private String idno;

    /**
     * 分库记录ID
     */
    private Integer distLogId;

    /**
     * 分库时间
     */
    private Date distTime;

    /**
     * 所属分库ID(dict_item.item_id)
     */
    private Integer distId;

    /**
     * 委案记录ID
     */
    private Integer entrustLogId;

    /**
     * 委案时间
     */
    private Date entrustTime;

    /**
     * 所属机构ID
     */
    private Integer orgId;

    /**
     * 分案记录ID
     */
    private Integer allotLogId;

    /**
     * 分案时间
     */
    private Date allotTime;

    /**
     * 所属机构员工ID(sys_user.user_id)
     */
    private Integer cpeId;

    /**
     * 案件状态：1-正常，10-关闭|已结清，11-关闭|特殊原因， 12-关闭|已核销，20-暂停|特殊，25-暂停|投诉
     */
    private Integer caseStatus;

    /**
     * 留案ID（对应flow_apply_retain.retain_id）
     */
    private Integer retainId;

    /**
     * 债权转让金额
     */
    private BigDecimal transAmount;

    /**
     * 处置金额
     */
    private BigDecimal handleAmount;

    /**
     * 累计还款入账金额
     */
    private BigDecimal totalRefundAmount;

    /**
     * 累计减免金额
     */
    private BigDecimal totalReductionAmount;

    /**
     * 剩余金额(不能为负，多收展示为0.00)
     */
    private BigDecimal residueAmount;

    /**
     * 当前委案金额
     */
    private BigDecimal entrustAmount;

    /**
     * 累计红包减免
     */
    private BigDecimal totalCouponAmount;

    /**
     * 累计积分减免
     */
    private BigDecimal totalIntegralAmount;

    /**
     * 创建人ID
     */
    private Integer createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人ID
     */
    private Integer updateId;

    /**
     * 更新时间
     */
    private Date updateTime;


}

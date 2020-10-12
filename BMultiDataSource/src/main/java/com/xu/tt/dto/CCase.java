package com.xu.tt.dto;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "c_case")
public class CCase {
	/**
	 * 序列号
	 */
	@Id
	@Column(name = "case_no")
	private String caseNo;

	/**
	 * 批次号
	 */
//	@Column(name = "batch_no")
//	private String batchNo;

	/**
	 * 案件分配ID
	 */
//	@Column(name = "case_org_id")
//	private Integer caseOrgId;

	/**
	 * 催收机构ID
	 */
//	@Column(name = "org_id")
//	private Integer orgId;

	/**
	 * 机构催收员
	 */
//	@Column(name = "org_user_id")
//	private Integer orgUserId;

	/**
	 * 产品; 对应d_option中的b_product
	 */
	@Column(name = "product_v")
	private String productV;

	/**
	 * 案人姓名
	 */
	@Column(name = "u_name")
	private String uName;

	/**
	 * 案件人手机号
	 */
	@Column(name = "u_phone")
	private String uPhone;

	/**
	 * 案人身份证号
	 */
	@Column(name = "u_idno")
	private String uIdno;

	/**
	 * 委案日期
	 */
//	@Column(name = "ct_entrust_dt")
//	private Date ctEntrustDt;
//
//	/**
//	 * 委案金额
//	 */
//	@Column(name = "ct_entrust_amount")
//	private BigDecimal ctEntrustAmount;
//
//	/**
//	 * 留案申请ID
//	 */
//	@Column(name = "retain_id")
//	private Integer retainId;
//
//	/**
//	 * 留案状态
//	 */
//	@Column(name = "retain_state")
//	private Integer retainState;
//
//	/**
//	 * 已回收金额
//	 */
//	@Column(name = "ct_payback_amount")
//	private BigDecimal ctPaybackAmount;
//
//	/**
//	 * 剩余金额; 不能为负，多收展示为0.00
//	 */
//	@Column(name = "ct_residue_amount")
//	private BigDecimal ctResidueAmount;
//
//	/**
//	 * 减免金额
//	 */
//	@Column(name = "ct_reduction_money")
//	private BigDecimal ctReductionMoney;
//
//	/**
//	 * 历史回收总额(不包含当前已回收金额)
//	 */
//	@Column(name = "payback_amount")
//	private BigDecimal paybackAmount;
//
//	/**
//	 * 历史减免总金额
//	 */
//	@Column(name = "reduction_amount")
//	private BigDecimal reductionAmount;
//
//	/**
//	 * 派案时间
//	 */
//	@Column(name = "case_dt")
//	private Date caseDt;
//
//	/**
//	 * 案件状态 1正常 10关闭/已结清 15关闭/特殊原因 20暂停/投诉 25暂停/特殊原因
//	 */
//	@Column(name = "case_status")
//	private Integer caseStatus;
//
//	/**
//	 * 状态时间
//	 */
//	@Column(name = "case_status_dt")
//	private Date caseStatusDt;
//
//	/**
//	 * 特殊状态备注
//	 */
//	@Column(name = "case_status_ext")
//	private String caseStatusExt;
//
//	/**
//	 * 最近一条催收记录ID
//	 */
//	@Column(name = "call_log_id")
//	private Integer callLogId;
//
//	/**
//	 * 催收状态
//	 */
//	@Column(name = "call_status_text")
//	private String callStatusText;
//
//	/**
//	 * 版本号(分案给催收公司)
//	 */
//	@Column(name = "allot_version")
//	private Integer allotVersion;
//
//	@Column(name = "ins_id")
//	private Integer insId;
//
//	@Column(name = "ins_dt")
//	private Date insDt;
//
//	@Column(name = "upd_id")
//	private Integer updId;
//
//	@Column(name = "upd_dt")
//	private Date updDt;
//
//	/**
//	 * 转让金额
//	 */
//	@Column(name = "loan_trans_amount")
//	private BigDecimal loanTransAmount;
//
//	@Column(name = "id_no_reverse")
//	private String idNoReverse;
//
//	/**
//	 * 委案批次
//	 */
//	@Column(name = "entrust_batch")
//	private String entrustBatch;
//
//	/**
//	 * 分案批次
//	 */
//	@Column(name = "allot_batch")
//	private String allotBatch;
//
//	/**
//	 * 特殊备注状态0-不可联 1-可联
//	 */
//	@Column(name = "sp_mark_status")
//	private Integer spMarkStatus;
//
//	/**
//	 * 特殊备注
//	 */
//	@Column(name = "sp_mark")
//	private String spMark;
//
//	/**
//	 * 还款编码
//	 */
//	@Column(name = "refund_code")
//	private String refundCode;
//
//	/**
//	 * 0本人可联、紧急联系人可联、普通联系人可联
//	 * 1本人可联、紧急联系人可联（普通联系人可联状态未知或全不可联）2紧急联系人可联、普通联系人可联（本人可联状态未知或全不可联）
//	 * 3本人可联、普通联系人可联（紧急联系人可联状态未知或全不可联）4仅本人可联 5仅紧急联系人可联 6仅普通联系人可联
//	 */
//	@Column(name = "contact_state")
//	private Integer contactState;
//
//	/**
//	 * {“integralAmount”:”抵用金”,”couponAmount”:”红包”}
//	 */
//	@Column(name = "amount_ext")
//	private String amountExt;

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
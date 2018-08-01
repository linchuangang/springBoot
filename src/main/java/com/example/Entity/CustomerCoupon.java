package com.example.Entity;




import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
*
* @ClassName: CustomerCoupon
* @Description: 映射p_customer_coupon表
* @author: carme-generator
*
*/
@Entity
@Table(name = "p_customer_coupon")
public class CustomerCoupon implements Serializable {

    private static final long serialVersionUID = 5454155825314635342L;
    /**
    * id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long              id;

    /**
    * 优惠券名称
    */
    @NotNull
    @Column(name = "coupon_name")
    private String            couponName;

    /**
    * 用户id
    */
    @Column(name = "p_customer_id")
    private Long              customerId;

    /**
    * 优惠券id
    */
    @NotNull
    @Column(name = "c_coupon_id")
    private Long              couponId;

    /**
    * 所属集团id
    */
    @Column(name = "p_seller_id")
    private Long              sellerId;

    /**
    * 用于优惠劵名称搜索
    */
    @Column(name = "name_search")
    private String            nameSearch;

    /**
    * 优惠券编号
    */
    @Column(name = "coupon_no")
    private String            couponNo;

    /**
    * 优惠券内容
    */
    @Column(name = "content")
    private String            content;

    /**
    * 用于优惠券内容搜索
    */
    @Column(name = "content_search")
    private String            contentSearch;

    /**
    * 绑定卡id
    */
    @Column(name = "c_card_id")
    private Long              cardId;

    /**
    * 绑定项目id
    */
    @Column(name = "c_item_id")
    private Long              itemId;

    /**
    * 绑定的商品id
    */
    @Column(name = "i_material_id")
    private Long              materialId;

    /**
    * 折扣率
    */
    @Column(name = "discount_ratio")
    private Integer discountRatio;

    /**
    * 发放方式 1：自动发放 2：线下发放 3：主动领取
    */
    @NotNull
    @Column(name = "issue_type")
    private Integer           issueType;

    /**
    * 优惠券类型 1：代金劵 2：免费劵 3：折扣劵
    */
    @Column(name = "type")
    private Integer           type;

    /**
    * 优惠券发放开始时间
    */
    @Column(name = "issue_begin_date")
    private Date    issueBeginDate;

    /**
    * 优惠券发放结束时间
    */
    @Column(name = "issue_end_date")
    private Date    issueEndDate;

    /**
    * 面额（单位：分）
    */
    @Column(name = "price")
    private Long              price;

    /**
    * 是否删除（0：否 1：是）
    */
    @Column(name = "is_delete")
    private Integer           isDelete;

    /**
    * 创建时间
    */
    @NotNull
    @Column(name = "created_at")
    private Date    createdAt;

    /**
    * 创建人
    */
    @NotNull
    @Column(name = "created_by")
    private String            createdBy;

    /**
    * 更新时间
    */
    @Column(name = "changed_at")
    private Date    changedAt;

    /**
    * 更新人
    */
    @Column(name = "changed_by")
    private String            changedBy;

    /**
    * 优惠券批次
    */
    @Column(name = "c_coupon_batch_id")
    private Long              couponBatchId;

    /**
    * 要求消费金额
    */
    @Column(name = "use_premise")
    private Long              usePremise;

    /**
    * 使用有效期类型（0：固定时间；1：领劵后天）
    */
    @Column(name = "expiry_date_type")
    private Integer           expiryDateType;

    /**
    * 固定时间：开始
    */
    @Column(name = "expiry_date_start")
    private Date    expiryDateStart;

    /**
    * 固定时间：结束
    */
    @Column(name = "expiry_date_end")
    private Date    expiryDateEnd;

    /**
    * 领劵后第几天
    */
    @Column(name = "expiry_date_day")
    private Integer           expiryDateDay;

    /**
    * 活动支持门店
    */
    @Column(name = "apply_store")
    private String            applyStore;

    /**
    * 状态： 0-待使用中的不可使用(未到使用时间) 1-待使用(可使用) 2-已使用 3-已过期    供页面展示
    */
    @Transient
    private Integer           status;

    /**
    * 最高抵扣金额
    */
    @Column(name = "discount_max_price")
    private Long              discountMaxPrice;

    /**
    * 优惠券发放状态：0，成功发放;1-草稿状态（未成功发放）
    */
    @Column(name = "draft")
    private Integer           draft;

    @Column(name = "is_used")
    private Integer           isUsed;

    /**
    * 优惠券适用类型：1-商品;2-次卡;3-不限;4-服务项目
    */
    @Column(name = "coupon_apply_type")
    private Integer           couponApplyType;

    /**
    * 优惠券项目类型（1-洗车类项目，2-美容类项目）
    */
    @Column(name = "item_type")
    private Integer           itemType;

    /**
     * 优惠券渠道（1-站内，2-站外）
     */
    @Column(name = "channel")
    private Integer           channel;

    /**
     * 用户userId(针对p_customer不存在用户)
     */
    @Column(name = "p_user_id")
    private Long              userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentSearch() {
        return contentSearch;
    }

    public void setContentSearch(String contentSearch) {
        this.contentSearch = contentSearch;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public Integer getDiscountRatio() {
        return discountRatio;
    }

    public void setDiscountRatio(Integer discountRatio) {
        this.discountRatio = discountRatio;
    }

    public Integer getIssueType() {
        return issueType;
    }

    public void setIssueType(Integer issueType) {
        this.issueType = issueType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getIssueBeginDate() {
        return issueBeginDate;
    }

    public void setIssueBeginDate(Date issueBeginDate) {
        this.issueBeginDate = issueBeginDate;
    }

    public Date getIssueEndDate() {
        return issueEndDate;
    }

    public void setIssueEndDate(Date issueEndDate) {
        this.issueEndDate = issueEndDate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Date changedAt) {
        this.changedAt = changedAt;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public Long getCouponBatchId() {
        return couponBatchId;
    }

    public void setCouponBatchId(Long couponBatchId) {
        this.couponBatchId = couponBatchId;
    }

    public Long getUsePremise() {
        return usePremise;
    }

    public void setUsePremise(Long usePremise) {
        this.usePremise = usePremise;
    }

    public Integer getExpiryDateType() {
        return expiryDateType;
    }

    public void setExpiryDateType(Integer expiryDateType) {
        this.expiryDateType = expiryDateType;
    }

    public Date getExpiryDateStart() {
        return expiryDateStart;
    }

    public void setExpiryDateStart(Date expiryDateStart) {
        this.expiryDateStart = expiryDateStart;
    }

    public Date getExpiryDateEnd() {
        return expiryDateEnd;
    }

    public void setExpiryDateEnd(Date expiryDateEnd) {
        this.expiryDateEnd = expiryDateEnd;
    }

    public Integer getExpiryDateDay() {
        return expiryDateDay;
    }

    public void setExpiryDateDay(Integer expiryDateDay) {
        this.expiryDateDay = expiryDateDay;
    }

    public String getApplyStore() {
        return applyStore;
    }

    public void setApplyStore(String applyStore) {
        this.applyStore = applyStore;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDiscountMaxPrice() {
        return discountMaxPrice;
    }

    public void setDiscountMaxPrice(Long discountMaxPrice) {
        this.discountMaxPrice = discountMaxPrice;
    }

    public Integer getDraft() {
        return draft;
    }

    public void setDraft(Integer draft) {
        this.draft = draft;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public Integer getCouponApplyType() {
        return couponApplyType;
    }

    public void setCouponApplyType(Integer couponApplyType) {
        this.couponApplyType = couponApplyType;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
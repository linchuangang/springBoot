package com.example.Entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @ClassName: Customer
 * @Description: 映射p_customer表
 * @author: huaikang
 *
 */
@Entity
@Table(name = "p_customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 5454155825314635342L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long              id;

    /**
     * 客户编号
     */
    @Column(name = "customer_no")
    private java.lang.String  customerNo;

    /**
     * 客户姓名
     */
    @Column(name = "name")
    private java.lang.String  name;

    /**
     * 客户姓名（中文，首字母简写，全拼）
     */
    @Column(name = "name_search")
    private String            nameSearch;

    /**
     * 所属集团
     */
    @Column(name = "belong_seller_id")
    private Long              belongSellerId;

    /**
     * 所属集团
     */
    @Column(name = "belong_seller_name")
    private String            belongSellerName;

    /**
     * 联系电话
     */
    @Column(name = "telephone_number")
    private java.lang.String  telephoneNumber;

    /**
     * 会员id
     */
    @Column(name = "p_user_id")
    private java.lang.Long    userId;

    /**
     * 会员卡种类
     */
    @Column(name = "member_card_type")
    private String            memberCardType;

    /**
     * 会员卡到期时间
     */
    @Column(name = "member_card_expire_date")
    private java.util.Date    memberCardExpireDate;

    /**
     * 最近到店时间
     */
    @Column(name = "late_arrivals_shop_time")
    private java.util.Date    lateArrivalsShopTime;

    /**
     * 首次到店时间
     */
    @Column(name = "first_arrivals_shop_time")
    private java.util.Date    firstArrivalsShopTime;

    /**
     * 消费次数
     */
    @Column(name = "consumer_times")
    private java.lang.Integer consumerTimes;

    /**
     * 累计消费金额
     */
    @Column(name = "cumulative_consumer_amount")
    private Long              cumulativeConsumerAmount;

    /**
     * 是否是企业客户,0:否,1:是
     */
    @NotNull
    @Column(name = "is_company")
    private Integer           isCompany;

    /**
     * 是否会员,0:否,1:个人会员,2:企业会员
     */
    @Column(name = "is_member")
    private Integer           isMember;

    /**
     * 企业id
     */
    @Column(name = "company_id")
    private java.lang.Long    companyId;

    /**
     * 企业名称
     */
    @Column(name = "company_name")
    private java.lang.String  companyName;

    /**
     * 企业名称（中文，首字母简写，全拼）
     */
    @Column(name = "company_name_search")
    private java.lang.String  companyNameSearch;

    /**
     * 注册门店id
     */
    @Column(name = "register_store_id")
    private java.lang.Long    registerStoreId;

    /**
     * 注册门店名称
     */
    @Column(name = "register_store_name")
    private java.lang.String  registerStoreName;

    /**
     * 操作员工id
     */
    @NotNull
    @Column(name = "operations_staff_id")
    private java.lang.Long    operationsStaffId;

    /**
     * 操作员工姓名
     */
    @NotNull
    @Column(name = "operations_staff_name")
    private java.lang.String  operationsStaffName;

    /**
     * 客户性别
     */
    @Column(name = "gender")
    private Integer           gender;

    /**
     * 客户年龄
     */
    @Column(name = "age")
    private java.lang.Integer age;

    /**
     * 客户生日
     */
    @Column(name = "birthday")
    private java.util.Date    birthday;

    /**
     * 客户身高
     */
    @Column(name = "height")
    private java.lang.Double  height;

    /**
     * 客户地址
     */
    @Column(name = "address")
    private java.lang.String  address;

    /**
     * 客户来源
     */
    @Column(name = "source")
    private java.lang.String  source;

    /**
     * 客户单位
     */
    @Column(name = "unit")
    private java.lang.String  unit;

    /**
     * 客户职业
     */
    @Column(name = "career")
    private java.lang.String  career;

    /**
     * 婚姻状况
     */
    @Column(name = "marital_status")
    private Integer           maritalStatus;

    /**
     * 是否有子女
     */
    @Column(name = "is_has_child")
    private Integer           isHasChild;

    /**
     * 子女数量
     */
    @Column(name = "child_quantity")
    private java.lang.Integer childQuantity;

    /**
     * 是否单身
     */
    @Column(name = "is_single")
    private Integer           isSingle;

    /**
     * 客户爱好
     */
    @Column(name = "intereste")
    private java.lang.String  intereste;

    /**
     * 电子邮箱
     */
    @Column(name = "email")
    private java.lang.String  email;

    /**
     * 身份证号
     */
    @Column(name = "id_card")
    private java.lang.String  idCard;

    /**
     * 备注
     */
    @Column(name = "remark")
    private java.lang.String  remark;

    /**
     * 用户权益
     */
    @Column(name = "member_droit")
    private String            memberDroit;

    /**
     * 成为会员的时间
     */
    @Column(name = "become_member_time")
    private Date              becomeMemberTime;

    /**
     * 是否删除,1:删除，0：未删除
     */
    @NotNull
    @Column(name = "is_delete")
    private Integer           isDelete;

    /**
     * 创建时间
     */
    @NotNull
    @Column(name = "created_at")
    private java.util.Date    createdAt;

    /**
     * 创建人
     */
    @NotNull
    @Column(name = "created_by")
    private java.lang.String  createdBy;

    /**
     * 修改时间
     */
    @Column(name = "changed_at")
    private java.util.Date    changedAt;

    /**
     * 修改人
     */
    @Column(name = "changed_by")
    private java.lang.String  changedBy;

    public java.lang.Long getId() {
        return id;
    }

    public void setId(java.lang.Long id) {
        this.id = id;
    }

    public java.lang.String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(java.lang.String customerNo) {
        this.customerNo = customerNo;
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public String getNameSearch() {
        return nameSearch;
    }

    public void setNameSearch(String nameSearch) {
        this.nameSearch = nameSearch;
    }

    public Long getBelongSellerId() {
        return belongSellerId;
    }

    public void setBelongSellerId(Long belongSellerId) {
        this.belongSellerId = belongSellerId;
    }

    public String getBelongSellerName() {
        return belongSellerName;
    }

    public void setBelongSellerName(String belongSellerName) {
        this.belongSellerName = belongSellerName;
    }

    public java.lang.String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(java.lang.String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public java.lang.Long getUserId() {
        return userId;
    }

    public void setUserId(java.lang.Long userId) {
        this.userId = userId;
    }

    public java.lang.Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(java.lang.Long companyId) {
        this.companyId = companyId;
    }

    public java.lang.String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(java.lang.String companyName) {
        this.companyName = companyName;
    }

    public java.lang.String getCompanyNameSearch() {
        return companyNameSearch;
    }

    public void setCompanyNameSearch(java.lang.String companyNameSearch) {
        this.companyNameSearch = companyNameSearch;
    }

    public String getMemberCardType() {
        return memberCardType;
    }

    public void setMemberCardType(String memberCardType) {
        this.memberCardType = memberCardType;
    }

    public java.util.Date getMemberCardExpireDate() {
        return memberCardExpireDate;
    }

    public void setMemberCardExpireDate(java.util.Date memberCardExpireDate) {
        this.memberCardExpireDate = memberCardExpireDate;
    }

    public java.util.Date getLateArrivalsShopTime() {
        return lateArrivalsShopTime;
    }

    public void setLateArrivalsShopTime(java.util.Date lateArrivalsShopTime) {
        this.lateArrivalsShopTime = lateArrivalsShopTime;
    }

    public java.util.Date getFirstArrivalsShopTime() {
        return firstArrivalsShopTime;
    }

    public void setFirstArrivalsShopTime(java.util.Date firstArrivalsShopTime) {
        this.firstArrivalsShopTime = firstArrivalsShopTime;
    }

    public java.lang.Integer getConsumerTimes() {
        return consumerTimes;
    }

    public void setConsumerTimes(java.lang.Integer consumerTimes) {
        this.consumerTimes = consumerTimes;
    }

    public Long getCumulativeConsumerAmount() {
        return cumulativeConsumerAmount;
    }

    public void setCumulativeConsumerAmount(Long cumulativeConsumerAmount) {
        this.cumulativeConsumerAmount = cumulativeConsumerAmount;
    }

    public Integer getIsMember() {
        return isMember;
    }

    public void setIsMember(Integer isMember) {
        this.isMember = isMember;
    }

    public java.lang.Long getRegisterStoreId() {
        return registerStoreId;
    }

    public void setRegisterStoreId(java.lang.Long registerStoreId) {
        this.registerStoreId = registerStoreId;
    }

    public java.lang.String getRegisterStoreName() {
        return registerStoreName;
    }

    public void setRegisterStoreName(java.lang.String registerStoreName) {
        this.registerStoreName = registerStoreName;
    }

    public java.lang.Long getOperationsStaffId() {
        return operationsStaffId;
    }

    public void setOperationsStaffId(java.lang.Long operationsStaffId) {
        this.operationsStaffId = operationsStaffId;
    }

    public java.lang.String getOperationsStaffName() {
        return operationsStaffName;
    }

    public void setOperationsStaffName(java.lang.String operationsStaffName) {
        this.operationsStaffName = operationsStaffName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public java.lang.Integer getAge() {
        return age;
    }

    public void setAge(java.lang.Integer age) {
        this.age = age;
    }

    public java.util.Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.util.Date birthday) {
        this.birthday = birthday;
    }

    public java.lang.Double getHeight() {
        return height;
    }

    public void setHeight(java.lang.Double height) {
        this.height = height;
    }

    public java.lang.String getAddress() {
        return address;
    }

    public void setAddress(java.lang.String address) {
        this.address = address;
    }

    public java.lang.String getSource() {
        return source;
    }

    public void setSource(java.lang.String source) {
        this.source = source;
    }

    public java.lang.String getUnit() {
        return unit;
    }

    public void setUnit(java.lang.String unit) {
        this.unit = unit;
    }

    public java.lang.String getCareer() {
        return career;
    }

    public void setCareer(java.lang.String career) {
        this.career = career;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Integer getIsHasChild() {
        return isHasChild;
    }

    public void setIsHasChild(Integer isHasChild) {
        this.isHasChild = isHasChild;
    }

    public java.lang.Integer getChildQuantity() {
        return childQuantity;
    }

    public void setChildQuantity(java.lang.Integer childQuantity) {
        this.childQuantity = childQuantity;
    }

    public Integer getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(Integer isSingle) {
        this.isSingle = isSingle;
    }

    public java.lang.String getIntereste() {
        return intereste;
    }

    public void setIntereste(java.lang.String intereste) {
        this.intereste = intereste;
    }

    public java.lang.String getEmail() {
        return email;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    public java.lang.String getIdCard() {
        return idCard;
    }

    public void setIdCard(java.lang.String idCard) {
        this.idCard = idCard;
    }

    public java.lang.String getRemark() {
        return remark;
    }

    public void setRemark(java.lang.String remark) {
        this.remark = remark;
    }

    public java.lang.String getMemberDroit() {
        return memberDroit;
    }

    public void setMemberDroit(java.lang.String memberDroit) {
        this.memberDroit = memberDroit;
    }

    public Date getBecomeMemberTime() {
        return becomeMemberTime;
    }

    public void setBecomeMemberTime(Date becomeMemberTime) {
        this.becomeMemberTime = becomeMemberTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = createdAt;
    }

    public java.lang.String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy) {
        this.createdBy = createdBy;
    }

    public java.util.Date getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(java.util.Date changedAt) {
        this.changedAt = changedAt;
    }

    public java.lang.String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(java.lang.String changedBy) {
        this.changedBy = changedBy;
    }

    public Integer getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(Integer isCompany) {
        this.isCompany = isCompany;
    }

}
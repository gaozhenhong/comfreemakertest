package com.wiwi.jsoil.cms.model;

import com.wiwi.jsoil.db.PageUtil;
import com.wiwi.jsoil.sys.model.Category;
import com.wiwi.jsoil.sys.model.User;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

public class ArticlesQ extends PageUtil
{
  private Long id;
  private User author;
  private String keywords;
  private String source;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date createDate;
  private String content;
  private String title;
  private String excerpt;
  private Integer status;
  private Integer commentStatus;

  @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date modified;
  private Category type;
  private String commentCount;
  private Integer hits;
  private Integer praise;
  private Integer istop;
  private Integer recommended;
  private Integer memberStatus;

  public String toWhereString()
  {
    this.parameterList.clear();

    String sqlStr = super.getOtherCondition();

    if ((this.id != null) && (this.id.longValue() != 0L)) {
      sqlStr = sqlStr + " AND id =?";
      this.parameterList.add(this.id);
    }

    if (this.author != null) {
      sqlStr = sqlStr + " AND authorId =?";
      this.parameterList.add(this.author.getId());
    }

    if ((this.keywords != null) && (!(this.keywords.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND keywords like ?";
      this.parameterList.add('%' + this.keywords + '%');
    }

    if ((this.source != null) && (!(this.source.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND source like ?";
      this.parameterList.add('%' + this.source + '%');
    }

    if (this.createDate != null) {
      sqlStr = sqlStr + " AND createDate =?";
      this.parameterList.add(this.createDate);
    }

    if ((this.content != null) && (!(this.content.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND content like ?";
      this.parameterList.add('%' + this.content + '%');
    }

    if ((this.title != null) && (!(this.title.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND title like ?";
      this.parameterList.add('%' + this.title + '%');
    }

    if ((this.excerpt != null) && (!(this.excerpt.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND excerpt like ?";
      this.parameterList.add('%' + this.excerpt + '%');
    }

    if (this.status != null) {
      sqlStr = sqlStr + " AND status =?";
      this.parameterList.add(this.status);
    }

    if ((this.commentStatus != null) && (this.commentStatus.intValue() != 0)) {
      sqlStr = sqlStr + " AND commentStatus =?";
      this.parameterList.add(this.commentStatus);
    }

    if (this.modified != null) {
      sqlStr = sqlStr + " AND modified =?";
      this.parameterList.add(this.modified);
    }

    if ((this.type != null) && (this.type.getId() != null)) {
      sqlStr = sqlStr + " AND typeId =?";
      this.parameterList.add(this.type.getId());
    }

    if ((this.commentCount != null) && (!(this.commentCount.equalsIgnoreCase("")))) {
      sqlStr = sqlStr + " AND commentCount like ?";
      this.parameterList.add('%' + this.commentCount + '%');
    }

    if ((this.hits != null) && (this.hits.intValue() != 0)) {
      sqlStr = sqlStr + " AND hits =?";
      this.parameterList.add(this.hits);
    }

    if ((this.praise != null) && (this.praise.intValue() != 0)) {
      sqlStr = sqlStr + " AND praise =?";
      this.parameterList.add(this.praise);
    }

    if ((this.istop != null) && (this.istop.intValue() != 0)) {
      sqlStr = sqlStr + " AND istop =?";
      this.parameterList.add(this.istop);
    }

    if ((this.recommended != null) && (this.recommended.intValue() != 0)) {
      sqlStr = sqlStr + " AND recommended =?";
      this.parameterList.add(this.recommended);
    }

    if ((this.memberStatus != null) && (this.memberStatus.intValue() != 0)) {
      sqlStr = sqlStr + " AND memberStatus =?";
      this.parameterList.add(this.memberStatus);
    }

    return sqlStr;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getAuthor() {
    return this.author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public String getKeywords() {
    return this.keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getSource() {
    return this.source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Date getCreateDate() {
    return this.createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getExcerpt() {
    return this.excerpt;
  }

  public void setExcerpt(String excerpt) {
    this.excerpt = excerpt;
  }

  public Integer getStatus() {
    return this.status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getCommentStatus() {
    return this.commentStatus;
  }

  public void setCommentStatus(Integer commentStatus) {
    this.commentStatus = commentStatus;
  }

  public Date getModified() {
    return this.modified;
  }

  public void setModified(Date modified) {
    this.modified = modified;
  }

  public Category getType() {
    return this.type;
  }

  public void setType(Category type) {
    this.type = type;
  }

  public String getCommentCount() {
    return this.commentCount;
  }

  public void setCommentCount(String commentCount) {
    this.commentCount = commentCount;
  }

  public Integer getHits() {
    return this.hits;
  }

  public void setHits(Integer hits) {
    this.hits = hits;
  }

  public Integer getPraise() {
    return this.praise;
  }

  public void setPraise(Integer praise) {
    this.praise = praise;
  }

  public Integer getIstop() {
    return this.istop;
  }

  public void setIstop(Integer istop) {
    this.istop = istop;
  }

  public Integer getRecommended() {
    return this.recommended;
  }

  public void setRecommended(Integer recommended) {
    this.recommended = recommended;
  }

  public Integer getMemberStatus() {
    return this.memberStatus;
  }

  public void setMemberStatus(Integer memberStatus) {
    this.memberStatus = memberStatus;
  }
}
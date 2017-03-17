package com.wiwi.jsoil.cms.model;

import com.wiwi.jsoil.cms.util.CmsConstants;
import com.wiwi.jsoil.sys.model.Category;
import com.wiwi.jsoil.sys.model.User;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class Articles
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

  public Articles()
  {
    this.istop = Integer.valueOf(0);
  }

  public Long getId()
  {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getKeywords() {
    return this.keywords;
  }

  public User getAuthor() {
    return this.author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public Category getType() {
    return this.type;
  }

  public void setType(Category type) {
    this.type = type;
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

  public Date getModified() {
    return this.modified;
  }

  public void setModified(Date modified) {
    this.modified = modified;
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

  public Date getCreateDate() {
    return this.createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Integer getCommentStatus() {
    return this.commentStatus;
  }

  public void setCommentStatus(Integer commentStatus) {
    this.commentStatus = commentStatus;
  }

  public String getCommentCount() {
    return this.commentCount;
  }

  public void setCommentCount(String commentCount) {
    this.commentCount = commentCount;
  }

  public Integer getMemberStatus() {
    return this.memberStatus;
  }

  public void setMemberStatus(Integer memberStatus) {
    this.memberStatus = memberStatus;
  }

  public String statusZh() {
    return CmsConstants.getStatusZh(this.status.intValue());
  }
}
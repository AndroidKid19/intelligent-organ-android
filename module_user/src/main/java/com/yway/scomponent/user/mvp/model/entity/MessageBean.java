package com.yway.scomponent.user.mvp.model.entity;

/**
 * @ProjectName: Android-Component-master
 * @Package: com.yway.scomponent.user.mvp.model.entity
 * @ClassName: MessageBean
 * @Description:
 * @Author: YIWUANYUAN
 * @CreateDate: 2020/7/21 13:39
 * @UpdateUser: YIWUANYUAN
 * @UpdateDate: 2020/7/21 13:39
 * @UpdateRemark: 更新说明：
 * @Version: 1.0
 */
public class MessageBean {

    /**
     * id : 1
     * title : 1
     * body : 111
     * type : 1
     * create_time : 2020-06-29 10:53:51
     * expire : null
     * is_show : 1
     * priority : 0
     * push : 0
     */

    private int id;
    private String title;
    private String body;
    private int type;
    private String create_time;
    private String expire;
    private int is_show;
    private int priority;
    private int push;
    private String content;

    public String getContent() {
        return content == null ? "" : content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body == null ? "" : body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreate_time() {
        return create_time == null ? "" : create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public int getIs_show() {
        return is_show;
    }

    public void setIs_show(int is_show) {
        this.is_show = is_show;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPush() {
        return push;
    }

    public void setPush(int push) {
        this.push = push;
    }
}

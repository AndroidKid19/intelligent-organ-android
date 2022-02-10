package com.yway.scomponent.organ.mvp.model.entity;

import java.util.List;

/**
 * @ProjectName: intelligent-organ-android
 * @Package: com.yway.scomponent.organ.mvp.model.entity
 * @ClassName: HomeMetingBean
 * @Description:
 * @Author: Yuan
 * @CreateDate: 2022/2/10 10:15
 * @UpdateUser: 更新者
 * @UpdateDate: 2022/2/10 10:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HomeMetingBean {
    /**
     * 待开会议list
     */
    private List<ConferenceBean> todoMeetingPersonnelRspBOList;

    /**
     * 正在会议list
     */
    private List<ConferenceBean> inMeetingPersonnelRspBOList;

    public List<ConferenceBean> getTodoMeetingPersonnelRspBOList() {
        return todoMeetingPersonnelRspBOList;
    }

    public void setTodoMeetingPersonnelRspBOList(List<ConferenceBean> todoMeetingPersonnelRspBOList) {
        this.todoMeetingPersonnelRspBOList = todoMeetingPersonnelRspBOList;
    }

    public List<ConferenceBean> getInMeetingPersonnelRspBOList() {
        return inMeetingPersonnelRspBOList;
    }

    public void setInMeetingPersonnelRspBOList(List<ConferenceBean> inMeetingPersonnelRspBOList) {
        this.inMeetingPersonnelRspBOList = inMeetingPersonnelRspBOList;
    }
}

/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yway.scomponent.organ.mvp.model.api;

/**
 * ================================================
 * 存放一些与 API 有关的东西,如请求地址,请求码等
 * <p>
 * Created by JessYan on 08/05/2016 11:25

 * ================================================
 */
public interface Api {
    String ZHIHU_DOMAIN = "http://news-at.zhihu.com";
    String HOME_DOMAIN_NAME = "wheelapi";
    String API_HOME = "/";

    //请求成功
    int REQUEST_SUCCESS = 1000;

    /**
     *上传图片
     * */
    String API_HOME_RESOURCE_UPLOAD = "resource_upload_app";

    //上传文件
    String API_HOME_UPLOAD_FILE = "/organ/fileUpload/upload";


    //通讯录查询
    String API_QUERYALLSYSORGANDSYSUSERLIST = "/organ/org/queryAllSysOrgAndSysUserList";


    //字典查询
    String API_QUERYSYSBYDICTCLASSIFY = "/organ/sysDict/querySysByDictClassify";


    //会议室列表
    String API_QUERYMEETINGROOMPAGELIST = "/organ/meetingRoom/queryMeetingRoomPageList";


    //会议预约接口 及 存入草稿箱接口
    String API_CREATEMEETINGRECORD = "/organ/meetingRecord/createMeetingRecord";


    //会议详情
    String API_QUERYBYMEETINGRECORDDETAILS = "/organ/meetingRecord/queryByMeetingRecordDetails";

}

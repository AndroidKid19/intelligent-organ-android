/*
 * Copyright 2018 JessYan
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
package com.yway.scomponent.commonsdk.core;

/**
 * ================================================
 * CommonSDK 的 Constants 可以定义公用的常量, 比如关于业务的常量或者正则表达式, 每个组件的 Constants 可以定义组件自己的私有常量
 * <p>
 * ================================================
 */
public interface Constants {
    //电话号码正则
    String PHONE_REGULAR = "^1[3-9]\\d{9}$";

    //分页条数
    int pageSize = 10;

    //用户信息
    String APP_USER_INFO = "app_user_info";

    //用户组织机构
    String APP_USER_ORGAN = "app_user_organ";

    //用户id
    String APP_USER_ID = "doctor_id";

    //用户token
    String USER_TOKEN = "token";

    //用户列表
    String APP_USER_LIST = "app_user_list";
    /**********************/

    //字典
    String APP_COMMON_DICT = "common_dict";


    int RESULT_COMMON_CODE = 10001;

    int RESULT_CHOOSE_COMPANY_CODE = 10002;

    int RESULT_CHOOSE_FILE_CODE = 10003;

    /**********************/

    /**性别 字典**/
    String SYS_DICT_SEX = "SYS_DICT_SEX";

    /**设备 字典**/
    String SYS_DICT_MEETING_DEVICE = "MEETING_DEVICE";


    /**会议设置 字典**/
    String SYS_DICT_MEETING_SET = "MEETING_SET";


    /**会议报道时间 字典**/
    String SYS_DICT_MEETING_CHECK_IN_TIME = "MEETING_CHECK_IN_TIME";


    /**职务 字典**/
    String SYS_DICT_POSITION = "POSITION";
}

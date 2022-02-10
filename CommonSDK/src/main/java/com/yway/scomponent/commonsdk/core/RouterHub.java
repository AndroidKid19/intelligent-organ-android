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
 * RouterHub 用来定义路由器的路由地址, 以组件名作为前缀, 对每个组件的路由地址进行分组, 可以统一查看和管理所有分组的路由地址
 * <p>
 * RouterHub 存在于基础库, 可以被看作是所有组件都需要遵守的通讯协议, 里面不仅可以放路由地址常量, 还可以放跨组件传递数据时命名的各种 Key 值
 * 再配以适当注释, 任何组件开发人员不需要事先沟通只要依赖了这个协议, 就知道了各自该怎样协同工作, 既提高了效率又降低了出错风险, 约定的东西自然要比口头上说强
 * <p>
 * 如果您觉得把每个路由地址都写在基础库的 RouterHub 中, 太麻烦了, 也可以在每个组件内部建立一个私有 RouterHub, 将不需要跨组件的
 * 路由地址放入私有 RouterHub 中管理, 只将需要跨组件的路由地址放入基础库的公有 RouterHub 中管理, 如果您不需要集中管理所有路由地址的话
 * 这也是比较推荐的一种方式
 * <p>
 * 路由地址的命名规则为 组件名 + 页面名, 如订单组件的订单详情页的路由地址可以命名为 "/order/OrderDetailActivity"
 * <p>
 * ARouter 将路由地址中第一个 '/' 后面的字符称为 Group, 比如上面的示例路由地址中 order 就是 Group, 以 order 开头的地址都被分配该 Group 下
 * 切记不同的组件中不能出现名称一样的 Group, 否则会发生该 Group 下的部分路由地址找不到的情况!!!
 * 所以每个组件使用自己的组件名作为 Group 是比较好的选择, 毕竟组件不会重名
 *
 * @see <a href="https://github.com/JessYanCoding/ArmsComponent/wiki#3.4">RouterHub wiki 官方文档</a>
 * Created by JessYan on 30/03/2018 18:07
 * ================================================
 */
public interface RouterHub {
    /**
     * 组名
     */
    String APP = "/app";//宿主 App 组件
    String HOME = "/home";//首页组件
    String PATIENT = "/patient";//患者组件
    String USER = "/user";//我的
    String LOGIN = "/login";//登陆

    /**
     * 服务组件, 用于给每个组件暴露特有的服务
     */
    String SERVICE = "/service";

    /**
     * 宿主 App 分组
     */
    String APP_SPLASHACTIVITY = APP + "/SplashActivity";
    String APP_MAINACTIVITY = APP + "/MainActivity";
    String APP_AGENTWEBACTIVITY = APP + "/WebViewActivity";


    /**
     * 首页分组
     */
    String HOME_SERVICE_HOMEINFOSERVICE = HOME + SERVICE + "/HomeInfoService";

    String HOME_HOMEACTIVITY = HOME + "/HomeActivity";
    String HOME_DETAILACTIVITY = HOME + "/DetailActivity";
    /**
     * 智能会议室
     */
    String HOME_CONFERENCEROOMACTIVITY = HOME + "/ConferenceRoomActivity";

    /**
     * 会议室预约
     */
    String HOME_ONLINESUBSCRIBEROOMACTIVITY = HOME + "/OnlineSubscribeRoomActivity";

    /**
     * 我的会议
     */
    String HOME_MYMEETINGACTIVITY = HOME + "/MyMeetingActivity";

    /**
     * 会议详情
     */
    String HOME_MEETINGDETAILSACTIVITY = HOME + "/MeetingDetailsActivity";

    /**
     * 预约申请
     */
    String HOME_APPLYROOMACTIVITY = HOME + "/ApplyRoomActivity";

    /**
     * 选择参会领导
     */
    String HOME_CHOOSEUSERACTIVITY = HOME + "/ChooseUserActivity";

    /**
     * 选择单位
     */
    String HOME_CHOOSECOMPANYACTIVITY = HOME + "/ChooseCompanyActivity";

    /**
     * 预约成功
     */
    String HOME_SUBSCRIBESUCCEESACTIVITY = HOME + "/SubscribeSucceesActivity";

    /**
     * 通讯录搜索
     */
    String HOME_USERSEARCHACTIVITY = HOME + "/UserSearchActivity";

    /**
     * 服务项目
     */
    String HOME_SERVICEPROJECTACTIVITY = HOME + "/ServiceProjectActivity";

    /**
     * 预约审核
     */
    String HOME_APPROVEACTIVITY = HOME + "/ApproveActivity";

    /**
     * 准备会议
     */
    String HOME_PREPAREMETINGACTIVITY = HOME + "/PrepareMetingActivity";


    /**
     * 我的预约
     */
    String HOME_MYSUBSCRIBEACTIVITY = HOME + "/MySubscribeActivity";

    /**
     * 访客记录
     */
    String HOME_VISITORRECORDACTIVITY = HOME + "/VisitorRecordActivity";

    /**
     * 我的
     * *********************************************************************
     */
    String USER_SERVICE_USERINFOSERVICE = USER + SERVICE + "/UserInfoService";

    String USER_MINEACTIVITY = USER + "/MineActivity";
    String USER_ABOUTACTIVITY = USER + "/AboutActivity";
    String USER_SETTINGACTIVITY = USER + "/SettingActivity";
    /**
     * 实名认证
     */
    String USER_CERTIFICATIONACTIVITY = USER + "/CertificationActivity";
    /**
     * 个人信息
     */
    String USER_USERCENTERACTIVITY = USER + "/UserCenterActivity";
    /**
     * 个人信息
     */
    String USER_USERDETAILSACTIVITY = USER + "/UserDetailsActivity";
    /**
     * 修改手机号
     */
    String USER_MODIFYPHONEACTIVITY = USER + "/ModifyPhoneActivity";

    /**
     * 修改密码
     */
    String USER_MODIFYPWDACTIVITY = USER + "/ModifyPwdActivity";

    /**
     * 修改个人信息
     */
    String USER_MODIFYUSERINFOACTIVITY = USER + "/ModifyUserInfoActivity";

    /**
     * 问题反馈
     */
    String USER_FEEDBACKACTIVITY= USER + "/FeedbackActivity";
    /**
     * 常用语
     */
    String USER_USEFULEXPRESSIONSACTIVITY = USER + "/UsefulExpressionsActivity";

    /**
     * 添加常用语
     */
    String USER_ADDUSEFULEXPRACTIVITY = USER + "/AddUsefulExprActivity";

    /**
     * 医师认证
     */
    String USER_DOCTORAUTHACTIVITY = USER + "/DoctorAuthActivity";

    /**
     * 患者评价
     */
    String USER_PATIENTSAPPRAISEACTIVITY = USER + "/PatientsAppraiseActivity";

    /**
     * 我的业绩
     */
    String USER_PERFORMANCEACTIVITY = USER + "/PerformanceActivity";


    /**
     * 系统消息
     */
    String USER_MESSAGEACTIVITY = USER + "/MessageActivity";


    /**
     * 消息详情
     */
    String USER_MESSAGEDETAILSACTIVITY = USER + "/MessageDetailsActivity";

    /**
     * 登陆
     * *********************************************************************
     */
    String USER_SERVICE_LOGINSERVICE = LOGIN + SERVICE + "/LoginInfoService";

    String LOGGIN_LOGINACTIVITY = LOGIN + "/LoginActivity";

    String LOGGIN_FORGETPWDACTIVITY = LOGIN + "/ForgetPwdActivity";

    /**
     * 注册
     */
    String LOGGIN_REGISTERACTIVITY = LOGIN + "/RegisterActivity";


    /**
     * 参数
     * *********************************************************************
     */

    /**
     * 需要填写指导意见id
     */
    String PARAM_HEALTH_LIST_ID = "health_list_id";

    /**
     * 患者用户id
     */
    String PARAM_AID = "aid";

    /**
     * 页面标识
     */
    String PARAM_FORM_POSITION = "form_position";

    /**
     * 店铺ID
     */
    String PARAM_SHOP_ID = "shop_id";

    /**
     * 页面标识
     */
    String PARAM_PAGE_POSITION = "page_position";


    /**
     * id
     */
    String PARAM_ID = "id";


    /**
     * 订单名称
     */
    String PARAM_SERVER_NAME = "server_name";


    /**
     * 消息内容
     */
    String PARAM_MESSAGE_CONTENT = "message_content";


    /**
     * 报告类型
     */
    String PARAM_SEARCH_TYPE = "search_type";

    /**
     * WebView url
     */
    String PARAM_WEBVIEWXURL = "webviewxUrl";


}

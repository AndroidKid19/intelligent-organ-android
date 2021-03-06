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
package com.yway.scomponent.organ.mvp.model.api.service;

import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.commonsdk.core.BaseResponse;
import com.yway.scomponent.commonsdk.core.DictClassifyBean;
import com.yway.scomponent.commonsdk.core.UploadFileBean;
import com.yway.scomponent.organ.mvp.model.api.Api;
import com.yway.scomponent.organ.mvp.model.entity.AppVersion;
import com.yway.scomponent.organ.mvp.model.entity.ConferenceBean;
import com.yway.scomponent.organ.mvp.model.entity.ConfigureBean;
import com.yway.scomponent.organ.mvp.model.entity.FavoritesBean;
import com.yway.scomponent.organ.mvp.model.entity.HomeMetingBean;
import com.yway.scomponent.organ.mvp.model.entity.MeetingDetailsBean;
import com.yway.scomponent.organ.mvp.model.entity.MeetingRecordBean;
import com.yway.scomponent.organ.mvp.model.entity.MessageBean;
import com.yway.scomponent.organ.mvp.model.entity.PayDetailsBean;
import com.yway.scomponent.organ.mvp.model.entity.RechargeRecordBean;
import com.yway.scomponent.organ.mvp.model.entity.RoomDetailsBean;
import com.yway.scomponent.organ.mvp.model.entity.VisitorRecordBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

/**
 * ================================================
 * ?????? {@link Retrofit#create(Class)} ?????????????????? ApiService ???????????????
 * ???????????? zhihu ????????? API
 * <p>
 * Created by JessYan on 08/05/2016 12:05
 * <p>
 * ================================================
 */
public interface HomeService {


    /**
     * App????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERY_LATESTVERSIONBYENTITY)
    Observable<BaseResponse<AppVersion>> queryLatestVersionByEntity(@Body() Map<String, Object> params);

    /**
     * ???????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYALLSYSORGANDSYSUSERLIST)
    Observable<BaseResponse<AddressCompanyBean>> queryAllSysOrgAndSysUserList(@Body() Map<String, Object> params);

    /**
     * ????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYSYSBYDICTCLASSIFY)
    Observable<BaseResponse<DictClassifyBean>> querySysByDictClassify(@Body() Map<String, Object> params);

    /**
     * ???????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.API_HOME_UPLOAD_FILE})
    @POST(Api.API_HOME_UPLOAD_FILE)
    Observable<BaseResponse<UploadFileBean>> uploadPictureMore(@Body MultipartBody files);

    /**
     * ???????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYMEETINGROOMPAGELIST)
    Observable<BaseResponse<RoomDetailsBean>> queryMeetingRoomPageList(@Body() Map<String, Object> params);

    /**
     * ?????????????????? ??? ?????????????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_CREATEMEETINGRECORD)
    Observable<BaseResponse> createMeetingRecord(@Body() Map<String, Object> params);

    /**
     * ????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYBYMEETINGRECORDDETAILS)
    Observable<BaseResponse<MeetingDetailsBean>> queryByMeetingRecordDetails(@Body() Map<String, Object> params);

    /**
     * ????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYMEETINGRECORDPAGELIST)
    Observable<BaseResponse<MeetingRecordBean>> queryMeetingRecordPageList(@Body() Map<String, Object> params);

    /**
     * ???????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_DELETEMEETINGRECORD)
    Observable<BaseResponse> deleteMeetingRecord(@Body() Map<String, Object> params);


    /**
     * ????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYMYMEETINGPAGELIST)
    Observable<BaseResponse<ConferenceBean>> queryMyMeetingPageList(@Body() Map<String, Object> params);


    /**
     * ???????????? - ?????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_GETMEETINGRECORDAPPROVALINGLIST)
    Observable<BaseResponse<MeetingRecordBean>> getMeetingRecordApprovalingList(@Body() Map<String, Object> params);

    /**
     * ???????????? - ?????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_GETMEETINGRECORDAPPROVAEDLIST)
    Observable<BaseResponse<MeetingRecordBean>> getMeetingRecordApprovaedList(@Body() Map<String, Object> params);


    /**
     * ???????????? - ?????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_DOMEETINGRECORDAPPROVAL)
    Observable<BaseResponse> doMeetingRecordApproval(@Body() Map<String, Object> params);

    /**
     * ???????????? - ?????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_GETMEETINGRECORDREADYINGLIST)
    Observable<BaseResponse<MeetingRecordBean>> getMeetingRecordReadyingList(@Body() Map<String, Object> params);

    /**
     * ???????????? - ?????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_GETMEETINGRECORDREADYEDLIST)
    Observable<BaseResponse<MeetingRecordBean>> getMeetingRecordReadyedList(@Body() Map<String, Object> params);

    /**
     * ??????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_DOMEETINGRECORDREADY)
    Observable<BaseResponse> doMeetingRecordReady(@Body() Map<String, Object> params);

    /**
     * ??????????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYMYMEETINGLIST)
    Observable<BaseResponse<HomeMetingBean>> queryMyMeetingList(@Body() Map<String, Object> params);

    /**
     * ????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYARTICLEPUBLISHPAGELIST)
    Observable<BaseResponse<MessageBean>> queryArticlePublishPageList(@Body() Map<String, Object> params);

    /**
     * ????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_DOCANCELMEETINGRECORD)
    Observable<BaseResponse> doCancelMeetingRecord(@Body() Map<String, Object> params);

    /**
     * ????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_DRAFTSUBMITMEETINGRECORD)
    Observable<BaseResponse> draftSubmitMeetingRecord(@Body() Map<String, Object> params);


    /**
     * ??????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_CREATEARTICLEFAVORITES)
    Observable<BaseResponse> createArticleFavorites(@Body() Map<String, Object> params);


    /**
     * ????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_CANCELARTICLEFAVORITES)
    Observable<BaseResponse> cancelArticleFavorites(@Body() Map<String, Object> params);

    /**
     * ??????????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYUSERISARTICLEFAVORITES)
    Observable<BaseResponse<FavoritesBean>> queryUserIsArticleFavorites(@Body() Map<String, Object> params);

    /**
     * ????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYVISITREGISTERRECORDPAGELIST)
    Observable<BaseResponse<VisitorRecordBean>> queryVisitRegisterRecordPageList(@Body() Map<String, Object> params);

    /**
     * ???????????? ??????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYVISITCOUNTBYYEARANDMONTHANDTODAY)
    Observable<BaseResponse<VisitorRecordBean>> queryVisitCountByYearAndMonthAndToday(@Body() Map<String, Object> params);


    /**
     * ????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYAPPROVALCONFIGURELIST)
    Observable<BaseResponse<ConfigureBean>> queryApprovalConfigureList(@Body() Map<String, Object> params);

    /**
     * ????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYACCOUNTTRANSACTIONRECORDLISTBYTHISMONTH)
    Observable<BaseResponse<RechargeRecordBean>> queryAccountTransactionRecordListByThisMonth(@Body() Map<String, Object> params);

    /**
     * ????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYACCOUNTTRANSACTIONRECORDPAGELIST)
    Observable<BaseResponse<RechargeRecordBean>> queryPayRecordPageList(@Body() Map<String, Object> params);

    /**
     * ????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_CREATEACCOUNTTRANSACTIONRECORD)
    Observable<BaseResponse> createAccountTransactionRecord(@Body() Map<String, Object> params);

    /**
     * ??????????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_QUERYBYUSERACCOUNT)
    Observable<BaseResponse<RechargeRecordBean>> queryByUserAccount(@Body() Map<String, Object> params);

    /**
     * ????????????
     */
    @Headers({DOMAIN_NAME_HEADER + Api.HOME_DOMAIN_NAME})
    @POST(Api.API_WXPAY)
    Observable<BaseResponse<PayDetailsBean>> wxPay(@Body() Map<String, Object> params);
}

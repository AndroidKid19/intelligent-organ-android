package com.yway.scomponent.organ.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.commonsdk.core.UserInfoBean;
import com.yway.scomponent.organ.mvp.contract.ChooseUserContract;
import com.yway.scomponent.organ.mvp.model.ChooseUserModel;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.organ.mvp.ui.adapter.AddressBookOrganAdapter;
import com.yway.scomponent.organ.mvp.ui.adapter.CheckedUserAdapter;
import com.yway.scomponent.organ.mvp.ui.adapter.ChooseUserAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/22 14:35
 * ================================================
 */
@Module
//构建ChooseUserModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class ChooseUserModule {

    @Binds
    abstract ChooseUserContract.Model bindChooseUserModel(ChooseUserModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(ChooseUserContract.View view) {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.HORIZONTAL, false);
    }

    @ActivityScope
    @Provides
    static List<AddressCompanyBean> provideAdressCompanyList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static List<Object> provideObjectList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static AddressBookOrganAdapter provideAddressBookOrganAdapter(List<AddressCompanyBean> data) {
        AddressBookOrganAdapter adapter = new AddressBookOrganAdapter(data);
        return adapter;
    }

    @ActivityScope
    @Provides
    static ChooseUserAdapter provideChooseUserAdapter(List<Object> data) {
        ChooseUserAdapter adapter = new ChooseUserAdapter(data);
        return adapter;
    }

    @ActivityScope
    @Provides
    static List<UserInfoBean> provideUserInfoBeanList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static CheckedUserAdapter provideCheckedUserAdapter(List<UserInfoBean> data) {
        CheckedUserAdapter adapter = new CheckedUserAdapter(data);
        return adapter;
    }
}
package com.yway.scomponent.organ.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.yway.scomponent.organ.mvp.contract.UserSearchContract;
import com.yway.scomponent.organ.mvp.model.UserSearchModel;
import com.yway.scomponent.organ.mvp.ui.adapter.AddressBookPartsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2022/02/09 19:58
 * ================================================
 */
@Module
//构建UserSearchModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class UserSearchModule {

    @Binds
    abstract UserSearchContract.Model bindUserSearchModel(UserSearchModel model);

    @ActivityScope
    @Provides
    static List<Object> provideObjectList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(UserSearchContract.View view) {
        return new LinearLayoutManager(view.getActivity());
    }
    @ActivityScope
    @Provides
    static AddressBookPartsAdapter provideAddressBookPartsAdapter(List<Object> data) {
        AddressBookPartsAdapter adapter = new AddressBookPartsAdapter(data);
        return adapter;
    }
}
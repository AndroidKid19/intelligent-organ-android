package com.yway.scomponent.organ.di.module;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.organ.mvp.contract.AddressBookContract;
import com.yway.scomponent.organ.mvp.model.AddressBookModel;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.organ.mvp.ui.adapter.AddressBookOrganAdapter;
import com.yway.scomponent.organ.mvp.ui.adapter.AddressBookPartsAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/11/15 11:13
 * ================================================
 */
@Module
//构建AddressBookModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class AddressBookModule {

    @Binds
    abstract AddressBookContract.Model bindAddressBookModel(AddressBookModel model);

    @FragmentScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(AddressBookContract.View view) {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.HORIZONTAL, false);
    }

    @FragmentScope
    @Provides
    static List<AddressCompanyBean> provideAdressCompanyList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static List<Object> provideObjectList() {
        return new ArrayList<>();
    }

    @FragmentScope
    @Provides
    static AddressBookOrganAdapter provideAddressBookOrganAdapter(List<AddressCompanyBean> data) {
        AddressBookOrganAdapter adapter = new AddressBookOrganAdapter(data);
        return adapter;
    }


    @FragmentScope
    @Provides
    static AddressBookPartsAdapter provideAddressBookPartsAdapter(List<Object> data) {
        AddressBookPartsAdapter adapter = new AddressBookPartsAdapter(data);
        return adapter;
    }
}
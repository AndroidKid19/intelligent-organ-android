package com.yway.scomponent.organ.di.module;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.organ.mvp.contract.ChooseCompanyContract;
import com.yway.scomponent.organ.mvp.model.ChooseCompanyModel;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.organ.mvp.ui.adapter.AddressBookOrganAdapter;
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
 * Created by MVPArmsTemplate on 2021/12/23 11:05
 * ================================================
 */
@Module
//构建ChooseCompanyModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class ChooseCompanyModule {

    @Binds
    abstract ChooseCompanyContract.Model bindChooseCompanyModel(ChooseCompanyModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(ChooseCompanyContract.View view) {
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
}
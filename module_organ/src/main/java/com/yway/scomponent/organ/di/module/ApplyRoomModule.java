package com.yway.scomponent.organ.di.module;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.jess.arms.di.scope.ActivityScope;
import com.yway.scomponent.commonsdk.core.UploadFileBean;
import com.yway.scomponent.organ.mvp.contract.ApplyRoomContract;
import com.yway.scomponent.organ.mvp.model.ApplyRoomModel;
import com.yway.scomponent.commonsdk.core.AddressCompanyBean;
import com.yway.scomponent.organ.mvp.model.entity.CheckBoxBean;
import com.yway.scomponent.organ.mvp.ui.adapter.CheckBoxAdapter;
import com.yway.scomponent.organ.mvp.ui.adapter.ChooseCompanyAdapter;
import com.yway.scomponent.organ.mvp.ui.adapter.ChooseFileAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/12/15 14:38
 * ================================================
 */
@Module
//构建ApplyRoomModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
public abstract class ApplyRoomModule {

    @Binds
    abstract ApplyRoomContract.Model bindApplyRoomModel(ApplyRoomModel model);

    @ActivityScope
    @Provides
    static RecyclerView.LayoutManager provideLayoutManager(ApplyRoomContract.View view) {
        return new FlexboxLayoutManager(view.getActivity(), FlexDirection.ROW, FlexWrap.WRAP);
    }

    @ActivityScope
    @Provides
    static List<CheckBoxBean> provideList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static CheckBoxAdapter provideAdapter(List<CheckBoxBean> list) {
        CheckBoxAdapter adapter = new CheckBoxAdapter(list);
        adapter.setOnItemClickListener((view, viewType, data, position) -> adapter.multipleChoose(position));
        return adapter;
    }

    @ActivityScope
    @Provides
    static List<AddressCompanyBean> provideAddressCompanyList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static ChooseCompanyAdapter provideChooseCompanyAdapter(List<AddressCompanyBean> list) {
        ChooseCompanyAdapter adapter = new ChooseCompanyAdapter(list);
        adapter.setOnItemClickListener((view, viewType, data, position) -> {
            adapter.removeItemData(position);
        });
        return adapter;
    }

    @ActivityScope
    @Provides
    static List<UploadFileBean> provideFileDetailsBeanList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    static ChooseFileAdapter provideChooseFileAdapter(List<UploadFileBean> list) {
        ChooseFileAdapter adapter = new ChooseFileAdapter(list);
        adapter.setOnItemClickListener((view, viewType, data, position) -> {
            adapter.removeItemData(position);
        });
        return adapter;
    }
}
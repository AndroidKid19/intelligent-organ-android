package com.yway.scomponent.organ.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.yway.scomponent.organ.di.module.AddressBookModule;

import com.jess.arms.di.scope.FragmentScope;
import com.yway.scomponent.organ.mvp.contract.AddressBookContract;
import com.yway.scomponent.organ.mvp.ui.fragment.AddressBookFragment;

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 2021/11/15 11:13
 * ================================================
 */
@FragmentScope
@Component(modules = AddressBookModule.class, dependencies = AppComponent.class)
public interface AddressBookComponent {

    void inject(AddressBookFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AddressBookComponent.Builder view(AddressBookContract.View view);

        AddressBookComponent.Builder appComponent(AppComponent appComponent);

        AddressBookComponent build();
    }
}
/*
 * Copyright 2018 AndroidKid19
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
package com.yway.scomponent.user.mvp.model.entity;

import java.io.Serializable;

/**
 * ================================================
 * Created by AndroidKid19 on 26/04/2016 15:19
 * <a href="mailto:yuanw9@sina.com">Contact me</a>
 * <a href="https://github.com/AndroidKid19Coding">Follow me</a>
 * ================================================
 */
public class ChannelBean implements Serializable {

    /**
     * channelId : 1
     * channelText : 全部
     */

    private int channelId;
    private String channelText;

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public String getChannelText() {
        return channelText == null ? "" : channelText;
    }

    public void setChannelText(String channelText) {
        this.channelText = channelText;
    }
}

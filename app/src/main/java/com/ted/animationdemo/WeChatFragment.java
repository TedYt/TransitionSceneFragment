package com.ted.animationdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Copyright (C) 2008 The Android Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * Created by Ted.Yt on 9/1/16.
 */
public class WeChatFragment extends Fragment implements View.OnClickListener {

    View wechatContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.wechat_fragment,container,false);

        view.findViewById(R.id.tv2).setOnClickListener(this);

        wechatContainer = view.findViewById(R.id.wechat_container);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv2:
                playAnimation(v);
                break;
            default:

                break;
        }
    }

    private void playAnimation(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        int width = v.getWidth();
        int height = v.getHeight();

        float finalRadius = (float)Math.hypot(wechatContainer.getWidth(),
                    wechatContainer.getHeight());

        //第一个参数是要对谁使用动画 view
        //第二三参数是动画起点的坐标
        //第四五参数是初始的半径和最终的半径
        Animator anim = ViewAnimationUtils.createCircularReveal(
                wechatContainer,
                x + width/2,
                y - height/2,
                width,
                finalRadius);
        wechatContainer.setBackgroundColor(getResources().getColor(R.color.colorBg));
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }
}

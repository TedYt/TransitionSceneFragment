package com.ted.animationdemo;

import android.animation.Animator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.Button;

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
public class FriendsFragment extends Fragment implements View.OnClickListener {

    View container;
    ViewGroup root;
    Scene scene1;
    Scene scene2;
    Scene scene3;

    View myView;

    Button mBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.friend_fragment_scene3,container,true);
        mBack = (Button) view.findViewById(R.id.back);
        mBack.setOnClickListener(this);

        view = inflater.inflate(R.layout.friend,container,false);

        root = (ViewGroup) view.findViewById(R.id.friend_root);
        myView = view.findViewById(R.id.circle);
        myView.setOnClickListener(this);


        scene1 = Scene.getSceneForLayout(root, R.layout.friend_fragment,getActivity());
        scene2 = Scene.getSceneForLayout(root, R.layout.friend_fragment_scene2, getActivity());
        //scene3 = Scene.getSceneForLayout(root, R.layout.friend_fragment_scene3,getActivity());

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.circle:
                Transition transition = TransitionInflater.from(getActivity())
                        .inflateTransition(R.transition.friend_tr);

                transition.addListener(new Transition.TransitionListener() {
                    @Override
                    public void onTransitionStart(Transition transition) {}

                    @Override
                    public void onTransitionEnd(Transition transition) {
                        circleRevealShow();
                    }

                    @Override
                    public void onTransitionCancel(Transition transition) {}

                    @Override
                    public void onTransitionPause(Transition transition) {}

                    @Override
                    public void onTransitionResume(Transition transition) {}
                });
                TransitionManager.go(scene2,transition);

                break;
            case R.id.back:
                circleHide();
                break;
        }
    }


    /**
     * 圆圈收缩
     */
    private void circleHide() {
        int[] location = new int[2];
        mBack.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];

        float initR = (float)Math.hypot(root.getWidth(),root.getHeight());
        float finalR = myView.getWidth() / 2;

        Animator anim = ViewAnimationUtils.createCircularReveal(
                root,
                (root.getLeft() + root.getRight()) / 2,
                (root.getTop() + root.getBottom()) / 2,
                initR,
                finalR);
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                backToScene1();
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

    /**
     * 退回到scene1
     */
    private void backToScene1() {
        mBack.setVisibility(View.INVISIBLE);
        root.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        Transition transition = TransitionInflater.from(getActivity())
                .inflateTransition(R.transition.friend_tr2);

        TransitionManager.go(scene1,transition);
    }

    /**
     * 圆圈扩展
     */
    private void circleRevealShow() {
        int[] location = new int[2];
        myView.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];

        int initR = myView.getWidth()/2;
        float finalR = (float)Math.hypot(root.getWidth(),root.getHeight());

        root.setBackgroundColor(getResources().getColor(R.color.colorBg));
        Animator anim = ViewAnimationUtils.createCircularReveal(
                root,
                (root.getLeft() + root.getRight())/2,
                (root.getTop() + root.getBottom())/2,
                initR,
                finalR);
        anim.setDuration(500);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //TransitionManager.go(scene3);
                mBack.setVisibility(View.VISIBLE);
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

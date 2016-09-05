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
import android.view.ViewTreeObserver;
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
 *
 *
 *
 * Scene1 到 scene2 enterAction 和 ExitAction的执行顺序
 * 调用TransitionManager.go(scene2) 执行scene2的enterAction,
 * 然后调用TransitionManager.go(scene1)执行scene2 exitAction, 然后是scene1 enterAction
 */
public class FriendsFragment extends Fragment implements View.OnClickListener {

    ViewGroup root;
    Scene scene1;
    Scene scene2;
    Scene scene3;

    int myViewWidth;
    int myViewHeight;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.friend,container,false);

        root = (ViewGroup) view.findViewById(R.id.friend_root);

        scene1 = Scene.getSceneForLayout(root, R.layout.friend_fragment,getActivity());
        scene1.setEnterAction(new Runnable() {
            @Override
            public void run() {
                Log.d("tui", "scene 1 enterAction");
                final View view1 = root.findViewById(R.id.circle);
                ViewTreeObserver vto = view1.getViewTreeObserver();
                vto.addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
                    @Override
                    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                        //setSize(view1.getMeasuredWidth(),view1.getMeasuredHeight());
                    }
                });
                view1.setOnClickListener(FriendsFragment.this);
            }
        });
        scene1.setExitAction(new Runnable() {
            @Override
            public void run() {
                Log.d("tui", "scene 1 exitAction");
            }
        });
        scene2 = Scene.getSceneForLayout(root, R.layout.friend_fragment_scene2, getActivity());
        scene2.setEnterAction(new Runnable() {
            @Override
            public void run() {
                Log.d("tui", "scene 2enterAction");
            }
        });
        scene2.setExitAction(new Runnable() {
            @Override
            public void run() {
                Log.d("tui", "scene 2 exitAction");
            }
        });
        scene3 = Scene.getSceneForLayout(root, R.layout.friend_fragment_scene3,getActivity());
        scene3.setEnterAction(new Runnable() {
            @Override
            public void run() {
                Button btn = (Button) root.findViewById(R.id.back);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(FriendsFragment.this);
            }
        });
        return view;
    }

    private void setSize(int measuredWidth, int measuredHeight) {
        myViewWidth = measuredWidth;
        myViewHeight = measuredHeight;
        Log.d("tui", "setSize, myViewWidth = " + myViewWidth + ", myViewHeight = " + myViewHeight);
    }

    @Override
    public void onResume() {
        super.onResume();
        TransitionManager.go(scene1);
    }

    @Override
    public void onClick(final View v) {

        switch (v.getId()){
            case R.id.circle:
                Transition transition = TransitionInflater.from(getActivity())
                        .inflateTransition(R.transition.friend_tr);

                transition.addListener(new Transition.TransitionListener() {
                    @Override
                    public void onTransitionStart(Transition transition) {}

                    @Override
                    public void onTransitionEnd(Transition transition) {
                        circleRevealShow(v);
                    }

                    @Override
                    public void onTransitionCancel(Transition transition) {}

                    @Override
                    public void onTransitionPause(Transition transition) {}

                    @Override
                    public void onTransitionResume(Transition transition) {}
                });
                TransitionManager.go(scene2,transition);
                Log.d("tui", "go to scene 2");
                break;
            case R.id.back:
                TransitionManager.go(scene2);
                circleHide(v);
                break;
        }
    }


    /**
     * 圆圈收缩
     * @param v
     */
    private void circleHide(final View v) {

        float initR = (float)Math.hypot(root.getWidth(),root.getHeight());
        float finalR = v.getWidth() / 2;//myViewWidth / 2 ;//myView.getWidth() / 2;
        Log.d("tui", "circleHide, finalR = " + finalR);
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
                backToScene1(v);
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
     * @param v
     */
    private void backToScene1(View v) {
        root.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        Transition transition = TransitionInflater.from(getActivity())
                .inflateTransition(R.transition.friend_tr2);

        TransitionManager.go(scene1,transition);
        Log.d("tui", "go to scene 1");
    }

    /**
     * 圆圈扩展
     * @param v
     */
    private void circleRevealShow(View v) {

        int initR = v.getWidth()/2;//myViewWidth / 2;//myView.getWidth()/2;
        float finalR = (float)Math.hypot(root.getWidth(),root.getHeight());
        Log.d("tui", "circleRevealShow, initR = " + initR);

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
                TransitionManager.go(scene3);
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

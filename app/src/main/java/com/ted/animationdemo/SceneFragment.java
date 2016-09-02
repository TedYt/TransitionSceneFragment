package com.ted.animationdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

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
public class SceneFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    private FrameLayout sceneRoot;

    private Scene scene1;
    private Scene scene2;
    private Scene scene3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.scene_fragment,container,false);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.scene_select);
        radioGroup.setOnCheckedChangeListener(this);

        sceneRoot = (FrameLayout) view.findViewById(R.id.scene_root);

        scene1 = Scene.getSceneForLayout(sceneRoot,R.layout.scene1,getActivity());
        scene2 = Scene.getSceneForLayout(sceneRoot,R.layout.scene2,getActivity());
        scene3 = Scene.getSceneForLayout(sceneRoot, R.layout.scene3,getActivity());

        return view;

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.scene1:
                TransitionManager.go(scene1);
                break;
            case R.id.scene2:
                TransitionManager.go(scene2);
                break;
            case R.id.scene3:
                TransitionManager.go(scene3);
                break;
            default:
                break;
        }
    }
}

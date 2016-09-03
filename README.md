# TransitionSceneFragment

1. Animator anim = ViewAnimationUtils.createCircularReveal(
                root,
                (root.getLeft() + root.getRight())/2,
                (root.getTop() + root.getBottom())/2,
                initR,
                finalR);
                
   第一个参数是要对谁用动画，这个参数不能用于detached view，只能用于viewGroup
   第二，三参数是动画的起点坐标
   第四五参数是动画开始初始半径和最终半径
   
2. TransitionManager.go(scene2,transition);
   可以使用自定义的transition，来切换scene。进一步，可以给自定义的transition增加一个监听事件，
   在动画结束后，启动另一个动画，如下：
   transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionStart(Transition transition) {
                }

                @Override
                public void onTransitionEnd(Transition transition) {
                    circleRevealShow();//启动新的动画
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                }

                @Override
                public void onTransitionPause(Transition transition) {
                }

                @Override
                public void onTransitionResume(Transition transition) {
                }
    });

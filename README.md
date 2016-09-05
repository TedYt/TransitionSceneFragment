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


3. 
        root = (ViewGroup) view.findViewById(R.id.friend_root);
        myView = view.findViewById(R.id.circle);
        myView.setOnClickListener(this);


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
                        setSize(view1.getMeasuredWidth(),view1.getMeasuredHeight());
                    }
                });

                view1.setOnClickListener(FriendsFragment.this);
            }
        });
        
        这里有两个setOnClickListener，如果不要setEnterAction里的，那动画回到scene1的时候，view就会失去点击事件。
        而setEnterAction会在回到scene1时调用，这时再设置点击事件，就会生效了。
        
        这样可以保证view的点击事件一直有效
        
4. 
        private void circleRevealShow(View v) {

            int initR = v.getWidth()/2;
            ...
        ｝
        
        获取点击的view的尺寸，可以通过onclick方法，把v的参数传递下去
        
        
5. 
  scene1 到 scene2 enterAction 和 ExitAction的执行顺序
  从scene1 调用TransitionManager.go(scene2) 执行scene2的enterAction,
  然后调用TransitionManager.go(scene1)执行scene2 exitAction, 然后是scene1 enterAction
  
  scene1             --->               scene2                --->        scene1 
        scene2的enterAction              scene2 exitAction,  scene1 enterAction
        
        

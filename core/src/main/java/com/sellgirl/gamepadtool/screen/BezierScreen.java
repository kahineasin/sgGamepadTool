package com.sellgirl.gamepadtool.screen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.BSpline;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
//import com.badlogic.gdx.physics.bullet.collision.CollisionObjectWrapper;
//import com.badlogic.gdx.physics.bullet.collision.ContactListener;
//import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
//import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithm;
//import com.badlogic.gdx.physics.bullet.collision.btCollisionAlgorithmConstructionInfo;
//import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
//import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
//import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
//import com.badlogic.gdx.physics.bullet.collision.btCollisionWorld;
//import com.badlogic.gdx.physics.bullet.collision.btDispatcher;
//import com.badlogic.gdx.physics.bullet.collision.btDispatcherInfo;
//import com.badlogic.gdx.physics.bullet.collision.btManifoldResult;
//import com.badlogic.gdx.physics.bullet.collision.btPersistentManifold;
//import com.badlogic.gdx.physics.bullet.collision.btSphereBoxCollisionAlgorithm;
//import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
//import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
//import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
//import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.sellgirl.gamepadtool.GamepadTool;
import com.sellgirl.gamepadtool.MainMenuScreen;
import com.sellgirl.gamepadtool.ScreenSetting;
//import com.mygdx.game.share.list.Array2;
import com.sellgirl.sgGameHelper.BezierControlLine;
import com.sellgirl.sgGameHelper.list.Array2;
import com.sellgirl.sgJavaHelper.config.SGDataHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BezierScreen implements Screen// ApplicationListener
{
    GamepadTool game;
    KeySettingScreen screen;
    public BezierScreen(GamepadTool game, KeySettingScreen screen, List<Vector2> bezier){
        this.game=game;
        this.screen=screen;
        create(bezier);
//        if(null!=bezier&&5<bezier.size()){
//            if(null==beziers){beziers=new Array2<>();}
//            for(int i=0;bezier.size()>i;i+=3){
//                Bezier item=new Bezier<>(
//                        last.middle,
//                        last.right,
//                        line.left,
//                        line.middle
//                );
////                beziers=new Array2<>();
//                beziers.add(bezier);
//            }
//        }
    }
//    class MyContactListener extends ContactListener {
//        @Override
//        public boolean onContactAdded (int userValue0, int partId0, int index0, boolean match0,
//                                       int userValue1, int partId1, int index1, boolean match1) {
//            if (match0)
//                ((ColorAttribute)instances.get(userValue0).materials.get(0).get(ColorAttribute.Diffuse)).color.set(Color.WHITE);
//            if (match1)
//                ((ColorAttribute)instances.get(userValue1).materials.get(0).get(ColorAttribute.Diffuse)).color.set(Color.WHITE);
//            return true;
//        }
//}
//static class MyMotionState extends btMotionState {
//        Matrix4 transform;
//        @Override
//        public void getWorldTransform (Matrix4 worldTrans) {
//            worldTrans.set(transform);
//        }
//        @Override
//        public void setWorldTransform (Matrix4 worldTrans) {
//            transform.set(worldTrans);
//        }
//    }
//    static class GameObject extends ModelInstance implements Disposable {
//        //public final btCollisionObject body;
//        public final btRigidBody body;
//        public final MyMotionState motionState;
//        public boolean moving;
////        public GameObject(Model model, String node, btCollisionShape shape) {
////            super(model, node);
////            body = new btCollisionObject();
////            body.setCollisionShape(shape);
////        }
//        public GameObject (Model model, String node, btRigidBody.btRigidBodyConstructionInfo constructionInfo) {
//            super(model, node);
//            motionState = new MyMotionState();
//            motionState.transform = transform;
//            body = new btRigidBody(constructionInfo);
//            body.setMotionState(motionState);
//        }
//
//        @Override
//        public void dispose () {
//            body.dispose();
//            motionState.dispose();
//        }
//        static class Constructor implements Disposable {
//            public final Model model;
//            public final String node;
//            public final btCollisionShape shape;
//            public final btRigidBody.btRigidBodyConstructionInfo constructionInfo;
//            private static final Vector3 localInertia = new Vector3();
////            public Constructor(Model model, String node, btCollisionShape shape) {
////                this.model = model;
////                this.node = node;
////                this.shape = shape;
////            }
//            public Constructor (Model model, String node, btCollisionShape shape, float mass) {
//                this.model = model;
//                this.node = node;
//                this.shape = shape;
//                if (mass > 0f){
//                    shape.calculateLocalInertia(mass, localInertia);}
//                else{
//                    localInertia.set(0, 0, 0);}
//                this.constructionInfo = new btRigidBody.btRigidBodyConstructionInfo(mass, null, shape, localInertia);
//            }
//
//            public GameObject construct() {
//                //return new GameObject(model, node, shape);
//                return new GameObject(model, node, constructionInfo);
//            }
//
//            @Override
//            public void dispose () {
//                shape.dispose();
//                constructionInfo.dispose();
//            }
//        }
//    }
    PerspectiveCamera cam;
    CameraInputController camController;
    ModelBatch modelBatch;
//    Array<ModelInstance> instances;
    Environment environment;

    Model model;
    ModelInstance ground;
    ModelInstance ball;
//    btCollisionShape groundShape;
//    btCollisionShape ballShape;
//    btCollisionObject groundObject;
//    btCollisionObject ballObject;
//    btCollisionConfiguration collisionConfig;
//    btDispatcher dispatcher;
//
//    Array<GameObject> instances;
//    ArrayMap<String, GameObject.Constructor> constructors;

//    MyContactListener contactListener;
//    btBroadphaseInterface broadphase;
//    btCollisionWorld collisionWorld;
//    btDynamicsWorld dynamicsWorld;
//    btConstraintSolver constraintSolver;

    final static short GROUND_FLAG = 1<<8;
    final static short OBJECT_FLAG = 1<<9;
    final static short ALL_FLAG = -1;

    private float xMeter;//x轴的长度
    private float yMeter;//x轴的长度
    private float meterToWidth;
    private float originX;
    private float originY;
    private float PtM(float pix){
//        return (pix/meterToWidth)+originX;
        return SGDataHelper.getFloatPrecision(((pix-originX)/meterToWidth),2);
    }
    private float PtMY(float pix){
//        return (pix/meterToWidth)+originX;

        return SGDataHelper.getFloatPrecision((pix-originY)/meterToWidth,2);
    }
    private float MtP(float meter){
        return SGDataHelper.getFloatPrecision((meter*meterToWidth)+originX,2);
    }
    private float MtPY(float meter){
        return SGDataHelper.getFloatPrecision((meter*meterToWidth)+originY,2);
    }
//    @Override
    public void create ( List<Vector2> initBezier) {

         xMeter=6f;//x轴的长度
         yMeter=xMeter*ScreenSetting.WORLD_HEIGHT/ScreenSetting.WORLD_WIDTH;//x轴的长度
         meterToWidth=ScreenSetting.WORLD_WIDTH/xMeter;
         originX=ScreenSetting.WORLD_WIDTH/xMeter;
         originY=ScreenSetting.WORLD_HEIGHT/xMeter;
//        Bullet.init();
//
//        modelBatch = new ModelBatch();
//
//        environment = new Environment();
//        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
//        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
//
//        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//        cam.position.set(3f, 7f, 10f);
//        cam.lookAt(0, 4f, 0);
//        cam.update();
//
//        camController = new CameraInputController(cam);
//        Gdx.input.setInputProcessor(camController);
//
//        ModelBuilder mb = new ModelBuilder();
//        mb.begin();
//        mb.node().id = "ground";
//        mb.part("ground", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.RED)))
//                .box(5f, 1f, 5f);
//        mb.node().id = "sphere";
//        mb.part("sphere", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.GREEN)))
//                .sphere(1f, 1f, 1f, 10, 10);
//        mb.node().id = "box";
//        mb.part("box", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.BLUE)))
//                .box(1f, 1f, 1f);
//        mb.node().id = "cone";
//        mb.part("cone", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.YELLOW)))
//                .cone(1f, 2f, 1f, 10);
//        mb.node().id = "capsule";
//        mb.part("capsule", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.CYAN)))
//                .capsule(0.5f, 2f, 10);
//        mb.node().id = "cylinder";
//        mb.part("cylinder", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal, new Material(ColorAttribute.createDiffuse(Color.MAGENTA)))
//                .cylinder(1f, 2f, 1f, 10);
//        model = mb.end();
//
//        constructors = new ArrayMap<String, GameObject.Constructor>(String.class, GameObject.Constructor.class);
////        constructors.put("ground", new GameObject.Constructor(model, "ground", new btBoxShape(new Vector3(2.5f, 0.5f, 2.5f))));
////        constructors.put("sphere", new GameObject.Constructor(model, "sphere", new btSphereShape(0.5f)));
////        constructors.put("box", new GameObject.Constructor(model, "box", new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f))));
////        constructors.put("cone", new GameObject.Constructor(model, "cone", new btConeShape(0.5f, 2f)));
////        constructors.put("capsule", new GameObject.Constructor(model, "capsule", new btCapsuleShape(.5f, 1f)));
////        constructors.put("cylinder", new GameObject.Constructor(model, "cylinder", new btCylinderShape(new Vector3(.5f, 1f, .5f))));
//        constructors.put("ground", new GameObject.Constructor(model, "ground", new btBoxShape(new Vector3(2.5f, 0.5f, 2.5f)), 0f));
//        constructors.put("sphere", new GameObject.Constructor(model, "sphere", new btSphereShape(0.5f), 1f));
//        constructors.put("box", new GameObject.Constructor(model, "box", new btBoxShape(new Vector3(0.5f, 0.5f, 0.5f)), 1f));
//        constructors.put("cone", new GameObject.Constructor(model, "cone", new btConeShape(0.5f, 2f), 1f));
//        constructors.put("capsule", new GameObject.Constructor(model, "capsule", new btCapsuleShape(.5f, 1f), 1f));
//        constructors.put("cylinder", new GameObject.Constructor(model, "cylinder", new btCylinderShape(new Vector3(.5f, 1f, .5f)), 1f));
//
//        instances = new Array<GameObject>();
//        instances.add(constructors.get("ground").construct());
//
//
//        //动力学世界
//        collisionConfig = new btDefaultCollisionConfiguration();
//        dispatcher = new btCollisionDispatcher(collisionConfig);
//        broadphase = new btDbvtBroadphase();
//        constraintSolver = new btSequentialImpulseConstraintSolver();
//        dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, constraintSolver, collisionConfig);
//        dynamicsWorld.setGravity(new Vector3(0, -10f, 0));
//        contactListener = new MyContactListener();
//
//        instances = new Array<GameObject>();
//        GameObject object = constructors.get("ground").construct();
//        object.body.setCollisionFlags(object.body.getCollisionFlags()
//                | btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT);
//        instances.add(object);
//        dynamicsWorld.addRigidBody((btRigidBody)object.body, GROUND_FLAG, ALL_FLAG);
//        object.body.setContactCallbackFlag(GROUND_FLAG);
//        object.body.setContactCallbackFilter(0);
//        object.body.setActivationState(Collision.DISABLE_DEACTIVATION);

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        mulProcess = new InputMultiplexer();
        inputListener=new TiledAStarInputProcessor(this);

//        font = MainMenuScreen.getFont2();
//        skin = MainMenuScreen.getSkin();
//        skin.add("default", MainMenuScreen.getButtonStyle(skin));
//        skin.add("default", MainMenuScreen.getLabelStyle(skin));
//        skin.add("default", MainMenuScreen.getTextFieldStyle(skin));

//        font = MainMenuScreen.getFont2();//刷新新的中文字
        font = game.getFont2();//刷新新的中文字
        skin = MainMenuScreen.getSkin2(font);
//        skin.add("default", font);
        stage = new Stage(new StretchViewport(ScreenSetting.WORLD_WIDTH, ScreenSetting.WORLD_HEIGHT),batch);


        Table table = new Table();
        table.align(Align.topLeft);
        table.pad(10);
        table.setX(0);
        table.setY(ScreenSetting.WORLD_HEIGHT);
        table.setWidth(ScreenSetting.WORLD_WIDTH);
//        table.setFillParent(true);
        stage.addActor(table);

        addControlLineButton = new TextButton("add Point", skin);

        addControlLineButton.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                BezierControlLine line=new BezierControlLine();
                float lineLen=50f;
                line.middle=new Vector2(ScreenSetting.WORLD_WIDTH*0.5f,ScreenSetting.WORLD_HEIGHT*0.5f);
                line.left=new Vector2(ScreenSetting.WORLD_WIDTH*0.5f-lineLen,ScreenSetting.WORLD_HEIGHT*0.5f-lineLen);
                line.right=new Vector2(ScreenSetting.WORLD_WIDTH*0.5f+lineLen,ScreenSetting.WORLD_HEIGHT*0.5f+lineLen);
                BezierControlLine last=bezierControls.get(bezierControls.size()-1);
                if(null==last.right){
                    if(null==last.left){
                        last.left=new Vector2(last.middle.x-10f,last.middle.y-10f);
                    }
//                    last.right=(new Vector2(last.middle.x*2f-last.left.x,last.middle.y*2f-last.left.y)).nor();
                    last.right=(new Vector2(last.middle.x*2f-last.left.x,last.middle.y*2f-last.left.y));
                }
                bezierControls.add(line);

                bezier=new Bezier<>(
                        last.middle,
                        last.right,
                        line.left,
                        line.middle
                );
//                beziers=new Array2<>();
                beziers.add(bezier);
            }
        });

//        final Label paramLbl=new Label("",skin);
        final TextField paramLbl=new TextField("",skin);
        paramLbl.setWidth(ScreenSetting.WORLD_WIDTH);
        TextButton showParamButton = new TextButton("显示参数", skin);
        showParamButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                StringBuilder sb=new StringBuilder();
                int idx=0;
                for(BezierControlLine i:bezierControls){
                    if(0<idx){
                        sb.append(",");
                    }
//            shapeRenderer.line(i.left==null?i.middle:i.left,i.right==null?i.middle:i.right);
                    if(null!=i.left){
                        sb.append("("+PtM(i.left.x)+","+PtMY(i.left.y)+")");
                    }else{
                        sb.append("("+PtM(i.middle.x)+","+PtMY(i.middle.y)+")");
                    }
                    sb.append(",");
                    sb.append("("+PtM(i.middle.x)+","+PtMY(i.middle.y)+")");
                    sb.append(",");
                    if(null!=i.right){
                        sb.append("("+PtM(i.right.x)+","+PtMY(i.right.y)+")");
                    }else{
                        sb.append("("+PtM(i.middle.x)+","+PtMY(i.middle.y)+")");
                    }

                    idx++;
                }
                String s=sb.toString();
                System.out.println(s);
                paramLbl.setText(s);
            }});
        TextButton showSPlineButton = new TextButton("SPline", skin);
        showSPlineButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                List<Vector2> list=BezierControlLine.lineToPoint(bezierControls);
                int size=list.size();
//                bSpline=new BSpline<Vector2>(list.subList(1,size-1).toArray(new Vector2[size-2]),3,false);
                bSpline=new BSpline<Vector2>(list.toArray(new Vector2[size]),3,false);
//                updateBSpline=true;
            }});

        TextButton initButton = new TextButton("init line", skin);
        initButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                initNormalLine();
            }});

        TextButton saveButton = new TextButton("保存曲线", skin);
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                ArrayList<Vector2> r=new ArrayList<>();
//                int idx=0;
//                for(BezierControlLine i:bezierControls){
////                    if(0<idx){
////                        sb.append(",");
////                    }
//
//                    if(null!=i.left){
////                        sb.append("("+PtM(i.left.x)+","+PtMY(i.left.y)+")");
//                        r.add(new Vector2(i.left.x,i.left.y));
//                    }else{
////                        sb.append("("+PtM(i.middle.x)+","+PtMY(i.middle.y)+")");
//                        r.add(new Vector2(i.middle.x,i.middle.y));
//                    }
////                    sb.append(",");
////                    sb.append("("+PtM(i.middle.x)+","+PtMY(i.middle.y)+")");
//                    r.add(new Vector2(i.middle.x,i.middle.y));
////                    sb.append(",");
//                    if(null!=i.right){
////                        sb.append("("+PtM(i.right.x)+","+PtMY(i.right.y)+")");
//                        r.add(new Vector2(i.right.x,i.right.y));
//                    }else{
////                        sb.append("("+PtM(i.middle.x)+","+PtMY(i.middle.y)+")");
//                        r.add(new Vector2(i.middle.x,i.middle.y));
//                    }
//
//                    idx++;
//                }
                ArrayList<Vector2> list=BezierControlLine.lineToPoint(bezierControls);
                for(Vector2 i:list){
                    i.x=PtM(i.x);
                    i.y=PtMY(i.y);
                }
                screen.saveBezier(list);
                game.setScreen(screen);
                screen.resume();
                BezierScreen.this.dispose();
            }});
//        stage.addActor(addControlLineButton);
        int spaceRight=20;

        table.add(addControlLineButton).spaceRight(spaceRight);
        table.add(showParamButton).spaceRight(spaceRight);
        table.add(showSPlineButton).spaceRight(spaceRight);
        table.add(initButton).spaceRight(spaceRight);
        table.add(saveButton);
        table.row();
        table.add(paramLbl).width(ScreenSetting.WORLD_WIDTH).colspan(5);
        //stage.addActor(table);

        mulProcess.addProcessor(stage);
        mulProcess.addProcessor(inputListener);
        Gdx.input.setInputProcessor(mulProcess);


        if(null==initBezier||6>initBezier.size()) {
            initNormalLine();
        }else{
            bezierControls = new Array2<>();
            beziers = new Array2<>();
            for(int i=0;initBezier.size()>i;i+=3){
                BezierControlLine item = new BezierControlLine();
                item.left = new Vector2(MtP(initBezier.get(i).x), MtPY(initBezier.get(i).y));
                item.middle = new Vector2(MtP(initBezier.get(i+1).x), MtPY(initBezier.get(i+1).y));
                item.right = new Vector2(MtP(initBezier.get(i+2).x), MtPY(initBezier.get(i+2).y));
                bezierControls.add(item);
//                if(initBezier.size()>i+4) {
//                    bezier = new Bezier<>(
//                            initBezier.get(i+1),
//                            initBezier.get(i+2),
//                            initBezier.get(i+3),
//                            initBezier.get(i+4)
//                    );
//                    beziers.add(bezier);
//                }
            }

            for(int i=0;bezierControls.size()-1>i;i++){
                bezier = new Bezier<>(
                        bezierControls.get(i).middle,
                        bezierControls.get(i).right,
                        bezierControls.get(i+1).left,
                        bezierControls.get(i+1).middle
                );
                beziers.add(bezier);
            }
        }
    }
    private void initNormalLine(){

        bezierControls = new Array2<>();
        BezierControlLine item = new BezierControlLine();
        float n=0.1f*meterToWidth;
        item.left=new Vector2(originX-n,originY-n);
        item.middle = new Vector2(originX, originY);
        item.right = new Vector2(originX+n, originY+n);
        bezierControls.add(item);

        item = new BezierControlLine();
        item.left=new Vector2(originX+meterToWidth-n,originY+meterToWidth-n);
        item.middle = new Vector2(originX+meterToWidth, originY+meterToWidth);
        item.right = new Vector2(originX+meterToWidth+n, originY+meterToWidth+n);
        bezierControls.add(item);

        bezier = new Bezier<>(
                bezierControls.get(0).middle,
                bezierControls.get(0).right,
                bezierControls.get(1).left,
                bezierControls.get(1).middle
        );
        beziers = new Array2<>();
        beziers.add(bezier);
    }

    protected Skin skin;
    protected Stage stage;
    protected TextButton addControlLineButton =null;

    public SpriteBatch batch;
    protected ShapeRenderer shapeRenderer;
    protected  InputMultiplexer mulProcess;
    protected TiledAStarInputProcessor inputListener;
    public BitmapFont font;
    Bezier<Vector2> bezier=null;
    Array2<Bezier<Vector2>> beziers=null;
    private BSpline<Vector2> bSpline=null;
//    private boolean updateBSpline=false;
//    public class BezierControlLine{
//        public Vector2 left;
//        public Vector2 middle;
//        public Vector2 right;
//        public void move(float x,float y){
//
//            middle.x+=x;
//            middle.y+=y;
//            if(null!=left){
//
//                left.x+=x;
//                left.y+=y;
//            }
//            if(null!=right){
//
//                right.x+=x;
//                right.y+=y;
//            }
//        }
//        public void moveTo(float x,float y){
//            move(x-middle.x,y-middle.y);
//        }
//
//    }
    private Array2<BezierControlLine> bezierControls;
    boolean collision;
    float spawnTimer;
    float angle, speed = 90f;
//    private float widthToMeter;
    @Override
    public void render (float delta) {

//        final float delta = Math.min(1f/30f, Gdx.graphics.getDeltaTime());

//        //普通世界
////        for (GameObject obj : instances) {
////            if (obj.moving) {
////                obj.transform.trn(0f, -delta, 0f);
////                obj.body.setWorldTransform(obj.transform);
//////                if (checkCollision(obj.body, instances.get(0).body)){
//////                    obj.moving = false;}
////
////                //checkCollision(obj.body, instances.get(0).body);
////            }
////        }
////        collisionWorld.performDiscreteCollisionDetection();
//
//        //动力学世界
//        angle = (angle + delta * speed) % 360f;
//        instances.get(0).transform.setTranslation(0, MathUtils.sinDeg(angle) * 2.5f, 0f);
//        //instances.get(0).body.setWorldTransform(instances.get(0).transform);
////        instances.get(0).body.setActivationState(Collision.ACTIVE_TAG);
//        //instances.get(0).body.activate();
//
//        dynamicsWorld.stepSimulation(delta, 5, 1f/60f);
////        for (GameObject obj : instances) {
////            obj.body.getWorldTransform(obj.transform);
////        }
//
//        if ((spawnTimer -= delta) < 0) {
//            spawn();
//            spawnTimer = 1.5f;
//        }
//
        //camController.update();


        if((inputListener.dragging)){
            if(null!=inputListener.draggingLine){
                inputListener.draggingLine.moveTo(Gdx.input.getX(),inputListener.getY2(Gdx.input.getY()));
            }else if(null!=inputListener.draggingPoint) {
                inputListener.draggingPoint.x = Gdx.input.getX();
                inputListener.draggingPoint.y = inputListener.getY2(Gdx.input.getY());
            }
        }

        ScreenUtils.clear(0, 0, 0.2f, 1);
        Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

//        modelBatch.begin(cam);
//        modelBatch.render(instances, environment);
//        modelBatch.end();

//        batch.begin();
//        font.draw(batch, "dragging:"+inputListener.dragging+" point:"+(null==inputListener.draggingPoint?"null":(inputListener.draggingPoint).x),
//                100,100);//+ 12);
//        batch.end();
        Batch batch=stage.getBatch();
        batch.begin();
//        font.draw(stage.getBatch(),"(0,0)",originX,originY);
        //x轴长度为3
        float startMeter= (-originX/meterToWidth);
        float endMeter= (int) (xMeter+startMeter);
        for(int i = (int) startMeter; i<=endMeter; i++){
            font.draw(stage.getBatch(),"("+i+","+0+")",meterToWidth*i+originX,originY);
        }
        float startYMeter= (-originY/meterToWidth);
        float endYMeter= (int) (yMeter+startYMeter);
        for(int i = (int) startYMeter; i<=endYMeter; i++){
            font.draw(stage.getBatch(),"(0,"+i+")",originX,meterToWidth*i+originY);
        }
        font.draw(stage.getBatch(),"x:鼠标速度",ScreenSetting.WORLD_WIDTH-150f,originY-30);
        font.draw(stage.getBatch(),"y:摇杆幅度",originX,ScreenSetting.WORLD_HEIGHT-100f);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        //座标轴
        shapeRenderer.setColor(Color.WHITE);
//        float originX=ScreenSetting.WORLD_WIDTH/3f;
//        float originY=ScreenSetting.WORLD_HEIGHT/3f;
        shapeRenderer.line(0,originY,ScreenSetting.WORLD_WIDTH,originY);
        shapeRenderer.line(originX,0,originX,ScreenSetting.WORLD_HEIGHT);

        shapeRenderer.setColor(0, 1, 0, 1);
//        last=null;
        boolean isFirst=true;
        for(Bezier j:beziers){
            for(float i=0.0f;i<=1;i+=0.01f){
                j.valueAt(out,i);
                //current=out;

                if(!isFirst){
                    shapeRenderer.line(last,out);
                }

                last.set(out);
                isFirst=false;
            }
        }
        shapeRenderer.setColor(Color.RED);
        for(BezierControlLine i:bezierControls){
//            shapeRenderer.line(i.left==null?i.middle:i.left,i.right==null?i.middle:i.right);
            if(null!=i.left){
                shapeRenderer.line(i.middle,i.left);
            }
            if(null!=i.right){
                shapeRenderer.line(i.middle,i.right);
            }
        }

        if(null!=bSpline){
            shapeRenderer.setColor(Color.YELLOW);
            isFirst=true;
            for(float i=0.0f;i<=1;i+=0.01f){
                bSpline.valueAt(out,i);
                //current=out;

                if(!isFirst){
                    shapeRenderer.line(last,out);
                }

                last.set(out);
                isFirst=false;
            }
        }

        shapeRenderer.end();

        stage.act();
        stage.draw();
    }
    private final Vector2 out = new Vector2();
    private  Vector2 last = new Vector2();
    //private  Vector2 current = null;
//    public void spawn() {
////        GameObject obj = constructors.values[1+ MathUtils.random(constructors.size-2)].construct();
////        obj.moving = true;
////        obj.transform.setFromEulerAngles(MathUtils.random(360f), MathUtils.random(360f), MathUtils.random(360f));
////        obj.transform.trn(MathUtils.random(-2.5f, 2.5f), 9f, MathUtils.random(-2.5f, 2.5f));
////        obj.body.setWorldTransform(obj.transform);
////
////        obj.body.setUserValue(instances.size);
////        obj.body.setCollisionFlags(obj.body.getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
////        instances.add(obj);
//////        collisionWorld.addCollisionObject(obj.body);
////        collisionWorld.addCollisionObject(obj.body, OBJECT_FLAG, GROUND_FLAG);
//
//        GameObject obj = constructors.values[1 + MathUtils.random(constructors.size - 2)].construct();
//        obj.transform.setFromEulerAngles(MathUtils.random(360f), MathUtils.random(360f), MathUtils.random(360f));
//        obj.transform.trn(MathUtils.random(-2.5f, 2.5f), 9f, MathUtils.random(-2.5f, 2.5f));
//        //obj.body.setWorldTransform(obj.transform);
//        obj.body.proceedToTransform(obj.transform);
//        obj.body.setUserValue(instances.size);
//        obj.body.setCollisionFlags(obj.body.getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
//        instances.add(obj);
////        dynamicsWorld.addRigidBody((btRigidBody)obj.body, OBJECT_FLAG, GROUND_FLAG);
//        dynamicsWorld.addRigidBody((btRigidBody)obj.body, OBJECT_FLAG, ALL_FLAG);
//        obj.body.setContactCallbackFlag(OBJECT_FLAG);
//        obj.body.setContactCallbackFilter(GROUND_FLAG);
//    }
//    boolean checkCollision0() {
//        CollisionObjectWrapper co0 = new CollisionObjectWrapper(ballObject);
//        CollisionObjectWrapper co1 = new CollisionObjectWrapper(groundObject);
//
//        btCollisionAlgorithmConstructionInfo ci = new btCollisionAlgorithmConstructionInfo();
//        ci.setDispatcher1(dispatcher);
//        btCollisionAlgorithm algorithm = new btSphereBoxCollisionAlgorithm(null, ci, co0.wrapper, co1.wrapper, false);
//
//        btDispatcherInfo info = new btDispatcherInfo();
//        btManifoldResult result = new btManifoldResult(co0.wrapper, co1.wrapper);
//
//        algorithm.processCollision(co0.wrapper, co1.wrapper, info, result);
//
//        boolean r = result.getPersistentManifold().getNumContacts() > 0;
//
//        result.dispose();
//        info.dispose();
//        algorithm.dispose();
//        ci.dispose();
//        co1.dispose();
//        co0.dispose();
//
//        return r;
//    }
//
//    boolean checkCollision(btCollisionObject obj0, btCollisionObject obj1) {
//        CollisionObjectWrapper co0 = new CollisionObjectWrapper(obj0);
//        CollisionObjectWrapper co1 = new CollisionObjectWrapper(obj1);
//
//        btPersistentManifold mf=dispatcher.getNewManifold(obj0, obj1);
//        btCollisionAlgorithm algorithm = dispatcher.findAlgorithm(co0.wrapper, co1.wrapper,mf,1);
//
//        btDispatcherInfo info = new btDispatcherInfo();
//        btManifoldResult result = new btManifoldResult(co0.wrapper, co1.wrapper);
//
//        algorithm.processCollision(co0.wrapper, co1.wrapper, info, result);
//
//        boolean r = result.getPersistentManifold().getNumContacts() > 0;
//
//        dispatcher.freeCollisionAlgorithm(algorithm.getCPointer());
//        result.dispose();
//        info.dispose();
//        co1.dispose();
//        co0.dispose();
//        mf.dispose();
//
//        return r;
//    }
    @Override
    public void dispose () {
//        if(null!=instances) {
//            for (GameObject obj : instances){
//                obj.dispose();}
//            instances.clear();
//        }

//        if(null!=constructors) {
//            for (GameObject.Constructor ctor : constructors.values()){
//                ctor.dispose();
//            }
//            constructors.clear();
//        }
//        if(null!=dispatcher){
//        dispatcher.dispose();}
//        if(null!=collisionConfig){
//        collisionConfig.dispose();}
//
//        contactListener.dispose();
//        if(null!=collisionWorld){collisionWorld.dispose();}
//        dynamicsWorld.dispose();
//        constraintSolver.dispose();
//        broadphase.dispose();

        if(null!=modelBatch){
        modelBatch.dispose();}
        if(null!=model){
        model.dispose();}

        if(null!=skin){
        skin.dispose();
        skin=null;
        }
    }

//    protected boolean isPaused=false;
    @Override public void pause () {
//        if(!isPaused) {
//            isPaused=true;
//        }
    }
    @Override public void resume () {
//        if(isPaused) {
//            isPaused=false;
////            if(null!=touchPAUSE&&touchPAUSE.isChecked()){
////                touchPAUSE.setChecked(false);
////            }
//            Gdx.input.setInputProcessor(mulProcess);
//        }
    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {

    }

//    @Override
//    public void render(float delta) {
//
//    }

    @Override public void resize (int width, int height) {}


    protected class TiledAStarInputProcessor extends InputAdapter {
        private BezierScreen test;
        private boolean dragging=false;
        private float touchSpace=10;

        public TiledAStarInputProcessor (BezierScreen test) {
            this.test = test;
        }

        @Override
        public boolean keyTyped (char character) {
//            switch (character) {
//                case 'm':
//                case 'M':
//                    test.checkMetrics.toggle();
//                    break;
//                case 'd':
//                case 'D':
//                    test.checkDiagonal.toggle();
//                    break;
//                case 's':
//                case 'S':
//                    test.checkSmooth.toggle();
//                    break;
//            }

            return true;
        }

        private Vector2 draggingPoint=null;
        protected BezierControlLine draggingLine=null;
        private String tag="TiledAStarInputProcessor";
        @Override
        public boolean touchDown (int screenX, int screenY, int pointer, int button) {
//            if(true){
//                return false;
//            }
            if(dragging){return true;}
           // Gdx.app.log(tag,"touchDown:");
            //Gdx.app.log(tag,"x:"+screenX+" y:"+screenY);

            for(BezierControlLine i: test.bezierControls){
                if(isInSpace(i.left,screenX,screenY)){
                    draggingPoint=i.left;
                    dragging=true;
                    return true;
                }else if(isInSpace(i.right,screenX,screenY)){
                    draggingPoint=i.right;
                    dragging=true;
                    return true;
                }else if(isInSpace(i.middle,screenX,screenY)){
//                    draggingPoint=i.middle;
                    draggingLine=i;
                    dragging=true;
                    return true;
                }
            }
            return true;
        }
        private boolean isInSpace(Vector2 pos,int x,int y){
            if(null==pos){return false;}
            float y2= getY2(y);
            return  x>=pos.x-touchSpace&&x<=pos.x+touchSpace
                    &&y2>=pos.y-touchSpace&&y2<=pos.y+touchSpace;
        }
        @Override
        public boolean touchUp (int screenX, int screenY, int pointer, int button) {
//            test.getCamera().unproject(test.tmpUnprojection.set(screenX, screenY, 0));
//            int tileX = (int)(test.tmpUnprojection.x / width);
//            int tileY = (int)(test.tmpUnprojection.y / width);
//            FlatTiledNode startNode = test.worldMap.getNode(tileX, tileY);
//            if (startNode.type == FlatTiledNode.TILE_FLOOR) {
//                test.startTileX = tileX;
//                test.startTileY = tileY;
//                test.updatePath(true);
//            }
            if(null!=draggingPoint){
                draggingPoint=null;
            }
            draggingLine=null;
            dragging=false;
            return true;
        }

        protected float getY2(float y){

            return ScreenSetting.WORLD_HEIGHT-y;
        }

        /**
         * 的手提电脑上，按着触屏板的鼠标左键时，不会进入此事件
         * @param screenX
         * @param screenY
         * @return
         */
        @Override
        public boolean mouseMoved (int screenX, int screenY) {
//            test.lastScreenX = screenX;
//            test.lastScreenY = screenY;
//            test.updatePath(false);

//            Gdx.app.log(tag,"mouseMoved:");
////            Gdx.app.log(tag,"x:"+screenX+" y:"+screenY);
////            Gdx.app.log(tag,"dragging:"+dragging);
////            Gdx.app.log(tag,"point:"+(null==draggingPoint?"null":draggingPoint.x));
//
//            if((!dragging)||null==draggingPoint){
//                return true;
//            }
//            draggingPoint.x=screenX;
//            draggingPoint.y=getY2(screenY);
            return true;
        }
    }
}
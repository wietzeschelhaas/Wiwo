package com.wgames.wiwo;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class CreateWorldObjects {


    private BodyDef bodyDef;


    public CreateWorldObjects(){
        bodyDef = new BodyDef();
    }

    public Body CreateCircle(World world,BodyType type,float radius,float posX,float posY) {
        bodyDef.type = type;

        bodyDef.position.set(posX,posY);
        CircleShape ballShape = new CircleShape();
        ballShape.setRadius(radius);

        Body ball = world.createBody(bodyDef);
        ball.createFixture(ballShape, 1);

        ballShape.dispose();
        return ball;
    }

    public Body CreateEdgeShape(World world,BodyType type, float x1, float y1, float x2, float y2) {
        EdgeShape groundShape = new EdgeShape();
        groundShape.set(x1, y1, x2, y2);

        bodyDef.type = type;
        Body ground = world.createBody(bodyDef);
        ground.createFixture(groundShape, 0);

        groundShape.dispose();

        return ground;
    }

    public Body CreateBox(World world, BodyType type, float xPos, float yPos, float width, float height) {

        bodyDef.type = type;
        bodyDef.position.set(xPos, yPos);
        Body box = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(width, height);
        box.createFixture(shape, 2.0f);
        shape.dispose();

        return box;
    }

}

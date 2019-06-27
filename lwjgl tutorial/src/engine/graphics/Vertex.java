package engine.graphics;

import engine.maths.Vectorf3f;

public class Vertex {
    private Vectorf3f position;
    
    public Vertex(Vectorf3f position){
       this.position=position;
    }

    public Vectorf3f getPosition() {
        return position;
    }
    
}

package engine.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import engine.graphics.Vertex;

/**
 * 
 * @author CodingAP, medioqrity
 * This class defines mesh, which is defined by
 * a lot of vertices
 */
public class Mesh {
    private Vertex[] vertices;
    private int[] indices;
    private int vao, // vertex array objects
                pbo, // position buffer objects?
                ibo; // indices buffer objects

    public Mesh(Vertex[] vertices, int[] indices) {
        // TODO Auto-generated constructor stub
        this.vertices = vertices;
        this.indices = indices;
    }
    
    public void create() {
        /**
         * why we can use an 'int' to bind array??
         */
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        /**
         * This part made the positionData readable by OpenGL, 
         * since OpenGL cannot read Vertex Objects ;)
         * 
         * By creating a float array "positionData" and
         * reading Vertex data into it, makes Vertex Objects data
         * readable by OpenGL: It can only read array of floats!
         */
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] positionData = new float[vertices.length * 3];
        for (int i = 0; i < vertices.length; ++i) {
            positionData[i * 3] = vertices[i].getPosition().getX();
            positionData[i * 3 + 1] = vertices[i].getPosition().getY();
            positionData[i * 3 + 2] = vertices[i].getPosition().getZ();
        }
        
        positionBuffer.put(positionData).flip(); // flip since that's how OpenGL reads the data
        
        /**
         * After reading data into buffer, the next step
         * is to connect the buffer with OpenGL.
         * How on earth should I try to understand this??
         */
        pbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, pbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // bind to 0 means unbind
        
        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        
        ibo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public void setVertices(Vertex[] vertices) {
        this.vertices = vertices;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    public int getVAO() {
        return vao;
    }

    public void setVAO(int vao) {
        this.vao = vao;
    }

    public int getPBO() {
        return pbo;
    }

    public void setPBO(int pbo) {
        this.pbo = pbo;
    }

    public int getIBO() {
        return ibo;
    }

    public void setIBO(int ibo) {
        this.ibo = ibo;
    }
}

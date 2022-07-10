package com.app.planner;

import java.util.*;

public class UndoRedoStack<E> extends Stack<E> {
    private Stack undoStack;
    private Stack redoStack;

    public UndoRedoStack() {
        undoStack = new Stack();
        redoStack = new Stack();
    }

    @Override
    public String toString() {
        return "UndoRedoStack{" +
                "undoStack=" + undoStack +
                ", redoStack=" + redoStack +
                '}';
    }

    public boolean canUndo() {
        return undoStack.size() >= 2;
    }

    public void addScreen(E value) {
        undoStack.push(value);
    }

    public E undo() {
        if (!canUndo()) {
            throw new IllegalStateException();
        }
        E value = (E) undoStack.pop();
        redoStack.push(value); // pop the latest screen to the redo stack
        return (E) undoStack.pop(); // pop the previous screen which will be the one we go to
    }

    // post: returns whether or not a redo can be done
    public boolean canRedo() {
        return !redoStack.isEmpty();
    }

    // pre : canRedo() (throws IllegalStateException if not)
    // post: redoes the last undone operation
    public E redo() {
        if (!canRedo()) {
            throw new IllegalStateException();
        }
        E value = (E) redoStack.pop();

        //undoStack.push(value); // pop from the redo stack and push it to the undo stack
        return value;
    }
}
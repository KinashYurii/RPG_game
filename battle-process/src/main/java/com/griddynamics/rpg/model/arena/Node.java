package com.griddynamics.rpg.model.arena;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Node {
    private int id;
    private int level;
    private boolean isFinish;
    private List<Node> ways;
}

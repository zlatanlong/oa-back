package cn.lcl.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TagTreeNode {

    private String title;

    private Integer key;

    private List<TagTreeNode> children;

    public void addChild(TagTreeNode tagTreeNode) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(tagTreeNode);
    }
}

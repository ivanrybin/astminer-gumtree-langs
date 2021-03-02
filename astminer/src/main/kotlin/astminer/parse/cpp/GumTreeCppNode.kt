package astminer.parse.cpp

import com.github.gumtreediff.tree.ITree
import com.github.gumtreediff.tree.TreeContext
import astminer.common.model.Node

class GumTreeCppNode(val wrappedNode: ITree, val context: TreeContext, val parent: GumTreeCppNode?) : Node {
    private val metadata: MutableMap<String, Any> = HashMap()

    override fun getMetadata(key: String): Any? {
        return metadata[key]
    }

    override fun setMetadata(key: String, value: Any) {
        metadata[key] = value
    }

    override fun isLeaf(): Boolean {
        return childrenList.isEmpty()
    }

    private val childrenList: MutableList<GumTreeCppNode> by lazy {
        wrappedNode.children.map { GumTreeCppNode(it, context, this) }.toMutableList()
    }

    override fun getTypeLabel(): String {
        return context.getTypeLabel(wrappedNode)
    }

    override fun getChildren(): List<Node> {
        return childrenList
    }

    override fun getParent(): Node? {
        return parent
    }

    override fun getToken(): String {
        return wrappedNode.label
    }

    override fun removeChildrenOfType(typeLabel: String) {
        childrenList.removeIf { it.getTypeLabel() == typeLabel }
    }

}